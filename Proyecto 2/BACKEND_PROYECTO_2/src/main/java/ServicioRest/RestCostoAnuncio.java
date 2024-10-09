
package ServicioRest;

import Backend.ConfigurarPrecioAnuncio;
import JPA.CostoAnuncio;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.OPTIONS;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author carlosrodriguez
 */

@Path("CostoAnuncio")
public class RestCostoAnuncio {
    
    private ConfigurarPrecioAnuncio configurarPrecioAnuncio=new ConfigurarPrecioAnuncio();

    @GET
    public Response ping() {
        return Response.ok("ping CostoAnuncio").build();
    }

    @OPTIONS
    public Response options() {
        return Response.ok().build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response configurarCostoAnuncio(CostoAnuncio[] costoAnuncios) throws Exception {
        String jsonResponse=null;
        
        
        
        jsonResponse = configurarPrecioAnuncio.proceso(costoAnuncios);
    

        return Response.ok(jsonResponse).build();
    }
}
