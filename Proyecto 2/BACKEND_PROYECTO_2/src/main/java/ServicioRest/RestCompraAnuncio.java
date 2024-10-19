package ServicioRest;

import Backend.anuncios.CompraAnuncio;
import Backend.anuncios.ListaDeAnuncios;
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
import java.util.List;
import java.util.Map;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 *
 * @author carlosrodriguez
 */
@Path("compraAnuncio")
public class RestCompraAnuncio {

    CompraAnuncio compraanuncio = new CompraAnuncio();
    ListaDeAnuncios listaDeAnuncios = new ListaDeAnuncios();

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

        String jsonResponse = compraanuncio.proceso(imagenInputStream, anuncioJson);

        return Response.ok(jsonResponse).build();
    }

    @POST
    @Path("listaAnuncios")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public List<Anuncio> listaAnuncios(@FormDataParam("usuario") String usuarioJson) throws Exception {
        // Procesar el JSON y convertirlo en un objeto Java

        // Llamamos al método 'proceso' para obtener el Map con los anuncios filtrados
        Map<String, List<?>> responseMap = listaDeAnuncios.procesoLista(usuarioJson);

        List<Anuncio> anuncios = (List<Anuncio>) responseMap.get("listaAnuncios");
        return anuncios; // Retornar la lista de anuncios
    }

    @POST
    @Path("cambioEstado")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response cambiarEstadoAnuncio(@FormDataParam("idAnuncio") String idAnuncio,@FormDataParam("estado") String estado) throws Exception {
        
        String jsonResponse =listaDeAnuncios.procesoCambioDeEstado(idAnuncio, estado);
        return Response.ok(jsonResponse).build();
    }
}
