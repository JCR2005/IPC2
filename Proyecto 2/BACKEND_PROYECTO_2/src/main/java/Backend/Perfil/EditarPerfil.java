package Backend.Perfil;

import JPA.Controladora;
import JPA.Usuario;
import respuetas.respuestaEditarPerfil;

/**
 * Esta clase maneja la lógica para editar los datos del perfil de un usuario.
 * Se encargará de validar y actualizar los datos proporcionados por el usuario en el perfil,
 * como el nombre, la contraseña y la descripción.
 */
public class EditarPerfil {

    private respuestaEditarPerfil respuesta = new respuestaEditarPerfil();
    private final Controladora controladora = new Controladora();

    /**
     * Verifica y valida los datos proporcionados por el usuario para su perfil.
     * Si los datos son válidos, realiza las validaciones correspondientes.
     * 
     * @param usuario El objeto Usuario con los datos a validar.
     * @return Un objeto respuestaEditarPerfil con el resultado de las validaciones.
     */
    public respuestaEditarPerfil verificarDatos(Usuario usuario) {

        // Si el nombre no está vacío, valida el nombre
        if (!usuario.getNombre().isEmpty()) {
            validarNombre(usuario);
        }

        // Si la contraseña no está vacía, valida la contraseña
        if (!usuario.getPassword().isEmpty()) {
            validarContraceña(usuario);
        }

        // Si la descripción no está vacía, valida la descripción
        if (!usuario.getDescripcion().isEmpty()) {
            validarDescripcion(usuario);
        }

        return this.respuesta;
    }

    /**
     * Valida la contraseña del usuario. La contraseña debe tener entre 8 y 32 caracteres
     * y no debe contener espacios.
     * 
     * @param usuario El objeto Usuario con la contraseña a validar.
     */
    private void validarContraceña(Usuario usuario) {
        // Si la contraseña no cumple con los requisitos, marca como no válida
        if ((usuario.getPassword().length() < 8) || (usuario.getPassword().length() > 32) || (usuario.getPassword().contains(" "))) {
            this.respuesta.setContraceñaValida(false);
        }
    }

    /**
     * Valida el nombre del usuario. El nombre debe tener entre 2 y 30 caracteres.
     * 
     * @param usuario El objeto Usuario con el nombre a validar.
     */
    private void validarNombre(Usuario usuario) {
        // Si el nombre no cumple con los requisitos, marca como no válido
        if ((usuario.getNombre().length() < 2) || (usuario.getNombre().length() > 30)) {
            this.respuesta.setNombreValido(false);
        }
    }

    /**
     * Valida la descripción del usuario. La descripción debe tener entre 10 y 100 caracteres.
     * 
     * @param usuario El objeto Usuario con la descripción a validar.
     */
    private void validarDescripcion(Usuario usuario) {
        // Si la descripción no cumple con los requisitos, marca como no válida
        if ((usuario.getDescripcion().length() < 10) || (usuario.getDescripcion().length() > 100)) {
            this.respuesta.setDescripcionValida(false);
        }
    }

    /**
     * Realiza el proceso completo de actualización del perfil del usuario.
     * Obtiene el usuario desde la base de datos, valida los datos y actualiza los campos
     * correspondientes en la base de datos.
     * 
     * @param usuario El objeto Usuario con los nuevos datos para actualizar el perfil.
     * @return Un objeto respuestaEditarPerfil con el resultado del proceso de actualización.
     */
    public respuestaEditarPerfil proceso(Usuario usuario) {
        try {
            obtenerUsuario(usuario);          // Obtiene el usuario desde la base de datos
            validarUsuario(respuesta.getUsuario());  // Valida si el usuario existe
            asignarCabios(usuario);           // Asigna los cambios a la respuesta
            actualizarDatos();                // Actualiza los datos del usuario en la base de datos
            return this.respuesta;            // Devuelve la respuesta con el resultado
        } catch (Exception e) {
            this.respuesta.setMensaje("Ha ocurrido un error al actualizar tus datos");
            return this.respuesta;
        }
    }

    /**
     * Asigna los cambios del usuario (nombre, contraseña, descripción) a la respuesta.
     * 
     * @param usuario El objeto Usuario con los datos a actualizar.
     */
    private void asignarCabios(Usuario usuario) {

        // Si el nombre no está vacío, asigna el nuevo nombre
        if (!usuario.getNombre().isEmpty()) {
            this.respuesta.getUsuario().setNombre(usuario.getNombre());
        }

        // Si la contraseña no está vacía, asigna la nueva contraseña
        if (!usuario.getPassword().isEmpty()) {
            this.respuesta.getUsuario().setPassword(usuario.getPassword());
        }

        // Si la descripción no está vacía, asigna la nueva descripción
        if (!usuario.getDescripcion().isEmpty()) {
            this.respuesta.getUsuario().setDescripcion(usuario.getDescripcion());
        }
    }

    /**
     * Actualiza los datos del usuario en la base de datos utilizando la controladora.
     * 
     * @throws Exception Si ocurre un error al intentar actualizar los datos en la base de datos.
     */
    private void actualizarDatos() throws Exception {
        // Llama a la controladora para actualizar el usuario en la base de datos
        this.controladora.editarUsuario(this.respuesta.getUsuario());
        // Establece los mensajes de éxito en la respuesta
        this.respuesta.setMensaje("Se ha actualizado los datos correctamente");
        this.respuesta.setDatosActualizados(true);
    }

    /**
     * Valida que el usuario no sea nulo y que esté correctamente obtenido desde la base de datos.
     * 
     * @param usuario El objeto Usuario a validar.
     */
    private void validarUsuario(Usuario usuario) {
        if (usuario == null) {
            this.respuesta.setMensaje("Al parecer ha ocurrido un error :(, si el problema persiste contacta a soporte.");
        }
    }

    /**
     * Obtiene los datos del usuario desde la base de datos utilizando la controladora.
     * 
     * @param usuario El objeto Usuario con los datos para obtener.
     * @throws Exception Si ocurre un error al obtener el usuario desde la base de datos.
     */
    private void obtenerUsuario(Usuario usuario) throws Exception {
        // Obtiene el usuario desde la base de datos
        this.respuesta.setUsuario(this.controladora.obetenerUsuario(usuario));
    }
}
