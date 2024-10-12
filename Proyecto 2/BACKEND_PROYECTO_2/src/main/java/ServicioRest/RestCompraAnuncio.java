package ServicioRest;

import Backend.CompraAnuncio;
import JPA.Anuncio;


import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.OPTIONS;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.InputStream;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 *
 * @author carlosrodriguez
 */
@Path("compraAnuncio")
public class RestCompraAnuncio {

    CompraAnuncio compraanuncio=new CompraAnuncio();
    
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
    public Response registrarCompra(@FormDataParam("anuncio") String anuncioJson,
            @FormDataParam("imagen") InputStream imagenInputStream) throws Exception {
        
       
        compraanuncio.proceso(imagenInputStream, anuncioJson);
    
        String jsonResponse = "{\"message\":\"  llega calida\"}"; // Cambia esto según tus necesidades

        return Response.ok(jsonResponse).build();
    }

    
}
