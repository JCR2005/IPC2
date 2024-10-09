package JPA;

import ControlPersistencia.ControladorPersistencia;

/**
 *
 * @author carlosrodriguez
 */
public class Controladora {

    ControladorPersistencia controladorPersistencia = new ControladorPersistencia();

    public void crearUsuario(Usuario usuario) throws Exception {

        controladorPersistencia.crearUsuario(usuario);
    }

    public boolean buscarUsuario(Usuario usuario) throws Exception {

        return controladorPersistencia.buscarUsuario(usuario);
    }

    public Usuario obetenerUsuario(Usuario usuario) throws Exception {

        return controladorPersistencia.obtenerUsuario(usuario);
    }

    public boolean actualizarPrecioAnucio(CostoAnuncio costoAnuncio) throws Exception {

        return controladorPersistencia.actualizarPrecioAnucio(costoAnuncio);
    }

}
