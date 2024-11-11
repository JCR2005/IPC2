

package respuetas.Administrador;


import Backend.administracion.DetalleAnuncio;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author carlosrodriguez
 */
public class RespuestaReporteEfectividadAnuncios {
       private String mensaje;
               private String pdf;
    private boolean procesoExitoso;
     private List<DetalleAnuncio> detalles;

    public RespuestaReporteEfectividadAnuncios() {
        this.detalles = new ArrayList<>();
    }

    public void add(DetalleAnuncio detalle) {
        detalles.add(detalle);
    }

    public List<DetalleAnuncio> getDetalles() {
        return detalles;
    }
    
}
