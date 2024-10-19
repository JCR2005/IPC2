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
public class Revista implements Serializable {

    @Id
    private String idRevista;

    private String autor;
    private String titulo;
    private String categoria;
    private String descripcion;
    private Date fechaPublicacion;
    private boolean aprobacion;
    private boolean comentarios;
    private boolean likes;
    private double costoAsociado;
    private int cantidadLikes;
    private boolean suscripciones;
    private double costoOcultacion;

    

    @Transient
    private String fechaPublicacionTexto;

    @Transient
    private String[] etiquetas;
    
    
    public void iniciarValores(){
        this.aprobacion=false;
        this.comentarios=true;
        this.likes=true;
        this.suscripciones=true;
        this.anuncios=true;
        this.costoAsociado=0.0;
        this.cantidadLikes=0;
        this.costoOcultacion=0.0;
    
    }
    
    public double getCostoOcultacion() {
        return costoOcultacion;
    }

    public void setCostoOcultacion(double costoOcultacion) {
        this.costoOcultacion = costoOcultacion;
    }

    public String[] getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(String[] etiquetas) {
        this.etiquetas = etiquetas;
    }

    private boolean anuncios;

    public boolean isAnuncios() {
        return anuncios;
    }

    public void setAnuncios(boolean anuncios) {
        this.anuncios = anuncios;
    }

    public boolean isSuscripciones() {
        return suscripciones;
    }

    public void setSuscripciones(boolean suscripciones) {
        this.suscripciones = suscripciones;
    }

    public String getIdRevista() {
        return idRevista;
    }

    public void setIdRevista(String idRevista) {
        this.idRevista = idRevista;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }


    public boolean isAprobacion() {
        return aprobacion;
    }

    public void setAprobacion(boolean aprobacion) {
        this.aprobacion = aprobacion;
    }

    public boolean isComentarios() {
        return comentarios;
    }

    public void setComentarios(boolean comentarios) {
        this.comentarios = comentarios;
    }

    public boolean isLikes() {
        return likes;
    }

    public void setLikes(boolean likes) {
        this.likes = likes;
    }

    public double getCostoAsociado() {
        return costoAsociado;
    }

    public void setCostoAsociado(double costoAsociado) {
        this.costoAsociado = costoAsociado;
    }

    public int getCantidadLikes() {
        return cantidadLikes;
    }

    public void setCantidadLikes(int cantidadLikes) {
        this.cantidadLikes = cantidadLikes;
    }

    public String getFechaPublicacionTexto() {
        return fechaPublicacionTexto;
    }

    public void setFechaPublicacionTexto(String fechaPublicacionTexto) {
        this.fechaPublicacionTexto = fechaPublicacionTexto;
    }
}
