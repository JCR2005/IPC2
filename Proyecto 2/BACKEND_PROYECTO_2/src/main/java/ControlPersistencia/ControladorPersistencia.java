package ControlPersistencia;

import ControlPersistencia.exceptions.NonexistentEntityException;
import JPA.Anuncio;
import JPA.Cartera;
import JPA.CostoAnuncio;
import JPA.Usuario;
import JPA.vigenciaAnuncio;
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
    private CarteraJpaController carteraJpaController = new CarteraJpaController();
    private vigenciaAnuncioJpaController vigenciaAnuncioJpaController = new vigenciaAnuncioJpaController();
    private AnuncioJpaController anuncioJpaController=new AnuncioJpaController();

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

    public void inicializarCostoAnuncio() throws Exception {

        costoAnuncio.initializeCostoAnuncio();

    }

    public void inicializarVigenciaAnuncio() throws Exception {

        vigenciaAnuncioJpaController.initializeVigenciaAnuncio();

    }

    public boolean actualizarPrecioAnucio(CostoAnuncio costoAnuncio) throws Exception {

        boolean precioEditado = false;

        try {
            this.costoAnuncio.edit(costoAnuncio);
            precioEditado = true;
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladorPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }

        return precioEditado;

    }

    public boolean actualizarVigenciaAnucio(vigenciaAnuncio vigenciaAnuncia) throws Exception {

        boolean vigenciaEditada = false;

        try {
            this.vigenciaAnuncioJpaController.edit(vigenciaAnuncia);
            vigenciaEditada = true;
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladorPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }

        return vigenciaEditada;

    }

    public boolean buscarCartera(String idCartera) {
        boolean existeCartera = false;
        if (this.carteraJpaController.findCartera(idCartera) != null) {
            existeCartera = true;

        }
        return existeCartera;

    }
    
    public boolean buscarAnuncio(String idAnuncio) {
        boolean existeAnuncio = false;
        if (this.anuncioJpaController.findAnuncio(idAnuncio) != null) {
            existeAnuncio = true;

        }
        return existeAnuncio;

    }

    public void crearCartera(Cartera cartera) throws Exception {
        carteraJpaController.create(cartera);
    }

    public String obtenrCartera(String IdUsuario) {

        Usuario usuario = this.usuario.findUsuario(IdUsuario);

        return usuario.getIdCartera();
    }

    public double obtenerSaldo(String idCartera) {

        Cartera cartera = this.carteraJpaController.findCartera(idCartera);

        return cartera.getSaldo();
    }

    public boolean actualizarCartera(String idCartera, double saldoRecarga) throws Exception {
        Cartera cartera = carteraJpaController.findCartera(idCartera);

        double recargar = cartera.getSaldo() + saldoRecarga;

        cartera.setSaldo(recargar);
        boolean carteraActualizada = false;

        try {
            this.carteraJpaController.edit(cartera);
            carteraActualizada = true;
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladorPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }

        return carteraActualizada;

    }

    public double obtenerPrecioAnuncio(String id) {

        CostoAnuncio costoAnuncio = this.costoAnuncio.findCostoAnuncio(id);

        return costoAnuncio.getCosto_Add();
    }

    public void crearAnuncio(Anuncio anuncio) {
      anuncioJpaController.create(anuncio);
    }

}
