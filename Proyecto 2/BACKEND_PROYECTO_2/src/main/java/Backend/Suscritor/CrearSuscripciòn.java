package Backend.Suscritor;

import JPA.Controladora;
import JPA.Revista;
import JPA.Suscripciòn;
import java.sql.Date;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import respuetas.Suscriptor.RespuetaSuscripciòn;

/**
 * Clase encargada de manejar las operaciones relacionadas con la suscripción de un usuario a revistas.
 * Incluye la obtención de revistas disponibles y la creación de nuevas suscripciones.
 * 
 * @author carlosrodriguez
 */
public class CrearSuscripciòn {

    private Controladora controladora = new Controladora();  // Instancia de la controladora para interactuar con la base de datos
    private RespuetaSuscripciòn respuesta = new RespuetaSuscripciòn();  // Objeto que guarda la respuesta de las operaciones

    /**
     * Obtiene la lista de revistas disponibles para un usuario, excluyendo las que ya están suscritas.
     * 
     * @param usuario El ID del usuario que quiere obtener las revistas.
     * @return Una respuesta con la lista de revistas disponibles para suscripción.
     */
    public RespuetaSuscripciòn obtenerListaDeRevistas(String usuario) {
        try {
            obtenerRevistas(usuario);  // Obtiene las revistas que están disponibles para el usuario
        } catch (Exception e) {
            this.respuesta.setMensaje("Algo salió mal, intenta más tarde.");
            this.respuesta.setProcesoExitoso(false);
        }
        return this.respuesta;
    }

    /**
     * Filtra las revistas aprobadas que aún no están suscritas por el usuario.
     * 
     * @param usuario El ID del usuario para verificar si ya está suscrito.
     */
    private void obtenerRevistas(String usuario) {
        List<Revista> revistasAprobadas = this.controladora.obtenerRevistas();
        List<Suscripciòn> suscripciòns = this.controladora.obtenerSuscripciones();

        for (Revista revista : revistasAprobadas) {
            boolean suscrito = false;

            // Comprobamos si el usuario ya está suscrito a la revista
            for (Suscripciòn suscripcion : suscripciòns) {
                if (suscripcion.getIdRevista().equals(revista.getIdRevista()) && suscripcion.getIdUsuario().equals(usuario)) {
                    suscrito = true;
                    break;  // Ya está suscrito, no necesitamos seguir buscando
                }
            }

            // Si el usuario no está suscrito y la revista está aprobada, la añadimos a la lista de revistas disponibles
            if (!suscrito && revista.isAprobacion()) {
                this.respuesta.getRevistas().add(revista);
            }
        }

        // Si no hay revistas disponibles para suscripción
        if (this.respuesta.getRevistas().isEmpty()) {
            this.respuesta.setMensaje("No hay revistas disponibles para suscripción.");
            this.respuesta.setProcesoExitoso(false);
        }
    }

    /**
     * Realiza la suscripción a una revista.
     * 
     * @param suscripciòn El objeto Suscripciòn que contiene la información del usuario y la revista.
     * @return Respuesta con el resultado de la operación de suscripción.
     */
    public RespuetaSuscripciòn suscribirRevista(Suscripciòn suscripciòn) {
        try {
            // Validamos que el usuario exista en la base de datos
            if (!validarExistenciaUsuario(suscripciòn.getIdUsuario())) {
                this.respuesta.setProcesoExitoso(false);
                this.respuesta.setMensaje("No se encontró el usuario.");
                return this.respuesta;
            }

            // Generamos la fecha de suscripción en el formato adecuado
            if (!generarFechaPublicacion(suscripciòn)) {
                this.respuesta.setProcesoExitoso(false);
                this.respuesta.setMensaje("Fecha de suscripción no válida.");
                return this.respuesta;
            }

            // Registramos la suscripción en la base de datos
            this.controladora.crearSuscripciòn(suscripciòn);
            this.respuesta.setMensaje("Te has suscrito a la revista exitosamente.");
            this.respuesta.setProcesoExitoso(true);
            return this.respuesta;

        } catch (Exception e) {
            this.respuesta.setProcesoExitoso(false);
            this.respuesta.setMensaje("Ocurrió un error inesperado, por favor contacta a soporte.");
            return this.respuesta;
        }
    }

    /**
     * Genera la fecha de suscripción en el formato adecuado y la asigna a la suscripción.
     * Convierte la fecha en formato ISO 8601 a un objeto Date de SQL para su almacenamiento.
     * 
     * @param suscripciòn El objeto Suscripciòn que contiene la fecha de suscripción.
     * @return true si la fecha fue generada correctamente, false en caso de error.
     */
    private boolean generarFechaPublicacion(Suscripciòn suscripciòn) {
        try {
            // Utilizamos el formato de fecha ISO 8601 con zona horaria
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
            OffsetDateTime fechaOffsetDateTime = OffsetDateTime.parse(suscripciòn.getFechaSuscripcionTexto(), formato);

            // Convertimos a LocalDate (solo la parte de la fecha, sin hora)
            LocalDate fechaLocalDate = fechaOffsetDateTime.toLocalDate();

            // Convertimos a java.sql.Date para su almacenamiento en la base de datos
            Date fechaPublicacion = java.sql.Date.valueOf(fechaLocalDate);
            suscripciòn.setFechaSuscricion(fechaPublicacion);  // Asignamos la fecha generada

            return true;
        } catch (Exception e) {
            return false;  // En caso de error al generar la fecha
        }
    }

    /**
     * Valida si un usuario existe en la base de datos.
     * 
     * @param idUsuario El ID del usuario que se va a verificar.
     * @return true si el usuario existe, false si no.
     * @throws Exception si ocurre un error en la consulta.
     */
    private boolean validarExistenciaUsuario(String idUsuario) throws Exception {
        return this.controladora.buscarUsuarios(idUsuario);
    }
}
