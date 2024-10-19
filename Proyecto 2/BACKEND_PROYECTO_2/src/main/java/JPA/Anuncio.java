package JPA;

import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author carlosrodriguez
 */
@Entity
public class Anuncio implements Serializable {

    @Id
    private String idAnuncio;

    // Constructor
    public Anuncio() {

    }
    private String usuario;
    private String tipoAnuncio;
    private String vigencia;
    private double precio;

    private Date fechaPublicacion;
    private Date fechaFinalizacion;

    private boolean estado;
    private String rutaImagen; // Atributo para la imagen
    private String anuncioVideo;
    private String anuncioTexto;

    @Transient // Esto indica que este campo no se persistirá en la base de datos
    private String fechaPublicacionTexto;

    public Date getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public void setFechaFinalizacion(Date fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getFechaPublicacionTexto() {
        return fechaPublicacionTexto;
    }

    public void setFechaPublicacionTexto(String fechaPublicacionTexto) {
        this.fechaPublicacionTexto = fechaPublicacionTexto;
    }

    public String getIdAnuncio() {
        return idAnuncio;
    }

    public void setIdAnuncio(String idAnuncio) {
        this.idAnuncio = idAnuncio;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getTipoAnuncio() {
        return tipoAnuncio;
    }

    public void setTipoAnuncio(String tipoAnuncio) {
        this.tipoAnuncio = tipoAnuncio;
    }

    public String getVigencia() {
        return vigencia;
    }

    public void setVigencia(String vigencia) {
        this.vigencia = vigencia;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public String getAnuncioVideo() {
        return anuncioVideo;
    }

    public void setAnuncioVideo(String anuncioVideo) {
        this.anuncioVideo = anuncioVideo;
    }

    public String getAnuncioTexto() {
        return anuncioTexto;
    }

    public void setAnuncioTexto(String anuncioTexto) {
        this.anuncioTexto = anuncioTexto;
    }

}
