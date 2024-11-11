package Backend.administracion;

/**
 *
 * @author carlosrodriguez
 */
public class DetalleAnuncio {
    private String idAnuncio; // Cambiado de int a String
    private String tipo;
    private int cantidadMostrada;
    private String url;

    public DetalleAnuncio(String idAnuncio, String tipo, int cantidadMostrada, String url) {
        this.idAnuncio = idAnuncio;
        this.tipo = tipo;
        this.cantidadMostrada = cantidadMostrada;
        this.url = url;
    }

    // Getters y setters aquí

    @Override
    public String toString() {
        return "DetalleAnuncio{" +
                "idAnuncio='" + idAnuncio + '\'' + // Cambiado a String en toString
                ", tipo='" + tipo + '\'' +
                ", cantidadMostrada=" + cantidadMostrada +
                ", url='" + url + '\'' +
                '}';
    }
}
