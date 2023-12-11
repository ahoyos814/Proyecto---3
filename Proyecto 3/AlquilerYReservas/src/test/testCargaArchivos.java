package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import org.junit.Test;

import main.*;


public class testCargaArchivos {

    static ArrayList<String> paths = new ArrayList<String>();

    public void inicializarPaths_GoodData(){
        paths.add( "Entrega 3\\AlquilerYReservas\\Base de Datos\\test_data\\good_data\\Usuarios.txt");
        paths.add( "Entrega 3\\AlquilerYReservas\\Base de Datos\\test_data\\good_data\\Sedes.txt");
        paths.add( "Entrega 3\\AlquilerYReservas\\Base de Datos\\test_data\\good_data\\Autos.txt");
        paths.add( "Entrega 3\\AlquilerYReservas\\Base de Datos\\test_data\\good_data\\Seguros.txt");
        paths.add( "Entrega 3\\AlquilerYReservas\\Base de Datos\\test_data\\good_data\\Tarifas.txt");
        paths.add( "Entrega 3\\AlquilerYReservas\\Base de Datos\\test_data\\good_data\\Alquileres.txt");
        paths.add( "Entrega 3\\AlquilerYReservas\\Base de Datos\\test_data\\good_data\\Pagos.txt");
    }

    public void inicializarPaths_BadData(){
        paths.add( "Entrega 3\\AlquilerYReservas\\Base de Datos\\test_data\\bad_data\\Usuarios.txt");
        paths.add( "Entrega 3\\AlquilerYReservas\\Base de Datos\\test_data\\bad_data\\Sedes.txt");
        paths.add( "Entrega 3\\AlquilerYReservas\\Base de Datos\\test_data\\bad_data\\Autos.txt");
        paths.add( "Entrega 3\\AlquilerYReservas\\Base de Datos\\test_data\\bad_data\\Seguros.txt");
        paths.add( "Entrega 3\\AlquilerYReservas\\Base de Datos\\test_data\\bad_data\\Tarifas.txt");
        paths.add( "Entrega 3\\AlquilerYReservas\\Base de Datos\\test_data\\bad_data\\Alquileres.txt");
        paths.add( "Entrega 3\\AlquilerYReservas\\Base de Datos\\test_data\\bad_data\\Pagos.txt");
    }

    // Cargas exitosas
    @Test
    public void testCargaUsuarios (){
        inicializarPaths_GoodData();
        Sistema sistema = new Sistema(paths, true);
        try {
            sistema.cargarUsuarios(paths.get(0));
            int tamañoReal = sistema.getUsuarios().size();
            int tamañoEsperado = 12;
            assertEquals( tamañoEsperado, tamañoReal);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Test
    public void testCargaSedes (){
        inicializarPaths_GoodData();
        Sistema sistema = new Sistema(paths, true);
        try {
            sistema.cargarSedes(paths.get(1));
            int tamañoReal = sistema.getSedes().size();
            int tamañoEsperado = 3;
            assertEquals( tamañoEsperado, tamañoReal);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

    @Test
    public void testCargaAutos (){
        inicializarPaths_GoodData();
        Sistema sistema = new Sistema(paths, true);
        try {
            sistema.cargarSedes(paths.get(1));
            sistema.cargarAutos(paths.get(2));
            int tamañoReal = sistema.getInventario().getAutos().size();
            int tamañoEsperado = 3;
            assertEquals( tamañoEsperado, tamañoReal);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void testCargaSeguros (){
        inicializarPaths_GoodData();
        Sistema sistema = new Sistema(paths, true);
        try {
            sistema.cargarSeguros(paths.get(3));
            int tamañoReal = sistema.getSeguros().size();
            int tamañoEsperado = 3;
            assertEquals( tamañoEsperado, tamañoReal);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void testCargaTarifas (){
        inicializarPaths_GoodData();
        Sistema sistema = new Sistema(paths, true);
        try {
            sistema.cargarTarifas(paths.get(4));
            int tamañoReal = sistema.getTarifas().size();
            int tamañoEsperado = 3;
            assertEquals( tamañoEsperado, tamañoReal);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void testCargaAlquileres (){
        inicializarPaths_GoodData();
        Sistema sistema = new Sistema(paths, true);
        try {
            sistema.cargarAlquileres(paths.get(5));
            int tamañoReal = sistema.getHistorial().size();
            int tamañoEsperado = 5;
            assertEquals( tamañoEsperado, tamañoReal);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

    @Test
    public void testCargaPagos (){
        inicializarPaths_GoodData();
        Sistema sistema = new Sistema(paths, true);
        try {
            sistema.cargarPagos(paths.get(6));
            int tamañoReal = sistema.getPagos().size();
            int tamañoEsperado = 4;
            assertEquals( tamañoEsperado, tamañoReal);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }        


    // Cargas fallidas
    
    @Test
    public void testCargaUsuarios_bad (){
        inicializarPaths_BadData();
        Sistema sistema = new Sistema(paths, true);
        assertThrows(Exception.class, ()-> sistema.cargarUsuarios(paths.get(0)));
        
    }

    @Test
    public void testCargaSedes_bad (){
        inicializarPaths_BadData();
        Sistema sistema = new Sistema(paths, true);
        assertThrows( Exception.class, () -> sistema.cargarSedes(paths.get(1)));
    }

    @Test
    public void testCargaAutos_bad (){
        inicializarPaths_BadData();
        Sistema sistema = new Sistema(paths, true);
        try {
            inicializarPaths_GoodData();
            sistema.cargarSedes(paths.get(1));
            inicializarPaths_BadData();

            assertThrows( Exception.class, () -> sistema.cargarAutos(paths.get(2)));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void testCargaSeguros_bad (){
        inicializarPaths_BadData();
        Sistema sistema = new Sistema(paths, true);
        assertThrows( Exception.class, () -> sistema.cargarSeguros(paths.get(3)));
    }

    @Test
    public void testCargaTarifas_bad (){
        inicializarPaths_BadData();
        Sistema sistema = new Sistema(paths, true);
        assertThrows( Exception.class, () -> sistema.cargarTarifas(paths.get(4)));

    }

    @Test
    public void testCargaAlquileres_bad (){
        inicializarPaths_BadData();
        Sistema sistema = new Sistema(paths, true);
        assertThrows(Exception.class, () -> sistema.cargarAlquileres(paths.get(5)));
    }

    @Test
    public void testCargaPagos_bad (){
        inicializarPaths_BadData();
        Sistema sistema = new Sistema(paths, true);
        assertThrows(Exception.class, () -> sistema.cargarPagos(paths.get(6)));

    }      





    
}