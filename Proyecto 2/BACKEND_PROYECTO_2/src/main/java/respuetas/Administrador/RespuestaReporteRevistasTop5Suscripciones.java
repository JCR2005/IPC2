
package respuetas.Administrador;

import JPA.Comentario;
import JPA.MeGusta;
import JPA.Revista;
import JPA.Suscripciòn;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author carlosrodriguez
 */
public class RespuestaReporteRevistasTop5Suscripciones {
    
    List<List<Suscripciòn>> meGustas=new ArrayList<>();

    public List<List<Suscripciòn>> getMeGustas() {
        return meGustas;
    }

    public void setMeGustas(List<List<Suscripciòn>> meGustas) {
        this.meGustas = meGustas;
    }
        List<Revista> idsRevistas=new ArrayList<>();
        
     private boolean procesoExitoso;
    
         private List<Revista> revistas = new ArrayList();
       private String mensaje;

    public List<Revista> getIdsRevistas() {
        return idsRevistas;
    }

    public void setIdsRevistas(List<Revista> idsRevistas) {
        this.idsRevistas = idsRevistas;
    }

    
    private String pdf;

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }
    
    
    
    public List<Revista> getRevistas() {
        return revistas;
    }

    public void setRevistas(List<Revista> revistas) {
        this.revistas = revistas;
    }
       
       
       
       public RespuestaReporteRevistasTop5Suscripciones(){
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

    public RespuestaReporteRevistasTop5Suscripciones(boolean procesoExitoso, String mensaje, String pdf) {
        this.procesoExitoso = procesoExitoso;
        this.mensaje = mensaje;
        this.pdf = pdf;
    }

    
}
