package ServicioRest.Editor;

import Backend.revistas.ConfiguracionReportePagos;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.IOException;
import org.glassfish.jersey.media.multipart.FormDataParam;
import respuetas.Editor.RespuestaReportePagos;

/**
 *
 * @author carlosrodriguez
 */
@Path("ReportePagos")
public class RestReportePagos {

    ConfiguracionReportePagos configuracionReportePagos = new ConfiguracionReportePagos();
    RespuestaReportePagos respuesta = new RespuestaReportePagos();

    @POST
    @Path("pagosGenerales")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerComentariosGeneral(@FormDataParam("idUsuario") String idUsuario, @FormDataParam("fechaInicio") String fechaInicio, @FormDataParam("fechaFin") String fechaFin, @FormDataParam("idRevista") String idRevista) throws IOException {
        // Lógica del método
        this.respuesta = configuracionReportePagos.obtenerPagos(idUsuario, fechaInicio, fechaFin, idRevista);
        return Response.ok(this.respuesta).build();
    }

    @POST
    @Path("listaRevistas")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerRevistas(@FormDataParam("idUsuario") String idUsuario) throws IOException {
        // Lógica del método
        this.respuesta = configuracionReportePagos.obtenerRevistas(idUsuario);
        return Response.ok(this.respuesta).build();
    }

}
