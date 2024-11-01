package ServicioRest.Editor;

import Backend.revistas.ConfiguracionArticulo;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.InputStream;
import org.glassfish.jersey.media.multipart.FormDataParam;
import respuetas.Editor.RespuestaArticulo;

/**
 *
 * @author carlosrodriguez
 */
@Path("articulo")
public class RestArticulo {
    
    ConfiguracionArticulo configuracionArticulo=new ConfiguracionArticulo();
    RespuestaArticulo respuesta=new RespuestaArticulo();

    @POST
    @Path("crearArticulo")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrarCompra(@FormDataParam("articulo") String anuncioJson,
            @FormDataParam("imagen") InputStream imagenInputStream,@FormDataParam("pdf") InputStream pdfInputStream) throws Exception {
        System.out.println("sssssssssssssssssssssssssssssssss");
     respuesta = configuracionArticulo.proceso(imagenInputStream,pdfInputStream, anuncioJson);

        return Response.ok(respuesta).build();
    }
}
