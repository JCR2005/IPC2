package respuetas.Suscriptor;

import JPA.Articulo;
import JPA.Revista;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author carlosrodriguez
 */
public class RespuestaVisualizaciòn {

    private boolean procesoExitoso;
    private List<Revista> revistas = new ArrayList();
     private List<Articulo> articulos = new ArrayList();
     private Articulo articulo;
     private Revista revista;
    private String mensaje;
    private boolean anuncios;

    public boolean isAnuncios() {
        return anuncios;
    }

    public void setAnuncios(boolean anuncios) {
        this.anuncios = anuncios;
    }
    
    

    public Revista getRevista() {
        return revista;
    }

    public void setRevista(Revista revista) {
        this.revista = revista;
    }

   
    public RespuestaVisualizaciòn() {
        this.procesoExitoso = true;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    
    public boolean isProcesoExitoso() {
        return procesoExitoso;
    }

    public void setProcesoExitoso(boolean procesoExitoso) {
        this.procesoExitoso = procesoExitoso;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<Revista> getRevistas() {
        return revistas;
    }

    public void setRevistas(List<Revista> revistas) {
        this.revistas = revistas;
    }

    public List<Articulo> getArticulos() {
        return articulos;
    }

    public void setArticulos(List<Articulo> articulos) {
        this.articulos = articulos;
    }
    
    

}
