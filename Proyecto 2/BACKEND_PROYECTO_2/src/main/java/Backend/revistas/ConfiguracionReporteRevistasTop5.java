package Backend.revistas;

import JPA.Comentario;
import JPA.Controladora;
import JPA.MeGusta;
import JPA.Revista;
import Reportes.jasperReports.ReporteComentariosJasperReport;
import Reportes.jasperReports.ReporteTop5EJasperReport;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
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
import respuetas.Editor.RespuestaReporteRevistasTop5;
import respuetas.Editor.respuestaReporteComentarios;

/**
 *
 * @author carlosrodriguez
 */
public class ConfiguracionReporteRevistasTop5 {

    private Controladora controladora = new Controladora();
    private RespuestaReporteRevistasTop5 respuesta = new RespuestaReporteRevistasTop5();
    
        public RespuestaReporteRevistasTop5 obtenerRevistas(String idUsuario) {
        List<Revista> revistas = controladora.obtenerRevistas();

        List<Revista> revistasFiltradas = revistas.stream()
                .filter(revista -> revista.getAutor().equals(idUsuario))
                .collect(Collectors.toList());

        respuesta.setRevistas(revistasFiltradas);
        return respuesta;
    }

    public RespuestaReporteRevistasTop5 obtenerComentarios(String idUsuario, String fechaInicio, String fechaFin, String idRevista) throws IOException {
        if (idRevista != null && !idRevista.isEmpty()) {
            obtenerComentariosDeRevista(fechaInicio, fechaFin, idRevista);
        } else {
            obtenerComentariosGenerales(idUsuario, fechaInicio, fechaFin);
        }
        crearReporte();
        return respuesta;
    }

