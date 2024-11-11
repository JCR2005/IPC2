
package respuetas.Suscriptor;

/**
 *
 * @author carlosrodriguez
 */
public class RespuestaLikes {
    
    private String mensaje;
    private boolean  procesoExitoso;
    private int cantidadLikes;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public boolean isProcesoExitoso() {
        return procesoExitoso;
    }

    public void setProcesoExitoso(boolean procesoExitoso) {
        this.procesoExitoso = procesoExitoso;
    }

    public int getCantidadLikes() {
        return cantidadLikes;
    }

    public void setCantidadLikes(int cantidadLikes) {
        this.cantidadLikes = cantidadLikes;
    }
    
    
}
