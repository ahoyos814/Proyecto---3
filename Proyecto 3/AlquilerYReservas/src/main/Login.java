package main;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Desktop;
import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Login extends Application {
    static Sistema sistema;
    static HashMap<String, Usuario> usuarios;
    static Stage primaryStage;

    static String pathUsuarios = "Entrega 3\\AlquilerYReservas\\Base de Datos\\Usuarios.txt";
    static String pathSedes = "Entrega 3\\AlquilerYReservas\\Base de Datos\\Sedes.txt";
    static String pathAutos = "Entrega 3\\AlquilerYReservas\\Base de Datos\\Autos.txt";
    static String pathSeguros = "Entrega 3\\AlquilerYReservas\\Base de Datos\\Seguros.txt";
    static String pathTarifas = "Entrega 3\\AlquilerYReservas\\Base de Datos\\Tarifas.txt";
    static String pathAlquileres = "Entrega 3\\AlquilerYReservas\\Base de Datos\\Alquileres.txt";
    static String pathPagos = "Entrega 3\\AlquilerYReservas\\Base de Datos\\Pagos.txt";

    static ArrayList<String> paths = new ArrayList<String>();

    public static void main(String[] args) throws Exception {
        
        paths.add(pathUsuarios);
        paths.add(pathSedes);
        paths.add(pathAutos);
        paths.add(pathSeguros);
        paths.add(pathTarifas);
        paths.add(pathAlquileres);
        paths.add(pathPagos);
        
        launch(args);

    }

    static void ejecutarConsola() {
        while (true) {
            try {
                System.out.println("\n");
                System.out.println("Hola, bienvenido al alquiler del Rayo McQueen!");
                System.out.println("Si ya tienes una cuenta digita 1, de lo contrario digita 0");
                while (true) {
                    System.out.println("\n");
                    String respuesta = input("--> ");

                    if (respuesta.equals("1")) {
                        ingresar();
                        break;

                    } else if (respuesta.equals("0")) {
                        crearUsuario();
                        break;
                    } else {
                        System.out.println("Escoje una opcion valida >:v");
                    }
                }
            } catch (Exception e) {
                e.getStackTrace();
            }

        }
    }

    public static void crearUsuario() {
        // Atributos para crear usuario
        String login;
        String password;
        String nombre;
        int numeroCelular;
        String nacionalidad;
        File cedulaFoto;

        // Datos para la licencia
        int licenciaID;
        String licenciaPaisExpedicion;
        LocalDateTime licenciaFechaVen;
        File licenciaFoto;

        // Datos para el medio de pago
        String tipo;
        int numeroTarjeta;
        LocalDateTime fechaVencimiento;
        int codigoSeguridad;

        System.out.println("\n");
        System.out.println("Recuerda, solo puedes crear una cuenta tipo cliente");
        System.out.println("Escoge un nombre de usuario (debe ser unico)");
        System.out.println("De lo contrario escoje 0 para volver");

        while (true) {
            System.out.println("\n");
            String respuesta = input("--> ");

            if (respuesta.equals("0")) {
                break;
            } else if (!sistema.existeUsuario(respuesta)) {
                login = respuesta;
                System.out.println("\n");
                System.out.println("Bien! Ahora escoje una constraseña");
                System.out.println("\n");
                password = input("--> ");

                System.out.println("\n");
                System.out.println("Ingresa tu nombre completo");
                System.out.println("\n");
                nombre = input("--> ");

                System.out.println("\n");
                System.out.println("Ingresa tu numero de celular");
                while (true) {
                    System.out.println("\n");
                    respuesta = input("--> ");
                    try {
                        numeroCelular = Integer.parseInt(respuesta);
                        break;
                    } catch (Exception e) {
                        System.out.println("Escoge un opcion valida >:v");
                    }
                }

                System.out.println("\n");
                System.out.println("Ingresa tu nacionalidad");
                System.out.println("\n");
                nacionalidad = input("--> ");

                System.out.println("\n");
                System.out.println("Escoge la foto de tu cedula ->");
                System.out.println("\n");
                escogerFoto();
                cedulaFoto = new File(getPath());

                System.out.println("\n");
                System.out.println("Ingresa el ID de tu licencia de conduccion");
                while (true) {
                    System.out.println("\n");
                    respuesta = input("--> ");
                    try {
                        licenciaID = Integer.parseInt(respuesta);
                        break;
                    } catch (Exception e) {
                        System.out.println("Escoge un opcion valida >:v");
                    }
                }

                System.out.println("\n");
                System.out.println("Ingresa el pais de expedicion de tu licencia");
                System.out.println("\n");
                respuesta = input("--> ");
                licenciaPaisExpedicion = respuesta;

                System.out.println("Ingresa la fecha de vencimiento de la licencia");
                System.out.println("    -> formato 'yyyy-MM-dd' ej: '2003-09-05'");
                while (true) {
                    System.out.println("\n");
                    String fechaTexto = input("--> ");

                    try {
                        licenciaFechaVen = sistema.stringToDateTime(fechaTexto + " 00:00");
                        break;
                    } catch (Exception e) {
                        System.out.println("\n");
                        System.out.println("ERROR! Tienes que respetar el formato >:v");
                        System.out.println("Intentalo de nuevo ^-^");
                    }
                }

                System.out.println("\n");
                System.out.println("Escoge la foto de tu licencia (o-o)");
                escogerFoto();
                licenciaFoto = new File(getPath());

                System.out.println("\n");
                System.out.println("Ingresa el tipo de tu tarjeta de credito (O<O)");
                System.out.println("\n");
                respuesta = input("--> ");
                tipo = respuesta;

                System.out.println("\n");
                System.out.println("Ingresa tu numero de tarjeta (._.)");
                while (true) {
                    System.out.println("\n");
                    respuesta = input("--> ");
                    try {
                        numeroTarjeta = Integer.parseInt(respuesta);
                        break;
                    } catch (Exception e) {
                        System.out.println("Escoge un opcion valida >:v");
                    }
                }

                System.out.println("Ingresa la fecha de vencimiento de tu tarjeta");
                System.out.println("    -> formato 'YY-MM' ej: '29-08'");
                while (true) {
                    System.out.println("\n");
                    String fechaTexto = input("--> ");

                    try {
                        fechaVencimiento = sistema.stringToDateTime("20" + fechaTexto + "-01 00:00");
                        break;
                    } catch (Exception e) {
                        System.out.println("\n");
                        System.out.println("ERROR! Tienes que respetar el formato >:v");
                        System.out.println("Intentalo de nuevo ^-^");
                    }
                }

                System.out.println("\n");
                System.out.println("Ingresa el codigo de suguridad de tu tarjeta (^.^)");
                while (true) {
                    System.out.println("\n");
                    respuesta = input("--> ");
                    try {
                        codigoSeguridad = Integer.parseInt(respuesta);
                        break;
                    } catch (Exception e) {
                        System.out.println("Escoge un opcion valida >:v");
                    }
                }

                sistema.agregarUsuario(new Cliente(login, password, nombre, numeroCelular, nacionalidad,
                        cedulaFoto, licenciaID, licenciaPaisExpedicion, licenciaFechaVen,
                        licenciaFoto, tipo, numeroTarjeta, fechaVencimiento,
                        codigoSeguridad));

                System.out.println("\n");
                System.out.println("Redirigiendo al menu principal...");
                break;

            } else {
                System.out.println("\n");
                System.out.println("Ese usuario ya existe, porfavor escoge otro");
            }
        }

    }

    public static void ingresar() throws Exception {
        System.out.println("\n");
        System.out.println("Ingresa tu nombre de usuario para entrar al sistema");
        System.out.println("De lo contrario escoje 0 para volver");

        while (true) {
            System.out.println("\n");
            String respuesta = input("--> ");

            if (respuesta.equals("0")) {
                break;
            } else if (sistema.existeUsuario(respuesta)) {
                String login = respuesta;
                Usuario usuario = sistema.getUsuario(login);

                System.out.println("Bien! Ahora ingresa tu constraseña");
                while (true) {
                    System.out.println("\n");
                    String password = input("--> ");
                    if (usuario.getPassword().equals(password)) {
                        break;
                    } else {
                        System.out.println("\n");
                        System.out.println("Contraseña incorrecta, intentalo de nuevo");
                    }
                }

                if (usuario instanceof Cliente) {
                    System.out.println("\n");
                    System.out.println("Redirigiendo a la consola cliente...");
                    Consola_Cliente.mostrarOpciones(sistema, (Cliente) usuario);
                    break;
                } else {
                    Empleado empleado = (Empleado) usuario;
                    if (empleado.getAutoridad().equals("adminGlobal")) {
                        System.out.println("\n");
                        System.out.println("Redirigiendo a la consola Admin...");
                        Consola_Admin.mostrarOpciones(sistema, (Empleado) usuario);
                        break;
                    } else {
                        System.out.println("\n");
                        System.out.println("Redirigiendo a la consola Empleado...");
                        System.out.println(empleado.toString());
                        Consola_Empleado.mostrarOpciones(sistema, (Empleado) usuario);
                        break;

                    }
                }

            } else {
                System.out.println("Ese usuario no existe, intentalo de nuevo :(");
            }
        }

    }

    public static String input(String mensaje) {
        try {
            System.out.print(mensaje);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            return reader.readLine();
        } catch (IOException e) {
            System.out.println("Error leyendo de la consola");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        abrirInfo();
        this.primaryStage = primaryStage;
        sistema = new Sistema(paths);
        /*
        sistema.guardarAlquileres();
        sistema.guardarAutos();
        sistema.guardarSedes();
        sistema.guardarUsuarios();
         */

        usuarios = sistema.getUsuarios();
        ejecutarConsola();
    }

    public static void escogerFoto() {
        String path = "Entrega 3\\\\AlquilerYReservas\\\\Base de Datos\\\\bridge.txt";
        File selectedFile;
        System.out.println("    Se abrio una ventana para que escojas el archivo!!");
        System.out.println("    (puedes cerrar la ventana y se seleccionara una foto por defecto)");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        selectedFile = fileChooser.showOpenDialog(primaryStage);

        if (selectedFile != null) {

            try (BufferedWriter br = new BufferedWriter(
                    new FileWriter(path))) {
                br.write(selectedFile.getAbsolutePath());
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            try (BufferedWriter br = new BufferedWriter(
                    new FileWriter(path))) {
                br.write("Entrega 3\\AlquilerYReservas\\Base de Datos\\FotosCedulas\\hanni.webp");
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static String getPath() {
        String path = "Entrega 3\\\\AlquilerYReservas\\\\Base de Datos\\\\bridge.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String linea = br.readLine();
            br.close();
            return linea;
        } catch (NumberFormatException | IOException e) {
            return "";
        }
    }

    public static void abrirInfo() {
        String rutaArchivo = "Entrega 3\\AlquilerYReservas\\INFORMACION IMPORTANTE.txt";

        try {
            Desktop desktop = Desktop.getDesktop();

            if (desktop.isSupported(Desktop.Action.OPEN)) {
                File archivo = new File(rutaArchivo);

                desktop.open(archivo);
            } else {
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
}
