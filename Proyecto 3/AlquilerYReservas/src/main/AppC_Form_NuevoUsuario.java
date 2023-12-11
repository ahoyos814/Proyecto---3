package main;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;


public class AppC_Form_NuevoUsuario extends JFrame implements ActionListener{

    private ArrayList<JLabel> listaLabels = new ArrayList<JLabel>();
    private ArrayList<JTextField> listaInputs = new ArrayList<JTextField>();

    private JTextField inputNombre;
    private JTextField inputNumero;
    private JTextField inputNacionalidad;
    private JTextField inputLogin;
    private JPasswordField inputPassword;
    private JRadioButton buttonMostrar;
    private File fotoCedula;
    public Sistema sistema ;

    private JTextField inputLicenciaID;
    private JTextField inputLicenciaPaisExpedicion;
    private JSpinner inputLicenciaFechaVen;
    private File inputLicenciaFoto;
    private JTextField inputLicenciaTipo;
    private JTextField inputLicenciaNumeroTarjeta;
    private JSpinner inputFechaVencimientoTarjeta;
    private JTextField inputLicenciaCodigoSeguridad;

    private App_Cliente appCliente;


    public AppC_Form_NuevoUsuario(Sistema pSistema, App_Cliente pAppCliente) {

        setTitle("CREAR NUEVO USUARIO");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        JPanel gridDatos = new JPanel(new GridLayout(0, 2));
        JLabel labelNombre = new JLabel("Nombre");
        insertarFuente(labelNombre);
        inputNombre = new JTextField();
        insertarFuente(inputNombre);
        gridDatos.add(labelNombre);
        gridDatos.add(inputNombre);
  
        JLabel labelNumero = new JLabel("Número de celular");
        insertarFuente(labelNumero);
        inputNumero = new JTextField();
        insertarFuente(inputNumero);
        gridDatos.add(labelNumero);
        gridDatos.add(inputNumero);

        JLabel labelNacionalidad = new JLabel("Nacionalidad");       
        insertarFuente(labelNacionalidad);
        inputNacionalidad = new JTextField();
        insertarFuente(inputNacionalidad);
        gridDatos.add(labelNacionalidad);
        gridDatos.add(inputNacionalidad);

    
        JLabel labelLogin = new JLabel("Login");
        insertarFuente(labelLogin);
        inputLogin = new JTextField();
        insertarFuente(inputLogin);
        gridDatos.add(labelLogin);
        gridDatos.add(inputLogin);

        
        JLabel labelArchivo = new JLabel("Foto de cédula");
        insertarFuente(labelArchivo);	
        JButton inputArchivo = new JButton("Buscar Archivo");
        inputArchivo.setFont(new Font("Century Gothic", Font.BOLD, 14));
        inputArchivo.addActionListener(this);
        gridDatos.add(labelArchivo);
        gridDatos.add(inputArchivo);

        JPanel gridDatosLicencia = new JPanel(new GridLayout(0, 2));

        
        JLabel labelLicenciaID = new JLabel("ID de licencia");       
        insertarFuente(labelLicenciaID);   
        inputLicenciaID = new JTextField();
        insertarFuente(inputLicenciaID);
        gridDatosLicencia.add(labelLicenciaID);
        gridDatosLicencia.add(inputLicenciaID);

        JLabel labelLicenciaPaisExpedicion = new JLabel("País de expedición de licencia");       
        insertarFuente(labelLicenciaPaisExpedicion);
        inputLicenciaPaisExpedicion = new JTextField();
        insertarFuente(inputLicenciaPaisExpedicion);
        gridDatosLicencia.add(labelLicenciaPaisExpedicion);
        gridDatosLicencia.add(inputLicenciaPaisExpedicion);

        JLabel labelLicenciaFechaVen = new JLabel("Fecha de vencimiento de licencia");       
        insertarFuente(labelLicenciaFechaVen);
        inputLicenciaFechaVen = new JSpinner(new SpinnerDateModel());
        configurarFecha(inputLicenciaFechaVen);
        gridDatosLicencia.add(labelLicenciaFechaVen);
        gridDatosLicencia.add(inputLicenciaFechaVen);

        JLabel labelLicenciaFoto = new JLabel("Foto de licencia");
        insertarFuente(labelLicenciaFoto);	
        JButton inputLicenciaFoto = new JButton("Buscar Foto Licencia");
        inputLicenciaFoto.setFont(new Font("Century Gothic", Font.BOLD, 14));
        inputLicenciaFoto.addActionListener(this);
        gridDatos.add(labelLicenciaFoto);
        gridDatos.add(inputLicenciaFoto);

        JLabel labelLicenciaTipo = new JLabel("Tipo de Licencia");       
        insertarFuente(labelLicenciaTipo);
        inputLicenciaTipo = new JTextField();
        insertarFuente(inputLicenciaTipo);
        gridDatosLicencia.add(labelLicenciaTipo);
        gridDatosLicencia.add(inputLicenciaTipo);

        JLabel labelLicenciaNumeroTarjeta = new JLabel("Numero de Tarjeta de Credito / Debito");       
        insertarFuente(labelLicenciaNumeroTarjeta);
        inputLicenciaNumeroTarjeta = new JTextField();
        insertarFuente(inputLicenciaNumeroTarjeta);
        gridDatosLicencia.add(labelLicenciaNumeroTarjeta);
        gridDatosLicencia.add(inputLicenciaNumeroTarjeta);


        JLabel labelFechaVencimientoTarjeta = new JLabel("Fecha de vencimiento de tarjeta");       
        insertarFuente(labelFechaVencimientoTarjeta);
        inputFechaVencimientoTarjeta = new JSpinner(new SpinnerDateModel());
        configurarFecha(inputFechaVencimientoTarjeta);
        gridDatosLicencia.add(labelFechaVencimientoTarjeta);
        gridDatosLicencia.add(inputFechaVencimientoTarjeta);

        JLabel labelLicenciaCodigoSeguridad = new JLabel("Codigo de seguridad de tarjeta");       
        insertarFuente(labelLicenciaCodigoSeguridad);
        inputLicenciaCodigoSeguridad = new JTextField();
        insertarFuente(inputLicenciaCodigoSeguridad);
        gridDatosLicencia.add(labelLicenciaCodigoSeguridad);
        gridDatosLicencia.add(inputLicenciaCodigoSeguridad);
        


        JPanel gridPassword = new JPanel(new GridLayout(1, 3));

        
        JLabel labelPassword = new JLabel("Password");
        insertarFuente(labelPassword);
        inputPassword = new JPasswordField();
        inputPassword.setFont(new Font("Century Gothic", Font.BOLD, 14));
        buttonMostrar = new JRadioButton("Mostrar");
        buttonMostrar.setFont(new Font("Century Gothic", Font.BOLD, 14));
        buttonMostrar.addActionListener(this);
        gridPassword.add(labelPassword);
        gridPassword.add(inputPassword);
        gridPassword.add(buttonMostrar);


        JPanel gridConfirmacion = new JPanel(new GridLayout(1, 2));


        JButton botonAceptar = new JButton("Aceptar");
        JButton botonCancelar = new JButton("Volver");
        botonAceptar.setFont(new Font("Century Gothic", Font.BOLD, 24));
        botonCancelar.setFont(new Font("Century Gothic", Font.BOLD, 24));
        botonAceptar.addActionListener(this);
        botonCancelar.addActionListener(this);
        gridConfirmacion.add(botonAceptar);
        gridConfirmacion.add(botonCancelar);
        

        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 0.25;
        add(gridDatos,c);

        c.gridx = 0;
        c.gridy = 1;
        c.weighty = 0.25;
        add(gridDatosLicencia,c);

        c.gridy = 2;
        c.weighty = 0.25;
        add(gridPassword, c);


        c.gridy = 3;
        c.weighty = 0.25;
        add(gridConfirmacion, c);

        this.sistema = pSistema;
        this.appCliente = pAppCliente;
        setLocationRelativeTo(null); 
        setVisible(true);

    }
    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            
            if (e.getActionCommand().equals("Buscar Archivo") ){
             
                showFileChooser("Foto de cédula");

            }
            else if (e.getActionCommand().equals("Buscar Foto Licencia") ){
             
                showFileChooser("Buscar Foto Licencia");

            }
            
