package ServicioRest.Administraciòn;

import Backend.administracion.ActualizarCostoAsociado;
import Backend.administracion.CostoGlobal;
import JPA.CostosGlobales;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataParam;
import respuetas.RespuestaRevistaAdministracion;

/**
 *
 * @author carlosrodriguez
 */
@Path("revistas")
public class RestRevistas {

    private RespuestaRevistaAdministracion respuesta = new RespuestaRevistaAdministracion();

    @GET
    @Path("costoAsociadoGlobal")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizacionCostoAsociadoGlobal() throws Exception {
        this.respuesta = null;
        CostoGlobal costosGlobales = new CostoGlobal();

        respuesta = costosGlobales.obtenerCostoGlobalAsociado();
        return Response.ok(respuesta).build();
    }

    @POST
    @Path("actualizacionCostoAsociado")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizacionCostoAsociado(@FormDataParam("idRevista") String idRevista, @FormDataParam("costoAsociado") String costoAsociado) throws Exception {
        this.respuesta = null;
        ActualizarCostoAsociado actualizarCostoAsociado = new ActualizarCostoAsociado();
        respuesta = actualizarCostoAsociado.actualizacionCostoAsociado(idRevista, costoAsociado);
        return Response.ok(respuesta).build();
    }

    @POST
    @Path("actualizacionCostoAsociadoGlobal")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizacionCostoAsociadoGlobal(@FormDataParam("costoAsociado") String costoAsociado) throws Exception {
        this.respuesta = null;
        CostoGlobal costosGlobales = new CostoGlobal();

        respuesta = costosGlobales.actualizarCostoAsociadoGlobal(costoAsociado);
        return Response.ok(respuesta).build();
    }

}
