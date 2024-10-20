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
}
