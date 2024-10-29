
package respuetas;

/**
 *
 * @author carlosrodriguez
 */
public class RespuetaEstadoAnuncio {
    
    private boolean cambioExitoso;
    private String mensaje;

    public RespuetaEstadoAnuncio() {
        this.cambioExitoso=false;
    }

    public boolean isCambioExitoso() {
        return cambioExitoso;
    }

    public void setCambioExitoso(boolean cambioExitoso) {
        this.cambioExitoso = cambioExitoso;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    
    
    
}
