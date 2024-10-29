package respuetas;

import JPA.CostosGlobales;

/**
 *
 * @author carlosrodriguez
 */
public class RespuestaRevistaAdministracion {

    private String mensaje;
    private boolean cambiosHechos;
    private boolean ActualizacionCostoAsociadoExitoso;
    private boolean costoOcultacion;
    private boolean existeRevista;
    private  boolean procesoExitoso;

    private CostosGlobales costosGlobales;
  
    
    public RespuestaRevistaAdministracion() {
        this.procesoExitoso=true;
        this.cambiosHechos=true;
        this.ActualizacionCostoAsociadoExitoso = false;
        this.costoOcultacion = true;
        this.existeRevista = true;

    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public boolean isActualizacionCostoAsociadoExitoso() {
        return ActualizacionCostoAsociadoExitoso;
    }

    public void setActualizacionCostoAsociadoExitoso(boolean ActualizacionCostoAsociadoExitoso) {
        this.ActualizacionCostoAsociadoExitoso = ActualizacionCostoAsociadoExitoso;
    }

    public boolean isCostoOcultacion() {
        return costoOcultacion;
    }

    public void setCostoOcultacion(boolean costoOcultacion) {
        this.costoOcultacion = costoOcultacion;
    }

    public boolean isExisteRevista() {
        return existeRevista;
    }

    public void setExisteRevista(boolean existeRevista) {
        this.existeRevista = existeRevista;
    }

    public boolean isCambiosHechos() {
        return cambiosHechos;
    }

    public void setCambiosHechos(boolean cambiosHechos) {
        this.cambiosHechos = cambiosHechos;
    }

    public CostosGlobales getCostosGlobales() {
        return costosGlobales;
    }

    public void setCostosGlobales(CostosGlobales costosGlobales) {
        this.costosGlobales = costosGlobales;
    }

    public boolean isProcesoExitoso() {
        return procesoExitoso;
    }

    public void setProcesoExitoso(boolean procesoExitoso) {
        this.procesoExitoso = procesoExitoso;
    }
    
    

}
