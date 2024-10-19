package JPA;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import java.io.Serializable;
import java.util.Objects;

/** 
 * Clase que representa la relación entre una revista y una etiqueta.
 */
@Entity
public class revistaEtiqueta implements Serializable {

    @EmbeddedId
    private RevistaEtiquetaId id; // Aquí es donde JPA busca el campo "id".

    public revistaEtiqueta() {
    }

    public revistaEtiqueta(String idRevista, String idEtiqueta) {
        this.id = new RevistaEtiquetaId(idRevista, idEtiqueta);
    }

    public RevistaEtiquetaId getId() {  // Es importante tener este getter
        return id;
    }

    public void setId(RevistaEtiquetaId id) {  // Y este setter también
        this.id = id;
    }

    public String getIdRevista() {
        return id.getIdRevista();
    }

    public void setIdRevista(String idRevista) {
        this.id.setIdRevista(idRevista);
    }

    public String getIdEtiqueta() {
        return id.getIdEtiqueta();
    }

    public void setIdEtiqueta(String idEtiqueta) {
        this.id.setIdEtiqueta(idEtiqueta);
    }

    @Embeddable
    public static class RevistaEtiquetaId implements Serializable {

        private String idRevista;
        private String idEtiqueta;

        public RevistaEtiquetaId() {
        }

        public RevistaEtiquetaId(String idRevista, String idEtiqueta) {
            this.idRevista = idRevista;
            this.idEtiqueta = idEtiqueta;
        }

        public String getIdRevista() {
            return idRevista;
        }

        public void setIdRevista(String idRevista) {
            this.idRevista = idRevista;
        }

        public String getIdEtiqueta() {
            return idEtiqueta;
        }

        public void setIdEtiqueta(String idEtiqueta) {
            this.idEtiqueta = idEtiqueta;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RevistaEtiquetaId that = (RevistaEtiquetaId) o;
            return Objects.equals(idRevista, that.idRevista) && Objects.equals(idEtiqueta, that.idEtiqueta);
        }

        @Override
        public int hashCode() {
            return Objects.hash(idRevista, idEtiqueta);
        }
    }
}
