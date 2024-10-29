package respuetas;

import JPA.Usuario;

/**
 *
 * @author carlosrodriguez
 */
public class respuestaPerfil {

    private String mensaje;
    private boolean usuarioEncontrado;
    private Usuario usuario = new Usuario();

    public respuestaPerfil() {

        this.mensaje="";
        this.usuarioEncontrado=false;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public boolean isUsuarioEncontrado() {
        return usuarioEncontrado;
    }

    public void setUsuarioEncontrado(boolean usuarioEncontrado) {
        this.usuarioEncontrado = usuarioEncontrado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