            else if (e.getActionCommand().equals("Mostrar")){
               
                if(buttonMostrar.isSelected()){
                    inputPassword.setEchoChar((char)0);
                }
                else{
                    inputPassword.setEchoChar('*');
                }
                
            }
            else if (e.getActionCommand().equals("Volver")){
                JOptionPane.showMessageDialog(this, "Creación de usuario cancelado");
                this.setVisible(false);
                appCliente.setVisible(true);
            }
            
            else if (e.getActionCommand().equals("Aceptar")){

                String nombre = inputNombre.getText();
                String numero = inputNumero.getText();
                String nacionalidad = inputNacionalidad.getText();
                String login = inputLogin.getText();
                String password = String.valueOf(inputPassword.getPassword());

                String licenciaID = inputLicenciaID.getText();
                String licenciaPaisExpedicion = inputLicenciaPaisExpedicion.getText();
                Date licenciaFechaVen = (Date) inputLicenciaFechaVen.getValue();
                String licenciaFoto = inputLicenciaFoto.getAbsolutePath();
                String licenciaTipo = inputLicenciaTipo.getText();
                String licenciaNumeroTarjeta = inputLicenciaNumeroTarjeta.getText();
                Date fechaVencimientoTarjeta = (Date) inputFechaVencimientoTarjeta.getValue();
                String licenciaCodigoSeguridad = inputLicenciaCodigoSeguridad.getText();



                if (nombre.equals("") || numero.equals("") || nacionalidad.equals("") || login.equals("") || password.equals("") || licenciaID.equals("") || licenciaPaisExpedicion.equals("") || licenciaFoto.equals("") || licenciaTipo.equals("") || licenciaNumeroTarjeta.equals("") || licenciaCodigoSeguridad.equals("")){
                    JOptionPane.showMessageDialog(this, "Debe llenar todos los campos");
                }

                else if (licenciaFechaVen == null || fechaVencimientoTarjeta == null ){
                    JOptionPane.showMessageDialog(this, "Debe llenar los campos de fecha");        
                }
                else if (!verificarEntero(numero) || !verificarEntero(licenciaID) || !verificarEntero(licenciaNumeroTarjeta) || !verificarEntero(licenciaCodigoSeguridad)){
                    JOptionPane.showMessageDialog(this, "El número de celular, el ID de licencia, el numero de tarjeta y el código de seguridad deben ser numeros enteros");
                }
                else if (fotoCedula == null){
                    JOptionPane.showMessageDialog(this, "Debe seleccionar una foto de cédula");
                }
                else if (inputLicenciaFoto == null){
                    JOptionPane.showMessageDialog(this, "Debe seleccionar una foto de licencia");
                }
                else if (sistema.existeUsuario(login)){
                    JOptionPane.showMessageDialog(this, "El login ya existe");
                }
                else{
                    JOptionPane.showMessageDialog(this, "Creación de usuario exitosa");
                    System.out.println("Creando usuario");
                    System.out.println(nombre + "\n" 
                    + numero + "\n"
                    + nacionalidad + "\n"
                    + login + "\n"
                    + password + "\n"
                    + fotoCedula.getAbsolutePath() + "\n"
                    + licenciaID + "\n"
                    + licenciaPaisExpedicion + "\n"
                    + new SimpleDateFormat("dd/MM/yyyy").format(licenciaFechaVen) + "\n"
                    + inputLicenciaFoto.getAbsolutePath() + "\n"
                    + licenciaTipo + "\n"
                    + licenciaNumeroTarjeta + "\n"
                    + new SimpleDateFormat("dd/MM/yyyy").format(fechaVencimientoTarjeta) + "\n"
                    + licenciaCodigoSeguridad + "\n"
                    );

                    String cadenaLicenciaFecha = new SimpleDateFormat("dd/MM/yyyy").format(licenciaFechaVen);
                    String cadenaTarjetaFecha = new SimpleDateFormat("dd/MM/yyyy").format(fechaVencimientoTarjeta);


                    Cliente nuevoCliente = new Cliente(login, password, nombre, Integer.parseInt(numero), nacionalidad, fotoCedula, Integer.parseInt(licenciaID), licenciaPaisExpedicion, extraerFecha(cadenaLicenciaFecha), inputLicenciaFoto, licenciaTipo, Integer.parseInt(licenciaNumeroTarjeta), extraerFecha(cadenaTarjetaFecha), Integer.parseInt(licenciaCodigoSeguridad));

                    

                    sistema.agregarUsuario(nuevoCliente);

                    System.out.println(nuevoCliente.toString());

                      System.out.println(
                        "Usuario Creado: \n" + "\n" + nombre + "\n" 
                    + numero + "\n"
                    + nacionalidad + "\n"
                    + login + "\n"
                    + password + "\n"
                    + fotoCedula.getAbsolutePath() + "\n"
                    + licenciaID + "\n"
                    + licenciaPaisExpedicion + "\n"
                    + new SimpleDateFormat("dd/MM/yyyy").format(licenciaFechaVen) + "\n"
                    + inputLicenciaFoto.getAbsolutePath() + "\n"
                    + licenciaTipo + "\n"
                    + licenciaNumeroTarjeta + "\n"
                    + new SimpleDateFormat("dd/MM/yyyy").format(fechaVencimientoTarjeta) + "\n"
                    + licenciaCodigoSeguridad + "\n"
                    );
                    
                    this.setVisible(false);
                    appCliente.setVisible(true);


                }

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

    private void showFileChooser(String opcion) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("Entrega 3\\AlquilerYReservas\\Base de Datos\\FotosTest"));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de imagen (*.jpg) (*.png) ", "jpg","png");
        fileChooser.setFileFilter(filter);
        // Mostrar el cuadro de diálogo de selección de archivos
        int result = fileChooser.showOpenDialog(this);

