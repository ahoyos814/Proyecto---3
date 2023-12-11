package main;
import java.io.File;
import java.time.LocalDateTime;

public class Cliente extends Usuario {

 
    private String nombre;
    private int numeroCelular;
    private String nacionalidad;
    private File cedulaFoto;

    // LICENCIA
    private LicenciaConduccion licencia;
    // Datos para la licencia
    // private int licenciaID;
    // private String licenciaPaisExpedicion;
    // private LocalDateTime licenciaFechaVen;
    // private File licenciaFoto;

    // METODO DE PAGO
    private MedioDePago tarjetaCredito;
    // Datos para el medio de pago
    // private String tipo;
    // private int numeroTarjeta;
    // private LocalDateTime fechaVencimiento;
    // private int codigoSeguridad;

    public Cliente(String login, String password, String nombre, int numeroCelular, String nacionalidad,
            File cedulaFoto,
            int licenciaID, String licenciaPaisExpedicion, LocalDateTime licenciaFechaVen,
            File licenciaFoto, String tipo, int numeroTarjeta, LocalDateTime fechaVencimiento,
            int codigoSeguridad) {

        super(login, password);
        this.nombre = nombre;
        this.numeroCelular = numeroCelular;
        this.nacionalidad = nacionalidad;
        this.cedulaFoto = cedulaFoto;

        this.licencia = new LicenciaConduccion(licenciaID, licenciaPaisExpedicion, licenciaFechaVen, licenciaFoto);

        this.tarjetaCredito = new MedioDePago(tipo, numeroTarjeta, fechaVencimiento, codigoSeguridad);

    }

    public Cliente(String login, String password) {
        super(login, password);
    }


    public String getNombre() {
        return nombre;
    }

    public int getNumeroCelular() {
        return numeroCelular;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public File getCedulaFoto() {
        return cedulaFoto;
    }

    public LicenciaConduccion getLicencia() {
        return licencia;
    }

    public MedioDePago getTarjetaCredito() {
        return tarjetaCredito;
    }

    public String toString(){
        return "Nombre: " + this.nombre + "\n" +
                "Número de celular: " + this.numeroCelular + "\n" +
                "Nacionalidad: " + this.nacionalidad + "\n" +
                "Cédula: " + this.cedulaFoto.getName() + "\n" +
                "Licencia: " + this.licencia.getLicenciaFoto().getName() + "\n" +
                "Tarjeta de crédito: " + this.tarjetaCredito.getTipo() + "\n";
                
    }
}
