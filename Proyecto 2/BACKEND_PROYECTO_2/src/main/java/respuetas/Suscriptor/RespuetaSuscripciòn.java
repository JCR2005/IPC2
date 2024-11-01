
package respuetas.Suscriptor;

import JPA.Revista;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author carlosrodriguez
 */

public class RespuetaSuscripciòn {
    
    private boolean procesoExitoso;
  private List<Revista> revistas=new ArrayList();
    private String mensaje;

    public RespuetaSuscripciòn() {
        this.procesoExitoso = true;
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

    public List<Revista> getRevistas() {
        return revistas;
    }

    public void setRevistas(List<Revista> revistas) {
        this.revistas = revistas;
    }

  
    
    
    
    
}