        // Manejar la elección del usuario
        if (result == JFileChooser.APPROVE_OPTION && fileChooser.getSelectedFile() != null ) {
           
            File selectedFile = fileChooser.getSelectedFile();
            if (opcion.equals("Buscar Foto Licencia")){
                inputLicenciaFoto = selectedFile;
                JOptionPane.showMessageDialog(this, "Foto de licencia seleccionado: " + selectedFile.getAbsolutePath());
                
            }
            else{
                fotoCedula = selectedFile;
                JOptionPane.showMessageDialog(this, "Foto de cédula seleccionado: " + selectedFile.getAbsolutePath());
                
            }
        
        
        }
        else {
        JOptionPane.showMessageDialog(this, "Selección de archivo cancelada");
        }

    }

    private Boolean verificarEntero(String numero){
        try{
            Integer.parseInt(numero);
            return true;
        }
        catch(NumberFormatException e){
            return false;
        }
    }

    public void configurarFecha (JSpinner spinner){
        JSpinner.DateEditor fechaEditor = new JSpinner.DateEditor(spinner, "dd/MM/yyyy");
        spinner.setEditor(fechaEditor);
    }
    


    public LocalDateTime extraerFecha(String fechaHora) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate date = LocalDate.parse(fechaHora, formatter);
            return date.atStartOfDay();
        
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Fallo al convertir la fecha");
            e.getMessage();
            return null;
        }
    } 

   

    
}
