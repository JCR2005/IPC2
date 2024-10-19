package respuetas;

/**
 *
 * @author carlosrodriguez
 */
public class respuestaAprobacionRevista {

    private String mensaje;
    private boolean costoAsociado;
    private boolean costoOcultacion;
    private boolean existeRevista;

    public respuestaAprobacionRevista() {

        this.costoAsociado = true;
        this.costoOcultacion = true;
        this.existeRevista = true;
        
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public boolean isCostoAsociado() {
        return costoAsociado;
    }

    public void setCostoAsociado(boolean costoAsociado) {
        this.costoAsociado = costoAsociado;
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
}
