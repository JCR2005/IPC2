package Backend.revistas;

import JPA.Comentario;
import JPA.Controladora;
import JPA.Revista;
import Reportes.jasperReports.ReporteComentariosJasperReport;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import respuetas.Editor.respuestaReporteComentarios;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * Configuración para la generación de reportes de comentarios.
 *
 * @author carlosrodriguez
 */
public class ConfiguracionReporteComentarios {

    private Controladora controladora = new Controladora();
    private respuestaReporteComentarios respuesta = new respuestaReporteComentarios();

    /**
     * Obtiene revistas asociadas al usuario especificado.
     *
     * @param idUsuario ID del usuario
     * @return Respuesta con la lista de revistas
     */
    public respuestaReporteComentarios obtenerRevistas(String idUsuario) {
        List<Revista> revistas = controladora.obtenerRevistas();

        List<Revista> revistasFiltradas = revistas.stream()
                .filter(revista -> revista.getAutor().equals(idUsuario))
                .collect(Collectors.toList());

        respuesta.setRevistas(revistasFiltradas);
        return respuesta;
    }

    /**
     * Obtiene comentarios basados en el usuario y el rango de fechas
     * especificado.
     *
     * @param idUsuario ID del usuario que solicita los comentarios
     * @param fechaInicio Fecha de inicio para filtrar comentarios
     * @param fechaFin Fecha de fin para filtrar comentarios
     * @param idRevista ID de la revista de interés
     * @return Respuesta con la lista de comentarios
     */
    public respuestaReporteComentarios obtenerComentarios(String idUsuario, String fechaInicio, String fechaFin, String idRevista) throws IOException {
        if (idRevista != null && !idRevista.isEmpty()) {
            obtenerComentariosDeRevista(idUsuario, fechaInicio, fechaFin, idRevista);
        } else {
            obtenerComentariosGenerales(idUsuario, fechaInicio, fechaFin);
        }

        crearReporte();
        return respuesta;
    }

    /**
     * Obtiene comentarios generales para el usuario especificado dentro del
     * rango de fechas.
     *
     * @param idUsuario ID del usuario que solicita los comentarios
     * @param fechaInicio Fecha de inicio para filtrar comentarios
     * @param fechaFin Fecha de fin para filtrar comentarios
     */
    private void obtenerComentariosGenerales(String idUsuario, String fechaInicio, String fechaFin) {
        List<Revista> revistasFiltradas = obtenerRevistasFiltradasPorAutor(idUsuario);
        List<Comentario> comentarios = controladora.obtenerListaComentarios();
        List<String> idsRevistasFiltradas = obtenerIdsRevistasFiltradas(revistasFiltradas);

        LocalDate[] fechas = parseFechas(fechaInicio, fechaFin);
        LocalDate inicio = fechas[0];
        LocalDate fin = fechas[1];

        List<Comentario> comentariosFiltrados = comentarios.stream()
                .filter(comentario -> idsRevistasFiltradas.contains(comentario.getIdRevista())
                && esFechaValida(comentario.getFecha(), inicio, fin))
                .collect(Collectors.toList());

        List<String> idsDeComentarios = comentariosFiltrados.stream()
                .map(Comentario::getIdRevista)
                .distinct() // Para evitar duplicados
                .collect(Collectors.toList());
        
        List<String> titulosDeRevistas = idsDeComentarios.stream()
        .map(id -> revistasFiltradas.stream()
                .filter(revista -> revista.getIdRevista().equals(id))
                .map(Revista::getTitulo)
                .findFirst() // Obtener el primer título encontrado
                .orElse("Título no encontrado")) // Manejar el caso donde no se encuentra la revista
        .collect(Collectors.toList());



        for (int i = 0; i < idsDeComentarios.size(); i++) {
            String idRevistaActual = idsDeComentarios.get(i);

            // Filtrar los comentarios que coinciden con el ID de revista actual y con la fecha válida
            List<Comentario> comentarioFil = comentarios.stream()
                    .filter(comentario -> comentario.getIdRevista().equals(idRevistaActual)
                    && esFechaValida(comentario.getFecha(), inicio, fin))
                    .collect(Collectors.toList());

            this.respuesta.getComentarios().add(comentarioFil);
        }

        this.respuesta.setIdsRevistas(titulosDeRevistas);

        for (int i = 0; i < respuesta.getIdsRevistas().size(); i++) {
            System.out.println("_____________________________________");
            System.out.println(respuesta.getIdsRevistas().get(i));
            System.out.println("_____________________________________");
            for (int j = 0; j < respuesta.getComentarios().get(i).size(); j++) {
                System.out.println(respuesta.getComentarios().get(i).get(j));
                System.out.println("_____________________________________");
            }
        }

    }

