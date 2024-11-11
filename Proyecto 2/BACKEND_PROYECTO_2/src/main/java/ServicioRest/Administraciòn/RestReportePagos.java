
package ServicioRest.Administraciòn;

import Backend.administracion.ConfiguracionReportePagosCompraAnuncio;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.IOException;
import org.glassfish.jersey.media.multipart.FormDataParam;
import respuetas.Administrador.RespuestaReportePagosComppraAnuncio;


/**
 *
 * @author carlosrodriguez
 */
@Path("ReportePagosAdministracios")
public class RestReportePagos {

    ConfiguracionReportePagosCompraAnuncio configuracionReportePagos = new ConfiguracionReportePagosCompraAnuncio();
    RespuestaReportePagosComppraAnuncio respuesta = new RespuestaReportePagosComppraAnuncio();

    @POST
    @Path("pagosCompraAnuncios")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerComentariosGeneral( @FormDataParam("fechaInicio") String fechaInicio, @FormDataParam("fechaFin") String fechaFin, @FormDataParam("tipoAnuncio") String tipoAnuncio) throws IOException {
        // Lógica del método
        this.respuesta = configuracionReportePagos.obtenerPagos( fechaInicio, fechaFin, tipoAnuncio);
        return Response.ok(this.respuesta).build();
    }

  

}
