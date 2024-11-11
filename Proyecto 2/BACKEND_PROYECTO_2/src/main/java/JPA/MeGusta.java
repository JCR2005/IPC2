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
public class MeGusta implements Serializable {
    
    @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)  // ID autoincrementable
    private Long idMeGusta;
    private Date fecha;
    private String idUsuario;
    private String idRevista;
    private boolean meGusta;

    public Long getIdMeGusta() {
        return idMeGusta;
    }

    public void setIdMeGusta(Long idMeGusta) {
        this.idMeGusta = idMeGusta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

  
    
    

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdRevista() {
        return idRevista;
    }

    public void setIdRevista(String idRevista) {
        this.idRevista = idRevista;
    }

    public boolean isMeGusta() {
        return meGusta;
    }

    public void setMeGusta(boolean meGusta) {
        this.meGusta = meGusta;
    }

    public MeGusta() {
    }

    public MeGusta(String idUsuario, String idRevista, boolean meGusta) {
        this.idUsuario = idUsuario;
        this.idRevista = idRevista;
        this.meGusta = meGusta;
    }

  
    
}
