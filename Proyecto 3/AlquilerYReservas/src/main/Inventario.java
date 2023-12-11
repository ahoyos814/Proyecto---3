package main;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Inventario {
    HashMap<String, Auto> autosTotales;
    HashMap<String, HashMap<String, Auto>> categorias;

    public Inventario() {
        autosTotales = new HashMap<>();
        categorias = new HashMap<>();
    }

    public void addAuto(Auto auto) {
        String categoria = auto.getCategoria();
        String placa = auto.getPlaca();
        if (categorias.get(categoria) == null) {
            HashMap<String, Auto> carrosPorCategoria = new HashMap<>();
            categorias.put(categoria, carrosPorCategoria);
        }
        autosTotales.put(placa, auto);
        categorias.get(categoria).put(placa, auto);
    }

    public ArrayList<String> getArrayCategorias() {
        return new ArrayList(categorias.keySet());
    }

    public HashMap<String, Auto> getAutos() {
        return this.autosTotales;
    }

    public ArrayList<Auto> getAutosPorCategoria(String categoria) {
        System.out.println(categoria);
        return new ArrayList(categorias.get(categoria).values());
    }

    public HashMap<String, HashMap<String, Auto>> getCategorias() {
        return this.categorias;
    }


    public Auto getAuto(String placa) {
        return autosTotales.get(placa);
    }

    public void eliminarAuto(Auto auto) {
        autosTotales.remove(auto.getPlaca());
        String categoria = auto.getCategoria();
        categorias.get(categoria).remove(auto.getPlaca());

    }
}
