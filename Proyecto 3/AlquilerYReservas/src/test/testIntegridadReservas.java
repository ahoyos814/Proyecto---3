package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.io.File;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import main.*;

public class testIntegridadReservas {

    /**
     * El test sigue la misma implementación de el metodo hacerReserva de Consola_Cliente de manera automatica.  
     * 
     * Se generan algunos escenarios de prueba 
     */

    Sistema sistema;
    Sistema sistemaPrueba;
    ArrayList<Object> escenarioDefecto = new ArrayList<Object>();
    ArrayList<String> paths = new ArrayList<String>();


    public Alquiler hacerReserva(ArrayList<Object> escenario)
                            throws Exception {

        Cliente cliente = (Cliente) escenario.get(0);
        System.out.println("Cliente inicializado: " + cliente.getLogin());
        Sede sedeInicio = (Sede) escenario.get(1);
        System.out.println("Sede inicial inicializada: " + sedeInicio.getNombre());
        Sede sedeFin = (Sede) escenario.get(2);
        System.out.println("Sede final inicializada: " + sedeFin.getNombre());
        LocalDateTime fechaInicio = (LocalDateTime) escenario.get(3);
        System.out.println("Fecha inicial inicializada: " + fechaInicio.toString());
        LocalDateTime fechaFin = (LocalDateTime) escenario.get(4);
        System.out.println("Fecha final inicializada: " + fechaFin.toString());
        ArrayList<LicenciaConduccion> conductoresAdicionales = (ArrayList<LicenciaConduccion>) escenario.get(5);
        System.out.println("Conductores adicionales inicializados: " + "null");
        String categoria = (String) escenario.get(6);
        System.out.println("Categoria inicializada: " + categoria);
        int tarifaCategoria = (int) escenario.get(7);
        System.out.println("Tarifa categoria inicializada: " + tarifaCategoria);
        String Seguro = (String) escenario.get(8);
        System.out.println("Seguro inicializado: " + Seguro);
        int tarifaSeguro = (int) escenario.get(9);
        System.out.println("Tarifa seguro inicializada: " + tarifaSeguro);
        int recargo = (int) escenario.get(10);
        System.out.println("Recargo inicializado: " + recargo);
        int idAlquiler = (int) escenario.get(11);
        System.out.println("Id alquiler inicializado: " + idAlquiler + "\n");

        if (sedeInicio == null || sedeFin == null || fechaInicio == null || fechaFin == null  || categoria == null || Seguro == null) {
            throw new Exception("No se puede hacer la reserva, faltan datos");
        }
        Boolean clienteVerificado = sistema.getUsuarios().containsKey(cliente.getLogin());
        System.out.println( "Cliente verificado:" + clienteVerificado );
        if (!clienteVerificado){
            throw new Exception("Error-Client");
        }


        int fechasVerificadas = verificarFechas(toDate(fechaInicio), toDate(fechaFin));
        System.out.println(fechasVerificadas == 0 ? "Fechas verificadas "+ fechasVerificadas : "Fechas no verificadas" + fechasVerificadas);
        if (fechasVerificadas == 1 || fechasVerificadas == 2){
            throw new Exception("Error-Fechas");
        }

        Boolean seguroVerificado = sistema.getSeguros().containsKey(Seguro);
        System.out.println("Seguro verificado: " + seguroVerificado);
        if (!seguroVerificado){
            throw new Exception("Error-Seguro");
        }

        Boolean seguroTarifaVerificado = ((Integer) sistema.getSeguros().get(Seguro)) == tarifaSeguro;
        System.out.println("Seguro tarifa verificado: " + seguroTarifaVerificado);
        if(!seguroTarifaVerificado){
            throw new Exception("Error-SeguroTarifa");
        }
        
        Boolean categoriaVerificado = sistema.getInventario().getArrayCategorias().contains(categoria);
        System.out.println("Categoria verificada: " + categoriaVerificado);
        if (!categoriaVerificado){
            throw new Exception("Error-Categoria");
        }
        Boolean categoriaTarifaVerificado = (Integer) sistema.getTarifas().get(categoria)[0] == tarifaCategoria;
        System.out.println("Categoria tarifa verificada: " + categoriaTarifaVerificado);
        if (!categoriaTarifaVerificado){
            throw new Exception("Error-CategoriaTarifa");
        }
        Boolean existeSedeInicial = sistema.getSedes().containsKey(sedeInicio.getNombre());
        System.out.println("Sede inicial verificada: " + existeSedeInicial);
        if(!existeSedeInicial){
            throw new Exception("Error-SedeInicial");
        }
        Boolean existeSedeFinal = sistema.getSedes().containsKey(sedeFin.getNombre());
        System.out.println("Sede final verificada: " + existeSedeFinal + "\n");
        if(!existeSedeFinal){
            throw new Exception("Error-SedeFinal");
        }



        int tarifaTotal = tarifaCategoria + tarifaSeguro + recargo;
        double costoTotal = darDias(fechaInicio, fechaFin) * tarifaTotal;

        File factura = Alquiler.generarYGuardarFactura(fechaInicio, fechaFin, categoria, tarifaCategoria, Seguro, tarifaSeguro, tarifaSeguro, recargo, tarifaTotal, cliente, categoria, Seguro);
        Alquiler alquiler = new Alquiler(cliente,"Activo",sedeInicio,sedeFin, fechaInicio,fechaFin,idAlquiler, factura, conductoresAdicionales, false, costoTotal);

        sistema.agregarAlquiler(alquiler, Seguro);

        return alquiler;
        
                    

    }

