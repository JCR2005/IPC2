package JPA;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.sql.Date;

/**
 *
 * @author carlosrodriguez
 */
@Entity
public class Comentario {

    public Comentario(Long idComentario, String Comentario, String idRevista, String idUsuario, Date fecha) {
        this.idComentario = idComentario;
        this.comentario = Comentario;
        this.idRevista = idRevista;
        this.idUsuario = idUsuario;
        this.fecha = fecha;
    }

    public Comentario() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idComentario;

    private String comentario;
    private String idRevista;
    private String idUsuario;
    private Date fecha;

    public Long getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(Long idComentario) {
        this.idComentario = idComentario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String Comentario) {
        this.comentario = Comentario;
    }

    public String getIdRevista() {
        return idRevista;
    }

    public void setIdRevista(String idRevista) {
        this.idRevista = idRevista;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

}
