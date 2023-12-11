package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class AppC_Reservas extends JFrame implements ActionListener{

    private ArrayList<JLabel> listaLabels = new ArrayList<JLabel>();
    private ArrayList<JTextField> listaInputs = new ArrayList<JTextField>();



    public Sistema sistema;
    private Cliente cliente;
    private Sede sede;
    private Auto auto;
    private Date fechaInicio;
    private Date fechaFinal;
    private GeneradorFacturas generadorFacturas;
    
    
    private JComboBox inputSedeFinal;
    private JComboBox inputSeguro;
    private JTextField inputRecargoEntrega;

    private HashMap<String, Sede> sedesEncontradas = new HashMap<String, Sede>();
 
    private String[] segurosString;
    private String[] sedesString;

    


    public AppC_Reservas(Sistema pSistema, Cliente cliente, Sede sede, Auto auto, Date fechaInicio, Date fechaFinal) {

        this.cliente = cliente;
        this.sede = sede;
        this.auto = auto;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.sistema = pSistema;

        cargarSegurosSedes();

        
        setTitle("RESERVAR AUTO");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        


        JPanel gridDatos = new JPanel(new GridLayout(0, 2));

        

    
        JLabel labelSedeInicio = new JLabel("Sede Inicio");
        insertarFuente(labelSedeInicio);
        JTextField inputSedeInicio = new JTextField(sede.getNombre());
        inputSedeInicio.setEditable(false);
        insertarFuente(inputSedeInicio);
        gridDatos.add(labelSedeInicio);
        gridDatos.add(inputSedeInicio);



        JLabel labelSedeFinal = new JLabel("Sede Final");
        insertarFuente(labelSedeFinal);
        inputSedeFinal = new JComboBox<>(sedesString);
        insertarFuente(inputSedeFinal);
        gridDatos.add(labelSedeFinal);
        gridDatos.add(inputSedeFinal);

        

        
        JLabel labelFechaInicial = new JLabel("Fecha Inicial");
        insertarFuente(labelFechaInicial);
        JTextField inputFechaInicial = new JTextField(DateToString(fechaInicio));
        inputFechaInicial.setEditable(false);
        insertarFuente(inputFechaInicial);
        gridDatos.add(labelFechaInicial);
        gridDatos.add(inputFechaInicial);
        


        JLabel labelFechaFinal = new JLabel("Fecha Inicial");
        insertarFuente(labelFechaFinal);
        JTextField inputFechaFinal = new JTextField(DateToString(fechaFinal));
        inputFechaFinal.setEditable(false);
        insertarFuente(inputFechaFinal);
        gridDatos.add(labelFechaFinal);
        gridDatos.add(inputFechaFinal);

        JLabel labelPrimaAdicionLabel = new JLabel("Prima Adicional por riesgo   ");
        insertarFuente(labelPrimaAdicionLabel);
        JTextField inputPrimaAdicional = new JTextField("" + sistema.getPrimaAdicional(auto.getTipoVehiculo()));
        inputPrimaAdicional.setEditable(false);
        insertarFuente(inputPrimaAdicional);
        gridDatos.add(labelPrimaAdicionLabel);
        gridDatos.add(inputPrimaAdicional);

        

        JLabel labelSeguro = new JLabel("Seguro");
        insertarFuente(labelSeguro);
        inputSeguro = new JComboBox<>(segurosString);
        insertarFuente(inputSeguro);
        gridDatos.add(labelSeguro);
        gridDatos.add(inputSeguro);

        
        
        

        int tarifaCategoria = (Integer) sistema.getTarifas().get(auto.getCategoria())[0];

        

        JLabel labelTarifaCategoria = new JLabel("Tarifa Categoria de vehiculo");
        insertarFuente(labelTarifaCategoria);
        JTextField inputTarifaCategoria = new JTextField("" + tarifaCategoria);
        inputTarifaCategoria.setEditable(false);
        insertarFuente(inputTarifaCategoria);
        gridDatos.add(labelTarifaCategoria);
        gridDatos.add(inputTarifaCategoria);

        int pRecargo = 0;
        if (sede.getNombre() != inputSedeFinal.getSelectedItem().toString()){
            pRecargo = sistema.getRecargo();
        }
        JLabel labelRecargoEntrega = new JLabel("Recargo por entrega en otra sede   ");
        insertarFuente(labelRecargoEntrega);
        inputRecargoEntrega = new JTextField("" + pRecargo);
        inputRecargoEntrega.setEditable(false);
        insertarFuente(inputRecargoEntrega);
        gridDatos.add(labelRecargoEntrega);
        gridDatos.add(inputRecargoEntrega);

        

        JPanel gridConfirmacion = new JPanel(new GridLayout(1, 2));


        JButton botonAceptar = new JButton("Aceptar");
        JButton botonCancelar = new JButton("Volver");
        botonAceptar.setFont(new Font("Century Gothic", Font.BOLD, 24));
        botonCancelar.setFont(new Font("Century Gothic", Font.BOLD, 24));
        botonAceptar.addActionListener(this);
        botonCancelar.addActionListener(this);
        gridConfirmacion.add(botonAceptar);
        gridConfirmacion.add(botonCancelar);

        inputSedeFinal.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                // Verifica si el evento es de selección
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    // Obtiene el elemento seleccionado y realiza alguna acción
                    String sedeFinalSeleccionada = (String) inputSedeFinal.getSelectedItem();
                    if(sedeFinalSeleccionada != sede.getNombre()){
                        inputRecargoEntrega.setText("" + sistema.getRecargo());
                    }
                    else{
                        inputRecargoEntrega.setText("0");
                    }
                    
                }
            }
        });

        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 0.5;
        add(gridDatos,c);

        c.gridy = 1;
        c.weighty = 0.5;
        add(gridConfirmacion, c);


        setLocationRelativeTo(null); 
        this.setVisible(true);


    }
    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            
            if (e.getActionCommand().equals("Volver")){
                this.setVisible(false);
                new AppC_ConsultasReservas(sistema, cliente);
            }
            
            else if (e.getActionCommand().equals("Aceptar")){

                int alquilerID = sistema.getNewAlquilerId();
                int tarifaCategoria = sistema.getPrecioCategoria(auto.getCategoria());
                String seguroSeleccionado = segurosString[inputSeguro.getSelectedIndex()].split("-")[0];
                int tarifaSeguro = (Integer) sistema.getSeguros().get(seguroSeleccionado);
                
                
                
                
                long dias = (long) calcularDias(fechaInicio, fechaFinal);
                String sedeFinalSeleccionada = inputSedeFinal.getSelectedItem().toString();
                
                
                double recargo = 0;

                if (sede.getNombre() != sedeFinalSeleccionada){
                    recargo = sistema.getRecargo();
                }
                int primaAdicional = sistema.getPrimaAdicional(auto.getTipoVehiculo());
                int tarifaDiariaTotal = tarifaCategoria + tarifaSeguro + primaAdicional;
                long costoTotal = (tarifaDiariaTotal * dias) + doubleEntero(recargo);
                
                

                generadorFacturas = new GeneradorFacturas("Factura"+alquilerID, alquilerID, cliente.getNombre(), cliente.getNumeroCelular(), sede.getNombre(), sedeFinalSeleccionada, DateToString(fechaInicio), DateToString(fechaFinal), dias, auto.getCategoria(), tarifaCategoria, seguroSeleccionado, tarifaSeguro, doubleEntero(recargo) ,tarifaDiariaTotal,costoTotal);

                generadorFacturas.crearFacturaPdf();
                File factura = generadorFacturas.getFactura();
                Sede sedeFinal = sistema.getSedes().get(sedeFinalSeleccionada);

                Alquiler alquiler = new Alquiler(cliente, "Activo",sede,sedeFinal,DateToLocalDateTime(fechaInicio), DateToLocalDateTime(fechaFinal), alquilerID,factura,null,false, longToDouble(costoTotal) );
                

                sistema.agregarAlquiler(alquiler, auto.getPlaca());


                auto.setFechaEstimadaDisponible(DateToLocalDateTime(fechaFinal));
                auto.setDisponibilidad(false);
                auto.setAlquilado(alquiler);
                sistema.eliminarAuto(auto);
                System.out.println("Auto eliminado");
                sistema.agregarAuto(auto);
                System.out.println("Auto agregado");


                int respuesta = JOptionPane.showConfirmDialog(
                null,
                "El costo total es de " + costoTotal+ "\n¿Desea realizar el pago del 30%?, es decir $" + (costoTotal*0.3)+" , esta acción te dará un 10% de descuento en el costo total del alquiler",
                "Confirmación",
                JOptionPane.YES_NO_OPTION);

                if (respuesta == JOptionPane.YES_OPTION) {


                    String resultado = 
                    "\n Rubros:"
                    + "\n" + "Tarifa Diaria Total: " + tarifaDiariaTotal + " = (" + tarifaCategoria + " | Tarifa por Categoria" + " + " + tarifaSeguro + " | Tarifa por Seguro" + ") * " + dias + " dias" + " + " + recargo + " | Recargo por entrega en otra sede"
                    + "\n" + "Prima adicional por riesgo del vechiculo: " + recargo
                    + "\n\nEl costo total es " + costoTotal + " y el ID de la reserva es " + alquilerID
                    + "\nSe te cobrara el 30% de este costo por adelantado, es decir " + (costoTotal * 0.3) + " y el resto se te cobrara al momento de la entrega del vehiculo (10% de descuento aplicado)"
                    + "\n¡¡¡Muchas gracias por preferirnos!!!" ;

                    Pago pago = new Pago(longToDouble(costoTotal)*0.3 + longToDouble(costoTotal)*0.1 , LocalDateTime.now(), "Credito", alquilerID, sistema.darNuevoIdPago(), cliente.getTarjetaCredito().getNumeroTarjeta());
                    sistema.guardarPago(pago);
                    
                    JOptionPane.showMessageDialog(this, resultado);
                    System.out.println("Reserva realizada");
                }

                else {
                    String resultado = 
                    "\n Rubros:"
                    + "\n" + "Tarifa Diaria Total: " + tarifaDiariaTotal + " = (" + tarifaCategoria + " | Tarifa por Categoria" + " + " + tarifaSeguro + " | Tarifa por Seguro" + ") * " + dias + " dias" + " + " + recargo + " | Recargo por entrega en otra sede"
                    + "\n" + "Prima adicional por riesgo del tipo  de vechiculo: " + primaAdicional
                    + "\n\nEl costo total es " + costoTotal + " y el ID de la reserva es " + alquilerID
                    + "\n¡¡¡Muchas gracias por preferirnos!!!" ;
                    
                    JOptionPane.showMessageDialog(this, resultado);
                    System.out.println("Reserva realizada");

                }

                this.setVisible(false);
                
                new AppC_ConsultasReservas(sistema, cliente);

            }
            else{
                this.setVisible(false);
                throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
            }
            
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(this, ex.getMessage());
            System.exit(0);
        }
    }
    
   
    public void insertarFuente(Object objeto){
        if(objeto.getClass() == JLabel.class){
            JLabel label = (JLabel) objeto;
            label.setFont(new Font("Century Gothic", Font.BOLD, 18));
            listaLabels.add(label);
        }
        else if(objeto.getClass() == JTextField.class){
            JTextField input = (JTextField) objeto;
            input.setFont(new Font("Century Gothic", Font.BOLD, 14));
            input.setForeground(Color.GRAY);
            listaInputs.add(input);
        }
        else{
            System.out.println("No se puede insertar la fuente");
        }
    }

   

    public String DateToString(Date date){
        // Convertir Date a LocalDateTime
        LocalDateTime localDateTime = date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();

        // Formato de la fecha que deseas, por ejemplo: "yyyy-MM-dd HH:mm:ss"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Convertir LocalDateTime a String
        return localDateTime.format(formatter);
    }

    public LocalDateTime DateToLocalDateTime(Date date){
        // Convertir Date a LocalDateTime
        LocalDateTime localDateTime = date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
        return localDateTime;
    }

    public void cargarSegurosSedes(){
        ArrayList<String> seguros = new ArrayList<>(sistema.getSeguros().keySet());
        segurosString = seguros.toArray(new String[seguros.size()]); 
        for (int i = 0; i < seguros.size(); i++) {
            String seguro = seguros.get(i);
            segurosString[i] = seguro + "-$" + sistema.getSeguros().get(seguro);
        }

        sedesEncontradas = sistema.getSedes();
        sedesString = new String[sedesEncontradas.size()];
        int index = 0;
        for (Sede sede : sedesEncontradas.values()) {
            sedesString[index] = sede.getNombre();
            index++;
        }
    }

    public int calcularDias (Date fechaInicio, Date fechaFinal){
        long diferencia = fechaFinal.getTime() - fechaInicio.getTime();
        double dias = Math.floor(diferencia / (1000 * 60 * 60 * 24));

        if (dias == 0){
            dias = 1 ;
        }
        return (int) dias;
    }

    public int doubleEntero (double numero){
        return (int) Math.floor(numero);
    }

    public double longToDouble(Long numero){
        return numero.doubleValue();
    }



    

    
}
