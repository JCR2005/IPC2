package Backend.administracion;

import JPA.Anuncio;
import JPA.Controladora;
import JPA.HistorialEfectividadAnuncios;
import JPA.Usuario;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import respuetas.Administrador.RespuestaReporteEfectividadAnuncios;

/**
 *
 * @author carlosrodriguez
 */
public class ConfiguracionReporteEficienciaAnuncios {

    Controladora controladora = new Controladora();
    RespuestaReporteEfectividadAnuncios respuesta = new RespuestaReporteEfectividadAnuncios();

    public RespuestaReporteEfectividadAnuncios obtenerHistorial(String fechaInicio, String fechaFin) {

        List<Usuario> anunciante = controladora.obtenerlistaUsuarios().stream()
                .filter(usuario -> (usuario.getTipoCuenta().endsWith("Anunciante"))).collect(Collectors.toList());

        List<List<Anuncio>> anunciosDeUsuarios = new ArrayList<>();

        for (int i = 0; i < anunciante.size(); i++) {
            String anunciador = anunciante.get(i).getUsuario();
            List<Anuncio> anuncios = controladora.obtenerAlumnos().stream()
                    .filter(anuncio -> (anunciador.endsWith(anuncio.getUsuario()) && anuncio.isEstado())).collect(Collectors.toList());

            anunciosDeUsuarios.add(anuncios);
        }

        Date fechaInicioDate = convertirStringADate(fechaInicio);
        Date fechaFinDate = convertirStringADate(fechaFin);

        // Obtener el historial completo
        List<HistorialEfectividadAnuncios> historial = this.controladora.obtenerHistorial();

        // Filtrar los objetos HistorialEfectividadAnuncios dentro del rango de fechas
        List<HistorialEfectividadAnuncios> historialFiltrado = historial.stream()
                .filter(historialEfectividad -> {
                    Date fecha = historialEfectividad.getFecha(); // Suponiendo que getFecha() devuelve java.sql.Date
                    return !fecha.before(fechaInicioDate) && !fecha.after(fechaFinDate);
                })
                .collect(Collectors.toList());

        for (int i = 0; i < anunciante.size(); i++) {
            String anunciador = anunciante.get(i).getUsuario();
            List<Anuncio> anuncios = controladora.obtenerAlumnos().stream()
                    .filter(anuncio -> (anunciador.endsWith(anuncio.getUsuario()) && anuncio.isEstado())).collect(Collectors.toList());

            anunciosDeUsuarios.add(anuncios);
        }
        return respuesta;
    }

    public Date convertirStringADate(String fecha) {
        try {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = formato.parse(fecha);
            return new Date(date.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
