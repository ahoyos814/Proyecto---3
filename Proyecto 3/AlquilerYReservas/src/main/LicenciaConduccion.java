package main;
import java.io.File;
import java.time.LocalDateTime;

public class LicenciaConduccion {

    private int licenciaID;
    private String licenciaPaisExpedicion;
    private LocalDateTime licenciaFechaVen;
    private File licenciaFoto;

    public LicenciaConduccion(int licenciaID, String licenciaPaisExpedicion, LocalDateTime licenciaFechaVen,
            File licenciaFoto) {
        this.licenciaID = licenciaID;
        this.licenciaPaisExpedicion = licenciaPaisExpedicion;
        this.licenciaFechaVen = licenciaFechaVen;
        this.licenciaFoto = licenciaFoto;
    }

    public int getLicenciaID() {
        return licenciaID;
    }

    public String getLicenciaPaisExpedicion() {
        return licenciaPaisExpedicion;
    }

    public LocalDateTime getLicenciaFechaVen() {
        return licenciaFechaVen;
    }

    public File getLicenciaFoto() {
        return licenciaFoto;
    }
}
