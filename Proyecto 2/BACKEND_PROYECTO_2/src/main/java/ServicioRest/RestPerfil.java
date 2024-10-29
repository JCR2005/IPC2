package ServicioRest;

import Backend.Perfil.EditarPerfil;
import Backend.Perfil.Perfil;
import JPA.Usuario;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.OPTIONS;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.InputStream;
import org.glassfish.jersey.media.multipart.FormDataParam;
import respuetas.respuestaEditarPerfil;
import respuetas.respuestaPerfil;

/**
 *
 * @author carlosrodriguez
 */
@Path("miPerfil")
public class RestPerfil {

    private Perfil perfil = new Perfil();
    private EditarPerfil editarPerfil=new EditarPerfil();

    @OPTIONS
    public Response options() {
        return Response.ok().build();
    }

    @POST
    @Path("miPerfil")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPerfil(Usuario usuario) throws Exception {
       
        respuestaPerfil respuesta = this.perfil.proceso(usuario);

        return Response.ok(respuesta).build();
    }
    
    @POST
    @Path("editarMiPerfil")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editarPerfil(Usuario usuario) throws Exception {
       
        respuestaEditarPerfil respuesta = this.editarPerfil.proceso(usuario);

        return Response.ok(respuesta).build();
    }
    
    @POST
    @Path("verificarDatos")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response verificarDatos(Usuario usuario) throws Exception {
       
        respuestaEditarPerfil respuesta = this.editarPerfil.verificarDatos(usuario);

        return Response.ok(respuesta).build();
    }

}
