package Backend.administracion;

import JPA.Controladora;
import JPA.Revista;
import JPA.Suscripciòn;
import Reportes.jasperReports.ReporteTop5SuscripcionmesJasperReport;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import respuetas.Administrador.RespuestaReporteRevistasTop5Suscripciones;

/**
 * Clase encargada de generar el reporte de las 5 revistas con más suscripciones
 * dentro de un rango de fechas determinado.
 * 
 * @author carlosrodriguez
 */
public class ConfiguracionReporteRevistasTop5Suscripciones {

    private Controladora controladora = new Controladora();
    private RespuestaReporteRevistasTop5Suscripciones respuesta = new RespuestaReporteRevistasTop5Suscripciones();

    /**
     * Obtiene todas las revistas disponibles.
     * 
     * @return Respuesta con la lista de revistas.
     */
    public RespuestaReporteRevistasTop5Suscripciones obtenerRevistas() {
        List<Revista> revistas = controladora.obtenerRevistas();
        respuesta.setRevistas(revistas);
        return respuesta;
    }

    /**
     * Obtiene los comentarios (Me gusta) dentro de un rango de fechas y genera el reporte correspondiente.
     * 
     * @param fechaInicio Fecha de inicio del rango en formato "yyyy-MM-dd".
     * @param fechaFin Fecha de fin del rango en formato "yyyy-MM-dd".
     * @return Respuesta con los datos del reporte generado.
     * @throws IOException Si ocurre un error durante la creación del reporte.
     */
    public RespuestaReporteRevistasTop5Suscripciones obtenerComentarios(String fechaInicio, String fechaFin) throws IOException {
        obtenerComentariosGenerales(fechaInicio, fechaFin); // Obtener comentarios
        crearReporte(); // Crear el reporte
        return respuesta; // Retornar la respuesta
    }

