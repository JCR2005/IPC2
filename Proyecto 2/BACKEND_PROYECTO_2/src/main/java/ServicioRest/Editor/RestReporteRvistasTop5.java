package ServicioRest.Editor;

import Backend.revistas.ConfiguracionReporteComentarios;
import Backend.revistas.ConfiguracionReporteRevistasTop5;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.IOException;
import org.glassfish.jersey.media.multipart.FormDataParam;
import respuetas.Editor.RespuestaReporteRevistasTop5;
import respuetas.Editor.respuestaReporteComentarios;

/**
 * Servicio REST para obtener reportes de comentarios.
 * 
 * @author carlosrodriguez
 */
@Path("ReporteTop5")
public class RestReporteRvistasTop5 {

    private RespuestaReporteRevistasTop5 respuesta = new RespuestaReporteRevistasTop5();
    private ConfiguracionReporteRevistasTop5 configuracionReporteComentarios = new ConfiguracionReporteRevistasTop5();
    
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
            @FormDataParam("idUsuario") String idUsuario,
            @FormDataParam("fechaInicio") String fechaInicio,
            @FormDataParam("fechaFin") String fechaFin,
            @FormDataParam("idRevista") String idRevista) {
        try {
            this.respuesta = configuracionReporteComentarios.obtenerComentarios(idUsuario, fechaInicio, fechaFin, idRevista);
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
    @POST
    @Path("listaRevistas")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerRevistas(@FormDataParam("idUsuario") String idUsuario) {
        try {
            this.respuesta = configuracionReporteComentarios.obtenerRevistas(idUsuario);
            return Response.ok(this.respuesta).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Error al obtener la lista de revistas: " + e.getMessage()).build();
        }
    }
}
