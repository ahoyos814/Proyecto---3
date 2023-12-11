package main;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


public class AppC_ConsultasReservas extends JFrame implements ActionListener{


    private Sistema sistema;
    private JComboBox inputSede;
    private JComboBox inputCategoriaVehiculo;
    private JTextField inputTipoVehiculo;
    private JTextField inputPrimaTipo;

    private JSpinner inputFechaInicio;
    private JSpinner inputFechaFinal;
    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private JScrollPane labelVehiculos;
    private JList listaVehiculos;
    private Cliente cliente;

    private HashMap<Integer, Auto> vehiculosEncontrados = new HashMap<Integer, Auto>();
    private HashMap<String, Sede> sedesEncontradas = new HashMap<String, Sede>();
    private String[] sedesString;
    private String[] tiposString;


    


    public AppC_ConsultasReservas(Sistema pSistema, Cliente pCliente ) {

        this.sistema = pSistema;
        this.cliente = pCliente;
        cargarSedesTipos();


        setTitle("CONSULTA Y RESERVA USUARIO");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;


        JPanel gridDatos = new JPanel(new GridLayout(0, 2));
    
        JLabel labelSede = new JLabel("Sede");
        insertarFuente(labelSede);
        inputSede = new JComboBox<>(sedesString);
        insertarFuente(inputSede);
        gridDatos.add(labelSede);
        gridDatos.add(inputSede);

        JLabel labelCategoria = new JLabel("Categoria Vehiculo");
        insertarFuente(labelCategoria);
        inputCategoriaVehiculo = new JComboBox<>(tiposString);
        insertarFuente(inputCategoriaVehiculo);
        gridDatos.add(labelCategoria);
        gridDatos.add(inputCategoriaVehiculo);

        JLabel labelTipoVehiculo = new JLabel("Tipo Vehiculo");
        insertarFuente(labelTipoVehiculo);
        inputTipoVehiculo = new JTextField("");
        inputTipoVehiculo.setEditable(false);
        insertarFuente(inputTipoVehiculo);
        gridDatos.add(labelTipoVehiculo);
        gridDatos.add(inputTipoVehiculo);
        
        JLabel labelPrimaTipo = new JLabel("Prima adicional");
        insertarFuente(labelPrimaTipo);
        inputPrimaTipo = new JTextField("");
        inputPrimaTipo.setEditable(false);
        insertarFuente(inputPrimaTipo);
        gridDatos.add(labelPrimaTipo);
        gridDatos.add(inputPrimaTipo);

        

        

        JLabel labelFechaInicio = new JLabel("Fecha de inicio         ");       
        insertarFuente(labelFechaInicio);
        inputFechaInicio = new JSpinner(new SpinnerDateModel());
        configurarFecha(inputFechaInicio);
        gridDatos.add(labelFechaInicio);
        gridDatos.add(inputFechaInicio);
        
        JLabel labelFechaFinal = new JLabel("Fecha final");       
        insertarFuente(labelFechaFinal);
        inputFechaFinal = new JSpinner(new SpinnerDateModel());
        configurarFecha(inputFechaFinal);
        gridDatos.add(labelFechaFinal);
        gridDatos.add(inputFechaFinal);



        JPanel gridBuscar = new JPanel(new GridLayout(1, 3));


        JButton botonBuscar = new JButton("Buscar Disponibilidad");
        botonBuscar.setFont(new Font("Century Gothic", Font.BOLD, 24));
        botonBuscar.addActionListener(this);
        gridBuscar.add(botonBuscar);
        

        JPanel gridVehiculos = new JPanel(new GridLayout(0, 1));
        listaVehiculos = new JList<>(listModel);
        labelVehiculos = new JScrollPane(listaVehiculos);
        labelVehiculos.setPreferredSize(new Dimension(300, 200));
        gridVehiculos.add(labelVehiculos);



        JPanel gridConfirmacion = new JPanel(new GridLayout(1, 2));


        JButton botonAceptar = new JButton("Reservar");
        botonAceptar.setEnabled(false);
        JButton botonCancelar = new JButton("Cancelar");
        botonAceptar.setFont(new Font("Century Gothic", Font.BOLD, 24));
        botonCancelar.setFont(new Font("Century Gothic", Font.BOLD, 24));
        botonAceptar.addActionListener(this);
        botonCancelar.addActionListener(this);
        gridConfirmacion.add(botonAceptar);
        gridConfirmacion.add(botonCancelar);


        listaVehiculos.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    // Acción que ocurre al seleccionar un elemento
                    int selectedIndex = listaVehiculos.getSelectedIndex();
                    if (selectedIndex != -1) {
                        String selectedElement = listModel.getElementAt(selectedIndex);
                        System.out.println("Elemento seleccionado: " + selectedElement);

                        Auto autoSeleccionado = vehiculosEncontrados.get(selectedIndex);
                        botonAceptar.setEnabled(true);
                        inputTipoVehiculo.setText(autoSeleccionado.getTipoVehiculo());
                        inputPrimaTipo.setText("$" + sistema.getPrimaAdicional(autoSeleccionado.getTipoVehiculo()));

                        
                    }
                }
            }
        });

        

        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 0.20;
        add(gridDatos,c);


        c.gridy = 1;
        c.weighty = 0.20;
        add(gridBuscar, c);

        c.gridy = 2;
        c.weighty = 1;
        c.gridheight = 1;
        add(gridVehiculos, c);


        c.gridy = 3;
        c.weighty = 0.20;
        add(gridConfirmacion, c);


        setLocationRelativeTo(null); 
        setVisible(true);

    }
    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            if (e.getActionCommand().equals("Buscar Disponibilidad")){

                Date fechaInicio = (Date) inputFechaInicio.getValue();
                Date fechaFinal = (Date) inputFechaFinal.getValue();
                int rta = verificarFechas(fechaInicio, fechaFinal);
                if(rta == 0){
                    cargarVehiculosDisponibles();
                }
                else if (rta == 1){
                    JOptionPane.showMessageDialog(this, "La fecha de inicio debe ser anterior, o igual, a la fecha de finalización.");
                }
                else if (rta == 2){
                    JOptionPane.showMessageDialog(this, "No es posible efectuar reservas para fechas anteriores al día actual.");
                }
            }
            else if (e.getActionCommand().equals("Cancelar")){
                JOptionPane.showMessageDialog(this, "Reserva cancelada");
                this.setVisible(false);
                System.exit(0);
            }
            
            else if (e.getActionCommand().equals("Reservar")){

                int selectedIndex = listaVehiculos.getSelectedIndex();
                Auto autoSeleccionado = vehiculosEncontrados.get(selectedIndex);
                Sede sede = sedesEncontradas.get((String) inputSede.getSelectedItem());

                System.out.println("getName()");
                new AppC_Reservas(sistema,cliente, sede, autoSeleccionado, (Date) inputFechaInicio.getValue(), (Date) inputFechaFinal.getValue());
                System.out.println("Realizando reserva");
                this.setVisible(false);

            }
            else{
                this.setVisible(false);
                throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
            }
            
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
   
    public void insertarFuente(Object objeto){
        if(objeto.getClass() == JLabel.class){
            JLabel label = (JLabel) objeto;
            label.setFont(new Font("Century Gothic", Font.BOLD, 18));
        }
        else if(objeto.getClass() == JTextField.class){
            JTextField input = (JTextField) objeto;
            input.setFont(new Font("Century Gothic", Font.BOLD, 14));
            input.setForeground(Color.GRAY);
        }
        else{
            System.out.println("No se puede insertar la fuente");
        }
    }
    

    public void configurarFecha (JSpinner spinner){
        JSpinner.DateEditor fechaEditor = new JSpinner.DateEditor(spinner, "dd/MM/yyyy");
        spinner.setEditor(fechaEditor);
    }

    public void cargarSedesTipos (){

        sedesEncontradas = sistema.getSedes();
        sedesString = new String[sedesEncontradas.size()];
        int index = 0;
        for (Sede sede : sedesEncontradas.values()) {
            sedesString[index] = sede.getNombre();
            index++;
        }

        HashMap<String, Integer> tipos = sistema.getTiposVehiculo();
        ArrayList<String> categorias = sistema.getInventario().getArrayCategorias();

        tiposString = new String[tipos.size()];
        index = 0;
        for (String categoria : categorias) {
            tiposString[index] = categoria;
            index++;
        }


    }

    public void cargarVehiculosDisponibles(){
            
            String sedeSeleccionada = (String) inputSede.getSelectedItem();
            HashMap<String, Auto> vehiculos = sistema.getInventario().getAutos();
            System.out.println("Vehiculos en inventario: " + vehiculos.size());
            listModel.clear();
            vehiculosEncontrados.clear();

            System.out.println("Buscando autos disponibles");
            for (Auto auto : vehiculos.values()) {
                String categoriaSeleccionada = (String) inputCategoriaVehiculo.getSelectedItem();
                Date fechaDisponible = convertToLocalDateTimeToDate(auto.getFechaEstimadaDisponible());

                Boolean comparacionFechas = fechaDisponible.compareTo((Date) inputFechaInicio.getValue()) > 0 ;
                System.out.println(comparacionFechas);
                System.out.println("Categoria seleccionada: " + "-" + categoriaSeleccionada + "-" + " Categoria auto: " + "-" + auto.getCategoria() +"-" );
                System.out.println("Sede seleccionada: " + "-" + sedeSeleccionada + "-" + " Sede auto: " + "-" + auto.getSedeActual().getNombre() +"-" + "\n");

                if (comparacionFechas  || !auto.getCategoria().equals(categoriaSeleccionada) 
                || !auto.getSedeActual().getNombre().equals(sedeSeleccionada)){
                    continue;
                }
                else{
                    vehiculosEncontrados.put(listModel.size(), auto);
                    listModel.addElement(auto.toString());
                }
            }

            if (listModel.size() == 0){
                JOptionPane.showMessageDialog(this, "No hay vehiculos disponibles para los parametros seleccionados");
            }
            
    }

    public int verificarFechas(Date fechaInicio, Date fechaFinal){

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

    public Date convertToLocalDateTimeToDate(LocalDateTime localDateTime) {
        // Convertir LocalDateTime a Instant
        // y luego crear un objeto Date a partir de Instant
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }


    private Date setMedianoche(Date fecha) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    


    

    
}
