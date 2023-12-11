package main;
import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;

public class Consola_Admin extends Consola_Empleado {

    static Empleado empleado;

    public static void mostrarOpciones(Sistema sistema, Empleado empleado) {
        System.out.println("\n");
        System.out.println("Hola! Bienvenido a la consola Admin, tu tienes todo el poder ^-^");
        while (true) {
            System.out.println("\n");
            System.out.println("Ingresa una de las siguientes opciones:");
            System.out.println("    0 --> Volver al menu principal");
            System.out.println("    1 --> Registrar conductores adicionales");
            System.out.println("    2 --> Mandar Auto a limpieza");
            System.out.println("    3 --> Mandar Auto a mantenimiento");
            System.out.println("    4 --> Registrar pago Final");
            System.out.println("    5 --> Crear empleado");
            System.out.println("    6 --> Agregar nuevo auto");
            System.out.println("    7 --> Eliminar auto");
            System.out.println("    8 --> Agregar seguro");
            System.out.println("    9 --> Agregar tarifas");
            System.out.println("    10--> generar historial de un auto");

            System.out.println("\n");
            String respuesta = input("--> ");

            if (respuesta.equals("0")) {
                break;
            } else if (respuesta.equals("1")) {
                registrarConductoresAdicionales(sistema);
            } else if (respuesta.equals("2")) {
                mandarALimpieza(sistema);
            } else if (respuesta.equals("3")) {
                mandarAMantenimiento(sistema);
            } else if (respuesta.equals("4")) {
                registrarPagoFinal(sistema);
            } else if (respuesta.equals("5")) {
                crearEmpleado(sistema, empleado);
            } else if (respuesta.equals("6")) {
                agregarAuto(sistema);
            } else if (respuesta.equals("7")) {
                eliminarAuto(sistema);
            } else if (respuesta.equals("8")) {
                agregarSeguro(sistema);
            } else if (respuesta.equals("9")) {
                agregarTarifa(sistema);
            } else if (respuesta.equals("10")) {
                generarHistorial(sistema);
            }

        }
    }

