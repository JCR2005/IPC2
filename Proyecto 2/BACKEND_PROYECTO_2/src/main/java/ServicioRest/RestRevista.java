package ServicioRest;


import Backend.administracion.aprobarRevista;
import Backend.administracion.costoOcultacionAdd;
import Backend.revistas.bloquearAnuncios;
import Backend.revistas.configuracionRevistas;
import Backend.revistas.crearRevista;
import JPA.Anuncio;
import JPA.Revista;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.OPTIONS;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Map;
import org.glassfish.jersey.media.multipart.FormDataParam;
import respuetas.respuestaAprobacionRevista;
import respuetas.respuestaBloqueoDeAdds;

/**
 *
 * @author carlosrodriguez
 */
@Path("revista")
public class RestRevista {

    aprobarRevista aprobarRevista=new aprobarRevista();
    costoOcultacionAdd costoOcultacionAdd=new costoOcultacionAdd();
    configuracionRevistas configuracionRevistas=new configuracionRevistas();
    bloquearAnuncios bloquearAnuncios=new bloquearAnuncios();
    
    @GET
    @Path("aprobacion")
    public   List<Revista> aprobacionRevista() {
        
        // Llamamos al método 'proceso' para obtener el Map con los anuncios filtrados
        Map<String, List<?>> responseMap = aprobarRevista.procesoLista();

        List<Revista> Revistas = (List<Revista>) responseMap.get("listaAnuncios");
        return Revistas;
    }
    
    @GET
    @Path("listasAprobadas")
    public   List<Revista> actualizacionCostoOcultacionAdds() {
        
        // Llamamos al método 'proceso' para obtener el Map con los anuncios filtrados
        Map<String, List<?>> responseMap = costoOcultacionAdd.procesoLista();

        List<Revista> Revistas = (List<Revista>) responseMap.get("listaAnuncios");
        return Revistas;
    }

    @OPTIONS
    public Response options() {
        return Response.ok().build();
    }

    @POST
    @Path("creacion")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)  
    public Response cambiarEstadoAnuncio(Revista revista) throws Exception {
        crearRevista crearevista = new crearRevista();

        String jsonResponse = crearevista.proceso(revista);
        return Response.ok(jsonResponse).build();
    }
    
    
    @POST
    @Path("aprobacion")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)  
    public Response aprobacionRevista(@FormDataParam("idRevista") String idRevista,@FormDataParam("costoAsociado") String costoAsociado,@FormDataParam("costoOcultacion") String costoOcultacion) throws Exception {
       

        respuestaAprobacionRevista response = aprobarRevista.procesoCambioDeEstado(idRevista, costoAsociado, costoOcultacion);
        return Response.ok(response).build();
    }
    
    
      @POST
    @Path("actualizacionCostoOcultacion")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)  
    public Response actualizacionCostoOcultacion(@FormDataParam("idRevista") String idRevista,@FormDataParam("costoOcultacion") String costoOcultacion) throws Exception {
       

        respuestaAprobacionRevista response = costoOcultacionAdd.procesoCambioDeEstado(idRevista, costoOcultacion);
        return Response.ok(response).build();
    }
    
       @POST
    @Path("listaRevistasUsuario")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public List<Revista> listaRevistas(@FormDataParam("usuario") String usuarioJson) throws Exception {
        // Procesar el JSON y convertirlo en un objeto Java
     
        // Llamamos al método 'proceso' para obtener el Map con los anuncios filtrados
        Map<String, List<?>> responseMap = configuracionRevistas.procesoLista(usuarioJson);

        List<Revista> Revista = (List<Revista>) responseMap.get("listaAnuncios");
        return Revista; // Retornar la lista de anuncios
    }
    
    @POST
    @Path("cambioEstadoLikes")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response cambiarEstadoLikes(@FormDataParam("idRevista") String idRevista,@FormDataParam("likes") String likes) throws Exception {
        
        String jsonResponse =configuracionRevistas.procesoCambioDeEstadoLikes(idRevista, likes);
        return Response.ok(jsonResponse).build();
    }
    
    
    @POST
    @Path("cambioEstadoComentarios")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response cambiarEstadoComentarios(@FormDataParam("idRevista") String idRevista,@FormDataParam("comentarios") String comentarios) throws Exception {
        
        String jsonResponse =configuracionRevistas.procesoCambioDeEstadoComentarios(idRevista, comentarios);
        return Response.ok(jsonResponse).build();
    }
    
    
    @POST
    @Path("cambioEstadoSuscripciones")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response cambiarEstadoSuscripciones(@FormDataParam("idRevista") String idRevista, @FormDataParam("suscripciones") String suscripciones) throws Exception {

        String jsonResponse = configuracionRevistas.procesoCambioDeEstadoSuscripciones(idRevista, suscripciones);
        return Response.ok(jsonResponse).build();
    }
    
    
    
    
      @POST
    @Path("listaRevistasAdda")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public List<Revista> listaRevistaAdds(@FormDataParam("usuario") String usuarioJson) throws Exception {
        // Procesar el JSON y convertirlo en un objeto Java
     
        // Llamamos al método 'proceso' para obtener el Map con los anuncios filtrados
        Map<String, List<?>> responseMap = bloquearAnuncios.procesoLista(usuarioJson);

        List<Revista> Revista = (List<Revista>) responseMap.get("listaAnuncios");
        return Revista; // Retornar la lista de anuncios
    }
    
    
    
    
     @POST
    @Path("bloquearAnuncios")
   @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)  
    public Response bloquearAnuncios(@FormDataParam("idRevista") String idRevista,@FormDataParam("fecha") String fecha,@FormDataParam("vigencia") String vigencia) throws Exception {
      
         respuestaBloqueoDeAdds response =bloquearAnuncios.procesoBloqueoAdds(idRevista, fecha, vigencia);
         System.out.println(response.getMensaje()+"_______________________");
        return Response.ok(response).build();
    }
}
