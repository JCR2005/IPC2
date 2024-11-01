package respuetas.Editor;

/**
 *
 * @author carlosrodriguez
 */
public class RespuestaArticulo {

    private boolean procesoExitoso;
    private String mensaje;

    public RespuestaArticulo() {
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

    
}
