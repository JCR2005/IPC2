
package respuetas;

/**
 *
 * @author carlosrodriguez
 */
public class RespuestaVerificacionIdentidad {
    
    private boolean usuarioVaerificado;
    private String mensaje;
    private String token;
    
    public RespuestaVerificacionIdentidad(){
        this.usuarioVaerificado=false;
    }
    
      public boolean isUsuarioVaerificado() {
        return usuarioVaerificado;
    }

    public void setUsuarioVaerificado(boolean usuarioVaerificado) {
        this.usuarioVaerificado = usuarioVaerificado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
