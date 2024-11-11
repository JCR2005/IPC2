package respuetas.Editor;

/**
 * Clase que representa una respuesta para un artículo procesado.
 * 
 * Esta clase se utiliza para manejar el resultado de un proceso relacionado con artículos,
 * proporcionando información sobre si el proceso fue exitoso o no, junto con un mensaje 
 * adicional que puede ser útil para indicar el estado o detalles del proceso.
 * 
 * @author carlosrodriguez
 */
public class RespuestaArticulo {

    /** Indica si el proceso relacionado con el artículo fue exitoso o no. */
    private boolean procesoExitoso;

    /** Mensaje adicional que puede acompañar el resultado del proceso. */
    private String mensaje;

    /**
     * Constructor por defecto que inicializa el estado del proceso como exitoso.
     */
    public RespuestaArticulo() {
        this.procesoExitoso = true;
    }

    /**
     * Obtiene el valor del atributo {@code procesoExitoso}.
     * 
     * @return {@code true} si el proceso fue exitoso, {@code false} si no lo fue.
     */
    public boolean isProcesoExitoso() {
        return procesoExitoso;
    }

    /**
     * Establece el valor del atributo {@code procesoExitoso}.
     * 
     * @param procesoExitoso el nuevo estado del proceso.
     */
    public void setProcesoExitoso(boolean procesoExitoso) {
        this.procesoExitoso = procesoExitoso;
    }

    /**
     * Obtiene el valor del mensaje asociado al resultado del proceso.
     * 
     * @return el mensaje asociado al proceso.
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * Establece un mensaje asociado al resultado del proceso.
     * 
     * @param mensaje el mensaje que se desea asociar al proceso.
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
