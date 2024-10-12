package ServicioRest;

import Backend.Cartera;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.OPTIONS;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 *
 * @author carlosrodriguez
 */
@Path("cartera")
public class RestCartera {

    Cartera cartera = new Cartera();

    public RestCartera() {
    }

    @GET
    public Response ping() {
        return Response.ok("ping compra").build();
    }

    @OPTIONS
    public Response options() {
        return Response.ok().build();
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response recargaCartera(@FormDataParam("usuario") String usuarioJson,
            @FormDataParam("recarga") String saldo) throws Exception {

        return Response.ok(cartera.recargarSaldo(usuarioJson, saldo)).build();
    }

    @POST
    @Path("obtenerSaldo")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response solicitarSaldo(@FormDataParam("usuario") String usuario) throws Exception {

        return Response.ok(cartera.proceso(usuario)).build();
    }

}