    @Before
    public void inicializarEscenarioDefecto(){

        System.out.println("\n Reiniciando datos \n");
        escenarioDefecto.clear();
        paths.clear();

        paths.add( "Entrega 3\\AlquilerYReservas\\Base de Datos\\test_data\\good_data\\Usuarios.txt");
        paths.add( "Entrega 3\\AlquilerYReservas\\Base de Datos\\test_data\\good_data\\Sedes.txt");
        paths.add( "Entrega 3\\AlquilerYReservas\\Base de Datos\\test_data\\good_data\\Autos.txt");
        paths.add( "Entrega 3\\AlquilerYReservas\\Base de Datos\\test_data\\good_data\\Seguros.txt");
        paths.add( "Entrega 3\\AlquilerYReservas\\Base de Datos\\test_data\\good_data\\Tarifas.txt");
        paths.add( "Entrega 3\\AlquilerYReservas\\Base de Datos\\test_data\\good_data\\Alquileres.txt");
        paths.add( "Entrega 3\\AlquilerYReservas\\Base de Datos\\test_data\\good_data\\Pagos.txt");

        sistema = new Sistema(paths);

        Cliente cliente = new Cliente("uwu", "uwu");
        String[] horariosSede1 = {"8", "11"};

        Empleado empleado1 = new Empleado("lisa", "blackpink","empleado","Sede_Bogota");
        Empleado empleado2 = new Empleado("rose", "blackpink","empleado","Sede_Bogota");
        HashMap<String, Empleado> empleadosSede1 = new HashMap<String, Empleado>();
        empleadosSede1.put(empleado1.getLogin(), empleado1);
        empleadosSede1.put(empleado2.getLogin(), empleado2);
        
        Sede sede1 = new Sede ("Sede_Chia","direccion",horariosSede1,empleado2,null,empleadosSede1);
        
        Inventario inventario = new Inventario();

        inventario.addAuto(new Auto("lujo","AUU-123","Lamborghini","Veneno","negro","automatico",sede1,true,true,true,StringToLocalDateTime("2023-12-08 13:28"),null,"Automovil"));
        inventario.addAuto(new Auto("lujo","AUU-123","Lamborghini","Veneno","negro","automatico",sede1,true,true,true,StringToLocalDateTime("2023-10-08 13:28"),null,"Automovil"));
        inventario.addAuto(new Auto("lujo","AUU-123","Lamborghini","Veneno","negro","automatico",sede1,true,true,true,StringToLocalDateTime("2023-10-08 13:28"),null,"Automovil"));


        Sede sedeInicio = new Sede ("Sede_Chia","direccion",horariosSede1,empleado2,null,empleadosSede1);;
        Sede sedeFinal = new Sede ("Sede_Chia","direccion",horariosSede1,empleado2,null,empleadosSede1);;

        LocalDateTime fechaInicio = LocalDateTime.of(2023, 12, 30, 0, 0);
        LocalDateTime fechaFinal = LocalDateTime.of(2023, 12, 31, 0, 0);
        String categoria = "lujo";
        int tarifaCategoria = 2000;
        String seguro = "Seguro de Lesiones Personales";
        int tarifaSeguro = 5000;
        int recargo = 0;
        int idAlquiler = 999;

        escenarioDefecto.add(cliente);
        escenarioDefecto.add(sedeInicio);
        escenarioDefecto.add(sedeFinal);
        escenarioDefecto.add(fechaInicio);
        escenarioDefecto.add(fechaFinal);
        escenarioDefecto.add(null);
        escenarioDefecto.add(categoria);
        escenarioDefecto.add(tarifaCategoria);
        escenarioDefecto.add(seguro);
        escenarioDefecto.add(tarifaSeguro);
        escenarioDefecto.add(recargo);
        escenarioDefecto.add(idAlquiler);

    }
    
    @After
    public void reiniciarSistema(){
        paths.clear();
        paths.add( "Entrega 3\\AlquilerYReservas\\Base de Datos\\test_data\\good_data\\Usuarios.txt");
        paths.add( "Entrega 3\\AlquilerYReservas\\Base de Datos\\test_data\\good_data\\Sedes.txt");
        paths.add( "Entrega 3\\AlquilerYReservas\\Base de Datos\\test_data\\good_data\\Autos.txt");
        paths.add( "Entrega 3\\AlquilerYReservas\\Base de Datos\\test_data\\good_data\\Seguros.txt");
        paths.add( "Entrega 3\\AlquilerYReservas\\Base de Datos\\test_data\\good_data\\Tarifas.txt");
        paths.add( "Entrega 3\\AlquilerYReservas\\Base de Datos\\test_data\\good_data\\Alquileres.txt");
        paths.add( "Entrega 3\\AlquilerYReservas\\Base de Datos\\test_data\\good_data\\Pagos.txt");

        System.out.println("Reiniciando sistema");
        sistema = new Sistema(paths);
    }

