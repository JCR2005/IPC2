package Backend;

import JPA.Controladora;
import JPA.Usuario;

/**
 * Clase encargada del proceso de registro de un usuario en el sistema.
 * Realiza diversas validaciones de los datos proporcionados por el usuario
 * y, si son correctos, crea un nuevo usuario en la base de datos.
 * Además, gestiona la creación de una cartera asociada a ciertos tipos de usuario.
 */
public class Registro {

    private String mensaje = ""; // Mensaje con el resultado de la validación
    private Controladora controladora = new Controladora(); // Controladora para acceder a la base de datos

    /**
     * Obtiene el mensaje actual asociado al proceso de registro.
     * 
     * @return El mensaje con el resultado de la validación.
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * Establece el mensaje con el resultado de la validación del registro.
     * 
     * @param mensaje El mensaje a establecer.
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * Realiza la validación de los datos del usuario. Si todos los datos son correctos,
     * crea un nuevo usuario en el sistema.
     * 
     * @param usuario El usuario a registrar.
     * @throws Exception Si ocurre un error durante el proceso de validación o creación del usuario.
     */
    public void validacion(Usuario usuario) throws Exception {

        // Verificación de cada uno de los campos del usuario
        if (usuarioExite(usuario)) {
            mensaje = "UsuarioExiste";
        } else if (!validarContraceña(usuario)) {
            mensaje = "ContraceñaInvalida";
        } else if (!validarNombre(usuario)) {
            mensaje = "nombreInvalido";
        } else if (!validarEdad(usuario)) {
            mensaje = "edadInvalida";
        } else if (!validarDescripcion(usuario)) {
            mensaje = "descripcionValida";
        } else if (verificarCamposVacios(usuario)) {
            mensaje = "Por favor llene todos los campos";
        } else {
            mensaje = "Felicidades " + usuario.getUsuario() + ", su cuenta ha sido creada";

            // Si el tipo de usuario requiere una cartera, la crea
            if (verificarNecesidadCartera(usuario.getTipoCuenta())) {
                usuario.setIdCartera(crearCartera());
            }

            // Crea el usuario en la base de datos
            controladora.crearUsuario(usuario);
        }
    }

    /**
     * Verifica si el tipo de usuario requiere una cartera.
     * 
     * @param tipoUsuario El tipo de cuenta del usuario.
     * @return true si el tipo de usuario necesita una cartera, false si no.
     */
    private boolean verificarNecesidadCartera(String tipoUsuario) {
        return tipoUsuario.endsWith("Anunciante") || tipoUsuario.endsWith("Editor");
    }

    /**
     * Verifica si alguno de los campos del usuario está vacío o es nulo.
     * 
     * @param usuario El usuario a verificar.
     * @return true si alguno de los campos está vacío o nulo, false si todos los campos están completos.
     */
    private boolean verificarCamposVacios(Usuario usuario) {
        boolean camposVacios = false;

        // Verifica cada campo del usuario
        if (usuario.getUsuario().isEmpty() || usuario.getUsuario() == null) camposVacios = true;
        if (usuario.getNombre().isEmpty() || usuario.getNombre() == null) camposVacios = true;
        if (usuario.getPassword().isEmpty() || usuario.getPassword() == null) camposVacios = true;
        if (usuario.getTipoCuenta().isEmpty() || usuario.getTipoCuenta() == null) camposVacios = true;
        if (usuario.getDescripcion().isEmpty() || usuario.getDescripcion() == null) camposVacios = true;
        if (usuario.getEdad() == 0) camposVacios = true;

        return camposVacios;
    }

    /**
     * Verifica si el usuario ya existe en la base de datos.
     * 
     * @param usuario El usuario a verificar.
     * @return true si el usuario ya existe, false si no.
     * @throws Exception Si ocurre un error durante la verificación.
     */
    private boolean usuarioExite(Usuario usuario) throws Exception {
        return controladora.buscarUsuario(usuario);
    }

    /**
     * Valida que la contraseña cumpla con los requisitos establecidos:
     * debe tener entre 8 y 32 caracteres y no contener espacios.
     * 
     * @param usuario El usuario cuya contraseña se va a validar.
     * @return true si la contraseña es válida, false si no lo es.
     */
    private boolean validarContraceña(Usuario usuario) {
        boolean contraValida = false;
        String password = usuario.getPassword();

        if (password.length() >= 8 && password.length() <= 32 && !password.contains(" ")) {
            contraValida = true;
        }

        return contraValida;
    }

    /**
     * Valida que el nombre del usuario tenga una longitud entre 2 y 30 caracteres.
     * 
     * @param usuario El usuario cuyo nombre se va a validar.
     * @return true si el nombre es válido, false si no lo es.
     */
    private boolean validarNombre(Usuario usuario) {
        boolean nombreValido = false;

        if (usuario.getNombre().length() >= 2 && usuario.getNombre().length() <= 30) {
            nombreValido = true;
        }

        return nombreValido;
    }

    /**
     * Valida que la edad del usuario esté en el rango de 18 a 100 años.
     * 
     * @param usuario El usuario cuya edad se va a validar.
     * @return true si la edad es válida, false si no lo es.
     */
    private boolean validarEdad(Usuario usuario) {
        boolean edadValida = false;

        if (usuario.getEdad() >= 18 && usuario.getEdad() <= 100) {
            edadValida = true;
        }

        return edadValida;
    }

    /**
     * Valida que la descripción del usuario tenga entre 10 y 100 caracteres.
     * 
     * @param usuario El usuario cuya descripción se va a validar.
     * @return true si la descripción es válida, false si no lo es.
     */
    private boolean validarDescripcion(Usuario usuario) {
        boolean descripcionValida = false;

        if (usuario.getDescripcion().length() >= 10 && usuario.getDescripcion().length() <= 100) {
            descripcionValida = true;
        }

        return descripcionValida;
    }

    /**
     * Crea una cartera para el usuario, generando un ID único para ella.
     * 
     * @return El ID de la cartera creada.
     * @throws Exception Si ocurre un error durante la creación de la cartera.
     */
    public String crearCartera() throws Exception {
        String idCartera = "";
        int contador = 0;
        boolean carteraGenerada = false;

        while (!carteraGenerada) {
            // Genera un ID de cartera con un formato de 16 dígitos
            String id_cartera = String.format("%016d", contador);

            // Si la cartera no existe, la crea
            if (!controladora.buscarCartera(id_cartera)) {
                controladora.crearCartera(id_cartera);
                carteraGenerada = true;
                idCartera = id_cartera;
            } else {
                contador++;
            }
        }

        return idCartera;
    }
}
