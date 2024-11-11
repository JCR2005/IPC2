
package respuetas.Suscriptor;

import JPA.Comentario;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author carlosrodriguez
 */
public class RespuestaComentario {
    
     private boolean procesoExitoso;
       private List<Comentario> comentarios = new ArrayList();
       private String mensaje;
       
       public RespuestaComentario(){
           this.procesoExitoso=true;
       }

    public boolean isProcesoExitoso() {
        return procesoExitoso;
    }

    public void setProcesoExitoso(boolean procesoExitoso) {
        this.procesoExitoso = procesoExitoso;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }
       
       
    
}
