/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package respuetas;

/**
 *
 * @author carlosrodriguez
 */
public class respuestaBloqueoDeAdds {

    private String mensaje;
    private boolean saldoValido;
    private boolean procesoExitoso;
    private boolean fechaValida;
    private boolean costoOcultacion;
    private boolean existeRevista;

    public respuestaBloqueoDeAdds() {
        this.saldoValido=true;
        this.procesoExitoso=false;
        this.fechaValida = true;
        this.costoOcultacion = true;
        this.existeRevista = true;

    }

    public void setExisteRevista(boolean existeRevista) {
        this.existeRevista = existeRevista;
    }

    public boolean isProcesoExitoso() {
        return procesoExitoso;
    }

    public void setProcesoExitoso(boolean procesoExitoso) {
        this.procesoExitoso = procesoExitoso;
    }

    public boolean isSaldoValido() {
        return saldoValido;
    }

    public void setSaldoValido(boolean saldoValido) {
        this.saldoValido = saldoValido;
    }
    private boolean vigenciaValida;

    public boolean isVigenciaValida() {
        return vigenciaValida;
    }

    public void setVigenciaValida(boolean vigenciaValida) {
        this.vigenciaValida = vigenciaValida;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public boolean isFechaValida() {
        return fechaValida;
    }

    public void setFechaValida(boolean fechaValida) {
        this.fechaValida = fechaValida;
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

}
