
import ControlPersistencia.ControladorPersistencia;


/**
 *
 * @author carlosrodriguez
 */
public class main {
    
    public static void main(String[] args) throws Exception {
        ControladorPersistencia controladorPersistencia=new ControladorPersistencia();
        controladorPersistencia.inicializarCostoAnuncio();
      controladorPersistencia.inicializarVigenciaAnuncio();
      controladorPersistencia.inicializarCostosGlobales();
        
    }
    
}
