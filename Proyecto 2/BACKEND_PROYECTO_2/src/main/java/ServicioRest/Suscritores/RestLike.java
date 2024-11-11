package ServicioRest.Suscritores;

import Backend.Suscritor.ConfiguracionLikes;
import JPA.Comentario;
import JPA.MeGusta;
import JPA.Suscripciòn;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.IOException;
import org.glassfish.jersey.media.multipart.FormDataParam;
import respuetas.Suscriptor.RespuestaLikes;

/**
 *
 * @author carlosrodriguez
 */
@Path("LikeRevista")
public class RestLike {

    RespuestaLikes respuesta = new RespuestaLikes();
    ConfiguracionLikes configuracionLikes = new ConfiguracionLikes();

    @POST
    @Path("likesRevista")
     @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearComentario( @FormDataParam("idRevista") String idRevista) throws IOException {
      
        
        this.respuesta = configuracionLikes.obtenerLikesDeRevistas(idRevista);
        return Response.ok(this.respuesta).build();
    }
    
        @POST
    @Path("darMeGusta")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response suscribir(MeGusta meGusta) throws Exception {
        
        
        this.respuesta = this.configuracionLikes.darMeGusta(meGusta);
        
        

        return Response.ok(this.respuesta).build();
    }


}