    @Test 
    public void testReservaExitosa(){
        try {
            reiniciarSistema();

            Alquiler alquilerFinal = hacerReserva(escenarioDefecto);

            reiniciarSistema();
            Alquiler reservaVerificada = sistema.getAlquiler((int) escenarioDefecto.get(11));
            assertEquals(alquilerFinal, reservaVerificada);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage() + "\n");
            e.getStackTrace();
        }

    }

    @Test
    public void testReservaFallida (){
        ArrayList<Object> escenarioFallido = new ArrayList<Object>();
        for (int i = 0; i < 7; i++) {
            escenarioFallido.add(null);
        }
        escenarioFallido.add(0);
        escenarioFallido.add(null);
        for (int i = 0; i < 3; i++) {
            escenarioFallido.add(0);
        }

        assertThrows(Exception.class, ()-> hacerReserva(escenarioFallido));

    }

    @Test 
    public void testClienteInexistente(){
        Cliente clienteFallido = new Cliente ("ERROR", "ERROR");
        escenarioDefecto.set(0, clienteFallido);
        System.out.println(((Cliente) escenarioDefecto.get(0)).getLogin());
        System.out.println(sistema.getUsuarios().containsKey(clienteFallido.getLogin()));

        try {
            hacerReserva(escenarioDefecto);
        } catch (Exception e) {
            assertEquals("Error-Client", e.getMessage());
        }
    }

    @Test 
    public void testFechasIncorrectas(){
        LocalDateTime fechaInicio = LocalDateTime.of(2021, 10, 10, 0, 0);
        LocalDateTime fechaFin = LocalDateTime.of(2021, 10, 8, 0, 0);

        escenarioDefecto.set(3, fechaInicio);
        escenarioDefecto.set(4, fechaFin);

        try {
            hacerReserva(escenarioDefecto);
        } catch (Exception e) {
            assertEquals("Error-Fechas", e.getMessage());
        }
    }

    @Test 
    public void testSeguroIncorrecto(){
        String seguroFallido = "ERROR";
        escenarioDefecto.set(8, seguroFallido);
        try {
            hacerReserva(escenarioDefecto);
        } catch (Exception e) {
            assertEquals("Error-Seguro", e.getMessage());
        }
    }

    @Test 
    public void testCategoriaIncorrecta(){
        String categoriaFallido = "ERROR";
        escenarioDefecto.set(6, categoriaFallido);

        try {
            hacerReserva(escenarioDefecto);
        } catch (Exception e) {
            assertEquals("Error-Categoria", e.getMessage());
        }
    }

    @Test 
    public void testCategoriaTarifaIncorrecta(){
        int categoriaTarifaIncorrecta = -1;
        escenarioDefecto.set(7, categoriaTarifaIncorrecta);

        try {
            hacerReserva(escenarioDefecto);
        } catch (Exception e) {
            assertEquals("Error-CategoriaTarifa", e.getMessage());
        }
    }

    @Test 
    public void testSeguroTarifaIncorrecta(){
        int seguroTarifaIncorrecta = -1;
        escenarioDefecto.set(9, seguroTarifaIncorrecta);

        try {
            hacerReserva(escenarioDefecto);
        } catch (Exception e) {
            assertEquals("Error-SeguroTarifa", e.getMessage());
        }
    }

    @Test 
    public void testSedeInicioIncorrecta(){
        ((Sede) escenarioDefecto.get(1)).setNombre("ERROR");
        try {
            hacerReserva(escenarioDefecto);
        } catch (Exception e) {
            assertEquals("Error-SedeInicial", e.getMessage());
        }
    }

    @Test 
    public void testSedeFinalIncorrecta(){
        ((Sede) escenarioDefecto.get(2)).setNombre("ERROR");
        try {
            hacerReserva(escenarioDefecto);
        } catch (Exception e) {
            assertEquals("Error-SedeFinal", e.getMessage());
        }
    }

    public LocalDateTime StringToLocalDateTime(String fecha){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(fecha, formatter);
    }




    public long darDias (LocalDateTime fechaInicio, LocalDateTime fechaFin){
        long dias = fechaInicio.until(fechaFin, ChronoUnit.DAYS);
        return dias;
    }


    
    public static int verificarFechas(Date fechaInicio, Date fechaFinal){

        Date fechaActual = setMedianoche(new Date());
        Date fechaInicial = setMedianoche(fechaInicio);
        Date fechaFin = setMedianoche(fechaFinal);

        if (fechaInicial.compareTo(fechaFin) == 0){
            return 0;
        }
        else if(fechaInicial.compareTo(fechaFin) > 0){
            return 1;
        }
        else if(!(fechaInicial.compareTo(fechaActual) > 0 || fechaFin.compareTo(fechaActual) > 0)){
            return 2;
        }
        else{
            return 0;
        }
    }

    public static  Date setMedianoche(Date fecha) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static  Date toDate (LocalDateTime fecha){
        Instant instant = fecha.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }
    
}
