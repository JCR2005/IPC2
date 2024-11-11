package respuetas.Editor;

/**
 * Clase que representa la respuesta de un proceso de creación.
 * Esta clase se utiliza para encapsular el resultado de un proceso de creación (como la creación de una revista)
 * y devolver una respuesta con el estado de la operación y un mensaje asociado.
 * 
 * @author carlosrodriguez
 */
public class RespuestaCreaciòn {

    // Indica si el proceso fue exitoso
    private boolean procesoExitoso;

    // Mensaje asociado al resultado del proceso
    private String mensaje;

    /**
     * Constructor de la clase. Inicializa la respuesta con un estado no exitoso.
     */
    public RespuestaCreaciòn() {
        this.procesoExitoso = false; // Inicializa el estado como no exitoso por defecto
    }

    /**
     * Obtiene el estado del proceso, indicando si fue exitoso o no.
     * 
     * @return true si el proceso fue exitoso, false si no lo fue
     */
    public boolean isProcesoExitoso() {
        return procesoExitoso;
    }

    /**
     * Establece el estado del proceso.
     * 
     * @param procesoExitoso true si el proceso fue exitoso, false si no lo fue
     */
    public void setProcesoExitoso(boolean procesoExitoso) {
        this.procesoExitoso = procesoExitoso;
    }

    /**
     * Obtiene el mensaje asociado a la respuesta del proceso.
     * 
     * @return el mensaje del proceso
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * Establece el mensaje de la respuesta.
     * 
     * @param mensaje el mensaje asociado al proceso
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
