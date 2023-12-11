package main;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;


public class GeneradorFacturas {

    private int id;
    private String nombre;
    private int numeroContacto;
    private String sedeInicial;
    private String sedeFinal;
    private String fechaInicial;
    private String fechaFinal;
    private long diasReserva;
    private String categoria;
    private int tarifaCategoria;
    private int tarifaDiariaTotal;
    private String seguro;
    private int tarifaSeguro;
    private int recargo;
    private long costoTotal;

    

    public String filePath = "Entrega 3\\AlquilerYReservas\\Base de Datos\\Facturas\\";
    public String firmaPath = "Entrega 3\\AlquilerYReservas\\Base de Datos\\Firmas\\firma_admin.jpg";



    
    public GeneradorFacturas(String filename,int id, String nombre, int numeroContacto, String sedeInicial, String sedeFinal, String fechaInicial, String fechaFinal, long diasReserva, String categoria, int tarifaCategoria, String seguro, int tarifaSeguro, int recargo, int tarifaDiariaTotal, long costoTotal) {
        this.id = id;
        this.nombre = nombre;
        this.numeroContacto = numeroContacto;
        this.sedeInicial = sedeInicial;
        this.sedeFinal = sedeFinal;
        this.fechaInicial = fechaInicial;
        this.fechaFinal = fechaFinal;
        this.diasReserva = diasReserva;
        this.categoria = categoria;
        this.tarifaCategoria = tarifaCategoria;
        this.seguro = seguro;
        this.tarifaSeguro = tarifaSeguro;
        this.tarifaDiariaTotal = tarifaDiariaTotal;
        this.recargo = recargo;
        this.costoTotal = costoTotal;
        addfileName(filename);
    }

    public static void main (String[] args) {
        GeneradorFacturas factura = new GeneradorFacturas("test",0, "hanni", 4, "Sede_Cajica", "Sede_Cajica", "2003-09-05 00:00", "2003-09-06 00:00", 1, "lujo", 2000, "Seguro de Lesiones Personales",2000, 0, 7000, 7000);
        factura.crearFacturaPdf();
    }
    

    public void crearFacturaPdf()  {

        try {
            int start = 750;

            PDDocument document = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.beginText();
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER_BOLD), 14);
            contentStream.newLineAtOffset(50, start);
            contentStream.showText("FACTURA ELECTRÓNICA - ALQUILER DE VEHÍCULOS");
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER_BOLD), 12);
            contentStream.newLineAtOffset(50, start-40);
            contentStream.showText("ID de ALQUILER:");
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER), 12);
            contentStream.showText(" " + id);
            
            escibirLinea(contentStream, "Nombre", getNombre());
            escibirLinea(contentStream, "Numero de contacto", String.valueOf(getNumeroContacto()));
            escibirLinea(contentStream, "Sede Incial", getSedeInicial());
            escibirLinea(contentStream, "Sede Final", getSedeFinal());
            escibirLinea(contentStream, "Fecha Incial", getFechaInicial());
            escibirLinea(contentStream, "Fecha final", getFechaFinal());
            escibirLinea(contentStream, "-----> En total son ", String.valueOf(getDiasReserva()) + " dias de reserva");
    
            

            
        
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER_BOLD), 12);
            contentStream.newLineAtOffset(0, -(40));
            contentStream.showText("RESUMEN DE COMPRA");
            escibirLinea(contentStream, "Categoria", getCategoria() + " $" + getTarifaCategoria());
            escibirLinea(contentStream, "Seguro", getSeguro() + " $" + getTarifaSeguro());
            escibirLinea(contentStream, "Recargo por entrega en otra sede", "$" + getRecargo());
            escibirLinea(contentStream, "Tarifa Diaria Total", "$" + getTarifaDiariaTotal());
            escibirLinea(contentStream, "COSTO TOTAL", "$" + getCostoTotal());
        

            contentStream.newLineAtOffset(0, -(20));

            contentStream.endText();

            PDImageXObject image = PDImageXObject.createFromFile(firmaPath, document);
            float scale = 0.5f; // Ajusta según sea necesario
            contentStream.drawImage(image,50,200, image.getWidth() * scale, image.getHeight() * scale);


            contentStream.close();

            document.save(filePath);
            document.close();

            System.out.println("PDF creado correctamente en " + filePath );
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void escibirLinea(PDPageContentStream contentStream, String titulo, String contenido  ) throws IOException{

        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER_BOLD), 12);
        contentStream.newLineAtOffset(0, -(20));
        contentStream.showText(titulo + ": ");
        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER), 12);
        contentStream.showText(contenido);

    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getNumeroContacto() {
        return numeroContacto;
    }

    public String getSedeInicial() {
        return sedeInicial;
    }

    public String getSedeFinal() {
        return sedeFinal;
    }

    public String getFechaInicial() {
        return fechaInicial;
    }

    public String getFechaFinal() {
        return fechaFinal;
    }

    public long getDiasReserva() {
        return diasReserva;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getTarifaCategoria() {
        return tarifaCategoria;
    }

    public String getSeguro() {
        return seguro;
    }

     public int getTarifaSeguro() {
        return tarifaSeguro;
    }

    public int getRecargo() {
        return recargo;
    }

    public long getCostoTotal() {
        return costoTotal;
    }

    public int getTarifaDiariaTotal() {
        return tarifaDiariaTotal;
    }

    public void addfileName(String fileName) {
        this.filePath = filePath + fileName + ".pdf";
    }

    public File getFactura() {
        return new File(filePath);
    }
    
    

    
}
