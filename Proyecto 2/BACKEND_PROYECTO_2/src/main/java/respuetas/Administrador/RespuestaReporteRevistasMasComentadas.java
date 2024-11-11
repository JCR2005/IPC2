package respuetas.Administrador;



import JPA.Comentario;
import JPA.Revista;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author carlosrodriguez
 */
public class RespuestaReporteRevistasMasComentadas {

    private String mensaje;
    List<Revista> revistas = new ArrayList<>();
    private String pdf;

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }
    
    
  
        List<List<Comentario>> comentarios = new ArrayList<>();

    private boolean procesoExitoso;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<Revista> getRevistas() {
        return revistas;
    }

    public void setRevistas(List<Revista> revistas) {
        this.revistas = revistas;
    }

  

    public boolean isProcesoExitoso() {
        return procesoExitoso;
    }

    public void setProcesoExitoso(boolean procesoExitoso) {
        this.procesoExitoso = procesoExitoso;
    }

    public List<List<Comentario>> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<List<Comentario>> comentarios) {
        this.comentarios = comentarios;
    }



  
    
    

}
