package Reportes;

import Reportes.jasperReports.ReporteComentariosJasperReport;
import JPA.Controladora;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import javax.swing.JOptionPane;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

public class Reporte {
Controladora controladora=new Controladora();
public Reporte() {
         try {
        // Carga el archivo .jasper
         ReporteComentariosJasperReport report = new ReporteComentariosJasperReport();
        JasperReport reporte = (JasperReport) JRLoader.loadObject(new File("/home/carlosrodriguez/JaspersoftWorkspace/MyReports/REPORTE_COMENTARIOS.jasper"));

        // Llenar el reporte
       // JasperPrint e = JasperFillManager.fillReport(reporte, null, report.getDataSource(this.controladora.obtenerListaComentarios()));

        // Crear un ByteArrayOutputStream para almacenar el PDF
        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
        
        // Exportar el reporte a PDF dentro del ByteArrayOutputStream
//        JasperExportManager.exportReportToPdfStream(e, pdfOutputStream);

        // Convertir el ByteArrayOutputStream en un arreglo de bytes
        byte[] pdfBytes = pdfOutputStream.toByteArray();

        // Convertir los bytes a Base64
        String pdfBase64 = Base64.getEncoder().encodeToString(pdfBytes);
     

        // Guardar el archivo PDF en el sistema de archivos
        String pdfFilePath = "/home/carlosrodriguez/ReporteComentarios.pdf";
        try (FileOutputStream fileOutputStream = new FileOutputStream(pdfFilePath)) {
            fileOutputStream.write(pdfBytes);
        }

        // Confirmación de que se guardó correctamente
        JOptionPane.showMessageDialog(null, "Reporte PDF generado correctamente, guardado en " + pdfFilePath + " y convertido a Base64.");

    } catch (JRException | IOException e) {
        e.printStackTrace();  // Imprime el error completo en la consola
    }
     
    }

    public static void main(String[] args) {
        new Reporte();
    }
}
