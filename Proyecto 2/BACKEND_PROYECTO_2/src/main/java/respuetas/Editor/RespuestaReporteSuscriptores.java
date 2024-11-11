package respuetas.Editor;

import JPA.Revista;
import JPA.Suscripciòn;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa la respuesta del reporte de suscriptores, conteniendo el
 * estado del proceso, las suscripciones y revistas relacionadas, y un mensaje informativo.
 * 
 * @autor carlosrodriguez
 */
public class RespuestaReporteSuscriptores {

       private String pdf;
    private boolean procesoExitoso;
    private List<Suscripciòn> suscripciones = new ArrayList<>();
    private List<Revista> revistas = new ArrayList<>();
    private String mensaje;

    /**
     * Constructor que inicializa el estado del proceso como exitoso.
     */
    public RespuestaReporteSuscriptores() {
        this.procesoExitoso = true;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }
    
    

    /**
     * Obtiene la lista de revistas asociadas al reporte.
     * 
     * @return Lista de {@link Revista}
     */
    public List<Revista> getRevistas() {
        return revistas;
    }

    /**
     * Establece la lista de revistas asociadas al reporte.
     * 
     * @param revistas Lista de {@link Revista}
     */
    public void setRevistas(List<Revista> revistas) {
        this.revistas = revistas;
    }

    /**
     * Verifica si el proceso fue exitoso.
     * 
     * @return {@code true} si el proceso fue exitoso, {@code false} en caso contrario.
     */
    public boolean isProcesoExitoso() {
        return procesoExitoso;
    }

    /**
     * Establece el estado del proceso.
     * 
     * @param procesoExitoso Estado del proceso
     */
    public void setProcesoExitoso(boolean procesoExitoso) {
        this.procesoExitoso = procesoExitoso;
    }

    /**
     * Obtiene el mensaje asociado al reporte.
     * 
     * @return Mensaje informativo
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * Establece el mensaje asociado al reporte.
     * 
     * @param mensaje Mensaje informativo
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * Obtiene la lista de suscripciones asociadas al reporte.
     * 
     * @return Lista de {@link Suscripcion}
     */
    public List<Suscripciòn> getSuscripciones() {
        return suscripciones;
    }

    /**
     * Establece la lista de suscripciones asociadas al reporte.
     * 
     * @param suscripciones Lista de {@link Suscripcion}
     */
    public void setSuscripciones(List<Suscripciòn> suscripciones) {
        this.suscripciones = suscripciones;
    }
}
