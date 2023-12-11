package main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class App_Cliente extends JFrame implements ActionListener {

    private Sistema sistema;
    static ArrayList<String> paths = new ArrayList<String>();


    public App_Cliente() {

        // Configuración de la ventana principal
        setTitle("Gestión de Usuarios");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear y configurar diseño de componentes
        JLabel tituloLabel = new JLabel("GESTIÓN DE USUARIOS");
        tituloLabel.setHorizontalAlignment(JLabel.CENTER);
        tituloLabel.setFont(new Font("Century Gothic", Font.BOLD, 24));
        JButton nuevoUsuarioButton = new JButton("Nuevo Usuario");
        nuevoUsuarioButton.setFont(new Font("Century Gothic", Font.BOLD, 18));
        JButton autenticarButton = new JButton("Autenticar Usuario");
        autenticarButton.setFont(new Font("Century Gothic", Font.BOLD, 18));
        nuevoUsuarioButton.addActionListener(this);
        autenticarButton.addActionListener(this);

        // Configurar el diseño de la interfaz
        setLayout(new GridLayout(3, 1));
        add(tituloLabel);
        add(autenticarButton);
        add(nuevoUsuarioButton);
        setLocationRelativeTo(null);

        inicializarPaths();
        sistema = new Sistema(paths);

        
      
    }


    private void inicializarPaths() {
        paths.add( "Entrega 3\\AlquilerYReservas\\Base de Datos\\Usuarios.txt");
        paths.add( "Entrega 3\\AlquilerYReservas\\Base de Datos\\Sedes.txt");
        paths.add( "Entrega 3\\AlquilerYReservas\\Base de Datos\\Autos.txt");
        paths.add( "Entrega 3\\AlquilerYReservas\\Base de Datos\\Seguros.txt");
        paths.add( "Entrega 3\\AlquilerYReservas\\Base de Datos\\Tarifas.txt");
        paths.add( "Entrega 3\\AlquilerYReservas\\Base de Datos\\Alquileres.txt");
        paths.add( "Entrega 3\\AlquilerYReservas\\Base de Datos\\Pagos.txt");
    }

    public static void main(String[] args) {
        new App_Cliente().setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //Manejo de eventos de los componenetes 
        try {
            if (e.getActionCommand().equals("Nuevo Usuario")){
                this.setVisible(false);
                new AppC_Form_NuevoUsuario(sistema, this);

            }
            else if (e.getActionCommand().equals("Autenticar Usuario")){
                this.setVisible(false);
                new AppC_Form_LoginUsuario(sistema, this);
            }
            else{
                throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }


    }
    
}
