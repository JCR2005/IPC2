package Backend.administracion;

import JPA.Anuncio;
import JPA.Controladora;
import JPA.Ingreso;
import JPA.Usuario;
import Reportes.jasperReports.ReporteIngresoPorAnuncianteJasperReport;
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
import respuetas.Administrador.RespuestaReporteGananciasPorAnunciante;

/**
 * Clase encargada de gestionar la configuración y generación de reportes de ganancias por anunciante.
 * Permite obtener los ingresos por anunciante en un rango de fechas y generar un reporte en formato PDF.
 * 
 * @author carlosrodriguez
 */
public class ConfiguracionReporteGananciaPorAnunciante {

    // Respuesta que contiene los resultados del reporte de ganancias por anunciante
    RespuestaReporteGananciasPorAnunciante respuesta = new RespuestaReporteGananciasPorAnunciante();
    
    // Controladora que gestiona la interacción con la base de datos
    private Controladora controladora = new Controladora();

    /**
     * Obtiene la lista de usuarios que son anunciantes.
     * 
     * @return Respuesta con la lista de anunciantes.
     */
    public RespuestaReporteGananciasPorAnunciante obtenerRevistas() {
        List<Usuario> usuarios = this.controladora.obtenerlistaUsuarios();

        // Filtra los usuarios que son anunciantes
        List<Usuario> anunciantes = usuarios.stream()
                .filter(usuario -> "Anunciante".equals(usuario.getTipoCuenta()))
                .collect(Collectors.toList());

        // Asigna los anunciantes a la respuesta
        this.respuesta.setAnunciantes(anunciantes);

        return respuesta;
    }

    /**
     * Obtiene los ingresos por anunciante o los pagos generales en un rango de fechas determinado.
     * Si el id del usuario está vacío, se obtienen los pagos generales.
     * 
     * @param idUsuario El ID del anunciante. Si está vacío, se obtienen pagos generales.
     * @param fechaInicio Fecha de inicio en formato "yyyy-MM-dd".
     * @param fechaFin Fecha de fin en formato "yyyy-MM-dd".
     * @return Respuesta con los resultados de los ingresos o pagos generales.
     * @throws IOException Si ocurre un error al generar el reporte.
     */
    public RespuestaReporteGananciasPorAnunciante obtenerIngresos(String idUsuario, String fechaInicio, String fechaFin) throws IOException {
        // Si el idUsuario no está vacío, obtiene los ingresos de un anunciante específico
        if (!idUsuario.isEmpty()) {
            obtenerIngresoPorUsusario(idUsuario, fechaInicio, fechaFin);
        } else {
            // Si no hay un idUsuario, obtiene los pagos generales
            obtenerPagosGenerales(fechaInicio, fechaFin);
        }

        // Genera el reporte en formato PDF
        crearReporte();

        return respuesta;
    }

    /**
     * Obtiene los pagos generales de todos los anunciantes en un rango de fechas.
     * 
     * @param fechaInicio Fecha de inicio en formato "yyyy-MM-dd".
     * @param fechaFin Fecha de fin en formato "yyyy-MM-dd".
     */
    private void obtenerPagosGenerales(String fechaInicio, String fechaFin) {

        // Obtiene todos los anuncios, usuarios y pagos
        List<Anuncio> anuncios = this.controladora.obtenerAlumnos();
        List<Usuario> usuarios = this.controladora.obtenerlistaUsuarios();
        List<Ingreso> pagos = this.controladora.obtenerListapagos();

        // Filtra los usuarios que son anunciantes
        List<Usuario> usuariosAnunciantes = usuarios.stream()
                .filter(usuario -> "Anunciante".equals(usuario.getTipoCuenta()))
                .collect(Collectors.toList());

        // Convierte las fechas de inicio y fin a LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate inicio = LocalDate.parse(fechaInicio, formatter);
        LocalDate fin = LocalDate.parse(fechaFin, formatter);

        // Procesa cada anunciante
        for (Usuario usuarioAnunciante : usuariosAnunciantes) {
            // Filtra los pagos del anunciante dentro del rango de fechas
            List<Ingreso> pagosDeUsuario = pagos.stream()
                    .filter(pago -> pago.getIdUsuario().equals(usuarioAnunciante.getUsuario()))
                    .filter(pago -> {
                        LocalDate fechaAnuncio = convertirADateLocal(pago.getFecha());
                        return (fechaAnuncio.isAfter(inicio.minusDays(1)) && fechaAnuncio.isBefore(fin.plusDays(1)));
                    })
                    .collect(Collectors.toList());

            // Filtra los anuncios del anunciante dentro del rango de fechas
            List<Anuncio> anunciosDeUsuario = anuncios.stream()
                    .filter(anuncio -> anuncio.getUsuario().equals(usuarioAnunciante.getUsuario()))
                    .filter(anuncio -> {
                        LocalDate fechaAnuncio = convertirADateLocal(anuncio.getFechaPublicacion());
                        return (fechaAnuncio.isAfter(inicio.minusDays(1)) && fechaAnuncio.isBefore(fin.plusDays(1)));
                    })
                    .collect(Collectors.toList());

            // Obtiene los tipos de anuncios del anunciante
            List<String> tiposAnuncio = anunciosDeUsuario.stream()
                    .map(Anuncio::getTipoAnuncio)
                    .collect(Collectors.toList());

            // Calcula el ingreso total del anunciante
            double ingresoTotal = 0;
            for (Ingreso pago : pagosDeUsuario) {
                ingresoTotal += pago.getMonto();
            }

            // Agrega los resultados a la respuesta
            this.respuesta.getGanaciasPorAnunciante().add(ingresoTotal);
            this.respuesta.getTiposDeAnuncio().add(tiposAnuncio);
            this.respuesta.getIngresos().add(pagosDeUsuario);
        }

        // Asigna los usuarios anunciantes a la respuesta
        this.respuesta.setAnunciantes(usuariosAnunciantes);
    }

