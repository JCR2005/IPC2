package Backend.Suscritor;

import JPA.Controladora;
import JPA.Revista;
import JPA.Suscripciòn;
import java.util.List;
import respuetas.Suscriptor.RespuestaBusqueda;

/**
 * Esta clase maneja la lógica de búsqueda de revistas para los usuarios suscriptores.
 * Permite obtener una lista de revistas aprobadas que el usuario aún no ha suscrito, 
 * y realiza la búsqueda filtrando por título.
 * 
 * @author carlosrodriguez
 */
public class ConfiguracionBusqueda {
    
    private final Controladora controladora = new Controladora();
    private final RespuestaBusqueda respuesta = new RespuestaBusqueda();

    /**
     * Método principal para obtener una lista de revistas filtradas por título 
     * que el usuario aún no ha suscrito.
     * 
     * @param titulo El título de la revista para filtrar la búsqueda.
     * @param usuario El identificador del usuario que está realizando la búsqueda.
     * @return Un objeto RespuestaBusqueda con las revistas encontradas o un mensaje de error.
     */
    public RespuestaBusqueda obtenerListaDeRevistas(String titulo, String usuario) {
        try {
            // Llama al método que obtiene las revistas y verifica la suscripción del usuario
            obtenerRevistas(titulo, usuario);
        } catch (Exception e) {
            // Si ocurre algún error, se establece un mensaje de error
            this.respuesta.setMensaje("Algo salió mal, intenta más tarde");
            this.respuesta.setProcesoExitoso(false);
        }

        return this.respuesta;
    }

    /**
     * Obtiene las revistas aprobadas y verifica si el usuario ya está suscrito a ellas.
     * Si el usuario no está suscrito y la revista está aprobada, la agrega a la lista de resultados.
     * Además, filtra las revistas por el título proporcionado.
     * 
     * @param titulo El título para filtrar las revistas.
     * @param usuario El identificador del usuario que realiza la búsqueda.
     */
    private void obtenerRevistas(String titulo, String usuario) {
        // Obtiene la lista de revistas aprobadas y suscripciones desde la base de datos
        List<Revista> revistasAprobadas = this.controladora.obtenerRevistas();
        List<Suscripciòn> suscripciones = this.controladora.obtenerSuscripciones();
        
        // Itera sobre las revistas aprobadas
        for (Revista revista : revistasAprobadas) {
            boolean suscrito = false;
            
            // Verifica si el usuario ya está suscrito a esta revista
            for (Suscripciòn suscripcion : suscripciones) {
                if (suscripcion.getIdRevista().equals(revista.getIdRevista()) 
                    && suscripcion.getIdUsuario().equals(usuario)) {
                    suscrito = true;
                    break;  // Si ya está suscrito, no hace falta seguir buscando
                }
            }
            
            // Si el usuario no está suscrito y la revista está aprobada
            if (!suscrito && revista.isAprobacion()) {
                // Filtra las revistas por el título
                if (revista.getTitulo().toLowerCase().contains(titulo.toLowerCase())) {
                    // Agrega la revista a la lista de resultados
                    this.respuesta.getRevistas().add(revista);
                }
            }
        }

        // Si no se encontraron revistas, se establece un mensaje de error
        if (this.respuesta.getRevistas().isEmpty()) {
            this.respuesta.setMensaje("No se encontraron revistas con ese título.");
            this.respuesta.setProcesoExitoso(false);
        } else {
            // Si se encontraron revistas, el proceso fue exitoso
            this.respuesta.setProcesoExitoso(true);
        }
    }
}
