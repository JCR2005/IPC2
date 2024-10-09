package ControlPersistencia;

import ControlPersistencia.exceptions.NonexistentEntityException;
import JPA.CostoAnuncio;
import JPA.Usuario;
import jakarta.persistence.EntityNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author carlosrodriguez
 */
public class ControladorPersistencia {

    private UsuarioJpaController usuario = new UsuarioJpaController();
    private CostoAnuncioJpaController costoAnuncio = new CostoAnuncioJpaController();

    public void crearUsuario(Usuario usuario) throws Exception {

        this.usuario.create(usuario);
    }

    public boolean buscarUsuario(Usuario usuario) throws Exception {

        boolean usuarioEncontrado = false;
        if (this.usuario.findUsuario(usuario.getUsuario()) != null) {
            usuarioEncontrado = true;

        }

        return usuarioEncontrado;
    }

    public Usuario obtenerUsuario(Usuario usuario) {

        return this.usuario.findUsuario(usuario.getUsuario());

    }

    public CostoAnuncioJpaController getCostoAnuncio() {

        return costoAnuncio;

    }

    public boolean actualizarPrecioAnucio(CostoAnuncio costoAnuncio) throws Exception {

        boolean precioEditado = false;

        try {
            this.costoAnuncio.edit(costoAnuncio);
            precioEditado = true;
        } catch (NonexistentEntityException  ex) {
            Logger.getLogger(ControladorPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        } 

        return precioEditado;

    }

}
