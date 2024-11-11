
package ServicioRest.Administraciòn;

import Backend.administracion.ConfiguracionReporteRevistasMasComentadas;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.IOException;
import org.glassfish.jersey.media.multipart.FormDataParam;
import respuetas.Administrador.RespuestaReporteRevistasMasComentadas;

/**
 *
 * @author carlosrodriguez
 */

@Path("RestReporteRevistasMasComentadas")
public class RestReporteRevistasMasComentadas {
    RespuestaReporteRevistasMasComentadas respuesta =new RespuestaReporteRevistasMasComentadas();
    ConfiguracionReporteRevistasMasComentadas configuracionReporteRevistasMasComentadas=new ConfiguracionReporteRevistasMasComentadas();
    
  
    
    
    @POST
    @Path("revistasTop5")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerComentariosGeneral(@FormDataParam("fechaInicio") String fechaInicio,@FormDataParam("fechaFin") String fechaFin) throws IOException {
        // Lógica del método
        this.respuesta = configuracionReporteRevistasMasComentadas.obtenerIngresos( fechaInicio, fechaFin);
        return Response.ok(this.respuesta).build();
    }
    
}
