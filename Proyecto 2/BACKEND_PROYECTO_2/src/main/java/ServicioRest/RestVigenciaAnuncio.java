package ServicioRest;

import Backend.ConfigurarPrecioAnuncio;
import Backend.ConfigurarVigenciaDeAnuncio;
import JPA.vigenciaAnuncio;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.OPTIONS;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import javax.naming.ConfigurationException;

/**
 *
 * @author carlosrodriguez
 */
@Path("duracionAnuncio")
public class RestVigenciaAnuncio {

    ConfigurarVigenciaDeAnuncio configurarVigenciaAnuncio = new ConfigurarVigenciaDeAnuncio();
    @GET
    public Response ping() {
        return Response.ok("ping ").build();
    }

    @OPTIONS
    public Response options() {
        return Response.ok().build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response configurarCostoAnuncio(vigenciaAnuncio[] vigenciaAnuncios) throws Exception {
        String jsonResponse = null;

        jsonResponse=configurarVigenciaAnuncio.proceso(vigenciaAnuncios);
        
        return Response.ok(jsonResponse).build();
    }
}
