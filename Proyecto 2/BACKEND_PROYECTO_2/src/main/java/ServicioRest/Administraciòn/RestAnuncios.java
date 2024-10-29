package ServicioRest.Administraciòn;

import Backend.administracion.EstadoAnuncio;
import JPA.Anuncio;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Map;
import org.glassfish.jersey.media.multipart.FormDataParam;
import respuetas.RespuetaEstadoAnuncio;

/**
 *
 * @author carlosrodriguez
 */
@Path("AnunciosAdministracion")
public class RestAnuncios {

    private EstadoAnuncio estadoAnuncio = new EstadoAnuncio();

    @GET
    @Path("listaAnuncios")

    public List<Anuncio> listaAnuncios() throws Exception {
        // Procesar el JSON y convertirlo en un objeto Java

        // Llamamos al método 'proceso' para obtener el Map con los anuncios filtrados
        Map<String, List<?>> responseMap = estadoAnuncio.procesoLista();

        List<Anuncio> anuncios = (List<Anuncio>) responseMap.get("listaAnuncios");
        return anuncios; // Retornar la lista de anuncios
    }

    @POST
    @Path("cambioEstado")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response cambiarEstadoAnuncio(@FormDataParam("idAnuncio") String idAnuncio, @FormDataParam("estado") String estado) throws Exception {
        RespuetaEstadoAnuncio respeusta = estadoAnuncio.procesoCambioDeEstado(idAnuncio, estado);
        return Response.ok(respeusta).build();
    }

}
