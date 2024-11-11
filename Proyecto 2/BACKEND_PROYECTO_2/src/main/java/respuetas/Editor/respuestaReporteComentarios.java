
package respuetas.Editor;

import JPA.Comentario;
import JPA.Revista;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author carlosrodriguez
 */
public class respuestaReporteComentarios {
       
     private boolean procesoExitoso;
       private List<List<Comentario>> comentarios = new ArrayList();
         private List<String> idsRevistas = new ArrayList();
       private String mensaje;
 private List<Revista> revistas = new ArrayList();

    public List<Revista> getRevistas() {
        return revistas;
    }

    public void setRevistas(List<Revista> revistas) {
        this.revistas = revistas;
    }
    
    private String pdf;

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public List<String> getIdsRevistas() {
        return idsRevistas;
    }

    public void setIdsRevistas(List<String> idsRevistas) {
        this.idsRevistas = idsRevistas;
    }
    
    
    

       
       
       public respuestaReporteComentarios(){
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

    public List<List<Comentario>> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<List<Comentario>> comentarios) {
        this.comentarios = comentarios;
    }


       
    
}
