package main;

import java.time.LocalDateTime;

public class MedioDePago {
    private String tipo;
    private int numeroTarjeta;
    private LocalDateTime fechaVencimiento;
    private int codigoSeguridad;

    public MedioDePago(String tipo, int numeroTarjeta, LocalDateTime fechaVencimiento, int codigoSeguridad) {
        this.tipo = tipo;
        this.numeroTarjeta = numeroTarjeta;
        this.fechaVencimiento = fechaVencimiento;
        this.codigoSeguridad = codigoSeguridad;
    }

    public String getTipo() {
        return tipo;
    }

    public int getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public LocalDateTime getFechaVencimiento() {
        return fechaVencimiento;
    }

    public int getCodigoSeguridad() {
        return codigoSeguridad;
    }
}
