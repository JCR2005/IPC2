package Backend.administracion;

import JPA.Comentario;
import JPA.Controladora;
import JPA.Revista;
import Reportes.jasperReports.ReporteTop5ComentariosJasperReport;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import respuetas.Administrador.RespuestaReporteRevistasMasComentadas;

/**
 * Clase encargada de generar el reporte de las 5 revistas con más comentarios
 * dentro de un rango de fechas determinado.
 * 
 * @author carlosrodriguez
 */
public class ConfiguracionReporteRevistasMasComentadas {

    private RespuestaReporteRevistasMasComentadas respuesta = new RespuestaReporteRevistasMasComentadas();
    private Controladora controladora = new Controladora();

    /**
     * Obtiene los ingresos y genera el reporte en base al rango de fechas proporcionado.
     * 
     * @param fechaInicio Fecha de inicio en formato "yyyy-MM-dd".
     * @param fechaFin Fecha de fin en formato "yyyy-MM-dd".
     * @return Respuesta con los datos del reporte.
     * @throws IOException Si ocurre un error durante la generación del reporte.
     */
    public RespuestaReporteRevistasMasComentadas obtenerIngresos(String fechaInicio, String fechaFin) throws IOException {
        obtenerPagosGenerales(fechaInicio, fechaFin); // Obtiene la información de los pagos generales
        crearReporte(); // Crea el reporte en PDF
        return respuesta; // Retorna la respuesta con los datos del reporte
    }

    /**
     * Obtiene los comentarios y revistas dentro del rango de fechas especificado,
     * y determina las 5 revistas más comentadas.
     * 
     * @param fechaInicio Fecha de inicio en formato "yyyy-MM-dd".
     * @param fechaFin Fecha de fin en formato "yyyy-MM-dd".
     */
    private void obtenerPagosGenerales(String fechaInicio, String fechaFin) {

        // Obtención de los comentarios y revistas
        List<Revista> revistas = new ArrayList<>();
        List<Comentario> comentarios = this.controladora.obtenerListaComentarios();

        // Parseo de las fechas de inicio y fin
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate inicio = LocalDate.parse(fechaInicio, formatter);
        LocalDate fin = LocalDate.parse(fechaFin, formatter);

        // Filtrar los comentarios que estén dentro del rango de fechas
        List<Comentario> comentariosFiltrados = comentarios.stream()
                .filter(comentario -> {
                    LocalDate fechaComentario = convertirADateLocal(comentario.getFecha());
                    return (fechaComentario.isAfter(inicio.minusDays(1)) && fechaComentario.isBefore(fin.plusDays(1)));
                })
                .collect(Collectors.toList());

        // Obtener los IDs de las 5 revistas con más comentarios, ordenadas de mayor a menor
        List<String> top5Revistas = comentariosFiltrados.stream()
                .collect(Collectors.groupingBy(Comentario::getIdRevista, Collectors.counting())) // Agrupar y contar comentarios
                .entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue())) // Ordenar de mayor a menor
                .limit(5) // Limitar a las 5 revistas más comentadas
                .map(Map.Entry::getKey) // Obtener solo los IDs de las revistas
                .collect(Collectors.toList());

        // Cargar las revistas correspondientes a los top 5
        for (String idRevista : top5Revistas) {
            revistas.add(this.controladora.obtenerRevista(idRevista));
        }

        // Imprimir las revistas y comentarios para depuración
        System.out.println("Top 5 revistas con más comentarios: " + top5Revistas);

        // Asignar los comentarios a cada revista
        for (int i = 0; i < top5Revistas.size(); i++) {
            final String idRevista = top5Revistas.get(i); // Usar final en la variable para la lambda
            List<Comentario> comentariosDeRevista = comentarios.stream()
                    .filter(comentario -> idRevista.equals(comentario.getIdRevista()))
                    .collect(Collectors.toList());

            respuesta.getComentarios().add(comentariosDeRevista); // Añadir los comentarios de cada revista
        }

        // Imprimir los comentarios para depuración
        for (int i = 0; i < top5Revistas.size(); i++) {
            System.out.println("#________________________________________________________________#");
            System.out.println(top5Revistas.get(i));
            for (Comentario comentario : respuesta.getComentarios().get(i)) {
                System.out.println(comentario.getComentario());
                System.out.println("__________________________________________________________________");
            }
        }

        // Asignar las revistas a la respuesta
        respuesta.setRevistas(revistas);
    }

    /**
     * Convierte un objeto de tipo Date a LocalDate.
     * 
     * @param fecha Objeto Date a convertir.
     * @return LocalDate correspondiente a la fecha proporcionada.
     */
    private LocalDate convertirADateLocal(Date fecha) {
        if (fecha instanceof java.sql.Date) {
            return ((java.sql.Date) fecha).toLocalDate(); // Conversion directa si es java.sql.Date
        } else {
            return fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(); // Para otros tipos de Date
        }
    }

    /**
     * Crea un reporte en formato PDF con los comentarios de las revistas más comentadas.
     * 
     * @throws FileNotFoundException Si no se encuentra el archivo del reporte Jasper.
     * @throws IOException Si ocurre un error durante la creación del reporte.
     */
    private void crearReporte() throws FileNotFoundException, IOException {
        ReporteTop5ComentariosJasperReport report = new ReporteTop5ComentariosJasperReport();
        try {
            // Cargar el archivo Jasper del reporte
            JasperReport reporte = (JasperReport) JRLoader.loadObject(new File("/home/carlosrodriguez/JaspersoftWorkspace/MyReports/Reporte_top5_comentarios.jasper"));

            // Llenar el reporte con los datos y crear un JasperPrint
            JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, null, report.getDataSource(this.respuesta.getComentarios(), this.respuesta.getRevistas()));

            // Crear un ByteArrayOutputStream para almacenar el PDF generado
            ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();

            // Exportar el reporte a PDF
            JasperExportManager.exportReportToPdfStream(jasperPrint, pdfOutputStream);

            // Convertir el contenido del PDF a un arreglo de bytes
            byte[] pdfBytes = pdfOutputStream.toByteArray();

            // Convertir los bytes del PDF a Base64 para incluirlo en la respuesta
            String pdfBase64 = Base64.getEncoder().encodeToString(pdfBytes);
            this.respuesta.setPdf(pdfBase64); // Guardar el PDF en formato Base64 en la respuesta

        } catch (JRException e) {
            e.printStackTrace();  // Imprimir el error completo en la consola si ocurre un error con JasperReports
        }
    }
}
