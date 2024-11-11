package ServicioRest.Suscritores;

import Backend.Suscritor.ConfiguracionComentarios;
import JPA.Comentario;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.IOException;
import org.glassfish.jersey.media.multipart.FormDataParam;
import respuetas.Suscriptor.RespuestaComentario;


/**
 *
 * @author carlosrodriguez
 */
@Path("Comentarios")
public class RestCometarios {
    
    RespuestaComentario respuesta=new RespuestaComentario();
    ConfiguracionComentarios configuracionComentarios =new ConfiguracionComentarios();
    

 @POST
@Path("crearComentario")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public Response crearComentario(Comentario comentario) throws IOException {
    System.out.println("Se ha recibido una solicitud para crear un comentario.");
    
    if (comentario != null) {
        System.out.println("Comentario recibido: " + comentario.getComentario());
    } else {
        System.out.println("No se ha recibido ningún comentario");
    }

    // Lógica del método
    this.respuesta = configuracionComentarios.crearComentario(comentario);
    return Response.ok(this.respuesta).build();
}

 @POST
@Path("comentarios")
@Consumes(MediaType.MULTIPART_FORM_DATA)
@Produces(MediaType.APPLICATION_JSON)
public Response obtenerComentarios(@FormDataParam("idRevista") String idRevista) throws IOException {

    
  
    // Lógica del método
    this.respuesta = configuracionComentarios.obtenerComentarios(idRevista);
    return Response.ok(this.respuesta).build();
}


}
