package Backend;

import JPA.Controladora;
import JPA.Usuario;
import respuetas.RespuestaVerificacionIdentidad;
import token.TokenService;

/**
 * Esta clase gestiona el proceso de autenticación de usuarios,
 * validando sus credenciales y generando tokens de acceso si son válidas.
 * Además, se encarga de asignar rutas iniciales según el tipo de cuenta del usuario.
 */
public class Login {

    private RespuestaVerificacionIdentidad repuesta = new RespuestaVerificacionIdentidad();
    private String mensaje = "";
    private Usuario usuario = null;
    private boolean usuarioValidado = false;
    private final Controladora controladora = new Controladora();

    /**
     * Verifica si el usuario ha sido validado correctamente.
     * 
     * @return true si el usuario está validado, false si no lo está.
     */
    public boolean isUsuarioValidado() {
        return usuarioValidado;
    }

    /**
     * Establece el estado de validación del usuario.
     * 
     * @param usuarioValidado El estado de validación del usuario.
     */
    public void setUsuarioValidado(boolean usuarioValidado) {
        this.usuarioValidado = usuarioValidado;
    }

    /**
     * Obtiene el mensaje asociado a la validación del usuario.
     * 
     * @return El mensaje asociado a la validación.
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * Establece el mensaje asociado a la validación del usuario.
     * 
     * @param mensaje El mensaje a establecer.
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * Valida las credenciales del usuario.
     * 
     * @param usuario El usuario a validar.
     * @return Una cadena JSON con el resultado del proceso de login.
     * @throws Exception Si ocurre un error durante la validación.
     */
    public String validarInicioSesion(Usuario usuario) throws Exception {
        String respueta = "";

        if (!buscarUsuario(usuario)) {
            this.mensaje = "El usuario es incorrecto";
        } else if (!validarPassword(usuario.getPassword())) {
            this.mensaje = "La contraseña es incorrecta";
        } else {
            this.mensaje = "Bienvenido " + usuario.getUsuario();
            this.usuarioValidado = true;
        }

        respueta = generarlogin();

        return respueta;
    }

    /**
     * Verifica la identidad del usuario para propósitos de autenticación.
     * 
     * @param usuario El usuario a verificar.
     * @return RespuestaVerificacionIdentidad con el mensaje y el estado de verificación.
     * @throws Exception Si ocurre un error durante la verificación.
     */
    public RespuestaVerificacionIdentidad verificarUsuario(Usuario usuario) throws Exception {
        if (!buscarUsuario(usuario)) {
            this.repuesta.setMensaje("Credenciales incorrectas");
        } else if (!validarPassword(usuario.getPassword())) {
            this.repuesta.setMensaje("Credenciales incorrectas");
        } else {
            this.repuesta.setMensaje("¡Eres tú! " + usuario.getUsuario());
            this.usuarioValidado = true;
            this.repuesta.setUsuarioVaerificado(true);
        }

        return repuesta;
    }

    /**
     * Busca un usuario en la base de datos utilizando la controladora.
     * 
     * @param usuario El usuario a buscar.
     * @return true si el usuario fue encontrado, false si no.
     * @throws Exception Si ocurre un error al buscar el usuario.
     */
    private boolean buscarUsuario(Usuario usuario) throws Exception {
        boolean usuarioExiste = false;

        if (controladora.buscarUsuario(usuario)) {
            this.usuario = controladora.obetenerUsuario(usuario);
            usuarioExiste = true;
        }

        return usuarioExiste;
    }

    /**
     * Valida la contraseña del usuario.
     * 
     * @param password La contraseña ingresada por el usuario.
     * @return true si la contraseña es válida, false si no lo es.
     * @throws Exception Si ocurre un error al validar la contraseña.
     */
    private boolean validarPassword(String password) throws Exception {
        boolean contraceñaValida = false;
        if (this.usuario.getPassword().endsWith(password) && !password.isEmpty()) {
            contraceñaValida = true;
        }
        return contraceñaValida;
    }

    /**
     * Asigna la ruta inicial correspondiente al tipo de cuenta del usuario.
     * 
     * @return La ruta inicial correspondiente a la cuenta del usuario.
     */
    public String asignarRutaInicial() {
        String ruta = "";

        if (this.usuario.getTipoCuenta().endsWith("Administrador")) {
            ruta = "/paginaPrincipalAdministrador";
        } else if (this.usuario.getTipoCuenta().endsWith("Anunciante")) {
            ruta = "/paginaprincipalanunciante";
        } else if (this.usuario.getTipoCuenta().endsWith("Editor")) {
            ruta = "/paginaPrincipalEditor";
        } else if (this.usuario.getTipoCuenta().endsWith("Suscriptor")) {
            ruta = "/paginaPrincipalSuscriptor";
        } else {
            ruta = "/soporte";
        }

        return ruta;
    }

    /**
     * Genera la respuesta JSON con el token, mensaje y ruta, si el usuario es válido.
     * 
     * @return Una cadena JSON con el resultado del proceso de login.
     */
    public String generarlogin() {
        String jsonResponse = "";
        TokenService tokenService = new TokenService();

        if (isUsuarioValidado()) {
            String token = tokenService.generateToken(this.usuario); // Genera el token
            String mensaje = this.mensaje;
            String ruta = asignarRutaInicial();
            jsonResponse = "{\"message\": \"" + mensaje + "\", \"token\": \"" + token + "\", \"ruta\": \"" + ruta + "\"}";
        } else {
            jsonResponse = "{\"message\": \"Verifica tus credenciales\"}";
        }

        return jsonResponse;
    }
}
