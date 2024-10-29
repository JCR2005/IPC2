package Backend.administracion;

import JPA.Anuncio;
import JPA.Controladora;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import respuetas.RespuetaEstadoAnuncio;

/**
 *
 * @author carlosrodriguez
 */
public class EstadoAnuncio {

    RespuetaEstadoAnuncio respuesta = new RespuetaEstadoAnuncio();

    private Controladora controladora = new Controladora();

    private List<Anuncio> anuncios = new ArrayList<>();

    public Map<String, List<?>> procesoLista() {

        obtenerAnuncios();

        // Usamos un Map para enviar múltiples arreglos
        Map<String, List<?>> response = new HashMap<>();

        response.put("listaAnuncios", this.anuncios);

        return response;
    }

    private void obtenerAnuncios() {
        this.anuncios = this.controladora.obtenerAlumnos();
    }

    public RespuetaEstadoAnuncio procesoCambioDeEstado(String idAnuncio, String estado) {

        try {
            // Obtener el anuncio
            Anuncio anuncio = this.controladora.obetenerAnuncio(idAnuncio);

            // Verificar si el anuncio existe
            if (anuncio == null) {
                this.respuesta.setMensaje("El anuncio no existe");
                 
                return this.respuesta;
            }

            // Validar el parámetro estado
            if (!"true".equalsIgnoreCase(estado) && !"false".equalsIgnoreCase(estado)) {
                this.respuesta.setMensaje("Ocurrio un error, contacta a soporte por favor");
                return this.respuesta;

            }

            // Cambiar el estado del anuncio
            boolean cambio = Boolean.parseBoolean(estado);

            if (cambio) {
                anuncio.setEstado(false);

                this.respuesta.setMensaje("Se desactivo el anuncio con exito");

            } else {
                anuncio.setEstado(true);
                this.respuesta.setMensaje("Se activo el anuncio con exito");

            }
            this.respuesta.setCambioExitoso(true);
            this.controladora.editarAnuncio(anuncio);

            return this.respuesta;
        } catch (Exception e) {
            this.respuesta.setMensaje("Ha ocurrido un error, contacta a soporte");
            this.respuesta.setCambioExitoso(false);
            return this.respuesta;

        }

    }
}
