/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
public class bloqueoAddsRevista implements Serializable {
    
    @Id
    private String idRevista;

   
    private Date fechaBloqueo;
    private int vigencia;
    private boolean bloqueoAddsRevista;
    
    @Transient
    private String fechaPublicacionTexto;

     public bloqueoAddsRevista(){
    }
    
    
    public bloqueoAddsRevista(String idRevista){
    
        this.idRevista=idRevista;
        this.bloqueoAddsRevista=true;
    
    }
    
    public boolean isBloqueoAddsRevista() {
        return bloqueoAddsRevista;
    }

    public void setBloqueoAddsRevista(boolean bloqueoAddsRevista) {
        this.bloqueoAddsRevista = bloqueoAddsRevista;
    }
    
    

    public String getFechaPublicacionTexto() {
        return fechaPublicacionTexto;
    }

    public void setFechaPublicacionTexto(String fechaPublicacionTexto) {
        this.fechaPublicacionTexto = fechaPublicacionTexto;
    }

    
     public String getIdRevista() {
        return idRevista;
    }

    public void setIdRevista(String idRevista) {
        this.idRevista = idRevista;
    }

    public Date getFechaBloqueo() {
        return fechaBloqueo;
    }

    public void setFechaBloqueo(Date fechaBloqueo) {
        this.fechaBloqueo = fechaBloqueo;
    }

    public int getVigencia() {
        return vigencia;
    }

    public void setVigencia(int vigencia) {
        this.vigencia = vigencia;
    }

}
