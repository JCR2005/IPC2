package ServicioRest.Suscritores;

import Backend.Suscritor.CrearSuscripciòn;
import JPA.Suscripciòn;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataParam;
import respuetas.Suscriptor.RespuetaSuscripciòn;

/**
 *
 * @author carlosrodriguez
 */
@Path("Suscripciones")
public class RestSuscripcòn {

    private RespuetaSuscripciòn respuesta = new RespuetaSuscripciòn();
    private CrearSuscripciòn suscripciòn = new CrearSuscripciòn();

    @POST
    @Path("listaRevistas")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public RespuetaSuscripciòn obtenerListaDeRevistas(@FormDataParam("usuario") String usuario) {

      
        this.respuesta = suscripciòn.obtenerListaDeRevistas(usuario);

        return this.respuesta;
    }

    @POST
    @Path("suscripcion")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response suscribir(Suscripciòn suscripciòn) throws Exception {
        this.respuesta = this.suscripciòn.suscribirRevista(suscripciòn);

        return Response.ok(this.respuesta).build();
    }

}
