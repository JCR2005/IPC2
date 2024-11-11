package JPA;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author carlosrodriguez
 */
@Entity
public class HistorialEfectividadAnuncios implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // ID autoincrementable
    private Long id;
       String usuario;
        String tipoAnuncio;
    String ruta;
    Date fecha;
    String idAnuncio;

    public String getTipoAnuncio() {
        return tipoAnuncio;
    }

    public HistorialEfectividadAnuncios() {
    }

    public HistorialEfectividadAnuncios(String usuario, String tipoAnuncio, String ruta, Date fecha, String idAnuncio) {
        this.usuario = usuario;
        this.tipoAnuncio = tipoAnuncio;
        this.ruta = ruta;
        this.fecha = fecha;
        this.idAnuncio = idAnuncio;
    }

    public void setTipoAnuncio(String tipoAnuncio) {
        this.tipoAnuncio = tipoAnuncio;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getIdAnuncio() {
        return idAnuncio;
    }

    public void setIdAnuncio(String idAnuncio) {
        this.idAnuncio = idAnuncio;
    }
    
    

}
