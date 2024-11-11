package ServicioRest.Administraciòn;

import Backend.administracion.ConfiguracionReporteRevistasTop5Suscripciones;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.IOException;
import org.glassfish.jersey.media.multipart.FormDataParam;
import respuetas.Administrador.RespuestaReporteRevistasTop5Suscripciones;

/**
 * Servicio REST para obtener reportes de comentarios.
 * 
 * @author carlosrodriguez
 */
@Path("RestReporteRvistasTop5Suscripciones")
public class RestReporteRvistasTop5Suscripciones {

    private RespuestaReporteRevistasTop5Suscripciones respuesta = new RespuestaReporteRevistasTop5Suscripciones();
    private ConfiguracionReporteRevistasTop5Suscripciones configuracionReporteComentarios = new ConfiguracionReporteRevistasTop5Suscripciones();
    
    /**
     * Obtiene comentarios generales basados en los parámetros proporcionados.
     *
     * @param idUsuario ID del usuario que solicita los comentarios
     * @param fechaInicio Fecha de inicio para filtrar comentarios
     * @param fechaFin Fecha de fin para filtrar comentarios
     * @param idRevista ID de la revista de interés
     * @return Response con los comentarios solicitados en formato JSON
     * @throws IOException si ocurre un error al procesar la solicitud
     */
    @POST
    @Path("Reporte")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerComentariosGeneral(
            @FormDataParam("fechaInicio") String fechaInicio,
            @FormDataParam("fechaFin") String fechaFin){
   
        try {
            this.respuesta = configuracionReporteComentarios.obtenerComentarios(fechaInicio, fechaFin);
            return Response.ok(this.respuesta).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Error al obtener los comentarios: " + e.getMessage()).build();
        }
        
                
    }
    
    /**
     * Obtiene la lista de revistas asociadas al usuario.
     *
     * @param idUsuario ID del usuario que solicita la lista de revistas
     * @return Response con la lista de revistas en formato JSON
     * @throws IOException si ocurre un error al procesar la solicitud
     */
    @GET
    @Path("listaRevistas")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerRevistas() {
        try {
            this.respuesta = configuracionReporteComentarios.obtenerRevistas();
            return Response.ok(this.respuesta).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Error al obtener la lista de revistas: " + e.getMessage()).build();
        }
    }
}
