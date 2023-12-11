package main;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Sistema {

    private Inventario inventarioTotal;
    private HashMap<Integer, Alquiler> historial;
    private HashMap<String, Sede> sedesTotales;
    private HashMap<String, Usuario> usuarios;
    private HashMap<Integer, Pago> pagos;
    private HashMap<String, Integer> tiposVehiculo;




    private HashMap<String, Integer> seguros;
    private HashMap<String, Object[]> tarifas;
    static private int recargoPorEntregarEnOtraSede = 500;

    static String pathUsuarios ;
    static String pathSedes ;
    static String pathAutos ;
    static String pathSeguros ;
    static String pathTarifas ;
    static String pathAlquileres ;
    static String pathPagos;
    static String pathTiposVehiculo = "Entrega 3\\AlquilerYReservas\\Base de Datos\\TiposVehiculo.txt";


    public Sistema(ArrayList<String> paths) {
        this.tarifas = new HashMap<>();
        this.seguros = new HashMap<>();
        this.usuarios = new HashMap<>();
        this.inventarioTotal = new Inventario();
        this.historial = new HashMap<>();
        this.sedesTotales = new HashMap<>();
        this.pagos = new HashMap<>();
        inicializarPaths(paths);
        leerYGuardarDatos();
    }

    public Sistema(ArrayList<String> paths, Boolean test){
        this.tarifas = new HashMap<>();
        this.seguros = new HashMap<>();
        this.usuarios = new HashMap<>();
        this.inventarioTotal = new Inventario();
        this.historial = new HashMap<>();
        this.sedesTotales = new HashMap<>();
        this.pagos = new HashMap<>();
        inicializarPaths(paths);
    }

    private void inicializarPaths(ArrayList<String> paths) {
        pathUsuarios = paths.get(0);
        pathSedes = paths.get(1);
        pathAutos = paths.get(2);
        pathSeguros = paths.get(3);
        pathTarifas = paths.get(4);
        pathAlquileres = paths.get(5);
        pathPagos = paths.get(6);
    }

    String[] haySuficientesAutos(String categoria, LocalDateTime fechaInicial, LocalDateTime fechaFinal) {
        String[] respuesta = new String[2];
        respuesta[0] = "";
        respuesta[1] = "";

        for (Sede sede : sedesTotales.values()) {

            String nombre = sede.getNombre();

            Inventario inventario = sedesTotales.get(nombre).getInventario();

            if (!inventario.getArrayCategorias().contains(categoria)) {
                continue;
            }

            ArrayList<Auto> autos = inventario.getAutosPorCategoria(categoria);

            for (Auto auto : autos) {
                boolean breakOutLoop = false;
                if (auto.isMantenimiento() || auto.isLimpieza()) {
                    continue;
                } else if (auto.isDisponible()) {
                    respuesta[0] = nombre;
                    respuesta[1] = auto.getPlaca();
                    return respuesta;
                } else {
                    for (Alquiler alquiler : auto.getHistorialDeAlquileres()) {
                        LocalDateTime fechaInicialReserva = alquiler.getFechaInicio();
                        LocalDateTime fechaFinalReserva = alquiler.getFechaFinal();

                        if (!(fechaFinal.isBefore(fechaInicialReserva) || fechaInicial.isAfter(fechaFinalReserva))) {
                            breakOutLoop = true;
                            break;
                        }
                    }
                    if (breakOutLoop) {
                        break;
                    }
                    respuesta[0] = nombre;
                    respuesta[1] = auto.getPlaca();
                    return respuesta;
                }
            }

        }
        return respuesta;
    }

    void leerYGuardarDatos() {
        try {
            cargarUsuarios(pathUsuarios);
            cargarSedes(pathSedes);
            cargarAlquileres(pathAlquileres);
            cargarAutos(pathAutos);
            cargarTarifas(pathTarifas);
            cargarSeguros(pathSeguros);
            cargarPagos(pathPagos);
            cargarTiposVehiculo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // FUNCIONES PARA CARGAR Y GUARDAR DATOS

    public void cargarSeguros(String nombreArchivo) throws NumberFormatException, IOException, Exception{
        BufferedReader br = new BufferedReader(new FileReader(nombreArchivo));
        String linea = br.readLine();
        while (linea != null) {
            String[] partes = linea.split(",");
            seguros.put(partes[0], Integer.parseInt(partes[1]));
            linea = br.readLine();
        }
        br.close();
        
    }

    void guardarSeguros() {
        String nombreArchivo = pathSeguros;
        try (BufferedWriter br = new BufferedWriter(new FileWriter(nombreArchivo))) {
            for (Map.Entry<String, Integer> entry : seguros.entrySet()) {
                String nombre = entry.getKey();
                Integer precio = entry.getValue();
                br.write(nombre + "," + precio);
                br.newLine();
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarTarifas(String nombreArchivo)  throws NumberFormatException, IOException, Exception {
       
        BufferedReader br = new BufferedReader(new FileReader(nombreArchivo));
        String linea = br.readLine();
        while (linea != null) {
            String[] partes = linea.split(",");
            Object[] datosTarifa = new Object[3];
            datosTarifa[0] = Integer.parseInt(partes[1]);
            datosTarifa[1] = stringToDateTime(partes[2]);
            datosTarifa[2] = stringToDateTime(partes[3]);
            tarifas.put(partes[0], datosTarifa);
            linea = br.readLine();
        }
        br.close();
       
    }

    void guardarTarifas() {
        String nombreArchivo = pathTarifas;
        try (BufferedWriter br = new BufferedWriter(new FileWriter(nombreArchivo))) {
            for (Map.Entry<String, Object[]> entry : tarifas.entrySet()) {
                String nombre = entry.getKey();
                Integer precio = (Integer) entry.getValue()[0];
                LocalDateTime fecha1 = (LocalDateTime) entry.getValue()[1];
                LocalDateTime fecha2 = (LocalDateTime) entry.getValue()[2];

                br.write(nombre + "," + precio + "," +
                        dateTimeToString(fecha1) + "," + dateTimeToString(fecha2));
                br.newLine();
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarUsuarios(String nombreArchivo) throws NumberFormatException, IOException, Exception {
        BufferedReader br = new BufferedReader(new FileReader(nombreArchivo));
        String linea = br.readLine();
        while (linea != null) {
            String[] partes = linea.split(",");
            if (partes.length == 4) {
                Empleado empleado = new Empleado(partes[0], partes[1], partes[2], partes[3]);
                usuarios.put(empleado.getLogin(), empleado);
            } else {
                Cliente cliente = new Cliente(partes[0], partes[1], partes[2], Integer.parseInt(partes[3]),
                        partes[4], new File(partes[5]), Integer.parseInt(partes[6]), partes[7],
                        stringToDateTime(partes[8]),
                        new File(partes[9]), partes[10], Integer.parseInt(partes[11]), stringToDateTime(partes[12]),
                        Integer.parseInt(partes[13]));

                usuarios.put(cliente.getLogin(), cliente);
            }
            linea = br.readLine();
        }
        br.close();
       
    }

    void guardarUsuarios() {
        String nombreArchivo = pathUsuarios;
        try (BufferedWriter br = new BufferedWriter(new FileWriter(nombreArchivo))) {
            for (Usuario usuario : usuarios.values()) {

                if (usuario instanceof Cliente) {
                    Cliente cliente = (Cliente) usuario;
                    LicenciaConduccion licencia = cliente.getLicencia();
                    MedioDePago tarjeta = cliente.getTarjetaCredito();
                    br.write(cliente.getLogin() + "," + cliente.getPassword() + "," +
                            cliente.getNombre() + "," + cliente.getNumeroCelular() + "," +
                            cliente.getNacionalidad() + "," + cliente.getCedulaFoto().getPath()
                            + "," + licencia.getLicenciaID() + "," + licencia.getLicenciaPaisExpedicion()
                            + "," + dateTimeToString(licencia.getLicenciaFechaVen()) + "," +
                            licencia.getLicenciaFoto().getPath() + "," + tarjeta.getTipo() + "," +
                            tarjeta.getNumeroTarjeta() + "," + dateTimeToString(tarjeta.getFechaVencimiento())
                            + "," + tarjeta.getCodigoSeguridad()

                    );
                    br.newLine();

                } else {
                    Empleado empleado = (Empleado) usuario;
                    br.write(empleado.getLogin() + "," + empleado.getPassword()
                            + "," + empleado.getAutoridad() + "," + empleado.getSede());
                    br.newLine();
                }

            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarSedes(String nombreArchivo) throws NumberFormatException, IOException, Exception{
        BufferedReader br = new BufferedReader(new FileReader(nombreArchivo));
        String linea = br.readLine();
        while (linea != null) {
            String[] partes = linea.split(",");
            String[] horarios = new String[2];
            horarios[0] = partes[2].split("-")[0];
            horarios[1] = partes[2].split("-")[1];

            Sede sede = new Sede(partes[0], partes[1], horarios, (Empleado) getUsuario(partes[3]),
                    crearInventarioParaSede(partes[0]),
                    crearHashMapEmpleados(partes[4].split("-")));
            sedesTotales.put(sede.getNombre(), sede);
            linea = br.readLine();
        }
        br.close();
        
    }

    void guardarSedes() {
        String nombreArchivo = pathSedes;
        try (BufferedWriter br = new BufferedWriter(new FileWriter(nombreArchivo))) {
            for (Sede sede : sedesTotales.values()) {

                br.write(sede.getNombre() + "," + sede.getUbicacion() + "," +
                        sede.getHorarios()[0] + "-" + sede.getHorarios()[1] + "," +
                        sede.getAdminLocal().getLogin() + "," + crearListaEmpleados(sede.getEmpleados())

                );
                br.newLine();

            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarAlquileres(String nombreArchivo) throws NumberFormatException, IOException, Exception{
        BufferedReader br = new BufferedReader(new FileReader(nombreArchivo));
        String linea = br.readLine();
        while (linea != null) {
            String[] partes = linea.split(",");
            Alquiler alquiler = new Alquiler((Cliente) usuarios.get(partes[0]), partes[1],
                    sedesTotales.get(partes[2]), sedesTotales.get(partes[3]),
                    stringToDateTime(partes[4]), stringToDateTime(partes[5]), Integer.parseInt(partes[6]),
                    new File(partes[7]), crearArrayListDeConductores(partes[8]), partes[9] == "true",Double.parseDouble(partes[10]));
            historial.put(Integer.parseInt(partes[6]), alquiler);
            linea = br.readLine();
        }
        br.close();
        
    }

    void guardarAlquileres() {
        String nombreArchivo = pathAlquileres;
        ArrayList<Alquiler> alquileres = new ArrayList<>(historial.values());
    
        try (BufferedWriter br = new BufferedWriter(new FileWriter(nombreArchivo))) {  
            for (Alquiler alquiler : alquileres) {

                String login = alquiler.getCliente().getLogin();
                String estado = alquiler.getEstado();
                String sedeInicio = alquiler.getSedeInicio().getNombre();
                String sedeFinal = alquiler.getSedeFinal().getNombre();
                String fechaInicio = dateTimeToString(alquiler.getFechaInicio());
                String fechaFinal = dateTimeToString(alquiler.getFechaFinal());
                String idAlquiler = alquiler.getIdAlquiler() + "";
                String factura = alquiler.getFactura().getPath();
                String conductores = "[]";
                if (alquiler.getConductoresAdicionales() != null){
                    if (alquiler.getConductoresAdicionales().size() > 0){
                        conductores = crearTextListDeConductores(alquiler.getConductoresAdicionales());
                    }
                }
                String bloqueoTarjeta = alquiler.isBloqueoTarjeta() + "";
                String monto = alquiler.getMonto() + "";

                String texto = login + "," + estado + "," + sedeInicio + "," + sedeFinal + "," + fechaInicio + "," + fechaFinal + "," + idAlquiler 
                                + "," + factura + "," + conductores + "," + bloqueoTarjeta + "," + monto;

                br.write(texto);
                br.newLine();
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarAutos(String nombreArchivo) throws NumberFormatException, IOException, Exception{
        BufferedReader br = new BufferedReader(new FileReader(nombreArchivo));
        String linea = br.readLine();
        while (linea != null) {
            String[] partes = linea.split(",");

            Auto auto;

            if (partes[6].equals("null")) {
                auto = new Auto(partes[0], partes[1], partes[2], partes[3], partes[4],
                        partes[5], // sin alquiler
                        sedesTotales.get(partes[7]), partes[8].equals("true"), partes[9].equals("true"),
                        partes[10].equals("true"), stringToDateTime(partes[11]),
                        crearArrayListaDeAlquileres(partes[12]), partes[13]);
            }
            else {
                auto = new Auto(partes[0], partes[1], partes[2], partes[3], partes[4],
                        partes[5], historial.get(Integer.parseInt(partes[6])),
                        sedesTotales.get(partes[7]), partes[8].equals("true"), partes[9].equals("true"),
                        partes[10].equals("true"), stringToDateTime(partes[11]),
                        crearArrayListaDeAlquileres(partes[12]), partes[13]);
            }

            inventarioTotal.addAuto(auto);
            Inventario inventarioPorSede = sedesTotales.get(partes[7]).getInventario();
            inventarioPorSede.addAuto(auto);
            linea = br.readLine();
        }
        br.close();
        
    }

    void guardarAutos() {
        String nombreArchivo = pathAutos;
        ArrayList<Auto> autos = new ArrayList<>(inventarioTotal.getAutos().values());
        try (BufferedWriter br = new BufferedWriter(new FileWriter(nombreArchivo))) {
            for (Auto auto : autos) {
               
                String categoria = auto.getCategoria();
                String placa = auto.getPlaca();
                String marca = auto.getMarca();
                String modelo = auto.getModelo();
                String color = auto.getColor();
                String transmision = auto.getTransmision();
                String alquilado = "null";
                if (auto.getAlquilado() != null) {
                    alquilado = auto.getAlquilado().getIdAlquiler() + "";
                } 
                String sedeActual = auto.getSedeActual().getNombre();
                String mantenimiento = auto.isMantenimiento() + "";
                String limpieza = auto.isLimpieza() + "";
                String disponible = auto.isDisponible() + "";
                String fechaEstimadaDisponible = dateTimeToString(auto.getFechaEstimadaDisponible());
                String listaAlquileres = "nuncaAlquilado";
                if (auto.getHistorialDeAlquileres() != null){
                    listaAlquileres = crearTextListDeAlquileres(auto.getHistorialDeAlquileres());
                }
                String tipoVehiculo = auto.getTipoVehiculo();
                String texto =  categoria + "," + placa + "," + marca + "," + modelo + "," + color + "," + transmision + "," + alquilado + "," + sedeActual + "," + mantenimiento + "," + limpieza + "," + disponible + "," + fechaEstimadaDisponible + "," + listaAlquileres + "," + tipoVehiculo ;
                br.write(texto);
                br.newLine();

            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // FUNCIONES AUXILIARES PARA LEER Y GUARDAR DATOS
    HashMap<String, Empleado> crearHashMapEmpleados(String[] empleadosText) {
        HashMap<String, Empleado> empleados = new HashMap<>();
        for (String login : empleadosText) {
            empleados.put(login, (Empleado) getUsuario(login));
        }
        return empleados;
    }

    String crearListaEmpleados(HashMap<String, Empleado> empleados) {
        String list = "";
        for (Empleado empleado : empleados.values()) {

            list += empleado.getLogin() + "-";
        }
        return list;
    }

    Inventario crearInventarioParaSede(String sede) {
        Inventario inventario = new Inventario();

        HashMap<String, HashMap<String, Auto>> categorias = inventario.getCategorias();

        for (String categoria : inventarioTotal.getArrayCategorias()) {
            HashMap<String, Auto> autosPorCategoria = new HashMap<>();
            categorias.put(categoria, autosPorCategoria);
        }

        HashMap<String, Auto> autosTotales = inventario.getAutos();

        for (Auto auto : inventarioTotal.getAutos().values()) {
            if (auto.getSedeActual().getNombre().equals(sede)) {
                autosTotales.put(auto.getPlaca(), auto);
                categorias.get(auto.getCategoria()).put(auto.getPlaca(), auto);
            }
        }

        return inventario;

    }

    ArrayList<LicenciaConduccion> crearArrayListDeConductores(String conductores) {
        ArrayList<LicenciaConduccion> conductoresAdicionales = new ArrayList<>();
        if (conductores.equals("[]")) {
            return conductoresAdicionales;
        }
        String[] licencias = conductores.split(";");
        for (String licenciaText : licencias) {
            System.out.println(licenciaText);
            String[] partes = licenciaText.split("_");
            LicenciaConduccion licencia = new LicenciaConduccion(Integer.parseInt(partes[0]), partes[1],
                    stringToDateTime(partes[2]), new File(partes[3]));
            conductoresAdicionales.add(licencia);
        }
        return conductoresAdicionales;
    }

    String crearTextListDeConductores(ArrayList<LicenciaConduccion> conductores) {
        String list = "";
        for (LicenciaConduccion licencia : conductores) {
            String miniList = licencia.getLicenciaID() + "_" + licencia.getLicenciaPaisExpedicion() + "_" +
                    dateTimeToString(licencia.getLicenciaFechaVen()) + "_" + licencia.getLicenciaFoto().getPath() + "_";
            list += miniList + ";";
        }
        return list;
    }

    ArrayList<Alquiler> crearArrayListaDeAlquileres(String alquileresTexto) {

        ArrayList<Alquiler> alquileres = new ArrayList<>();
        if (alquileresTexto.equals("nuncaAlquilado")) {
            return alquileres;
        }
        String[] partes = alquileresTexto.split("-");
        for (String idAlquiler : partes) {
            Alquiler alquiler = historial.get(Integer.parseInt(idAlquiler));
            alquileres.add(alquiler);
        }
        return alquileres;
    }

    String crearTextListDeAlquileres(ArrayList<Alquiler> alquileres) {
        String list = "";
        for (Alquiler alquiler : alquileres) {
            String id = alquiler.getIdAlquiler() + "";
            list += id + "-";
        }
        if (list.equals("")) {
            return "nuncaAlquilado";
        }
        return list;
    }

    LocalDateTime stringToDateTime(String fechaTexto) {

        if (fechaTexto.equals("sinFecha")) {
            return LocalDateTime.now();
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        LocalDateTime fecha = LocalDateTime.parse(fechaTexto, formatter);

        return fecha;

    }

    static String dateTimeToString(LocalDateTime fecha) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return fecha.format(formatter);

    }

    public boolean existeUsuario(String login) {
        return usuarios.get(login) != null;
    }

    // OTRAS FUNCIONES
    public void agregarUsuario(Usuario usuario) {
        usuarios.put(usuario.getLogin(), usuario);
        guardarUsuarios();
    }

    public Usuario getUsuario(String login) {
        if (usuarios.get(login) != null){
            return usuarios.get(login);
        }
        else{
            return null;
        }
    }

    public Cliente getCliente(String login) {
        if (usuarios.get(login).getClass() != Cliente.class) {
            return null;
        }
        else if (usuarios.get(login) == null){
            return null;
        }
        else {
            return (Cliente) usuarios.get(login);
        }
  
    }

    public Inventario getInventario() {
        return this.inventarioTotal;
    }

    public HashMap<String, Usuario> getUsuarios() {
        return this.usuarios;
    }

    public HashMap<String, Sede> getSedes() {
        return this.sedesTotales;
    }

    public ArrayList<String> getNombresSedes() {
        return new ArrayList(sedesTotales.keySet());
    }

    public int getRecargo() {
        return this.recargoPorEntregarEnOtraSede;
    }

    public HashMap<String, Object[]> getTarifas() {
        return this.tarifas;
    }

    public HashMap<String, Integer> getSeguros() {
        return this.seguros;
    }

    public Alquiler getAlquiler(int idAlquiler) {
        return historial.get(idAlquiler);
    }

    public HashMap<Integer, Alquiler> getHistorial(){
        return this.historial;
    }

    public void agregarAuto(Auto auto) {
        inventarioTotal.addAuto(auto);
        Inventario inventarioSede = sedesTotales.get(auto.getSedeActual().getNombre()).getInventario();
        inventarioSede.addAuto(auto);
        guardarAutos();
    }

    public void eliminarAuto(Auto auto) {
        inventarioTotal.eliminarAuto(auto);
        String sede = auto.getSedeActual().getNombre();
        sedesTotales.get(sede).getInventario().eliminarAuto(auto);
        guardarAutos();
    }

    public void agregarSeguro(String nombre, int valor) {
        seguros.put(nombre, valor);
        guardarSeguros();
    }

    public void agregarTarifa(String nombre, int valor,
            LocalDateTime fechaInicial, LocalDateTime fechaFinal) {
        Object[] datosTarifa = new Object[3];
        datosTarifa[0] = valor;
        datosTarifa[1] = fechaInicial;
        datosTarifa[2] = fechaFinal;
        tarifas.put(nombre, datosTarifa);
        guardarTarifas();
    }

    public int getPrecioCategoria(String categoria) {
        return (Integer) tarifas.get(categoria)[0];
    }

    public int getNewAlquilerId() {
        return historial.size();
    }

    public void agregarAlquiler(Alquiler alquiler, String placa) {
        historial.put(alquiler.getIdAlquiler(), alquiler);
        Auto auto = inventarioTotal.getAuto(placa);
        auto.setDisponibilidad(false);
        auto.agregarAlquiler(alquiler);
        guardarAlquileres();

    }

    public void guardarPago(Pago pago){
        if(pagos.containsKey(pago.getIdPago())){
            System.out.println("El pago ya existe");
        }
        else{
            pagos.put(pago.getIdPago(), pago);
            try (BufferedWriter br = new BufferedWriter(new FileWriter(pathPagos, true))) {
                br.write(pago.getMonto() + "," + dateTimeToString(pago.getFechaPago()) + "," + pago.getMetodoPago() + "," + pago.getAlquilerAsociado() + "," + pago.getIdPago() + "," + pago.getNumeroTarjeta());
                br.newLine();
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
    }

    public void cargarPagos(String path) throws NumberFormatException, IOException, Exception{
        BufferedReader br = new BufferedReader(new FileReader(path));
        String linea = br.readLine();
        while (linea != null) {
            String[] partes = linea.split(",");
            double monto = Double.parseDouble(partes[0]);
            LocalDateTime fechaPago = stringToDateTime(partes[1]);
            String metodoPago = partes[2];
            int alquilerAsociado = Integer.parseInt(partes[3]);
            int idPago = Integer.parseInt(partes[4]);
            int numeroTarjeta = Integer.parseInt(partes[5]);
            Pago pago = new Pago(monto, fechaPago, metodoPago, alquilerAsociado, idPago, numeroTarjeta);
            pagos.put(idPago, pago);
            linea = br.readLine();
        }
        br.close();
       
    }

    public int darNuevoIdPago(){
        return pagos.size()+1;
    }

    public Pago getPago(int idPago){
        return pagos.get(idPago);
    }

    public HashMap<Integer, Pago> getPagos(){
        return pagos;
    }

    public ArrayList<Pago> getPagosPorIdAlquiler(int idAlquiler){
        ArrayList<Pago> pagosPorAlquiler = new ArrayList<>();
        for (Pago pago : pagos.values()) {
            if (pago.getAlquilerAsociado() == idAlquiler){
                pagosPorAlquiler.add(pago);
            }
        }
        return pagosPorAlquiler;
    }

    public ArrayList<String> darPasarelasPago(){

        ArrayList<String> pasarelas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("Entrega 3\\AlquilerYReservas\\conf\\pasarelas.txt"))) {
            String linea;

            // Lee cada línea del archivo hasta llegar al final
            while ((linea = br.readLine()) != null) {
                // Procesa la línea como desees
                String[] partes = linea.split("\\.");
                pasarelas.add(partes[2]);
            }
            return pasarelas;
        } catch (IOException e) {
            // Manejo de excepciones en caso de error de lectura
            e.getMessage();
            return null;
        }
    }

    public void cargarTiposVehiculo(){
        tiposVehiculo = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(pathTiposVehiculo))) {
            String linea = br.readLine();
            while (linea != null) {
                String[] partes = linea.split(",");
                tiposVehiculo.put(partes[0], Integer.parseInt(partes[1]));
                linea = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPrimaAdicional(String tipoVehiculo){
        return tiposVehiculo.get(tipoVehiculo);
    }

    public HashMap<String, Integer> getTiposVehiculo(){
        return tiposVehiculo;
    }

    public ArrayList<String> getTiposVehiculoStrings(){
        ArrayList<String> tiposVehiculoStrings = new ArrayList<>();
        for (String tipoVehiculo : tiposVehiculo.keySet()) {
            tiposVehiculoStrings.add(tipoVehiculo);
        }
        return tiposVehiculoStrings;
    }
}
