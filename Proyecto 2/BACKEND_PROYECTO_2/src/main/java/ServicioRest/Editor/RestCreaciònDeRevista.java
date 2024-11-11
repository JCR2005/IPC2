package ServicioRest.Editor;

import Backend.revistas.CrearRevista;
import JPA.Revista;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import respuetas.Editor.RespuestaCreaciòn;

/**
 * Clase de servicio REST para la creación de una revista.
 * Expone un endpoint para crear una nueva revista mediante una solicitud POST.
 * La respuesta incluye el resultado del proceso de creación.
 * 
 * @author carlosrodriguez
 */
@Path("RestCreaciònDeRevista")
public class RestCreaciònDeRevista {

    // Instancia de la clase RespuestaCreaciòn que almacena el resultado del proceso
    private RespuestaCreaciòn respuesta = new RespuestaCreaciòn();
    
    // Instancia del objeto CrearRevista para ejecutar la lógica de creación
    private CrearRevista crearevista = new CrearRevista();

    /**
     * Endpoint para crear una revista.
     * Este método recibe un objeto de tipo Revista en formato JSON, 
     * lo procesa para realizar la creación en el backend y devuelve una respuesta en formato JSON.
     * 
     * @param revista Objeto Revista recibido en la solicitud POST
     * @return Response con el resultado del proceso de creación de la revista
     * @throws Exception Si ocurre algún error durante el proceso de creación
     */
    @POST
    @Path("creacion")
    @Consumes(MediaType.APPLICATION_JSON) // Especifica que recibe un cuerpo en formato JSON
    @Produces(MediaType.APPLICATION_JSON)  // Especifica que la respuesta se devuelve en formato JSON
    public Response crearRevista(Revista revista) throws Exception {
        // Se llama al método proceso de CrearRevista para realizar la creación
        respuesta = crearevista.proceso(revista);
        
        // Se devuelve la respuesta en formato JSON con un estado HTTP 200 (OK)
        return Response.ok(respuesta).build();
    }
}
