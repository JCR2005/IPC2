package Backend.anuncios;

import ControlPersistencia.exceptions.NonexistentEntityException;
import JPA.Anuncio;
import JPA.Controladora;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author carlosrodriguez
 */
public class ListaDeAnuncios {

    private Controladora controladora = new Controladora();
 
    private List<Anuncio> anuncios = new ArrayList<>();

    public Map<String, List<?>> procesoLista(String usuario) {

        obtenerAnuncios(usuario);

        // Usamos un Map para enviar múltiples arreglos
        Map<String, List<?>> response = new HashMap<>();

        response.put("listaAnuncios",this. anuncios);

        return response;
    }

    private void obtenerAnuncios(String usuario) {
        List<Anuncio> anuncios = this.controladora.obtenerAlumnos();

        this.anuncios = anuncios.stream()
                .filter(anuncio -> anuncio.getUsuario().endsWith(usuario))
                .collect(Collectors.toList());

    }

    public String procesoCambioDeEstado(String idAnuncio, String estado) {
        String mensaje;
        try {
            // Obtener el anuncio
            Anuncio anuncio = this.controladora.obetenerAnuncio(idAnuncio);

            // Verificar si el anuncio existe
            if (anuncio == null) {
                return "{\"message\":\"El anuncio no existe.\"}";
            }

            // Validar el parámetro estado
            if (!"true".equalsIgnoreCase(estado) && !"false".equalsIgnoreCase(estado)) {
                return "{\"message\":\"El estado debe ser 'true' o 'false'.\"}";
            }

            // Cambiar el estado del anuncio
            boolean cambio = Boolean.parseBoolean(estado);
         
            if (cambio) {
                  anuncio.setEstado(false);
                       mensaje = "Se desactivo el anuncio " ;
            }else  {
                anuncio.setEstado(true);
                    mensaje = "Se activo el anuncio " ;
                   
              
            }
          
            this.controladora.editarAnuncio(anuncio);

            // Crear mensaje de éxito
          
        } catch (Exception e) {
            mensaje = "Ha ocurrido un error: " + e.getMessage(); // Incluir el mensaje del error
        }
        
        
        return "{\"message\":\"" + mensaje + "\"}";
    }

}
