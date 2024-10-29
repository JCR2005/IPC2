/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.Perfil;

import JPA.Controladora;
import JPA.Usuario;
import respuetas.respuestaEditarPerfil;

/**
 *
 * @author carlosrodriguez
 */
public class EditarPerfil {

    respuestaEditarPerfil respuesta = new respuestaEditarPerfil();
    private final Controladora controladora = new Controladora();

    public respuestaEditarPerfil verificarDatos(Usuario usuario) {

        if (!usuario.getNombre().isEmpty()) {
            validarNombre(usuario);
        }
        if (!usuario.getPassword().isEmpty()) {
            validarContraceña(usuario);
        }

        if (!usuario.getDescripcion().isEmpty()) {
            validarDescripcion(usuario);
        }

        return this.respuesta;
    }

    private void validarContraceña(Usuario usuario) {
        
        if ((usuario.getPassword().length() < 8) || (usuario.getPassword().length() >32) || (usuario.getPassword().contains(" "))) {
            this.respuesta.setContraceñaValida(false);
        }

    }

    private void validarNombre(Usuario usuario) {
        if ((usuario.getNombre().length() < 2) || (usuario.getNombre().length() > 30)) {
            this.respuesta.setNombreValido(false);
        }
    }

    private void validarDescripcion(Usuario usuario) {

        if ((usuario.getDescripcion().length() < 10) || (usuario.getDescripcion().length() > 100)) {
            this.respuesta.setDescripcionValida(false);
        }

    }

    public respuestaEditarPerfil proceso(Usuario usuario) {

        try {
            obtenerUsuario(usuario);
            validarUsuario(respuesta.getUsuario());
            asignarCabios(usuario);
            actualizarDatos();
            return this.respuesta;
        } catch (Exception e) {
            this.respuesta.setMensaje("Ha ocurrido un error al actualizar tus datos");
            return this.respuesta;
        }

    }

    private void asignarCabios(Usuario usuario) {

        if (!usuario.getNombre().isEmpty()) {
            this.respuesta.getUsuario().setNombre(usuario.getNombre());
        }
        if (!usuario.getPassword().isEmpty()) {
            this.respuesta.getUsuario().setPassword(usuario.getPassword());
        }

        if (!usuario.getDescripcion().isEmpty()) {
            this.respuesta.getUsuario().setDescripcion(usuario.getDescripcion());
        }
    }

    private void actualizarDatos() throws Exception {

        this.controladora.editarUsuario(this.respuesta.getUsuario());
        this.respuesta.setMensaje("Se ha actualizado los datos correctamente");
        this.respuesta.setDatosActualizados(true);
    }

    private void validarUsuario(Usuario usuario) {
        if (usuario == null) {
            this.respuesta.setMensaje("Al parecer ha ocurrido un error :(, si el problema persiste contacta a soporte.");
        }
    }

    private void obtenerUsuario(Usuario usuario) throws Exception {
        this.respuesta.setUsuario(this.controladora.obetenerUsuario(usuario));
    }
}
