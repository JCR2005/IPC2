package ServicioRest;

import Backend.Registro;
import JPA.Usuario;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.OPTIONS;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("Registro")
public class RestRegistro {

    private Registro registro = new Registro();

    @GET
    public Response ping() {
        return Response.ok("ping Registro").build();
    }

    @OPTIONS
    public Response options() {
        return Response.ok().build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)  // Asegúrate de que la respuesta sea JSON
    public Response registrarUsuario(Usuario usuario) throws Exception {

        String jsonResponse = null;

       registro.validacion(usuario);
       
            jsonResponse = "{\"message\": \" "+registro.getMensaje()+"\"}";
          
    
         
        

        // Crear una respuesta JSON
        return Response.ok(jsonResponse).build();
    }
}
