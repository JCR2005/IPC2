package JPA;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author carlosrodriguez
 */
@Entity
public class Articulo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // ID autoincrementable
    private Long idArticulo;

    private String nombre;
    private String rutaImagen;
    private String rutaPDF;
    private String descripcion;
    private Date fecha;
    private String idRevista;

    @Transient
    private String fechaCreacionTexto;
    
     @Transient
    private String pdf;

    public Articulo() {
    }

    public Long getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(Long idArticulo) {
        this.idArticulo = idArticulo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public String getRutaPDF() {
        return rutaPDF;
    }

    public void setRutaPDF(String rutaPDF) {
        this.rutaPDF = rutaPDF;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getIdRevista() {
        return idRevista;
    }

    public void setIdRevista(String idRevista) {
        this.idRevista = idRevista;
    }

    public String getFechaCreacionTexto() {
        return fechaCreacionTexto;
    }

    public void setFechaCreacionTexto(String fechaCreacionTexto) {
        this.fechaCreacionTexto = fechaCreacionTexto;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }
    
    
    
    
}
