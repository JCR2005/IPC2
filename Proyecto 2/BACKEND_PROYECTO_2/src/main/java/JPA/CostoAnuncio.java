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
public class CostoAnuncio implements Serializable {

    @Id
    private String id_Add;
    private int costo_Add;

    public CostoAnuncio()  {
      
    }

   

    public CostoAnuncio(String id_Add, int costo_Add) {
        this.id_Add = id_Add;
        this.costo_Add = costo_Add;

    }

    public String getId_Add() {
        return id_Add;
    }

    public void setId_Add(String id_Add) {
        this.id_Add = id_Add;
    }

    public int getCosto_Add() {
        return costo_Add;
    }

    public void setCosto_Add(int costo_Add) {
        this.costo_Add = costo_Add;
    }

    @Override
    public String toString() {
        return "User{"
                + "id_Add='" + id_Add + '\''
                + ", costo_Add='" + costo_Add + '\''
                + '}';
    }
}
