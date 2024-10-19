/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JPA;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 *
 * @author carlosrodriguez
 */

@Entity
public class etiqueta {

    @Id
    private String idEtiqueta;

    private String etiqueta;

    public etiqueta() {
    }
    
    

    public String getIdEtiqueta() {
        return idEtiqueta;
    }

    public void setIdEtiqueta(String idEtiqueta) {
        this.idEtiqueta = idEtiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

}
