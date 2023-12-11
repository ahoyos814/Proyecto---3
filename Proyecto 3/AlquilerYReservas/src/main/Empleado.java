package main;

public class Empleado extends Usuario {


    private String autoridad;
    private String sede;

    public Empleado(String login, String password, String autoridad, String sede) {

        super(login, password);
        this.autoridad = autoridad;
        this.sede = sede;
    }

    public String getAutoridad() {
        return autoridad;
    }

    public String getSede() {
        return sede;
    }

    public String toString(){
        return "Login: " + getLogin() + "\n" +
                "Password: " + getPassword() + "\n" +
                "Autoridad: " + getAutoridad() + "\n" +
                "Sede: " + getSede() + "\n";

    }
}
