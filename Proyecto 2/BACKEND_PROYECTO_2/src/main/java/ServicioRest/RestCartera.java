package ServicioRest;

import Backend.ConfiguracionCartera;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.OPTIONS;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.FormParam;

/**
 * Clase que define los endpoints de la API para manejar la funcionalidad de la cartera.
 * Permite recargar saldo y obtener el saldo de un usuario específico.
 * 
 * @author carlosrodriguez
 */
@Path("cartera")
public class RestCartera {

    private final ConfiguracionCartera cartera = new ConfiguracionCartera();

    /**
     * Constructor de la clase RestCartera.
     * Inicializa la instancia de Cartera.
     */
    public RestCartera() {
    }

    /**
     * Endpoint para realizar una comprobación básica de conexión (ping).
     * 
     * @return Respuesta con un mensaje de confirmación.
     */
    @GET
    public Response ping() {
        return Response.ok("ping compra").build();
    }

    /**
     * Endpoint OPTIONS que define las opciones disponibles para el recurso "cartera".
     * 
     * @return Respuesta con los métodos permitidos.
     */
    @OPTIONS
    public Response options() {
        return Response.ok().build();
    }

    /**
     * Endpoint para recargar el saldo de la cartera de un usuario.
     * 
     * @param usuarioJson El JSON con los detalles del usuario.
     * @param saldo El monto de la recarga que se va a aplicar al saldo.
     * @return Respuesta con el resultado de la recarga.
     * @throws Exception Si ocurre un error durante el proceso de recarga.
     */
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response recargaCartera(@FormParam("usuario") String usuarioJson,
                                   @FormParam("recarga") String saldo) throws Exception {
        if (usuarioJson == null || usuarioJson.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("{\"error\": \"Usuario no puede ser nulo o vacío\"}")
                           .build();
        }

        if (saldo == null || saldo.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("{\"error\": \"Saldo no puede ser nulo o vacío\"}")
                           .build();
        }

        try {
            // Llamar al servicio de recarga de saldo
            return Response.ok(cartera.recargarSaldo(usuarioJson, saldo)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("{\"error\": \"Error al procesar la recarga: " + e.getMessage() + "\"}")
                           .build();
        }
    }

    /**
     * Endpoint para obtener el saldo actual de un usuario.
     * 
     * @param usuario El usuario cuyo saldo se desea consultar.
     * @return Respuesta con el saldo del usuario.
     * @throws Exception Si ocurre un error al obtener el saldo.
     */
    @POST
    @Path("obtenerSaldo")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response solicitarSaldo(@FormParam("usuario") String usuario) throws Exception {
        if (usuario == null || usuario.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("{\"error\": \"El usuario no puede ser nulo o vacío\"}")
                           .build();
        }

        try {
            // Obtener el saldo del usuario
            return Response.ok(cartera.proceso(usuario)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("{\"error\": \"Error al obtener el saldo: " + e.getMessage() + "\"}")
                           .build();
        }
    }
}
