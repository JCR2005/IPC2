package JPA;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import java.io.Serializable;
import java.sql.Date;

@Entity
public class Suscripciòn implements Serializable{

   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // ID autoincrementable
    private Long idSuscripcion;
    
    private String idRevista;
    private String idUsuario;
    private Date fechaSuscricion; // Columna normal, no parte de la clave primaria
    
    
   
    @Transient
    private String fechaSuscripcionTexto;

    // Constructor, getters y setters
    public Suscripciòn() {}

    public Suscripciòn(String idRevista, String idUsuario, Date fechaSuscricion) {
        this.idRevista = idRevista;
        this.idUsuario = idUsuario;
        this.fechaSuscricion = fechaSuscricion;
    }

    
    // Getters y setters
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

    public Date getFechaSuscricion() {
        return fechaSuscricion;
    }

    public void setFechaSuscricion(Date fechaSuscricion) {
        this.fechaSuscricion = fechaSuscricion;
    }


    public Long getIdSuscripcion() {
        return idSuscripcion;
    }

    public void setIdSuscripcion(Long idSuscripcion) {
        this.idSuscripcion = idSuscripcion;
    }

    public String getFechaSuscripcionTexto() {
        return fechaSuscripcionTexto;
    }

    public void setFechaSuscripcionTexto(String fechaSuscripcionTexto) {
        this.fechaSuscripcionTexto = fechaSuscripcionTexto;
    }

    
  
}
