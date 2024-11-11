
package respuetas.Anunciantes;

import JPA.vigenciaAnuncio;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author carlosrodriguez
 */
public class RespuestaAnuncios {
    
    private double precioTexto;
        private double preciovideoo;
            private double precioImagen;
                    
            List<vigenciaAnuncio> vigencia=new ArrayList<>();

    public List<vigenciaAnuncio> getVigencia() {
        return vigencia;
    }

    public void setVigencia(List<vigenciaAnuncio> vigencia) {
        this.vigencia = vigencia;
    }
                    

    public double getPrecioTexto() {
        return precioTexto;
    }

    public void setPrecioTexto(double precioTexto) {
        this.precioTexto = precioTexto;
    }

    public double getPreciovideoo() {
        return preciovideoo;
    }

    public void setPreciovideoo(double preciovideoo) {
        this.preciovideoo = preciovideoo;
    }

    public double getPrecioImagen() {
        return precioImagen;
    }

    public void setPrecioImagen(double precioImagen) {
        this.precioImagen = precioImagen;
    }
            
            
            
}