    private static void generarHistorial(Sistema sistema) {
        String respuesta;
        String textLog = "";

        System.out.println("\n");
        System.out.println("Ingresa la placa del auto que quieres ver su historial");
        String placa;
        while (true) {
            System.out.println("\n");
            placa = input("--> ");
            if (sistema.getInventario().getAuto(placa) != null) {
                break;
            } else {
                System.out.println("\n");
                System.out.println("Ese carro no existe, digita otra vez la placa");
            }
        }

        ArrayList<Alquiler> alquileres = sistema.getInventario().getAuto(placa).getHistorialDeAlquileres();

        for (Alquiler alquiler : alquileres) {
            String path = alquiler.getFactura().getPath();
            String fileContent;
            try {
                fileContent = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
                textLog += "\n" + fileContent;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String filePath = "Entrega 3\\AlquilerYReservas\\Base de Datos\\logDeVehiculos\\" + placa + ".txt";

        try {
            FileWriter fileWriter = new FileWriter(filePath);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write("-------------------------");
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            bufferedWriter.write("HISTORIAL DE ALQUILERES DEL AUTO " + placa);
            bufferedWriter.newLine();
            bufferedWriter.write(textLog);
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            bufferedWriter.write("-------------------------");
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Desktop desktop = Desktop.getDesktop();

            if (desktop.isSupported(Desktop.Action.OPEN)) {
                File archivo = new File(filePath);

                desktop.open(archivo);
            } else {
                System.out.println("Lo sentimos, parace que en tu computador no se pueden abrir archivos :(");
                System.out.println(
                        "Puedes consultar la factura en la carpeta 'logDeVehiculos' que se encuentra dentro del directorio 'Base de Datos'");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void agregarTarifa(Sistema sistema) {
        String respuesta;

        System.out.println("\n");
        System.out.println("Escoge para que tipo de auto quieres poner una tarifa");
        ArrayList<String> categorias = sistema.getInventario().getArrayCategorias();
        for (int i = 0; i < categorias.size(); i++) {
            System.out.println("    " + i + " --> " + categorias.get(i));
        }
        System.out.println("Escoge cual quieres :)");

        int numeroCategoria;
        while (true) {
            System.out.println("\n");
            respuesta = input("--> ");
            try {
                numeroCategoria = Integer.parseInt(respuesta);
                if (0 <= numeroCategoria && categorias.size() - 1 >= numeroCategoria) {
                    break;
                } else {
                    System.out.println("Escoge un opcion valida >:v");
                }
            } catch (Exception e) {
                System.out.println("Escoge un opcion valida >:v");
                continue;
            }
        }
        String categoria = categorias.get(numeroCategoria);

        System.out.println("\n");
        System.out.println("Ingresa el valor diario de la tarifa");

        int valor;
        while (true) {
            System.out.println("\n");
            respuesta = input("--> ");
            try {
                valor = Integer.parseInt(respuesta);
                break;
            } catch (Exception e) {
                System.out.println("El valor debe ser un numero >:v");
            }
        }

        System.out.println("\n");
        System.out.println("Muy bien! Las tarifas estas activas por un periodo de tiempo (ej: Temporada alto o baja)");
        System.out.println("Vamos a seleccionar un rango de fechas donde la tarifa estara activa!");
        System.out.println("Escoge la FECHA INICIAL 'yyyy-MM-dd' ej: '2003-09-05'");

        LocalDateTime fechaInicial;
        while (true) {
            System.out.println("\n");
            String fechaTexto = input("--> ");

            try {
                fechaInicial = sistema.stringToDateTime(fechaTexto + " 00:00");
                break;
            } catch (Exception e) {
                System.out.println("\n");
                System.out.println("ERROR! Tienes que respetar el formato >:v");
                System.out.println("Intentalo de nuevo ^-^");
            }
        }

        System.out.println("\n");
        System.out.println("Ahora escoge la FECHA FINAL");

        LocalDateTime fechaFinal;
        while (true) {
            System.out.println("\n");
            String fechaTexto = input("--> ");

            try {
                fechaFinal = sistema.stringToDateTime(fechaTexto + " 00:00");
                break;
            } catch (Exception e) {
                System.out.println("\n");
                System.out.println("ERROR! Tienes que respetar el formato >:v");
                System.out.println("Intentalo de nuevo ^-^");
            }
        }

        sistema.agregarTarifa(categoria, valor, fechaInicial, fechaFinal);

        System.out.println("\n");
        System.out.println("Se agrego exitosamente la tarifa!");
        System.out.println("Ingresa cualquier caracter para volver al menu");
        System.out.println("\n");
        String nada = input("--> ");
    }

    private static void agregarSeguro(Sistema sistema) {
        String respuesta;

        System.out.println("\n");
        System.out.println("Ingresa el nombre de un seguro nuevo o uno ya existente:");
        System.out.println("\n");
        String seguro = input("--> ");

        System.out.println("\n");
        System.out.println("Ingresa el valor diario del seguro");

        int valor;
        while (true) {
            System.out.println("\n");
            respuesta = input("--> ");
            try {
                valor = Integer.parseInt(respuesta);
                break;
            } catch (Exception e) {
                System.out.println("El valor debe ser un numero >:v");
            }
        }

        sistema.agregarSeguro(seguro, valor);

        System.out.println("\n");
        System.out.println("Se elimino exitosamente el seguro!");
        System.out.println("Ingresa cualquier caracter para volver al menu");
        System.out.println("\n");
        String nada = input("--> ");
    }

    private static void eliminarAuto(Sistema sistema) {
        System.out.println("\n");
        System.out.println("Digita la placa del auto que quieres eliminar");

        String placa;
        while (true) {
            System.out.println("\n");
            placa = input("--> ");
            if (sistema.getInventario().getAuto(placa) != null) {
                break;
            } else {
                System.out.println("\n");
                System.out.println("Ese carro no existe, digita otra vez la placa");
            }
        }

        sistema.eliminarAuto(sistema.getInventario().getAuto(placa));

        System.out.println("\n");
        System.out.println("Se elimino exitosamente el auto!");
        System.out.println("Ingresa cualquier caracter para volver al menu");
        System.out.println("\n");
        String nada = input("--> ");
    }

    public static void agregarAuto(Sistema sistema) {
        String respuesta;
        // atributos del carro
        String categoria;
        String placa;
        String marca;
        String modelo;
        String color;
        String transmision;
        Sede sedeActual;
        boolean mantenimiento;
        boolean limpieza;
        boolean disponible;
        LocalDateTime fechaEstimadaDisponible;
        ArrayList<Alquiler> historialDeAlquileres;
        String tipoVehiculo;
        String primaAdicional;

        System.out.println("\n");
        System.out.println("Bien, te vamos pedir toda la informacion del auto para poder registrarlo");

        System.out.println("\n");
        System.out.println("Ingresa la categoria del auto");
        System.out.println("Solo tenemos lujo, van, y small");
        System.out.println("Si agregas uno que no existe, tambiem debes crear su tarifa, o la aplicacion fallara");
        System.out.println("\n");
        categoria = input("--> ");

        System.out.println("\n");
        System.out.println("Ingresa la placa del auto");
        System.out.println("\n");
        placa = input("--> ");

        System.out.println("\n");
        System.out.println("Ingresa la marca del auto");
        System.out.println("\n");
        marca = input("--> ");

        System.out.println("\n");
        System.out.println("Ingresa el modelo del auto");
        System.out.println("\n");
        modelo = input("--> ");

        System.out.println("\n");
        System.out.println("Ingresa el color del auto");
        System.out.println("\n");
        color = input("--> ");

        System.out.println("\n");
        System.out.println("Ingresa el tipo de transmision del auto (automatico o manual)");
        System.out.println("\n");
        transmision = input("--> ");

        System.out.println("\n");
        System.out.println("Ingresa el tipo de vehiculo: \n1. Automóvil \n2. Moto \n3. atv \n4. Camion \n5. Bicicleta eléctrica \n6. Bus");

        ArrayList<String> tiposVehiculos = sistema.getTiposVehiculoStrings();
        for (int i = 0; i < tiposVehiculos.size(); i++) {
            System.out.println("(" + (i+1) + ") " + tiposVehiculos.get(i));
        }
        System.out.println("\n");
        while (true) {
            System.out.println("\n");
            tipoVehiculo = input("--> ");
            try {
                int opcion = Integer.parseInt(tipoVehiculo);
                if(opcion < 1 || opcion > 6){
                    throw new Exception("La opcion debe ser un numero entre 1 y 6");
                }else{
                    tipoVehiculo = tiposVehiculos.get(opcion - 1);
                    break;
                }
                
            } catch (Exception e) {
                System.out.println("\n");
                System.out.println("Escoge un opcion valida >:v (Debe ser un número entre 1 y 6)");
                continue;
            }
        }


        System.out.println("\n");
        System.out.println("Escoge la sede donde lo quieres ubicar");
        ArrayList<String> sedes = new ArrayList<>(sistema.getSedes().keySet());
        for (int i = 0; i < sedes.size(); i++) {
            System.out.println("    " + i + " --> " + sedes.get(i));
        }

        int numeroSede;
        while (true) {
            System.out.println("\n");
            respuesta = input("--> ");
            try {
                numeroSede = Integer.parseInt(respuesta);
                if (0 <= numeroSede && sedes.size() - 1 >= numeroSede) {
                    break;
                } else {
                    System.out.println("\n");
                    System.out.println("Escoge un opcion valida >:v");
                }
            } catch (Exception e) {
                System.out.println("\n");
                System.out.println("Escoge un opcion valida >:v");
                continue;
            }
        }
        sedeActual = sistema.getSedes().get(sedes.get(numeroSede));
        mantenimiento = false;
        limpieza = false;
        disponible = true;
        fechaEstimadaDisponible = LocalDateTime.now();
        historialDeAlquileres = new ArrayList<>();

        Auto auto = new Auto(categoria, placa, marca, modelo, color, transmision, sedeActual, mantenimiento, limpieza,
                disponible, fechaEstimadaDisponible, historialDeAlquileres,tipoVehiculo);

        sistema.agregarAuto(auto);

        System.out.println("\n");
        System.out.println("Muy bien se agrego el nuevo auto!");
        System.out.println("Ingresa cualquier caracter para volver al menu");
        System.out.println("\n");
        String nada = input("--> ");
    }

}