    // Método auxiliar para obtener las revistas filtradas por el autor
    private List<Revista> obtenerRevistasFiltradasPorAutor(String idUsuario) {
        List<Revista> revistas = controladora.obtenerRevistas();
        return revistas.stream()
                .filter(revista -> revista.getAutor().equals(idUsuario))
                .collect(Collectors.toList());
    }

private void obtenerComentariosGenerales(String idUsuario, String fechaInicio, String fechaFin) {
    // Obtener revistas filtradas por autor
    List<Revista> revistasFiltradas = obtenerRevistasFiltradasPorAutor(idUsuario);
    List<MeGusta> meGustas = controladora.obtenerListaMeGustas();
    
    // Obtener los IDs de revistas filtradas
    List<String> idsRevistasFiltradas = obtenerIdsRevistasFiltradas(revistasFiltradas);
    
    // Parsear las fechas de inicio y fin
    LocalDate[] fechas = parseFechas(fechaInicio, fechaFin);
    LocalDate inicio = fechas[0];
    LocalDate fin = fechas[1];
    
    // Filtrar los comentarios (MeGustas) con base en las fechas y los "Me gusta" verdaderos
    List<MeGusta> comentariosFiltrados = meGustas.stream()
            .filter(comentario -> idsRevistasFiltradas.contains(comentario.getIdRevista())
                    && esFechaValida(comentario.getFecha(), inicio, fin)
                    && comentario.isMeGusta()) // Filtra solo los "Me gusta" verdaderos
            .collect(Collectors.toList());

    // Contar la cantidad de "Me gusta" por ID de revista
    Map<String, Long> cantidadMeGustasPorRevista = comentariosFiltrados.stream()
            .collect(Collectors.groupingBy(MeGusta::getIdRevista, Collectors.counting()));
    
    // Ordenar las revistas por la cantidad de "Me gusta" de forma descendente
    List<String> idsOrdenadosPorMeGusta = cantidadMeGustasPorRevista.entrySet().stream()
            .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue())) // Orden descendente
            .map(Map.Entry::getKey)  // Extrae los IDs de revista
            .collect(Collectors.toList());

    // Limitar a las primeras 5 revistas más gustadas
    List<String> top5IdsRevistas = idsOrdenadosPorMeGusta.stream()
            .limit(5)  // Limita a las 5 primeras
            .collect(Collectors.toList());

    // Filtrar los comentarios para que solo queden los que pertenecen a las 5 revistas más gustadas
    List<MeGusta> comentariosTop5 = comentariosFiltrados.stream()
            .filter(comentario -> top5IdsRevistas.contains(comentario.getIdRevista()))
            .collect(Collectors.toList());

    // Ordenar los "Me gusta" dentro de los top 5 según la cantidad de veces que su idRevista aparece
    List<MeGusta> comentariosOrdenados = comentariosTop5.stream()
            .sorted((comentario1, comentario2) -> {
                // Contar cuántas veces aparece el idRevista de cada comentario
                long count1 = comentariosTop5.stream().filter(c -> c.getIdRevista().equals(comentario1.getIdRevista())).count();
                long count2 = comentariosTop5.stream().filter(c -> c.getIdRevista().equals(comentario2.getIdRevista())).count();
                return Long.compare(count2, count1); // Orden descendente por frecuencia
            })
            .collect(Collectors.toList());

    // Obtener los IDs de las revistas en el mismo orden que los "Me gusta" ordenados
    List<String> idsOrdenados = comentariosOrdenados.stream()
            .map(MeGusta::getIdRevista)  // Extraer el ID de la revista
            .distinct()  // Asegurarse de que los IDs no se repitan
            .collect(Collectors.toList());

    // Obtener los títulos de las revistas de los IDs ordenados
    List<String> titulosRevistasOrdenados = idsOrdenados.stream()
            .map(idRevista -> {
                // Buscar la revista por el ID en la lista de revistas filtradas
                return revistasFiltradas.stream()
                        .filter(revista -> revista.getIdRevista().equals(idRevista))
                        .map(Revista::getTitulo)  // Obtener el título de la revista
                        .findFirst()  // Obtener el primer resultado (debería haber solo uno)
                        .orElse(null);  // Si no se encuentra, retorna null
            })
            .collect(Collectors.toList());

    // Crear una lista de listas de MeGusta para cada idRevista
    List<List<MeGusta>> listaDeComentariosPorRevista = new ArrayList<>();

    for (String idRevista : idsOrdenados) {
        // Filtrar los comentarios que pertenecen a este idRevista
        List<MeGusta> comentariosPorRevista = comentariosOrdenados.stream()
                .filter(comentario -> comentario.getIdRevista().equals(idRevista))
                .collect(Collectors.toList());

        // Añadir los comentarios filtrados a la lista de listas
        listaDeComentariosPorRevista.add(comentariosPorRevista);
    }

    // Asignar los títulos de las revistas y los comentarios organizados a la respuesta
    respuesta.setIdsRevistas(titulosRevistasOrdenados); 
    respuesta.setMeGustas(listaDeComentariosPorRevista); // Lista de listas de "Me gusta" por cada revista

       for (int i = 0; i < respuesta.getIdsRevistas().size(); i++) {
            System.out.println("_____________________________________");
            System.out.println(respuesta.getIdsRevistas().get(i));
            System.out.println("_____________________________________");
            for (int j = 0; j < respuesta.getMeGustas().get(i).size(); j++) {
                System.out.println(respuesta.getMeGustas().get(i).get(j).isMeGusta());
                System.out.println("_____________________________________");
            }
        }

    // Imprimir para verificar
    System.out.println("Títulos de revistas ordenados: " + titulosRevistasOrdenados);
    System.out.println("Lista de comentarios por revista: " + listaDeComentariosPorRevista);
}





       private void obtenerComentariosDeRevista(String fechaInicio, String fechaFin, String idRevista) {
        // Obtener la lista de "Me gusta"
        List<MeGusta> meGustas = controladora.obtenerListaMeGustas();

        // Parsear las fechas de inicio y fin
        LocalDate[] fechas = parseFechas(fechaInicio, fechaFin);
        LocalDate inicio = fechas[0];
        LocalDate fin = fechas[1];

        // Filtrar los comentarios (MeGustas) con base en la fecha, el idRevista y los "Me gusta" verdaderos
        List<MeGusta> comentariosFiltrados = meGustas.stream()
                .filter(comentario -> comentario.getIdRevista().equals(idRevista)
                && esFechaValida(comentario.getFecha(), inicio, fin)
                && comentario.isMeGusta()) // Filtrar solo "Me gusta" verdaderos
                .collect(Collectors.toList());

        // Obtener el título de la revista correspondiente
        Revista revista = controladora.obtenerRevistas().stream()
                .filter(r -> r.getIdRevista().equals(idRevista))
                .findFirst()
                .orElse(null); // Si no se encuentra, retorna null

        if (revista != null) {
            String tituloRevista = revista.getTitulo();

            // Asignar el título de la revista y los "Me gusta" filtrados a la respuesta
            respuesta.setIdsRevistas(Collections.singletonList(tituloRevista));
            respuesta.setMeGustas(Collections.singletonList(comentariosFiltrados));

            // Imprimir los resultados
            System.out.println("_____________________________________");
            System.out.println("Revista: " + tituloRevista);
            System.out.println("_____________________________________");

            for (MeGusta comentario : comentariosFiltrados) {
                System.out.println("Me gusta: " + comentario.isMeGusta());
                System.out.println("_____________________________________");
            }

        } else {
            System.out.println("La revista con ID " + idRevista + " no fue encontrada.");
        }

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
        ReporteTop5EJasperReport report = new ReporteTop5EJasperReport();
        try {
            // Carga el archivo .jasper
            JasperReport reporte = (JasperReport) JRLoader.loadObject(new File("/home/carlosrodriguez/JaspersoftWorkspace/MyReports/Reporte_top5_Editor.jasper"));

            // Llenar el reporte
            JasperPrint e = JasperFillManager.fillReport(reporte, null, report.getDataSource(this.respuesta.getMeGustas(),this.respuesta.getIdsRevistas()));
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
