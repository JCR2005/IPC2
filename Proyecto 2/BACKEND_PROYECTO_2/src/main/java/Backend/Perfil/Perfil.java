package Backend.Perfil;

import JPA.Controladora;
import JPA.Usuario;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import respuetas.respuestaEditarPerfil;
import respuetas.respuestaPerfil;

/**
 *
 * @author carlosrodriguez
 */
public class Perfil {
    
    private final respuestaPerfil respuesta = new respuestaPerfil();
    private final Controladora controladora = new Controladora();

    public respuestaPerfil proceso(Usuario usuario){

        try {
          
                obtenerUsuario(usuario);
                validarUsuario(respuesta.getUsuario());
                 cargarFoto(respuesta.getUsuario());
                this.respuesta.setUsuarioEncontrado(true);
                return respuesta;
        } catch (Exception e) {
                this.respuesta.setMensaje("Al parecer ha ocurrido un error :(, si el problema persiste contacta a soporte.");
                return respuesta;
        }
    }

    private void obtenerUsuario(Usuario usuario) throws Exception {
      this.respuesta.setUsuario(this.controladora.obetenerUsuario(usuario));
    }
    
    private void validarUsuario(Usuario usuario){
        if (usuario==null) {
               this.respuesta.setMensaje("Al parecer ha ocurrido un error :(, si el problema persiste contacta a soporte.");
        }
    }
    

public void cargarFoto(Usuario usuario) throws IOException {
    File imagen = new File(usuario.getUrlFoto());
    if (imagen.exists()) {
        try (FileInputStream fileInputStream = new FileInputStream(imagen)) {
            byte[] imageBytes = fileInputStream.readAllBytes();
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            usuario.setFoto(base64Image);
        }
    } else {
        throw new IOException("La imagen no existe en la ruta especificada: " + usuario.getUrlFoto());
    }
}

 

}
