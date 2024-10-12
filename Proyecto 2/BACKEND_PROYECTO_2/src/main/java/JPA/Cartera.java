
package JPA;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.io.Serializable;

/**
 *
 * @author carlosrodriguez
 */

@Entity
public class Cartera implements Serializable {
    
    @Id
    private String idCartera;
    private double saldo;
    
public Cartera() {
    }

    public Cartera(String idCartera, double saldo) {
        this.idCartera = idCartera;
        this.saldo = saldo;
    }

    
    
    public String getIdCartera() {
        return idCartera;
    }

    public void setIdCartera(String idCartera) {
        this.idCartera = idCartera;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
    
    @Override
    public String toString() {
        return "User{"
                + "idCartera='" + this.idCartera + '\''
                + ", saldo='" + this.saldo + '\''
               
                + '}';
    }
    
}
