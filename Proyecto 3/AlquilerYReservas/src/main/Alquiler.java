package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.time.temporal.ChronoUnit;

public class Alquiler {
    private Cliente cliente;
    private String estado;
    private Sede sedeInicio;
    private Sede sedeFinal;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFinal;
    private int idAlquiler;
    private File factura;
    private ArrayList<LicenciaConduccion> conductoresAdicionales;
    private boolean bloqueoTarjeta;
    private double monto;

    public Alquiler(Cliente cliente, String estado, Sede sedeInicio, Sede sedeFinal, LocalDateTime fechaInicio,
            LocalDateTime fechaFinal, int idAlquiler, File facutura,
            ArrayList<LicenciaConduccion> conductoresAdicionales,
            boolean bloqueoTarjeta, double monto) {
        this.cliente = cliente;
        this.estado = estado;
        this.sedeInicio = sedeInicio;
        this.sedeFinal = sedeFinal;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.idAlquiler = idAlquiler;
        this.factura = facutura;
        this.conductoresAdicionales = conductoresAdicionales;
        this.bloqueoTarjeta = bloqueoTarjeta;
        this.monto = monto;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    
    public String getEstado() {
        return this.estado;
    }

    public Sede getSedeInicio() {
        return this.sedeInicio;
    }

    public Sede getSedeFinal() {
        return this.sedeFinal;
    }

    public LocalDateTime getFechaInicio() {
        return this.fechaInicio;
    }

    public LocalDateTime getFechaFinal() {
        return this.fechaFinal;
    }

    public int getIdAlquiler() {
        return this.idAlquiler;
    }

    public File getFactura() {
        return this.factura;
    }

    public ArrayList<LicenciaConduccion> getConductoresAdicionales() {
        return this.conductoresAdicionales;
    }

    public boolean isBloqueoTarjeta() {
        return this.bloqueoTarjeta;
    }

    public void agregarConductor(LicenciaConduccion licencia) {
        conductoresAdicionales.add(licencia);
    }

    public void setBloqueoTarjeta(boolean bool) {
        bloqueoTarjeta = bool;
    }

    static public File generarYGuardarFactura(LocalDateTime fechaInicial, LocalDateTime fechaFinal,
            String categoria, int tarifaCategoria, String seguro, int tarifaSeguro,
            int tarifaDiaraTotal, int recargo, int idAlquiler, Cliente cliente, String sedeInicial,
            String sedeFinal) {
        String filePath = "Entrega 3\\AlquilerYReservas\\Base de Datos\\Facturas\\" + idAlquiler + ".txt";
        long diasTotales = ChronoUnit.DAYS.between(fechaInicial.toLocalDate(), fechaFinal.toLocalDate());
        long costoTotal = (tarifaDiaraTotal * diasTotales) + recargo;

        try {
            FileWriter fileWriter = new FileWriter(filePath);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write("-------------------------");
            bufferedWriter.newLine();
            bufferedWriter.write("ID de ALQUILER: " + idAlquiler);
            bufferedWriter.newLine();
            bufferedWriter.write("Nombre: " + cliente.getNombre());
            bufferedWriter.newLine();
            bufferedWriter.write("Numero de contacto: " + cliente.getNumeroCelular());
            bufferedWriter.newLine();
            bufferedWriter.write("Sede Incial: " + sedeInicial);
            bufferedWriter.newLine();
            bufferedWriter.write("Sede Final: " + sedeFinal);
            bufferedWriter.newLine();
            bufferedWriter.write("Fecha Incial: " + Sistema.dateTimeToString(fechaInicial));
            bufferedWriter.newLine();
            bufferedWriter.write("Fecha final: " + Sistema.dateTimeToString(fechaFinal));
            bufferedWriter.newLine();
            bufferedWriter.write("      --> En total son " + diasTotales + " dias de reserva");
            bufferedWriter.newLine();
            bufferedWriter.write("RESUMEN DE COMPRA");
            bufferedWriter.newLine();
            bufferedWriter.write("      --> Categoria: " + categoria + " $" + tarifaCategoria);
            bufferedWriter.newLine();
            bufferedWriter.write("      --> Seguro: " + seguro + " $" + tarifaSeguro);
            bufferedWriter.newLine();
            bufferedWriter.write("      --> Recargo por entrega en otra sede: " + " $" + recargo);
            bufferedWriter.newLine();
            bufferedWriter.write("      Tarifa Diaria Total: " + " $" + tarifaDiaraTotal);
            bufferedWriter.newLine();
            bufferedWriter.write("      COSTO TOTAL: " + " $" + costoTotal);
            bufferedWriter.newLine();
            bufferedWriter.write("-------------------------");
            bufferedWriter.close();

            GeneradorFacturas factura = new GeneradorFacturas( Integer.toString(idAlquiler),idAlquiler, cliente.getNombre(), cliente.getNumeroCelular(), sedeInicial, sedeFinal, Sistema.dateTimeToString(fechaInicial), Sistema.dateTimeToString(fechaFinal), diasTotales, categoria, tarifaCategoria, seguro, tarifaSeguro, recargo, tarifaDiaraTotal, costoTotal);
            factura.crearFacturaPdf();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new File(filePath);
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public double getMonto() {
        return this.monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }
    public String toString(){
        return "ID Alquiler: " + getIdAlquiler() + "\n" +
                "Cliente: " + getCliente().getNombre() + "\n" +
                "Estado: " + getEstado() + "\n" +
                "Sede Inicial: " + getSedeInicio().getNombre() + "\n" +
                "Sede Final: " + getSedeFinal().getNombre() + "\n" +
                "Fecha Inicial: " + getFechaInicio() + "\n" +
                "Fecha Final: " + getFechaFinal() + "\n" +
                "Monto: " + getMonto() + "\n";
    }


}
