package main;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Auto {
    private String categoria;
    private String placa;
    private String marca;
    private String modelo;
    private String color;
    private String transmision;
    private Alquiler alquilado;
    private Sede sedeActual;
    private boolean mantenimiento;
    private boolean limpieza;
    private boolean disponible;
    private LocalDateTime fechaEstimadaDisponible;
    private ArrayList<Alquiler> historialDeAlquileres;
    private String tipoVehiculo;

    public Auto(String categoria, String placa, String marca, String modelo, String color, String transmision,
            Alquiler alquilado, Sede sedeActual, boolean mantenimiento, boolean limpieza, boolean disponible,
            LocalDateTime fechaEstimadaDisponible, ArrayList<Alquiler> historialDeAlquileres, String tipoVehiculo) {
        this.categoria = categoria;
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
        this.transmision = transmision;
        this.alquilado = alquilado;
        this.sedeActual = sedeActual;
        this.mantenimiento = mantenimiento;
        this.limpieza = limpieza;
        this.disponible = disponible;
        this.fechaEstimadaDisponible = fechaEstimadaDisponible;
        this.historialDeAlquileres = historialDeAlquileres;
        this.tipoVehiculo = tipoVehiculo;
        verificarDisponibilidad();
    }

    private void verificarDisponibilidad() {
        if (mantenimiento && LocalDateTime.now().isAfter(fechaEstimadaDisponible)) {
            mantenimiento = false;
        }

        if (limpieza & LocalDateTime.now().isAfter(fechaEstimadaDisponible)) {
            limpieza = false;
        }
    }

    public Auto(String categoria, String placa, String marca, String modelo, String color, String transmision,
            Sede sedeActual, boolean mantenimiento, boolean limpieza, boolean disponible,
            LocalDateTime fechaEstimadaDisponible, ArrayList<Alquiler> historialDeAlquileres, String tipoVehiculo) {
        this.categoria = categoria;
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
        this.transmision = transmision;
        this.sedeActual = sedeActual;
        this.mantenimiento = mantenimiento;
        this.limpieza = limpieza;
        this.disponible = disponible;
        this.fechaEstimadaDisponible = fechaEstimadaDisponible;
        this.historialDeAlquileres = historialDeAlquileres;
        this.tipoVehiculo = tipoVehiculo;
        verificarDisponibilidad();
    }


    public String getCategoria() {
        return this.categoria;
    }

    public String getPlaca() {
        return this.placa;
    }

    public String getMarca() {
        return this.marca;
    }

    public String getModelo() {
        return this.modelo;
    }

    public String getColor() {
        return this.color;
    }

    public String getTransmision() {
        return this.transmision;
    }

    public Alquiler getAlquilado() {
        return this.alquilado;
    }

    public Sede getSedeActual() {
        return this.sedeActual;
    }

    public boolean isMantenimiento() {
        return this.mantenimiento;
    }

    public boolean isLimpieza() {
        return this.limpieza;
    }

    public boolean isDisponible() {
        return this.disponible;
    }

    public LocalDateTime getFechaEstimadaDisponible() {
        return this.fechaEstimadaDisponible;
    }

    public ArrayList<Alquiler> getHistorialDeAlquileres() {
        return this.historialDeAlquileres;
    }

    public void setDisponibilidad(boolean bool) {
        disponible = bool;
    }

    public void setMantenimieto(boolean bool) {
        mantenimiento = bool;
    }

    public void setLimpieza(boolean bool) {
        limpieza = bool;
    }

    public void setFechaEstimadaDisponible(LocalDateTime fecha) {
        fechaEstimadaDisponible = fecha;
    }

    public void agregarAlquiler(Alquiler alquiler) {
        historialDeAlquileres.add(alquiler);
    }

    public File crearArchivoLog() {
        return null;
    }


    public String getTipoVehiculo() {
        return this.tipoVehiculo;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public void setAlquilado (Alquiler alquiler) {
        this.alquilado = alquiler;
    }



    public String toString() {
        return "Categoria: "  + categoria + " | " + "Tipo Vehiculo: " + tipoVehiculo + " | " + "Placa: " + placa  + " | " + "Marca: " + marca + " | " + "Modelo: " + modelo + " | " + "Color: " + color + " | " + "Transmision: " + transmision + " | " + "Sede Actual: " + sedeActual.getNombre()
                + " | " + "Mantenimiento: " + mantenimiento + " | " + "Limpieza: " + limpieza + " | " + "Disponible: " + disponible
                + " | " + "Fecha Estimada Disponible: " + fechaEstimadaDisponible ;
    }
}
