package respuetas.Administrador;

import respuetas.Editor.*;
import JPA.Ingreso;
import JPA.Revista;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa la respuesta de un reporte de pagos,
 * conteniendo el estado del proceso, los ingresos y revistas relacionadas, 
 * así como un mensaje informativo.
 * 
 * @autor carlosrodriguez
 */
public class RespuestaReportePagosComppraAnuncio {

    private boolean procesoExitoso;
    private List<String> tiposAnuncio = new ArrayList<>();
    private List<Ingreso> ingresos = new ArrayList<>();
    private List<Revista> revistas = new ArrayList<>();
    private double totalPago;
    private String mensaje;
    private String pdf;

    /**
     * Constructor que inicializa el estado del proceso como exitoso.
     */
    public RespuestaReportePagosComppraAnuncio() {
        this.procesoExitoso = true;
        this.totalPago=0;
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
     * Obtiene la lista de ingresos asociados al reporte.
     * 
     * @return Lista de {@link Ingreso}
     */
    public List<Ingreso> getIngresos() {
        return ingresos;
    }

    /**
     * Establece la lista de ingresos asociados al reporte.
     * 
     * @param ingresos Lista de {@link Ingreso}
     */
    public void setIngresos(List<Ingreso> ingresos) {
        this.ingresos = ingresos;
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

    public double getTotalPago() {
        return totalPago;
    }

    public void setTotalPago(double totalPago) {
        this.totalPago = totalPago;
    }

    public List<String> getTiposAnuncio() {
        return tiposAnuncio;
    }

    public void setTiposAnuncio(List<String> tiposAnuncio) {
        this.tiposAnuncio = tiposAnuncio;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }
    
    
    
}
