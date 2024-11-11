package Backend.Perfil;

import JPA.Controladora;
import JPA.Usuario;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import respuetas.respuestaPerfil;

/**
 * Esta clase maneja el proceso de recuperación y visualización del perfil de un usuario.
 * Incluye la obtención de datos del usuario, la validación de la existencia de dichos datos
 * y la carga de la foto del perfil del usuario en formato base64.
 * 
 * @author carlosrodriguez
 */
public class Perfil {
    
    private final respuestaPerfil respuesta = new respuestaPerfil();
    private final Controladora controladora = new Controladora();

    /**
     * Proceso principal para obtener los datos del perfil del usuario, validarlos
     * y cargar la foto en formato base64.
     * 
     * @param usuario El objeto Usuario con los datos necesarios para el proceso.
     * @return Un objeto respuestaPerfil con los resultados del proceso.
     */
    public respuestaPerfil proceso(Usuario usuario){
        try {
            // Obtiene el usuario de la base de datos
            obtenerUsuario(usuario);
            // Valida que el usuario exista
            validarUsuario(respuesta.getUsuario());
            // Carga la foto del usuario si existe
            cargarFoto(respuesta.getUsuario());
            // Marca que el usuario fue encontrado y el proceso fue exitoso
            this.respuesta.setUsuarioEncontrado(true);
            return respuesta;
        } catch (Exception e) {
            // Si ocurre algún error, establece un mensaje de error
            this.respuesta.setMensaje("Al parecer ha ocurrido un error :(, si el problema persiste contacta a soporte.");
            return respuesta;
        }
    }

    /**
     * Obtiene los datos del usuario desde la base de datos utilizando la controladora.
     * 
     * @param usuario El objeto Usuario con la información para obtener.
     * @throws Exception Si ocurre un error al obtener los datos del usuario.
     */
    private void obtenerUsuario(Usuario usuario) throws Exception {
        this.respuesta.setUsuario(this.controladora.obetenerUsuario(usuario));
    }
    
    /**
     * Valida que el usuario obtenido no sea nulo.
     * 
     * @param usuario El objeto Usuario que se desea validar.
     */
    private void validarUsuario(Usuario usuario){
        if (usuario == null) {
            // Si el usuario no existe, se establece un mensaje de error
            this.respuesta.setMensaje("Al parecer ha ocurrido un error :(, si el problema persiste contacta a soporte.");
        }
    }

    /**
     * Carga la foto del perfil del usuario desde el sistema de archivos y la convierte
     * a formato base64 para ser fácilmente transportada o mostrada en una aplicación web.
     * 
     * @param usuario El objeto Usuario que contiene la ruta de la foto.
     * @throws IOException Si ocurre un error al leer la imagen o si la imagen no existe.
     */
    private void cargarFoto(Usuario usuario) throws IOException {
        // Obtiene el archivo de imagen desde la ruta especificada en el objeto Usuario
        File imagen = new File(usuario.getUrlFoto());
        
        // Verifica si el archivo existe
        if (imagen.exists()) {
            try (FileInputStream fileInputStream = new FileInputStream(imagen)) {
                // Lee el archivo de imagen y lo convierte en un arreglo de bytes
                byte[] imageBytes = fileInputStream.readAllBytes();
                // Codifica los bytes de la imagen a base64
                String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                // Establece la imagen codificada en base64 en el objeto Usuario
                usuario.setFoto(base64Image);
            }
        } else {
            // Si la imagen no existe en la ruta especificada, lanza una excepción
            throw new IOException("La imagen no existe en la ruta especificada: " + usuario.getUrlFoto());
        }
    }
}
