package Backend.Suscritor;

import JPA.Comentario;
import JPA.Controladora;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import respuetas.Suscriptor.RespuestaComentario;

/**
 * Esta clase maneja la lógica de creación y obtención de comentarios para revistas,
 * incluyendo la validación de los datos de entrada y el manejo de la fecha de publicación.
 * 
 * @author carlosrodriguez
 */
public class ConfiguracionComentarios {

    private final Controladora controladora = new Controladora();
    private final RespuestaComentario respuesta = new RespuestaComentario();

    /**
     * Crea un nuevo comentario para una revista específica.
     * 
     * @param comentario El objeto Comentario que contiene la información del comentario a crear.
     * @return Un objeto RespuestaComentario que contiene el resultado del proceso de creación.
     */
    public RespuestaComentario crearComentario(Comentario comentario) {

        try {
            // Validar que el usuario exista
            if (!validarExistenciaUsuario(comentario.getIdUsuario())) {
                this.respuesta.setProcesoExitoso(false);
                this.respuesta.setMensaje("No se encontró el usuario");
                return this.respuesta;
            }

            // Validar que la revista exista
            if (!validarExistenciaRevista(comentario.getIdRevista())) {
                this.respuesta.setProcesoExitoso(false);
                this.respuesta.setMensaje("No se encontró la revista");
                return this.respuesta;
            }

            // Validar que el comentario no esté vacío y tenga un tamaño adecuado
            if (!validarExistenciaComentario(comentario.getComentario())) {
                this.respuesta.setProcesoExitoso(false);
                return this.respuesta;
            }

            // Generar la fecha de publicación del comentario
            this.generarFechaPublicacion(comentario);

            // Crear el comentario en la base de datos
            this.controladora.crearComentario(comentario);

            // Establecer la respuesta exitosa
            this.respuesta.setProcesoExitoso(true);
            this.respuesta.setMensaje("Comentario creado exitosamente.");
            return this.respuesta;
        } catch (Exception e) {
            // Manejo de excepciones en caso de errores en el proceso
            this.respuesta.setProcesoExitoso(false);
            this.respuesta.setMensaje("Oops! Ha ocurrido un error al intentar subir tu comentario.");
            return this.respuesta;
        }
    }

    /**
     * Asigna la fecha actual al comentario.
     * 
     * @param comentario El objeto Comentario al que se le asignará la fecha actual.
     */
    private void generarFechaPublicacion(Comentario comentario) {
        // Obtiene la fecha actual
        java.sql.Date fechaActual = new java.sql.Date(System.currentTimeMillis());
        
        // Asigna la fecha al comentario
        comentario.setFecha(fechaActual);
    }

    /**
     * Valida que el usuario exista en la base de datos.
     * 
     * @param idUsuario El identificador del usuario a validar.
     * @return true si el usuario existe, false en caso contrario.
     * @throws Exception Si ocurre un error al consultar la base de datos.
     */
    private boolean validarExistenciaUsuario(String idUsuario) throws Exception {
        return this.controladora.buscarUsuarios(idUsuario);
    }

    /**
     * Valida que la revista exista en la base de datos.
     * 
     * @param idRevista El identificador de la revista a validar.
     * @return true si la revista existe, false en caso contrario.
     * @throws Exception Si ocurre un error al consultar la base de datos.
     */
    private boolean validarExistenciaRevista(String idRevista) throws Exception {
        return this.controladora.buscarRevista(idRevista);
    }

    /**
     * Valida que el comentario sea válido (no esté vacío y no exceda la longitud máxima).
     * 
     * @param comentario El texto del comentario a validar.
     * @return true si el comentario es válido, false en caso contrario.
     */
    private boolean validarExistenciaComentario(String comentario) {
        if (comentario == null || comentario.trim().isEmpty()) {
            this.respuesta.setMensaje("Ingrese un comentario");
            return false; // El comentario está vacío o es null
        }

        int longitudComentario = comentario.length();

        // Validar que el comentario no exceda el límite de longitud
        if (longitudComentario > 0 && longitudComentario <= 1500) {
            return true; // El comentario es válido
        } else {
            this.respuesta.setMensaje("El comentario es demasiado largo");
            return false; // El comentario es demasiado largo
        }
    }

    /**
     * Obtiene la lista de comentarios de una revista específica.
     * 
     * @param idRevista El identificador de la revista de la cual se quieren obtener los comentarios.
     * @return Un objeto RespuestaComentario con la lista de comentarios de la revista.
     */
    public RespuestaComentario obtenerComentarios(String idRevista) {
        // Obtiene la lista completa de comentarios
        List<Comentario> todosLosComentarios = this.controladora.obtenerListaComentarios();

        // Filtra los comentarios por el idRevista
        List<Comentario> comentariosFiltrados = todosLosComentarios.stream()
                .filter(comentario -> comentario.getIdRevista().equals(idRevista))
                .collect(Collectors.toList());

        // Invierte el orden de los comentarios para que los más recientes aparezcan primero
        Collections.reverse(comentariosFiltrados);

        // Establece los comentarios filtrados en la respuesta
        this.respuesta.setComentarios(comentariosFiltrados);

        return this.respuesta;
    }
}
