package Backend.Suscritor;

import JPA.Controladora;
import JPA.MeGusta;
import java.util.List;
import respuetas.Suscriptor.RespuestaLikes;

/**
 * Clase encargada de manejar la lógica relacionada con los "me gusta" en las revistas.
 * Permite obtener la cantidad de "me gusta" de una revista específica, así como añadir o quitar un "me gusta".
 * 
 * @author carlosrodriguez
 */
public class ConfiguracionLikes {

    private MeGusta meGustaEnProceso;
    private final RespuestaLikes respuesta = new RespuestaLikes();
    private final Controladora controladora = new Controladora();

    /**
     * Obtiene la cantidad de "me gusta" para una revista específica.
     * 
     * @param idRevista El identificador de la revista a consultar.
     * @return Un objeto RespuestaLikes con la cantidad de "me gusta" para la revista.
     */
    public RespuestaLikes obtenerLikesDeRevistas(String idRevista) {
        int contador = 0;
        List<MeGusta> likesDeRevista = controladora.obtenerLikes();

        // Si la lista de "me gusta" no es nula, contar los "me gusta" para la revista especificada
        if (likesDeRevista != null) {
            for (MeGusta like : likesDeRevista) {
                if (like.getIdRevista().equals(idRevista) && like.isMeGusta()) {
                    contador++;
                }
            }
        }

        this.respuesta.setCantidadLikes(contador);
        return this.respuesta;
    }

    /**
     * Añade o quita un "me gusta" para una revista, dependiendo del estado actual del "me gusta".
     * 
     * @param meGusta El objeto MeGusta que contiene la información del "me gusta" del usuario.
     * @return Un objeto RespuestaLikes con el resultado de la operación.
     */
    public RespuestaLikes darMeGusta(MeGusta meGusta) {
        try {
            // Obtener la fecha actual
            java.util.Date fechaActual = new java.util.Date();  

            // Comprobar si ya existe un "me gusta" para este usuario y revista
            if (obtenerMegGusta(meGusta)) {
                // Si ya existe, cambiar el estado del "me gusta"
                if (meGustaEnProceso.isMeGusta()) {
                    meGustaEnProceso.setMeGusta(false);  // Si ya le gusta, quitar el "me gusta"
                } else {
                    meGustaEnProceso.setMeGusta(true);   // Si no le gusta, poner el "me gusta"
                }

                // Asignar la fecha actual al "me gusta"
                meGustaEnProceso.setFecha(new java.sql.Date(fechaActual.getTime()));

                // Actualizar el "me gusta" en la base de datos
                this.controladora.actualizarMeGusta(meGustaEnProceso);
            } else {
                // Si no existe un "me gusta", añadir uno nuevo
                meGusta.setMeGusta(true);
                meGusta.setFecha(new java.sql.Date(fechaActual.getTime()));  // Establecer la fecha actual

                // Guardar el "me gusta" en la base de datos
                this.controladora.añadirMeGusta(meGusta);
            }

            // Respuesta exitosa
            this.respuesta.setMensaje("Operación de 'Me Gusta' exitosa.");
        } catch (Exception e) {
            // Manejo de excepciones en caso de error
            e.printStackTrace();
            this.respuesta.setMensaje("Error al procesar el 'Me Gusta'.");
        }

        return this.respuesta;
    }

    /**
     * Verifica si ya existe un "me gusta" para un usuario y una revista específicos.
     * 
     * @param meGusta El objeto MeGusta con los datos del usuario y la revista.
     * @return true si el usuario ya ha dado su "me gusta" a la revista, false en caso contrario.
     */
    private boolean obtenerMegGusta(MeGusta meGusta) {
        String idUsuario = meGusta.getIdUsuario();
        String idRevista = meGusta.getIdRevista();

        // Obtener todos los "me gusta" registrados
        List<MeGusta> likesDeRevista = controladora.obtenerLikes();
        
        // Verificar si el usuario ya ha dado su "me gusta" a la revista
        if (likesDeRevista != null) {
            for (MeGusta like : likesDeRevista) {
                if (like.getIdUsuario().equals(idUsuario) && like.getIdRevista().equals(idRevista)) {
                    meGustaEnProceso = like;  // Guardar el "me gusta" en proceso
                    return true;
                }
            }
        }

        return false;  // Si no se encontró un "me gusta" para este usuario y revista
    }
}
