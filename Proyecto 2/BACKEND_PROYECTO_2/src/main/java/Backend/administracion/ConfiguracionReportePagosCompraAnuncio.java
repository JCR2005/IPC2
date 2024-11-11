package Backend.administracion;

import JPA.Anuncio;
import JPA.Controladora;
import JPA.Ingreso;
import JPA.Usuario;
import Reportes.jasperReports.ReporteAnunciosCompradosJasperReport;
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
import respuetas.Administrador.RespuestaReportePagosComppraAnuncio;

/**
 * Clase encargada de generar reportes de pagos por compra de anuncios.
 * Permite filtrar los pagos por fechas y tipo de anuncio.
 * 
 * @author carlosrodriguez
 */
public class ConfiguracionReportePagosCompraAnuncio {

    private Controladora controladora = new Controladora();
    private RespuestaReportePagosComppraAnuncio respuesta = new RespuestaReportePagosComppraAnuncio();

    /**
     * Obtiene los pagos en un rango de fechas y opcionalmente por tipo de anuncio.
     * Si el tipo de anuncio está vacío, se obtienen todos los pagos.
     * 
     * @param fechaInicio Fecha de inicio en formato "yyyy-MM-dd".
     * @param fechaFin Fecha de fin en formato "yyyy-MM-dd".
     * @param tipoAnuncio El tipo de anuncio para filtrar (opcional).
     * @return Respuesta con los pagos y el reporte en formato Base64.
     * @throws IOException Si ocurre un error durante la creación del reporte.
     */
    public RespuestaReportePagosComppraAnuncio obtenerPagos(String fechaInicio, String fechaFin, String tipoAnuncio) throws IOException {
        if (!tipoAnuncio.isEmpty()) {
            obtenerPagosPorTipoAnuncio(fechaInicio, fechaFin, tipoAnuncio);
        } else {
            obtenerPagosGenerales(fechaInicio, fechaFin);
        }
        crearReporte();
        return respuesta;
    }

    /**
     * Obtiene los pagos filtrados por tipo de anuncio dentro de un rango de fechas.
     * 
     * @param fechaInicio Fecha de inicio en formato "yyyy-MM-dd".
     * @param fechaFin Fecha de fin en formato "yyyy-MM-dd".
     * @param tipoAnuncio El tipo de anuncio a filtrar.
     */
    private void obtenerPagosPorTipoAnuncio(String fechaInicio, String fechaFin, String tipoAnuncio) {

        // Obtiene los datos necesarios: anuncios, pagos y usuarios
        List<Anuncio> anuncios = controladora.obtenerAlumnos();
        List<Ingreso> pagos = controladora.obtenerListapagos();
        List<Usuario> usuarios = controladora.obtenerlistaUsuarios();

        // Filtra los usuarios que son anunciantes
        List<Usuario> usuariosAnunciantes = usuarios.stream()
                .filter(usuario -> "Anunciante".equals(usuario.getTipoCuenta()))
                .collect(Collectors.toList());

        // Filtra los anuncios por el tipo especificado
        List<String> idsAnunciosFiltrados = anuncios.stream()
                .filter(anuncio -> tipoAnuncio.equals(anuncio.getTipoAnuncio()))
                .map(Anuncio::getIdAnuncio)
                .collect(Collectors.toList());

        // Convierte las fechas a LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate inicio = LocalDate.parse(fechaInicio, formatter);
        LocalDate fin = LocalDate.parse(fechaFin, formatter);

        // Filtra los pagos de acuerdo a las condiciones
        List<String> idsUsuariosAnunciantes = usuariosAnunciantes.stream()
                .map(Usuario::getUsuario)
                .collect(Collectors.toList());

        List<Ingreso> ingresosFiltrados = pagos.stream()
                .filter(pago -> idsAnunciosFiltrados.contains(pago.getIdVinculado())
                        && convertirADateLocal(pago.getFecha()).isAfter(inicio.minusDays(1))
                        && convertirADateLocal(pago.getFecha()).isBefore(fin.plusDays(1))
                        && idsUsuariosAnunciantes.contains(pago.getIdUsuario()))
                .collect(Collectors.toList());

        // Calcula el total de los pagos
        double totalPago = ingresosFiltrados.stream()
                .mapToDouble(Ingreso::getMonto)
                .sum();

        // Actualiza la respuesta con los resultados
        respuesta.setTotalPago(totalPago);
        respuesta.setIngresos(ingresosFiltrados);
        respuesta.getTiposAnuncio().add(tipoAnuncio);
    }

