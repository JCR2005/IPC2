
package respuetas;

import JPA.Usuario;

/**
 *
 * @author carlosrodriguez
 */
public class respuestaEditarPerfil {
    
    private boolean datosActualizados;
    private boolean contraceñaValida;
    private boolean descripcionValida;
    private boolean nombreValido;
     private Usuario usuario = new Usuario();
    private String mensaje;

    public respuestaEditarPerfil() {
        this.datosActualizados=false;
        this.contraceñaValida=true;
        this.descripcionValida=true;
        this.nombreValido=true;
        
    }

    public boolean isDatosActualizados() {
        return datosActualizados;
    }

    public void setDatosActualizados(boolean datosActualizados) {
        this.datosActualizados = datosActualizados;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
       public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean isContraceñaValida() {
        return contraceñaValida;
    }

    public void setContraceñaValida(boolean contraceñaValida) {
        this.contraceñaValida = contraceñaValida;
    }

    public boolean isDescripcionValida() {
        return descripcionValida;
    }

    public void setDescripcionValida(boolean descripcionValida) {
        this.descripcionValida = descripcionValida;
    }

    public boolean isNombreValido() {
        return nombreValido;
    }

    public void setNombreValido(boolean nombreValido) {
        this.nombreValido = nombreValido;
    }
    
    
}
