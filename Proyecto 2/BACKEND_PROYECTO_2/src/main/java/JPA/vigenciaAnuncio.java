package JPA;

/**
 *
 * @author carlosrodriguez
 */
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.io.Serializable;

@Entity
public class vigenciaAnuncio implements Serializable {

    public vigenciaAnuncio() {
    }

    @Id
    private String id_vigencia;

    public String getId_vigencia() {
        return id_vigencia;
    }
    

    public void setId_vigencia(String id_vigencia) {
        this.id_vigencia = id_vigencia;
    }

    public int getVigencia() {
        return vigencia;
    }

    public void setVigencia(int vigencia) {
        this.vigencia = vigencia;
    }
    private int vigencia;

    public vigenciaAnuncio(String id_vigencia, int vigencia) {
        this.id_vigencia = id_vigencia;
        this.vigencia = vigencia;
    }
    
    

}
