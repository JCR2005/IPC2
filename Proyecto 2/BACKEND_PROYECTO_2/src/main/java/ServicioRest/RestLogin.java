package ServicioRest;

import Backend.Login;
import JPA.Usuario;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.OPTIONS;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("Login")
public class RestLogin {

    private Login login = new Login();

    @GET
    public Response ping() {
        return Response.ok("ping Login").build();
    }

    @OPTIONS
    public Response options() {
        return Response.ok().build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrarUsuario(Usuario usuario) throws Exception {
        String jsonResponse;
        
        jsonResponse = login.validarInicioSesion(usuario);

        return Response.ok(jsonResponse).build();
    }
}
