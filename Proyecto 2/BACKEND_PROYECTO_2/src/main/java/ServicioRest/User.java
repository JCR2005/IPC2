/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ServicioRest;

/**
 *
 * @author carlosrodriguez
 */

public class User {
    private String usuario;
    private String password;
    private String tipoCuenta;
    private String nombre;
    private int edad;
    private String descripcion;

    // Getters y setters
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    @Override
    public String toString() {
        return "User{" +
                "usuario='" + usuario + '\'' +
                ", password='" + password + '\'' +
                ", tipoCuenta='" + tipoCuenta + '\'' +
                ", nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}

