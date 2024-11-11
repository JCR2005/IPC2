package ServicioRest.Suscritores;

import Backend.Suscritor.Visualizacion;
import JPA.Articulo;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.IOException;

import org.glassfish.jersey.media.multipart.FormDataParam;
import respuetas.Suscriptor.RespuestaVisualizaciòn;
import respuetas.Suscriptor.RespuetaSuscripciòn;

/**
 *
 * @author carlosrodriguez
 */
@Path("Visualizaciòn")
public class RestVusaulizaciòn {

    private RespuestaVisualizaciòn respuesta = new RespuestaVisualizaciòn();
    private Visualizacion Visualizacion = new Visualizacion();

    @POST
    @Path("listaRevistasSuscritas")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerListaDeRevistasSuscritas(@FormDataParam("usuario") String usuario) {

        // Llamamos al método 'proceso' para obtener el Map con los anuncios filtrados
        this.respuesta = Visualizacion.obtenerListaDeRevistasSuscritas(usuario);

        return Response.ok(this.respuesta).build();
    }

    @POST
    @Path("listaAticulos")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerListaDeArticulos(@FormDataParam("idRevista") String idRevista) throws Exception {

        // Llamamos al método 'proceso' para obtener el Map con los anuncios filtrados
        this.respuesta = Visualizacion.obtenerListaDeArticulos(idRevista);

        return Response.ok(this.respuesta).build();
    }

    @POST
    @Path("articulo")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerArticulo(@FormDataParam("idArticulo") String idArticulo) throws IOException {
        System.out.println("entro1");
        // Llamamos al método 'proceso' para obtener el Map con los anuncios filtrados
        this.respuesta = Visualizacion.obtenerArticulo(idArticulo);

        return Response.ok(this.respuesta).build();
    }

}