    /**
     * Obtiene los ingresos de un anunciante específico en un rango de fechas.
     * 
     * @param anunciante El ID del anunciante.
     * @param fechaInicio Fecha de inicio en formato "yyyy-MM-dd".
     * @param fechaFin Fecha de fin en formato "yyyy-MM-dd".
     */
    private void obtenerIngresoPorUsusario(String anunciante, String fechaInicio, String fechaFin) {

        // Obtiene todos los anuncios, usuarios y pagos
        List<Anuncio> anuncios = this.controladora.obtenerAlumnos();
        List<Usuario> usuarios = this.controladora.obtenerlistaUsuarios();
        List<Ingreso> pagos = this.controladora.obtenerListapagos();

        // Filtra el usuario específico que es anunciante
        List<Usuario> usuariosAnunciantes = usuarios.stream()
                .filter(usuario -> anunciante.equals(usuario.getUsuario()))
                .collect(Collectors.toList());

        // Convierte las fechas de inicio y fin a LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate inicio = LocalDate.parse(fechaInicio, formatter);
        LocalDate fin = LocalDate.parse(fechaFin, formatter);

        // Procesa cada anunciante
        for (Usuario usuarioAnunciante : usuariosAnunciantes) {
            // Filtra los pagos del anunciante dentro del rango de fechas
            List<Ingreso> pagosDeUsuario = pagos.stream()
                    .filter(pago -> pago.getIdUsuario().equals(usuarioAnunciante.getUsuario()))
                    .filter(pago -> {
                        LocalDate fechaAnuncio = convertirADateLocal(pago.getFecha());
                        return (fechaAnuncio.isAfter(inicio.minusDays(1)) && fechaAnuncio.isBefore(fin.plusDays(1)));
                    })
                    .collect(Collectors.toList());

            // Filtra los anuncios del anunciante dentro del rango de fechas
            List<Anuncio> anunciosDeUsuario = anuncios.stream()
                    .filter(anuncio -> anuncio.getUsuario().equals(usuarioAnunciante.getUsuario()))
                    .filter(anuncio -> {
                        LocalDate fechaAnuncio = convertirADateLocal(anuncio.getFechaPublicacion());
                        return (fechaAnuncio.isAfter(inicio.minusDays(1)) && fechaAnuncio.isBefore(fin.plusDays(1)));
                    })
                    .collect(Collectors.toList());

            // Obtiene los tipos de anuncios del anunciante
            List<String> tiposAnuncio = anunciosDeUsuario.stream()
                    .map(Anuncio::getTipoAnuncio)
                    .collect(Collectors.toList());

            // Calcula el ingreso total del anunciante
            double ingresoTotal = 0;
            for (Ingreso pago : pagosDeUsuario) {
                ingresoTotal += pago.getMonto();
            }

            // Agrega los resultados a la respuesta
            this.respuesta.getGanaciasPorAnunciante().add(ingresoTotal);
            this.respuesta.getTiposDeAnuncio().add(tiposAnuncio);
            this.respuesta.getIngresos().add(pagosDeUsuario);
        }

        // Asigna el anunciante filtrado a la respuesta
        this.respuesta.setAnunciantes(usuariosAnunciantes);
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
     * Crea el reporte en formato PDF usando JasperReports y lo convierte a Base64.
     * 
     * @throws FileNotFoundException Si no se encuentra el archivo Jasper.
     * @throws IOException Si ocurre un error durante el proceso de exportación.
     */
    private void crearReporte() throws FileNotFoundException, IOException {
        ReporteIngresoPorAnuncianteJasperReport report = new ReporteIngresoPorAnuncianteJasperReport();
        try {
            // Carga el archivo .jasper
            JasperReport reporte = (JasperReport) JRLoader.loadObject(new File("/home/carlosrodriguez/JaspersoftWorkspace/MyReports/Blank_Letter_Landscape.jasper"));

            // Llena el reporte con los datos de los ingresos
            JasperPrint e = JasperFillManager.fillReport(reporte, null, report.getDataSource(
                    this.respuesta.getIngresos(), this.respuesta.getAnunciantes(), this.respuesta.getGanaciasPorAnunciante()));

            // Crea un ByteArrayOutputStream para almacenar el PDF
            ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();

            // Exporta el reporte a PDF
            JasperExportManager.exportReportToPdfStream(e, pdfOutputStream);

            // Convierte el ByteArrayOutputStream en un arreglo de bytes
            byte[] pdfBytes = pdfOutputStream.toByteArray();

            // Convierte los bytes a Base64
            String pdfBase64 = Base64.getEncoder().encodeToString(pdfBytes);
            this.respuesta.setPdf(pdfBase64);

        } catch (JRException e) {
            e.printStackTrace();  // Imprime el error completo en la consola
        }
    }
}
