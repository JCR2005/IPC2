
package ServicioRest.Administraciòn;

import Backend.administracion.ConfiguracionReporteGananciaPorAnunciante;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.IOException;
import org.glassfish.jersey.media.multipart.FormDataParam;

import respuetas.Administrador.RespuestaReporteGananciasPorAnunciante;

/**
 *
 * @author carlosrodriguez
 */

@Path("ReporteGananciasPorAnunciantess")
public class RestReporteGananciasPorAnunciantes {
    RespuestaReporteGananciasPorAnunciante respuesta =new RespuestaReporteGananciasPorAnunciante();
    ConfiguracionReporteGananciaPorAnunciante configuracionReporteGananciaPorAnunciante=new ConfiguracionReporteGananciaPorAnunciante();
       @GET
    @Path("listaUsuarios")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerRevistas() throws IOException {
        // Lógica del método
        this.respuesta = configuracionReporteGananciaPorAnunciante.obtenerRevistas();
        return Response.ok(this.respuesta).build();
    }
    
    
    @POST
    @Path("ingresosAnunciantes")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerComentariosGeneral(@FormDataParam("idUsuario") String idUsuario, @FormDataParam("fechaInicio") String fechaInicio,@FormDataParam("fechaFin") String fechaFin) throws IOException {
        // Lógica del método
        this.respuesta = configuracionReporteGananciaPorAnunciante.obtenerIngresos(idUsuario, fechaInicio, fechaFin);
        return Response.ok(this.respuesta).build();
    }
    
}
