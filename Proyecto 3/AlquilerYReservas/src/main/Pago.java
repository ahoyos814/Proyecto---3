package main;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Pago {
    private int idPago;
    private int alquilerAsociado;
    private double monto;
    private int cedulaCliente;
    private int numeroTarjeta;
    private LocalDateTime fechaPago;
    private String metodoPago;

    public Pago(double monto, LocalDateTime fechaPago, String metodoPago, int alquilerAsociado, int idPago, int numeroTarjeta) {
        this.monto = monto;
        this.fechaPago = fechaPago;
        this.metodoPago = metodoPago;
        this.alquilerAsociado = alquilerAsociado;
        this.idPago = idPago;
        this.numeroTarjeta = numeroTarjeta;
    }
    public double getMonto() {
        return monto;
    }
    public void setMonto(double monto) {
        this.monto = monto;
    }
    public LocalDateTime getFechaPago() {
        return fechaPago;
    }
    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
    }
    public String getMetodoPago() {
        return metodoPago;
    }
    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }
    public int getAlquilerAsociado() {
        return alquilerAsociado;
    }
    public void setAlquilerAsociado(int alquilerAsociado) {
        this.alquilerAsociado = alquilerAsociado;
    }

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public int getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(int numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String convertirFechaString(LocalDateTime fecha) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String fechaString = fecha.format(formato);
        return fechaString;
    }

    public String toString() {
        return idPago+","+alquilerAsociado+","+monto+","+numeroTarjeta+","+convertirFechaString(fechaPago)+","+metodoPago;
    }



    
    
}
