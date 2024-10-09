package JPA;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.io.Serializable;

/**
 *
 * @author carlosrodriguez
 */
@Entity
public class Usuario implements Serializable {

    @Id
    private String usuario;
    private String password; // Cambia el nombre aquí si es necesario
    private String tipoCuenta;
    private String nombre;
    private int edad;
    private String descripcion;
    private String urlFoto;

   
    public Usuario() {
    }

    public Usuario(String usuario, String nombre, String tipoUsuario, String password, String descripcion, String urlFoto, int edad) {
        this.usuario = usuario;
        this.nombre = nombre;
        this.tipoCuenta = tipoUsuario;
        this.password = password; // Cambia el nombre aquí si es necesario
        this.descripcion = descripcion;
        this.urlFoto = urlFoto;
        this.edad = edad;

    }

    // Getters y Setters
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String nombreUsuario) {
        this.usuario = nombreUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoUsuario) {
        this.tipoCuenta = tipoUsuario;
    }

    public String getPassword() { // Cambia el nombre aquí
        return password;
    }

    public void setPassword(String password) { // Cambia el nombre aquí
        this.password = password;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
     public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }


    @Override
    public String toString() {
        return "User{"
                + "usuario='" + usuario + '\''
                + ", password='" + password + '\''
                + ", tipoCuenta='" + tipoCuenta + '\''
                + ", nombre='" + nombre + '\''
                + ", edad=" + edad
                + ", descripcion='" + descripcion + '\''
                + ", urlFoto='" + urlFoto + '\''
                + '}';
    }
}
