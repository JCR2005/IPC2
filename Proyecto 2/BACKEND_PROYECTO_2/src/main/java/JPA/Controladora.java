package JPA;


import ControlPersistencia.ControladorPersistencia;
import java.util.List;

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
    
      public boolean buscarUsuarios(String usuario) throws Exception {

        return controladorPersistencia.buscarUsuarios(usuario);
    }


    public Usuario obetenerUsuario(Usuario usuario) throws Exception {

        return controladorPersistencia.obtenerUsuario(usuario);
    }

    public boolean actualizarPrecioAnucio(CostoAnuncio costoAnuncio) throws Exception {

        return controladorPersistencia.actualizarPrecioAnucio(costoAnuncio);
    }

    public boolean actualizarVigenciaAnucio(vigenciaAnuncio vigenciaAnuncio) throws Exception {

        return controladorPersistencia.actualizarVigenciaAnucio(vigenciaAnuncio);
    }

    public boolean buscarCartera(String idCartera) {

        return controladorPersistencia.buscarCartera(idCartera);

    }
    
      public boolean buscarAnuncio(String idAnuncio) {

        return controladorPersistencia.buscarAnuncio(idAnuncio);

    }


    public void crearCartera(String idCartea) throws Exception {

        Cartera cartera = new Cartera(idCartea, 0);

        controladorPersistencia.crearCartera(cartera);
    }

    public String obtenerCartera(String usuario) {

        return controladorPersistencia.obtenrCartera(usuario);

    }

        public Cartera obtenerCarteraCompleta(String usuario) {

        return controladorPersistencia.obtenrCarteraCompleta(usuario);

    }
    public double obtenerSaldo(String idCartera) {
        return controladorPersistencia.obtenerSaldo(idCartera);
    }

    public boolean actualizarCartera(String idCartera, double saldoRecarga) throws Exception {
        return controladorPersistencia.actualizarCartera(idCartera, saldoRecarga);
    }

    public double obtnerPrecioAnuncio(String id) {

        return controladorPersistencia.obtenerPrecioAnuncio(id);
    }

    public void crearAnuncio(Anuncio anuncio) {
       controladorPersistencia.crearAnuncio(anuncio);
    }

    public List<Anuncio> obtenerAlumnos() {
       return controladorPersistencia.obtenerAnuncios();
    }

    public Anuncio obetenerAnuncio(String idAnuncio) {
       
        return controladorPersistencia.obtenerAnuncios(idAnuncio);
    }
    
    public void editarAnuncio(Anuncio anuncio) throws Exception {
       controladorPersistencia.editarAnuncio(anuncio);
    }

    public boolean buscarRevista(String idRevista) {
     
        return controladorPersistencia.buscarRevista(idRevista);
    }

    public void crearRevista(Revista revista) throws Exception {
       controladorPersistencia.crearRevista(revista);
    }

    public void guardarTags(revistaEtiqueta revistaEtiqueta) throws Exception {
        controladorPersistencia.guardarTags(revistaEtiqueta);
    }

    public List<Revista> obtenerRevistas() {
          return controladorPersistencia.obtenerRevistas();
    }   

    public Revista obtenerRevista(String idRevista) {
      return controladorPersistencia.obtenerRevista(idRevista);
    }

    public void editarRevista(Revista revista) throws Exception {
        controladorPersistencia.editarRevista(revista);
    }

    public void darAltaRevistaAnucios(bloqueoAddsRevista bloqueoAddsRevista) throws Exception {
        controladorPersistencia.darAltaRevistaAnuncios(bloqueoAddsRevista);
    }

    public bloqueoAddsRevista obetenerRevistaEnProcesos(String idRevista) {
       return controladorPersistencia.obetenerRevistaEnProcesos(idRevista);
    }

   

    public void bloquearAdds(bloqueoAddsRevista revista) throws Exception {
        this.controladorPersistencia. bloquearAdds( revista);
    }
    
     public List<bloqueoAddsRevista> obtenerListaBloqueoAnuncios() {
       return controladorPersistencia.obtenerListaBloqueoAnuncios();
    }

    public void editarUsuario(Usuario usuario) throws Exception {
      controladorPersistencia.editarUsuario(usuario);
    }

    public CostosGlobales obtenerCostoAsociado() {
        
        return controladorPersistencia.obtenerCostoAsociadoGlobal();
    }

    public void editarCostoGlobal(CostosGlobales costosGlobales) throws Exception {
       controladorPersistencia.editarCostoGlobal(costosGlobales);
    }

    public void crearSuscripciòn(Suscripciòn suscripciòn) throws Exception {
         System.out.println("legoooooooooooooooooooooooooo22");
      this.controladorPersistencia.crearSuscripciòn(suscripciòn);
    }

    public List<Suscripciòn> obtenerSuscripciones() {
       return controladorPersistencia.obtenerListaSuscripciones();
    }

    public void guardarArticulo(Articulo articulo) {
       this.controladorPersistencia.crearArticulo(articulo);
    }

    public List<Articulo> obtenerArticulos() {
      return  this.controladorPersistencia.obtenerArticulos();
    }

    public Articulo obtenerArticulo(Long idArticulo) {
        return  this.controladorPersistencia.obtenerArticulo(idArticulo);
    }

    public void crearComentario(Comentario comentario) {
        this.controladorPersistencia.crearComentario(comentario);
    }

    public List<Comentario> obtenerListaComentarios() {
       return this.controladorPersistencia.obtenerListaComentarios();
    }

    public void crearIngreso(Ingreso ingreso) {
        this.controladorPersistencia.crearIngreso(ingreso);
    }

    public List<Ingreso> obtenerListapagos() {
         return this.controladorPersistencia.obtenerListaPagos();
    }

    public List<Usuario> obtenerlistaUsuarios() {
       return this.controladorPersistencia.obtenerListaUsuarios();
    }

    public List<MeGusta> obtenerLikes() {
        return this.controladorPersistencia.obtenerLikesDeRevistas();
    }

    public void añadirMeGusta(MeGusta meGusta) throws Exception {
         this.controladorPersistencia.añadirMeGusta(meGusta);
    }

    public void actualizarMeGusta(MeGusta meGusta) throws Exception {
     this.controladorPersistencia.actualizarMeGusta(meGusta);
    }

    public List<MeGusta> obtenerListaMeGustas() {
      return controladorPersistencia.obtenerMegustas();
    }

    public void actualizarAnuncio(Anuncio anuncio) throws Exception {
      controladorPersistencia.actualizarAnuncio(anuncio);
    }

    public boolean actualizarCarteraCompleta(Cartera cartera) throws Exception {
       return controladorPersistencia.actualizarCarteraCompleta(cartera);
    }

    public void actualizarBloqueoAdds(bloqueoAddsRevista bloqueo) throws Exception, Exception {
        controladorPersistencia.actualizarBloqueoAdds(bloqueo);
    }

    public  List<CostoAnuncio> obtenerCostosAnuncios() {
        
        return controladorPersistencia.obtenerCostosAnuncios();
    }

    public List<vigenciaAnuncio> obtenerVigenciaAnuncios() {
      return controladorPersistencia.obtenerVigenciaAnuncios();
    }

    public void crearRegistro(HistorialEfectividadAnuncios historialEfectividadAnuncio) {
       controladorPersistencia.crearRegistro(historialEfectividadAnuncio);
    }

    public List<HistorialEfectividadAnuncios> obtenerHistorial() {
       return controladorPersistencia.obtenerHistorial();
    }

  

    


}
