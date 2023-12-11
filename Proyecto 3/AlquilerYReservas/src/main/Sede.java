package main;
import java.util.HashMap;

public class Sede {

    private String nombre;
    private String ubicacion;
    private String[] horarios;
    private Empleado adminLocal;
    private Inventario inventario;
    private HashMap<String, Empleado> empleados;

    public Sede(String nombre, String ubicacion, String[] horarios, Empleado adminLocal,
            Inventario inventario, HashMap<String, Empleado> empleados) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.horarios = horarios;
        this.adminLocal = adminLocal;
        this.inventario = inventario;
        this.empleados = empleados;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Inventario getInventario() {
        return this.inventario;
    }

    public String getUbicacion() {
        return this.ubicacion;
    }

    public String[] getHorarios() {
        return this.horarios;
    }

    public Empleado getAdminLocal() {
        return this.adminLocal;
    }

    public HashMap<String, Empleado> getEmpleados() {
        return this.empleados;
    }

    public void setNombre (String nombre) {
        this.nombre = nombre;
    }
}
