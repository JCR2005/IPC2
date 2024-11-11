
package respuetas.Editor;

import JPA.Comentario;
import JPA.MeGusta;
import JPA.Revista;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author carlosrodriguez
 */
public class RespuestaReporteRevistasTop5 {
    
    List<List<MeGusta>> meGustas=new ArrayList<>();
        List<String> idsRevistas=new ArrayList<>();
        
     private boolean procesoExitoso;
    
         private List<Revista> revistas = new ArrayList();
       private String mensaje;

    public List<String> getIdsRevistas() {
        return idsRevistas;
    }

    public void setIdsRevistas(List<String> idsRevistas) {
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
       
       
       
       public RespuestaReporteRevistasTop5(){
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
    public List<List<MeGusta>> getMeGustas() {
        return meGustas;
    }

    public void setMeGustas(List<List<MeGusta>> meGustas) {
        this.meGustas = meGustas;
    }
    
}
