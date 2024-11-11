package respuetas;

import JPA.Anuncio;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que encapsula la respuesta de un proceso relacionado con anuncios.
 * Esta clase almacena listas de anuncios categorizados (texto, video, imagen)
 * y también listas para direcciones e imágenes asociadas a los anuncios.
 * 
 * @author carlosrodriguez
 */
public class RespuestaProcesoAnuncios {

    // Listas que almacenan los anuncios categorizados por tipo
    private List<Anuncio> anunciosTexto = new ArrayList<>();
    private List<Anuncio> anunciosVideo = new ArrayList<>();
    private List<Anuncio> anunciosImagen = new ArrayList<>();

    // Listas adicionales para almacenar direcciones e imágenes asociadas
    private List<String> direcciones = new ArrayList<>();
    private List<String> imagenes = new ArrayList<>();

    /**
     * Obtiene la lista de anuncios de tipo texto.
     * 
     * @return Lista de anuncios de tipo texto
     */
    public List<Anuncio> getAnunciosTexto() {
        return anunciosTexto;
    }

    /**
     * Establece la lista de anuncios de tipo texto.
     * 
     * @param anunciosTexto Lista de anuncios de tipo texto
     */
    public void setAnunciosTexto(List<Anuncio> anunciosTexto) {
        this.anunciosTexto = anunciosTexto;
    }

    /**
     * Obtiene la lista de anuncios de tipo video.
     * 
     * @return Lista de anuncios de tipo video
     */
    public List<Anuncio> getAnunciosVideo() {
        return anunciosVideo;
    }

    /**
     * Establece la lista de anuncios de tipo video.
     * 
     * @param anunciosVideo Lista de anuncios de tipo video
     */
    public void setAnunciosVideo(List<Anuncio> anunciosVideo) {
        this.anunciosVideo = anunciosVideo;
    }

    /**
     * Obtiene la lista de anuncios de tipo imagen.
     * 
     * @return Lista de anuncios de tipo imagen
     */
    public List<Anuncio> getAnunciosImagen() {
        return anunciosImagen;
    }

    /**
     * Establece la lista de anuncios de tipo imagen.
     * 
     * @param anunciosImagen Lista de anuncios de tipo imagen
     */
    public void setAnunciosImagen(List<Anuncio> anunciosImagen) {
        this.anunciosImagen = anunciosImagen;
    }

    /**
     * Obtiene la lista de direcciones asociadas a los anuncios.
     * 
     * @return Lista de direcciones asociadas a los anuncios
     */
    public List<String> getDirecciones() {
        return direcciones;
    }

    /**
     * Establece la lista de direcciones asociadas a los anuncios.
     * 
     * @param direcciones Lista de direcciones asociadas a los anuncios
     */
    public void setDirecciones(List<String> direcciones) {
        this.direcciones = direcciones;
    }

    /**
     * Obtiene la lista de imágenes asociadas a los anuncios.
     * 
     * @return Lista de imágenes asociadas a los anuncios
     */
    public List<String> getImagenes() {
        return imagenes;
    }

    /**
     * Establece la lista de imágenes asociadas a los anuncios.
     * 
     * @param imagenes Lista de imágenes asociadas a los anuncios
     */
    public void setImagenes(List<String> imagenes) {
        this.imagenes = imagenes;
    }
}
