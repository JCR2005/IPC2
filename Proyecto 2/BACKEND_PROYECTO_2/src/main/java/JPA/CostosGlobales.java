/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JPA;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.io.Serializable;

/**
 *
 * @author carlosrodriguez
 */

@Entity
public class CostosGlobales implements Serializable {
    
        @Id
        private String idCosto;
        private double costo;
        
        
    public CostosGlobales()  {
      
    }
    
    public CostosGlobales(String idCosto, double costo)  {
        this.idCosto=idCosto;
        this.costo=costo;
      
    }

    public String getIdCosto() {
        return idCosto;
    }

    public void setIdCosto(String idCosto) {
        this.idCosto = idCosto;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }
    
    
}
