
package Backend.revistas;

import JPA.Controladora;
import JPA.Ingreso;
import JPA.Revista;
import Reportes.jasperReports.ReportePagosJasperReport;
import Reportes.jasperReports.ReporteSuscripcionesJasperReport;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import respuetas.Editor.RespuestaReportePagos;

/**
 *
 * @author carlosrodriguez
 */
public class ConfiguracionReportePagos {
    
    
    
       private Controladora controladora = new Controladora();

    RespuestaReportePagos respuesta = new RespuestaReportePagos();

    public RespuestaReportePagos obtenerRevistas(String idUsuario) {
        List<Revista> revistas = this.controladora.obtenerRevistas();

        List<Revista> revistasFiltradas = revistas.stream()
                .filter(revista -> revista.getAutor().equals(idUsuario))
                .collect(Collectors.toList());

        respuesta.setRevistas(revistasFiltradas);
        return respuesta;

    }
    
    
     public RespuestaReportePagos obtenerPagos(String idUsuario, String fechaInicio, String fechaFin, String idRevista) throws IOException {
        System.out.println(idRevista + ":::::::::::::::::::::::::::::::::::::::::::::::");
        if (!idRevista.isEmpty()) {
                System.out.println(idRevista + ":::::::::::::::::::::::::::::::::::::::::::::::Entroooo");
            obtenerPagosDeRevista(idUsuario, fechaInicio, fechaFin, idRevista);
        } else {
            obtenerComentariosGenerales(idUsuario, fechaInicio, fechaFin);
        }
        crearReporte();
        return respuesta;
    }

    public void obtenerComentariosGenerales(String idUsuario, String fechaInicio, String fechaFin) {

        // Obtén todas las revistas y comentarios
        List<Revista> revistas = this.controladora.obtenerRevistas();
        List<Ingreso> pagos = this.controladora.obtenerListapagos();

        // Filtra las revistas que pertenecen al usuario especificado
        List<Revista> revistasFiltradas = revistas.stream()
                .filter(revista -> revista.getAutor().equals(idUsuario))
                .collect(Collectors.toList());

        // Obtén los IDs de las revistas filtradas (ahora son String)
        List<String> idsRevistasFiltradas = revistasFiltradas.stream()
                .map(Revista::getIdRevista) // Extrae el ID de cada revista (tipo String)
                .collect(Collectors.toList());

        // Convertir las fechas de inicio y fin a LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");  // Asegúrate de que el formato de la fecha sea correcto
        LocalDate inicio = LocalDate.parse(fechaInicio, formatter);
        LocalDate fin = LocalDate.parse(fechaFin, formatter);

        // Filtra los comentarios cuyo getIdRevista esté en los IDs de las revistas filtradas y también por el rango de fechas
        List<Ingreso> ingresosFiltrados = pagos.stream()
                .filter(pago -> idsRevistasFiltradas.contains(pago.getIdVinculado())
                && convertirADateLocal(pago.getFecha()).isAfter(inicio.minusDays(1))
                && convertirADateLocal(pago.getFecha()).isBefore(fin.plusDays(1))) // Filtra por fechas
                .collect(Collectors.toList());

        
        for (int i = 0; i < ingresosFiltrados.size(); i++) {
            respuesta.setTotalPago(ingresosFiltrados.get(i).getMonto()+respuesta.getTotalPago());
            
        }
        respuesta.setIngresos(ingresosFiltrados);

    }

// Método auxiliar para convertir de Date a LocalDate
    private LocalDate convertirADateLocal(Date fecha) {
        if (fecha instanceof java.sql.Date) {
            return ((java.sql.Date) fecha).toLocalDate();
        } else {
            return fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
    }

    private void obtenerPagosDeRevista(String idUsuario, String fechaInicio, String fechaFin, String idRevista) {
        // Obtén todas las revistas y comentarios
        List<Revista> revistas = this.controladora.obtenerRevistas();
        List<Ingreso> pagos = this.controladora.obtenerListapagos();

        // Filtra las revistas que pertenecen al usuario especificado
        List<Revista> revistasFiltradas = revistas.stream()
                .filter(revista -> revista.getAutor().equals(idUsuario))
                .collect(Collectors.toList());

        // Obtén los IDs de las revistas filtradas (ahora son String)
        List<String> idsRevistasFiltradas = revistasFiltradas.stream()
                .map(Revista::getIdRevista) // Extrae el ID de cada revista (tipo String)
                .collect(Collectors.toList());

        // Convertir las fechas de inicio y fin a LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate inicio = LocalDate.parse(fechaInicio, formatter);
        LocalDate fin = LocalDate.parse(fechaFin, formatter);

        // Filtra los comentarios por los IDs de las revistas filtradas, el rango de fechas y el idRevista específico (si está presente)
        List<Ingreso> pagosFiltrados = pagos.stream()
                .filter(pago -> idsRevistasFiltradas.contains(pago.getIdVinculado())
                && convertirADateLocal(pago.getFecha()).isAfter(inicio.minusDays(1))
                && convertirADateLocal(pago.getFecha()).isBefore(fin.plusDays(1))
                && (idRevista == null || idRevista.isEmpty() || pago.getIdVinculado().equals(idRevista))) // Filtra por idRevista si se proporciona
                .collect(Collectors.toList());

        respuesta.setIngresos(pagosFiltrados);
    }

    
       private void crearReporte() throws FileNotFoundException, IOException {
        ReportePagosJasperReport report = new ReportePagosJasperReport();
        try {
            // Carga el archivo .jasper
            JasperReport reporte = (JasperReport) JRLoader.loadObject(new File("/home/carlosrodriguez/Documentos/Proyecto 2/Proyecto 2/BACKEND_PROYECTO_2/src/main/java/Reportes/REPORTE_PAGOS.jasper"));

            // Llenar el reporte
            JasperPrint e = JasperFillManager.fillReport(reporte, null, report.getDataSource(this.respuesta.getIngresos()));

            // Crear un ByteArrayOutputStream para almacenar el PDF
            ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();

            // Exportar el reporte a PDF dentro del ByteArrayOutputStream
            JasperExportManager.exportReportToPdfStream(e, pdfOutputStream);

            // Convertir el ByteArrayOutputStream en un arreglo de bytes
            byte[] pdfBytes = pdfOutputStream.toByteArray();

            // Convertir los bytes a Base64
            String pdfBase64 = Base64.getEncoder().encodeToString(pdfBytes);
            this.respuesta.setPdf(pdfBase64);

        } catch (JRException e) {
            e.printStackTrace();  // Imprime el error completo en la consola
        }
    }
    
}
