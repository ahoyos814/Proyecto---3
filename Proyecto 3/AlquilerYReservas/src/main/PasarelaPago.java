package main;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



public class PasarelaPago {

    private String nombrePasarela;
    public String pathlog = "Entrega 3\\AlquilerYReservas\\conf\\logs\\";
    public String pathPagos = "Entrega 3\\AlquilerYReservas\\Base de Datos\\Pagos.txt";

    public String formato;
    static Sistema sistema;

    public PasarelaPago(String nombrePasarela, String formato, Sistema pSistema) {
        this.nombrePasarela = nombrePasarela;
        pathlog += nombrePasarela + "." + formato;
        sistema = pSistema;
    }



    public Pago realizarPago(double monto, LocalDateTime fechaPago, String metodoPago, int alquilerAsociado, int idPago, int numeroTarjeta){
        Pago pago = new Pago(monto, fechaPago, metodoPago, alquilerAsociado, idPago, numeroTarjeta);

        String logRegistro = getNumeroTransaccion() + "," + idPago + "," + alquilerAsociado + "," + monto + "," + numeroTarjeta + "," + fechaPago.toString(); 
        agregarLinea(logRegistro, pathlog);
        sistema.guardarPago(pago);

        System.out.println("Pago realizado con exito");
        return pago;
    }

    public int getNumeroTransaccion(){
        String path = pathlog;

        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String linea = br.readLine();
            int numeroTransaccion = 0;
            while (linea != null) {
                numeroTransaccion ++ ;
                linea = br.readLine();
            }

            br.close();
            return numeroTransaccion;

        } catch (IOException e) {
            // Manejo de excepciones en caso de error de lectura
            e.printStackTrace();
            return 0;
        }
    }

    public String getNombrePasarela() {
        return nombrePasarela;
    }

    public void setNombrePasarela(String nombrePasarela) {
        this.nombrePasarela = nombrePasarela;
    }

    public void agregarLinea (String contenido, String ruta){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ruta, true))) {
            // Añadir la nueva línea al final del archivo
            writer.write(contenido);
            writer.newLine();
        } catch (IOException e) {
            // Manejo de excepciones en caso de error al escribir en el archivo
            e.printStackTrace();
        }
    }

    public LocalDateTime StringToLocalDateTime(String fecha){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        // Parsea el String a LocalDateTime usando el formatter
        LocalDateTime fechaFinal = LocalDateTime.parse(fecha, formatter);
        // Imprimir el resultado
        return fechaFinal;

    }


    
}