    /**
     * Obtiene comentarios de una revista específica.
     *
     * @param idUsuario ID del usuario que solicita los comentarios
     * @param fechaInicio Fecha de inicio para filtrar comentarios
     * @param fechaFin Fecha de fin para filtrar comentarios
     * @param idRevista ID de la revista de interés
     */
    private void obtenerComentariosDeRevista(String idUsuario, String fechaInicio, String fechaFin, String idRevista) {
        List<Revista> revistasFiltradas = obtenerRevistasFiltradasPorAutor(idUsuario);
        List<Comentario> comentarios = controladora.obtenerListaComentarios();
        List<String> idsRevistasFiltradas = obtenerIdsRevistasFiltradas(revistasFiltradas);

        LocalDate[] fechas = parseFechas(fechaInicio, fechaFin);
        LocalDate inicio = fechas[0];
        LocalDate fin = fechas[1];

        List<Comentario> comentariosFiltrados = comentarios.stream()
                .filter(comentario -> idsRevistasFiltradas.contains(comentario.getIdRevista())
                && esFechaValida(comentario.getFecha(), inicio, fin)
                && comentario.getIdRevista().equals(idRevista))
                .collect(Collectors.toList());
        
        Revista revista= controladora.obtenerRevista(idRevista);
        respuesta.getIdsRevistas().add(revista.getTitulo());
        respuesta.getComentarios().add(comentariosFiltrados);
    }

    // Método auxiliar para obtener las revistas filtradas por el autor
    private List<Revista> obtenerRevistasFiltradasPorAutor(String idUsuario) {
        List<Revista> revistas = controladora.obtenerRevistas();
        return revistas.stream()
                .filter(revista -> revista.getAutor().equals(idUsuario))
                .collect(Collectors.toList());
    }

    // Método auxiliar para obtener los IDs de las revistas filtradas
    private List<String> obtenerIdsRevistasFiltradas(List<Revista> revistasFiltradas) {
        return revistasFiltradas.stream()
                .map(Revista::getIdRevista)
                .collect(Collectors.toList());
    }

    // Método auxiliar para convertir de Date a LocalDate
    private LocalDate convertirADateLocal(Date fecha) {
        return fecha instanceof java.sql.Date
                ? ((java.sql.Date) fecha).toLocalDate()
                : fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    // Método auxiliar para verificar si una fecha está dentro del rango
    private boolean esFechaValida(Date fecha, LocalDate inicio, LocalDate fin) {
        LocalDate fechaComentario = convertirADateLocal(fecha);
        return !fechaComentario.isBefore(inicio) && !fechaComentario.isAfter(fin);
    }

    // Método auxiliar para parsear fechas
    private LocalDate[] parseFechas(String fechaInicio, String fechaFin) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate inicio = LocalDate.parse(fechaInicio, formatter);
        LocalDate fin = LocalDate.parse(fechaFin, formatter);
        return new LocalDate[]{inicio, fin};
    }

    private void crearReporte() throws FileNotFoundException, IOException {
        ReporteComentariosJasperReport report = new ReporteComentariosJasperReport();
        try {
            // Carga el archivo .jasper
            JasperReport reporte = (JasperReport) JRLoader.loadObject(new File("/home/carlosrodriguez/JaspersoftWorkspace/MyReports/Reporte_Comentarios.jasper"));

            // Llenar el reporte
            JasperPrint e = JasperFillManager.fillReport(reporte, null, report.getDataSource(this.respuesta.getComentarios(),this.respuesta.getIdsRevistas()));
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
