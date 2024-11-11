package ServicioRest.Suscritores;

import Backend.Suscritor.ConfiguracionBusqueda;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.IOException;
import org.glassfish.jersey.media.multipart.FormDataParam;
import respuetas.Suscriptor.RespuestaBusqueda;
import respuetas.Suscriptor.RespuestaVisualizaciòn;

/**
 *
 * @author carlosrodriguez
 */
@Path("Busqueda")
public class RestBusqueda {

    RespuestaBusqueda respuesta = new RespuestaBusqueda();
    ConfiguracionBusqueda busqueda = new ConfiguracionBusqueda();

    @POST
    @Path("busqueda")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerComentarios(@FormDataParam("titulo") String titulo,@FormDataParam("idUsuario") String idUsuario) throws IOException {

        this.respuesta = busqueda.obtenerListaDeRevistas( titulo,idUsuario);
        return Response.ok(this.respuesta).build();
    }

}
