package Backend.revistas;

import JPA.Anuncio;
import JPA.Controladora;
import JPA.Revista;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author carlosrodriguez
 */
public class configuracionRevistas {

    private Controladora controladora = new Controladora();
    private List<Revista> revistas = new ArrayList<>();

    public Map<String, List<?>> procesoLista(String usuario) {

        obtenerRevistas(usuario);

        // Usamos un Map para enviar múltiples arreglos
        Map<String, List<?>> response = new HashMap<>();

        response.put("listaAnuncios", this.revistas);

        return response;
    }

    private void obtenerRevistas(String usuario) {
        List<Revista> revistas = this.controladora.obtenerRevistas();

        this.revistas = revistas.stream()
                .filter(revista -> revista.getAutor().endsWith(usuario))
                .filter(revista -> revista.isAprobacion())
                .collect(Collectors.toList());

    }

    public String procesoCambioDeEstadoLikes(String idRevista, String likes) {
        String mensaje;
        try {
            // Obtener el anuncio
            Revista revista = this.controladora.obtenerRevista(idRevista);
            // Verificar si el anuncio existe
            if (revista == null) {
                return "{\"message\":\"la revista no existe.\"}";
            }
            // Validar el parámetro estado
            if (!"true".equalsIgnoreCase(likes) && !"false".equalsIgnoreCase(likes)) {
                return "{\"message\":\"El estado debe ser 'true' o 'false'.\"}";
            }
            // Cambiar el estado del anuncio
            boolean cambio = Boolean.parseBoolean(likes);
            if (cambio) {
                revista.setLikes(false);
                mensaje = "Los likes a esta revista han sido restringidos ";
            } else {
                revista.setLikes(true);
                mensaje = "Los like a esta revista han sido permitidos";

            }

            this.controladora.editarRevista(revista);

            // Crear mensaje de éxito
        } catch (Exception e) {
            mensaje = "Ha ocurrido un error: " + e.getMessage(); // Incluir el mensaje del error
        }

        return "{\"message\":\"" + mensaje + "\"}";
    }
    
    
    
      public String procesoCambioDeEstadoComentarios(String idRevista, String comentarios) {
        String mensaje;
        try {
            // Obtener el anuncio
            Revista revista = this.controladora.obtenerRevista(idRevista);
            // Verificar si el anuncio existe
            if (revista == null) {
                return "{\"message\":\"la revista no existe.\"}";
            }
            // Validar el parámetro estado
            if (!"true".equalsIgnoreCase(comentarios) && !"false".equalsIgnoreCase(comentarios)) {
                return "{\"message\":\"El estado debe ser 'true' o 'false'.\"}";
            }
            // Cambiar el estado del anuncio
            boolean cambio = Boolean.parseBoolean(comentarios);
            if (cambio) {
                revista.setComentarios(false);
                mensaje = "Los comentarios a esta revista han sido restringidos ";
            } else {
                revista.setComentarios(true);
                mensaje = "Los comentarios a esta revista han sido permitidos";

            }

            this.controladora.editarRevista(revista);

            // Crear mensaje de éxito
        } catch (Exception e) {
            mensaje = "Ha ocurrido un error: " + e.getMessage(); // Incluir el mensaje del error
        }

        return "{\"message\":\"" + mensaje + "\"}";
    }

 public String procesoCambioDeEstadoSuscripciones(String idRevista, String suscripciones) {
        String mensaje;
        try {
            // Obtener el anuncio
            Revista revista = this.controladora.obtenerRevista(idRevista);
            // Verificar si el anuncio existe
            if (revista == null) {
                return "{\"message\":\"la revista no existe.\"}";
            }
            // Validar el parámetro estado
            if (!"true".equalsIgnoreCase(suscripciones) && !"false".equalsIgnoreCase(suscripciones)) {
                return "{\"message\":\"El estado debe ser 'true' o 'false'.\"}";
            }
            // Cambiar el estado del anuncio
            boolean cambio = Boolean.parseBoolean(suscripciones);
            if (cambio) {
                revista.setSuscripciones(false);
                mensaje = "Las suscripciones a esta revista han sido restringidas ";
            } else {
                revista.setSuscripciones(true);
                mensaje = "Las suscripciones a esta revista han sido permitidas";

            }

            this.controladora.editarRevista(revista);

            // Crear mensaje de éxito
        } catch (Exception e) {
            mensaje = "Ha ocurrido un error: " + e.getMessage(); // Incluir el mensaje del error
        }

        return "{\"message\":\"" + mensaje + "\"}";
    }

}

