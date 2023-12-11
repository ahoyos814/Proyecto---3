package main;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;


public class AppC_Form_LoginUsuario extends JFrame implements ActionListener{

    private ArrayList<JLabel> listaLabels = new ArrayList<JLabel>();
    private ArrayList<JTextField> listaInputs = new ArrayList<JTextField>();


    private JTextField inputLogin;
    private JPasswordField inputPassword;
    private JRadioButton buttonMostrar;
    private Login login;
    public Sistema sistema;

    private App_Cliente app_Cliente;
    


    public AppC_Form_LoginUsuario(Sistema pSistema, App_Cliente pApp_Cliente) {

        setTitle("AUTENTICAR USUARIO");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;


        JPanel gridDatos = new JPanel(new GridLayout(0, 2));
    
        JLabel labelLogin = new JLabel("Login");
        insertarFuente(labelLogin);
        inputLogin = new JTextField();
        insertarFuente(inputLogin);
        gridDatos.add(labelLogin);
        gridDatos.add(inputLogin);



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
        c.weighty = 0.3;
        add(gridDatos,c);


        c.gridy = 1;
        c.weighty = 0.3;
        add(gridPassword, c);


        c.gridy = 2;
        c.weighty = 0.3;
        add(gridConfirmacion, c);

        this.sistema = pSistema;
        this.app_Cliente = pApp_Cliente;
        setLocationRelativeTo(null); 
        setVisible(true);

    }
    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            if (e.getActionCommand().equals("Mostrar")){
               
                if(buttonMostrar.isSelected()){
                    inputPassword.setEchoChar((char)0);
                }
                else{
                    inputPassword.setEchoChar('*');
                }
                
            }
            else if (e.getActionCommand().equals("Volver")){
                JOptionPane.showMessageDialog(this, "Inicio de sesión cancelada");
                this.setVisible(false);
                app_Cliente.setVisible(true);
            }
            
            else if (e.getActionCommand().equals("Aceptar")){

                String login = inputLogin.getText();
                String password = String.valueOf(inputPassword.getPassword());

                if (login.equals("") || password.equals("")){
                    JOptionPane.showMessageDialog(this, "Debe llenar todos los campos");
                }
                else{
                    Cliente usuario = ingresarDefault(login, password); 
                    if (usuario != null){
                        this.setVisible(false);
                        JOptionPane.showMessageDialog(this, "Bienvenido " + login + " !!!");
                        new AppC_ConsultasReservas(sistema, usuario);
                    }
                    else{
                        JOptionPane.showMessageDialog(this, "Inicio de sesión fallido (Usuario o contraseña incorrectos)");
                    }
                    
                    
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

    public Cliente ingresarDefault(String user, String password) {
       

        System.out.println("Verificando usuario\n\n");
        String respuesta = user;
        Cliente usuarioAutenticado = null;


        if (respuesta.equals("")) {
            System.out.println("Usuario invalido");
            return null;
        } else if (sistema.existeUsuario(respuesta)) {
            
            System.out.println("Usuario encontrado");
            usuarioAutenticado = sistema.getCliente(user);
            if (usuarioAutenticado.getPassword().equals(password)) {
                System.out.println("Usuario autenticado");
                return usuarioAutenticado;
            } 
        }
        System.out.println("El usuario o la contraseña son incorrectos");
        return null;
        

        
    }



    

    
}