    /**
     * Obtiene los comentarios (Me gusta) filtrados dentro del rango de fechas para las revistas seleccionadas.
     * 
     * @param fechaInicio Fecha de inicio del rango en formato "yyyy-MM-dd".
     * @param fechaFin Fecha de fin del rango en formato "yyyy-MM-dd".
     */
    private void obtenerComentariosGenerales(String fechaInicio, String fechaFin) {
        // Obtener todas las revistas y suscripciones
        List<Revista> revistasFiltradas = this.controladora.obtenerRevistas();
        List<Suscripciòn> meGustas = controladora.obtenerSuscripciones();

        // Obtener los IDs de las revistas filtradas
        List<String> idsRevistasFiltradas = obtenerIdsRevistasFiltradas(revistasFiltradas);

        // Parsear las fechas de inicio y fin
        LocalDate[] fechas = parseFechas(fechaInicio, fechaFin);
        LocalDate inicio = fechas[0];
        LocalDate fin = fechas[1];

        // Filtrar los "Me gusta" dentro del rango de fechas y que pertenecen a las revistas seleccionadas
        List<Suscripciòn> comentariosFiltrados = meGustas.stream()
                .filter(comentario -> idsRevistasFiltradas.contains(comentario.getIdRevista())
                        && esFechaValida(comentario.getFechaSuscricion(), inicio, fin))
                .collect(Collectors.toList());

        // Contar la cantidad de "Me gusta" por ID de revista
        Map<String, Long> cantidadMeGustasPorRevista = comentariosFiltrados.stream()
                .collect(Collectors.groupingBy(Suscripciòn::getIdRevista, Collectors.counting()));

        // Obtener las 5 revistas con más "Me gusta"
        List<String> top5IdsRevistas = cantidadMeGustasPorRevista.entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue())) // Orden descendente
                .map(Map.Entry::getKey) // Extrae los IDs
                .limit(5) // Limitar a las 5 primeras
                .collect(Collectors.toList());

        // Filtrar los comentarios que pertenecen a las 5 revistas más "gustadas"
        List<Suscripciòn> comentariosTop5 = comentariosFiltrados.stream()
                .filter(comentario -> top5IdsRevistas.contains(comentario.getIdRevista()))
                .collect(Collectors.toList());

        // Ordenar los "Me gusta" por la cantidad de veces que su ID de revista aparece
        List<Suscripciòn> comentariosOrdenados = comentariosTop5.stream()
                .sorted((comentario1, comentario2) -> {
                    long count1 = comentariosTop5.stream().filter(c -> c.getIdRevista().equals(comentario1.getIdRevista())).count();
                    long count2 = comentariosTop5.stream().filter(c -> c.getIdRevista().equals(comentario2.getIdRevista())).count();
                    return Long.compare(count2, count1); // Orden descendente por frecuencia
                })
                .collect(Collectors.toList());

        // Obtener los IDs de las revistas ordenadas
        List<String> idsOrdenados = comentariosOrdenados.stream()
                .map(Suscripciòn::getIdRevista)
                .distinct() // Asegurarse de que los IDs no se repitan
                .collect(Collectors.toList());

        // Obtener las revistas correspondientes a los IDs ordenados
        List<Revista> revistasOrdenadas = obtenerRevistasOrdenadas(revistasFiltradas, idsOrdenados);

        // Crear una lista de comentarios (Me gusta) organizados por revista
        List<List<Suscripciòn>> listaDeComentariosPorRevista = obtenerComentariosPorRevista(comentariosOrdenados, idsOrdenados);

        // Asignar los títulos de las revistas y los comentarios organizados a la respuesta
        respuesta.setIdsRevistas(revistasOrdenadas);
        respuesta.setMeGustas(listaDeComentariosPorRevista); // Lista de "Me gusta" por cada revista

        // Verificación (debe eliminarse en producción)
        imprimirDatosDeRevistasYComentarios(revistasOrdenadas, listaDeComentariosPorRevista);
    }

    /**
     * Obtiene los IDs de las revistas filtradas.
     * 
     * @param revistasFiltradas Lista de revistas filtradas.
     * @return Lista de IDs de revistas.
     */
    private List<String> obtenerIdsRevistasFiltradas(List<Revista> revistasFiltradas) {
        return revistasFiltradas.stream()
                .map(Revista::getIdRevista)
                .collect(Collectors.toList());
    }

    /**
     * Verifica si una fecha está dentro del rango proporcionado.
     * 
     * @param fecha Fecha a verificar.
     * @param inicio Fecha de inicio del rango.
     * @param fin Fecha de fin del rango.
     * @return true si la fecha está dentro del rango, false si está fuera.
     */
    private boolean esFechaValida(Date fecha, LocalDate inicio, LocalDate fin) {
        LocalDate fechaComentario = convertirADateLocal(fecha);
        return !fechaComentario.isBefore(inicio) && !fechaComentario.isAfter(fin);
    }

    /**
     * Convierte un objeto de tipo Date a LocalDate.
     * 
     * @param fecha Objeto Date a convertir.
     * @return LocalDate correspondiente a la fecha proporcionada.
     */
    private LocalDate convertirADateLocal(Date fecha) {
        return fecha instanceof java.sql.Date
                ? ((java.sql.Date) fecha).toLocalDate()
                : fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * Convierte las fechas de inicio y fin en LocalDate.
     * 
     * @param fechaInicio Fecha de inicio en formato "yyyy-MM-dd".
     * @param fechaFin Fecha de fin en formato "yyyy-MM-dd".
     * @return Un arreglo con las fechas de inicio y fin en LocalDate.
     */
    private LocalDate[] parseFechas(String fechaInicio, String fechaFin) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate inicio = LocalDate.parse(fechaInicio, formatter);
        LocalDate fin = LocalDate.parse(fechaFin, formatter);
        return new LocalDate[]{inicio, fin};
    }

    /**
     * Obtiene las revistas ordenadas por su ID.
     * 
     * @param revistasFiltradas Lista de revistas filtradas.
     * @param idsOrdenados Lista de IDs ordenados.
     * @return Lista de revistas ordenadas.
     */
    private List<Revista> obtenerRevistasOrdenadas(List<Revista> revistasFiltradas, List<String> idsOrdenados) {
        return idsOrdenados.stream()
                .map(idRevista -> revistasFiltradas.stream()
                        .filter(revista -> revista.getIdRevista().equals(idRevista))
                        .findFirst()
                        .orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * Organiza los comentarios (Me gusta) por cada revista.
     * 
     * @param comentariosOrdenados Lista de comentarios ordenados.
     * @param idsOrdenados Lista de IDs ordenados.
     * @return Lista de listas de comentarios (Me gusta) por revista.
     */
    private List<List<Suscripciòn>> obtenerComentariosPorRevista(List<Suscripciòn> comentariosOrdenados, List<String> idsOrdenados) {
        return idsOrdenados.stream()
                .map(idRevista -> comentariosOrdenados.stream()
                        .filter(comentario -> comentario.getIdRevista().equals(idRevista))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    /**
     * Método de verificación (debe eliminarse en producción).
     * 
     * @param revistasOrdenadas Lista de revistas ordenadas.
     * @param listaDeComentariosPorRevista Lista de comentarios organizados por revista.
     */
    private void imprimirDatosDeRevistasYComentarios(List<Revista> revistasOrdenadas, List<List<Suscripciòn>> listaDeComentariosPorRevista) {
        for (int i = 0; i < revistasOrdenadas.size(); i++) {
            System.out.println("_____________________________________");
            System.out.println(revistasOrdenadas.get(i).getTitulo());
            System.out.println(revistasOrdenadas.get(i).getIdRevista());
            System.out.println("_____________________________________");
            for (Suscripciòn comentario : listaDeComentariosPorRevista.get(i)) {
                System.out.println(comentario.getIdUsuario());
                System.out.println("_____________________________________");
            }
        }
    }

    /**
     * Crea el reporte en formato PDF y lo convierte a Base64.
     * 
     * @throws FileNotFoundException Si el archivo de reporte no se encuentra.
     * @throws IOException Si ocurre un error durante la creación del reporte.
     */
    private void crearReporte() throws FileNotFoundException, IOException {
        ReporteTop5SuscripcionmesJasperReport report = new ReporteTop5SuscripcionmesJasperReport();
        try {
            // Cargar el reporte .jasper
            JasperReport reporte = (JasperReport) JRLoader.loadObject(new File("/home/carlosrodriguez/JaspersoftWorkspace/MyReports/Reporte_top5_suscripciones.jasper"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, null, report.getDataSource(this.respuesta.getMeGustas(), this.respuesta.getIdsRevistas()));

            // Generar el PDF
            ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, pdfOutputStream);

            // Convertir el PDF a Base64
            String pdfBase64 = Base64.getEncoder().encodeToString(pdfOutputStream.toByteArray());
            this.respuesta.setPdf(pdfBase64);

        } catch (JRException e) {
            e.printStackTrace(); // Manejo de excepciones
        }
    }
}