    /**
     * Obtiene los pagos generales (sin filtro por tipo de anuncio) dentro de un rango de fechas.
     * 
     * @param fechaInicio Fecha de inicio en formato "yyyy-MM-dd".
     * @param fechaFin Fecha de fin en formato "yyyy-MM-dd".
     */
    private void obtenerPagosGenerales(String fechaInicio, String fechaFin) {

        // Obtiene los datos necesarios: anuncios, pagos y usuarios
        List<Anuncio> anuncios = controladora.obtenerAlumnos();
        List<Ingreso> pagos = controladora.obtenerListapagos();
        List<Usuario> usuarios = controladora.obtenerlistaUsuarios();

        // Filtra los usuarios que son anunciantes
        List<Usuario> usuariosAnunciantes = usuarios.stream()
                .filter(usuario -> "Anunciante".equals(usuario.getTipoCuenta()))
                .collect(Collectors.toList());

        // Obtiene los tipos de anuncio y los IDs de los anuncios
        List<String> tiposAnuncio = anuncios.stream()
                .map(Anuncio::getTipoAnuncio)
                .distinct()
                .collect(Collectors.toList());
        List<String> idsAnunciosFiltrados = anuncios.stream()
                .map(Anuncio::getIdAnuncio)
                .collect(Collectors.toList());

        // Convierte las fechas a LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate inicio = LocalDate.parse(fechaInicio, formatter);
        LocalDate fin = LocalDate.parse(fechaFin, formatter);

        // Filtra los pagos
        List<String> idsUsuariosAnunciantes = usuariosAnunciantes.stream()
                .map(Usuario::getUsuario)
                .collect(Collectors.toList());

        List<Ingreso> ingresosFiltrados = pagos.stream()
                .filter(pago -> idsAnunciosFiltrados.contains(pago.getIdVinculado())
                        && convertirADateLocal(pago.getFecha()).isAfter(inicio.minusDays(1))
                        && convertirADateLocal(pago.getFecha()).isBefore(fin.plusDays(1))
                        && idsUsuariosAnunciantes.contains(pago.getIdUsuario()))
                .collect(Collectors.toList());

        // Calcula el total de los pagos
        double totalPago = ingresosFiltrados.stream()
                .mapToDouble(Ingreso::getMonto)
                .sum();

        // Actualiza la respuesta con los resultados
        respuesta.setTotalPago(totalPago);
        respuesta.setIngresos(ingresosFiltrados);
        respuesta.setTiposAnuncio(tiposAnuncio);
    }

    /**
     * Convierte un objeto de tipo Date a LocalDate.
     * 
     * @param fecha La fecha a convertir.
     * @return La fecha convertida a LocalDate.
     */
    private LocalDate convertirADateLocal(Date fecha) {
        if (fecha instanceof java.sql.Date) {
            return ((java.sql.Date) fecha).toLocalDate();
        } else {
            return fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
    }

    /**
     * Crea el reporte en formato PDF utilizando JasperReports y lo convierte a Base64.
     * 
     * @throws FileNotFoundException Si no se encuentra el archivo del reporte.
     * @throws IOException Si ocurre un error durante la creación del reporte.
     */
    private void crearReporte() throws FileNotFoundException, IOException {
        ReporteAnunciosCompradosJasperReport report = new ReporteAnunciosCompradosJasperReport();
        try {
            // Carga el archivo .jasper
            JasperReport reporte = (JasperReport) JRLoader.loadObject(new File("/home/carlosrodriguez/Documentos/Proyecto 2/Proyecto 2/BACKEND_PROYECTO_2/src/main/java/Reportes/REPORTE_ANUNCIOS_COMPRADOS.jasper"));

            // Llena el reporte con los datos
            JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, null, report.getDataSource(respuesta.getIngresos(), respuesta.getTiposAnuncio()));

            // Crea el ByteArrayOutputStream para almacenar el PDF
            ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();

            // Exporta el reporte a PDF
            JasperExportManager.exportReportToPdfStream(jasperPrint, pdfOutputStream);

            // Convierte el ByteArrayOutputStream a un arreglo de bytes
            byte[] pdfBytes = pdfOutputStream.toByteArray();

            // Convierte los bytes a Base64
            String pdfBase64 = Base64.getEncoder().encodeToString(pdfBytes);
            respuesta.setPdf(pdfBase64);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}
