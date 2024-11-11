
package ServicioRest.Editor;

import Backend.revistas.ConfiguracionReporteSuscripciones;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.IOException;
import org.glassfish.jersey.media.multipart.FormDataParam;
import respuetas.Editor.RespuestaReporteSuscriptores;

/**
 *
 * @author carlosrodriguez
 */

@Path("ReporteSuscripciones")
public class RestReporteSuscriptores {
    
    RespuestaReporteSuscriptores respuesta =new RespuestaReporteSuscriptores();
    ConfiguracionReporteSuscripciones ConfiguracionReporteSuscripciones=new ConfiguracionReporteSuscripciones();
    
     @POST
    @Path("suscripcionesGenerales")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerComentariosGeneral(@FormDataParam("idUsuario") String idUsuario, @FormDataParam("fechaInicio") String fechaInicio, @FormDataParam("fechaFin") String fechaFin, @FormDataParam("idRevista") String idRevista) throws IOException {
        // Lógica del método
        this.respuesta = ConfiguracionReporteSuscripciones.obtenerSuscripcioness(idUsuario, fechaInicio, fechaFin, idRevista);
        return Response.ok(this.respuesta).build();
    }
    
       @POST
    @Path("listaRevistas")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerRevistas(@FormDataParam("idUsuario") String idUsuario) throws IOException {
        // Lógica del método
        this.respuesta = ConfiguracionReporteSuscripciones.obtenerRevistas(idUsuario);
        return Response.ok(this.respuesta).build();
    }
    
}
