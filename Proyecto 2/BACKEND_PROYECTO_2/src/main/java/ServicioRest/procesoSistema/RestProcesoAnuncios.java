package ServicioRest.procesoSistema;

import JPA.HistorialEfectividadAnuncios;
import Procesos.ProsecoAnuncios;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.IOException;
import respuetas.RespuestaProcesoAnuncios;

/**
 * Clase que define los servicios REST para obtener anuncios de diferentes tipos
 * (texto, video, imagen). Utiliza el método adecuado del proceso de anuncios
 * para obtener y devolver los anuncios solicitados. Los resultados se devuelven
 * en formato JSON.
 *
 * @author carlosrodriguez
 */
@Path("ProcesoAnuncios")
public class RestProcesoAnuncios {

    // Instancia de la clase RespuestaProcesoAnuncios para almacenar los resultados de los procesos
    private RespuestaProcesoAnuncios respuesta = new RespuestaProcesoAnuncios();

    // Instancia del proceso de anuncios que contiene la lógica para obtener los anuncios
    private ProsecoAnuncios proceso = new ProsecoAnuncios();

    /**
     * Endpoint para obtener los anuncios de tipo texto.
     *
     * Este método obtiene los anuncios de tipo texto desde el proceso de
     * anuncios y los devuelve en formato JSON como respuesta.
     *
     * @return Response con los anuncios de tipo texto en formato JSON
     * @throws IOException Si ocurre un error al procesar los anuncios
     */
    @GET
    @Path("anunciosTexto")
    @Consumes(MediaType.MULTIPART_FORM_DATA)  // Especifica que la solicitud puede incluir datos multipart (aunque no es estrictamente necesario en este caso)
    @Produces(MediaType.APPLICATION_JSON)    // Especifica que la respuesta será en formato JSON
    public Response obtenerAnunciosTexto() throws IOException {
        // Obtiene los anuncios de tipo texto desde el proceso de anuncios
        this.respuesta = proceso.obtenerAnunciosTexto();

        // Devuelve la respuesta con los anuncios de tipo texto en formato JSON
        return Response.ok(this.respuesta).build();
    }

    /**
     * Endpoint para obtener los anuncios de tipo video.
     *
     * Este método obtiene los anuncios de tipo video desde el proceso de
     * anuncios y los devuelve en formato JSON como respuesta.
     *
     * @return Response con los anuncios de tipo video en formato JSON
     * @throws IOException Si ocurre un error al procesar los anuncios
     */
    @GET
    @Path("AnuncioVideo")
    @Consumes(MediaType.MULTIPART_FORM_DATA)  // Especifica que la solicitud puede incluir datos multipart (aunque no es estrictamente necesario en este caso)
    @Produces(MediaType.APPLICATION_JSON)    // Especifica que la respuesta será en formato JSON
    public Response obtenerAnunciosVideo() throws IOException {
        // Obtiene los anuncios de tipo video desde el proceso de anuncios
        this.respuesta = proceso.obtenerAnunciosVideo();

        // Devuelve la respuesta con los anuncios de tipo video en formato JSON
        return Response.ok(this.respuesta).build();
    }

    /**
     * Endpoint para obtener los anuncios de tipo imagen.
     *
     * Este método obtiene los anuncios de tipo imagen desde el proceso de
     * anuncios y los devuelve en formato JSON como respuesta.
     *
     * @return Response con los anuncios de tipo imagen en formato JSON
     * @throws IOException Si ocurre un error al procesar los anuncios
     */
    @GET
    @Path("AnuncioImagen")
    @Consumes(MediaType.MULTIPART_FORM_DATA)  // Especifica que la solicitud puede incluir datos multipart (aunque no es estrictamente necesario en este caso)
    @Produces(MediaType.APPLICATION_JSON)    // Especifica que la respuesta será en formato JSON
    public Response obtenerAnunciosImagen() throws IOException {
        // Obtiene los anuncios de tipo imagen desde el proceso de anuncios
        this.respuesta = proceso.obtenerAnunciosImagenes();

        // Devuelve la respuesta con los anuncios de tipo imagen en formato JSON
        return Response.ok(this.respuesta).build();
    }

    @POST
    @Path("historial")
    @Consumes(MediaType.APPLICATION_JSON)  // Especifica que la solicitud puede incluir datos multipart (aunque no es estrictamente necesario en este caso)
    @Produces(MediaType.APPLICATION_JSON)    // Especifica que la respuesta será en formato JSON
    public Response obtenerAnunciosImagen(HistorialEfectividadAnuncios historialEfectividadAnuncios) throws IOException {
        // Obtiene los anuncios de tipo imagen desde el proceso de anuncios
        this.respuesta = proceso.registrarHistorial(historialEfectividadAnuncios);

        // Devuelve la respuesta con los anuncios de tipo imagen en formato JSON
        return Response.ok(this.respuesta).build();
    }
}
