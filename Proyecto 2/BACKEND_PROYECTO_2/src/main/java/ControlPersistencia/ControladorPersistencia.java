package ControlPersistencia;

import ControlPersistencia.ComentarioJpaController;
import ControlPersistencia.exceptions.NonexistentEntityException;
import JPA.Anuncio;
import JPA.Articulo;
import JPA.Cartera;
import JPA.Comentario;
import JPA.CostoAnuncio;
import JPA.CostosGlobales;
import JPA.HistorialEfectividadAnuncios;
import JPA.Ingreso;

import JPA.MeGusta;
import JPA.Revista;
import JPA.Suscripciòn;
import JPA.Usuario;
import JPA.bloqueoAddsRevista;
import JPA.revistaEtiqueta;
import JPA.vigenciaAnuncio;

import java.util.List;
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
    private AnuncioJpaController anuncioJpaController = new AnuncioJpaController();
    private RevistaJpaController revistaJpaController = new RevistaJpaController();
    private revistaEtiquetaJpaController revistaEtiquetaJpaController = new revistaEtiquetaJpaController();
    private bloqueoAddsRevistaJpaController bloqueoAddsRevistas = new bloqueoAddsRevistaJpaController();
    private CostosGlobalesJpaController costosGlobalesJpaController = new CostosGlobalesJpaController();
    private SuscripciònJpaController suscripciònJpaController = new SuscripciònJpaController();
    private ArticuloJpaController articuloJpaController = new ArticuloJpaController();
    private ComentarioJpaController comentarioJpaController=new ComentarioJpaController();
    private IngresosJpaController ingresosJpaController=new IngresosJpaController();
    private MeGustaJpaController likeJpaController=new MeGustaJpaController();
    private HistorialEfectividadAnunciosJpaController historialEfectividadAnunciosJpaController=new HistorialEfectividadAnunciosJpaController();
    

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

    public boolean buscarUsuarios(String usuario) throws Exception {

        boolean usuarioEncontrado = false;
        if (this.usuario.findUsuario(usuario) != null) {
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
    
     public Cartera obtenrCarteraCompleta(String idCartera) {

        Cartera cartera = this.carteraJpaController.findCartera(idCartera);

        return cartera;
    }

    public double obtenerSaldo(String idCartera) {

        Cartera cartera = this.carteraJpaController.findCartera(idCartera);

        return cartera.getSaldo();
    }

    public boolean actualizarCartera(String idCartera, double saldo) throws Exception {
        Cartera cartera = carteraJpaController.findCartera(idCartera);

        cartera.setSaldo(saldo);
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

    public List<Anuncio> obtenerAnuncios() {
        return anuncioJpaController.findAnuncioEntities();
    }

    public Anuncio obtenerAnuncios(String idAnuncio) {
        return anuncioJpaController.findAnuncio(idAnuncio);
    }

    public void editarAnuncio(Anuncio anuncio) throws Exception {
        anuncioJpaController.edit(anuncio);
    }

    public boolean buscarRevista(String idRevista) {
        boolean existeRevista = false;
        if (this.revistaJpaController.findRevista(idRevista) != null) {
            existeRevista = true;

        }
        return existeRevista;
    }

    public void crearRevista(Revista revista) throws Exception {
        this.revistaJpaController.create(revista);
    }

    public void guardarTags(revistaEtiqueta revistaEtiqueta) throws Exception {
        this.revistaEtiquetaJpaController.create(revistaEtiqueta);
    }

    public List<Revista> obtenerRevistas() {
        return revistaJpaController.findRevistaEntities();
    }

    public Revista obtenerRevista(String idRevista) {
        return revistaJpaController.findRevista(idRevista);
    }

    public void editarRevista(Revista revista) throws Exception {
        revistaJpaController.edit(revista);
    }

    public void darAltaRevistaAnuncios(bloqueoAddsRevista bloqueoAddsRevista) throws Exception {
        bloqueoAddsRevistas.create(bloqueoAddsRevista);
    }

    public bloqueoAddsRevista obetenerRevistaEnProcesos(String idRevista) {
        return bloqueoAddsRevistas.findbloqueoAddsRevista(idRevista);

    }

    public void bloquearAdds(bloqueoAddsRevista revista) throws Exception {
        bloqueoAddsRevistas.edit(revista);
    }

    public List<bloqueoAddsRevista> obtenerListaBloqueoAnuncios() {
        return bloqueoAddsRevistas.findbloqueoAddsRevistaEntities();
    }

    public void editarUsuario(Usuario usuario) throws Exception {
        this.usuario.edit(usuario);
    }

    public void inicializarCostosGlobales() throws Exception {
        this.costosGlobalesJpaController.initializeCostosGlobales();
    }

    public CostosGlobales obtenerCostoAsociadoGlobal() {
        return costosGlobalesJpaController.findCostosGlobales("CostoAsociado");
    }

    public void editarCostoGlobal(CostosGlobales costosGlobales) throws Exception {
        costosGlobalesJpaController.edit(costosGlobales);
    }

    public void crearSuscripciòn(Suscripciòn suscripciòn) throws Exception {
        System.out.println("legoooooooooooooooooooooooooo");
        this.suscripciònJpaController.create(suscripciòn);
    }

    public List<Suscripciòn> obtenerListaSuscripciones() {
        return this.suscripciònJpaController.findSuscripciònEntities();
    }

    public void crearArticulo(Articulo articulo) {
        this.articuloJpaController.create(articulo);
    }

    public List<Articulo> obtenerArticulos() {
        return this.articuloJpaController.findArticuloEntities();
    }

    public Articulo obtenerArticulo(Long idArticulo) {
        return this.articuloJpaController.findArticulo(idArticulo);
    }

    public void crearComentario(Comentario comentario) {
       this.comentarioJpaController.create(comentario);
    }

    public List<Comentario> obtenerListaComentarios() {
       return this.comentarioJpaController.findComentarioEntities();
    }

    public void crearIngreso(Ingreso ingreso) {
      this.ingresosJpaController.create(ingreso);
    }

    public List<Ingreso> obtenerListaPagos() {
      return this.ingresosJpaController.findIngresosEntities();
    }

    public List<Usuario> obtenerListaUsuarios() {
       return  this.usuario.findUsuarioEntities();
    }

    public List<MeGusta> obtenerLikesDeRevistas() {
        return  this.likeJpaController.findMeGustaEntities();
    }

    public void añadirMeGusta(MeGusta meGusta) throws Exception {
        this.likeJpaController.create(meGusta);
    }

    public void actualizarMeGusta(MeGusta meGusta) throws Exception {
        this.likeJpaController.edit(meGusta);
    }

    public List<MeGusta>  obtenerMegustas() {
        return likeJpaController.findMeGustaEntities();
    }

    public void actualizarAnuncio(Anuncio anuncio) throws Exception {
      anuncioJpaController.edit(anuncio);
    }

    public boolean actualizarCarteraCompleta(Cartera cartera) throws Exception {
        boolean carteraActualizada = false;

        try {
            this.carteraJpaController.edit(cartera);
            carteraActualizada = true;
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladorPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return carteraActualizada;
    }

    public void actualizarBloqueoAdds(bloqueoAddsRevista bloqueo) throws Exception {
       this.bloqueoAddsRevistas.edit(bloqueo);
    }

    public  List<CostoAnuncio> obtenerCostosAnuncios() {
        return costoAnuncio.findCostoAnuncioEntities();
    }

    public List<vigenciaAnuncio> obtenerVigenciaAnuncios() {
       return vigenciaAnuncioJpaController.findvigenciaAnuncioEntities();
    }

    public void crearRegistro(HistorialEfectividadAnuncios historialEfectividadAnuncio) {
       historialEfectividadAnunciosJpaController.create(historialEfectividadAnuncio);
    }

    public List<HistorialEfectividadAnuncios> obtenerHistorial() {
        return historialEfectividadAnunciosJpaController.findHistorialEfectividadAnunciosEntities();
    }

  

}
