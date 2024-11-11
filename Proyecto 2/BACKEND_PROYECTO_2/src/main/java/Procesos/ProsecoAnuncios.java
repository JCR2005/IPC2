package Procesos;

import JPA.Anuncio;
import JPA.Controladora;
import JPA.HistorialEfectividadAnuncios;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import respuetas.RespuestaProcesoAnuncios;

/**
 * Clase que maneja el procesamiento de los anuncios.
 * Se encarga de filtrar anuncios por su vigencia y tipo (texto, video, imagen),
 * y retornar los resultados procesados.
 * 
 * @author carlosrodriguez
 */
public class ProsecoAnuncios {

    // Respuesta que se generará y devolverá con los anuncios procesados
    private final RespuestaProcesoAnuncios respuesta = new RespuestaProcesoAnuncios();
    
    // Controladora para interactuar con la base de datos
    private final Controladora controladora = new Controladora();
    
    // Lista de todos los anuncios obtenidos de la base de datos
    private final List<Anuncio> anuncios = controladora.obtenerAlumnos();
    
    // Lista de anuncios filtrados por vigencia
    private final List<Anuncio> anunciosfiltrado = filtraPorVigencia();

    /**
     * Obtiene los anuncios de tipo "texto" que están vigentes.
     * 
     * @return RespuestaProcesoAnuncios con los anuncios de tipo texto.
     */
    public RespuestaProcesoAnuncios obtenerAnunciosTexto() {

        // Filtrar anuncios de tipo "texto" y que estén activos
        List<Anuncio> anunciosTexto = anunciosfiltrado.stream()
                .filter(anuncio -> "texto".equals(anuncio.getTipoAnuncio()) && anuncio.isEstado())
                .collect(Collectors.toList());

        // Asignar la lista filtrada a la respuesta
        respuesta.setAnunciosTexto(anunciosTexto);

        return this.respuesta;
    }

    /**
     * Filtra los anuncios que están vigentes, es decir, que no han expirado.
     * Un anuncio se considera vigente si la fecha actual es antes de su fecha de expiración.
     * 
     * @return Lista de anuncios vigentes.
     */
    public List<Anuncio> filtraPorVigencia() {

        // Filtrar anuncios por vigencia y actualizar el estado si han expirado
        List<Anuncio> anunciosVigentes = anuncios.stream()
                .peek(anuncio -> {
                    // Obtener la fecha de publicación y la vigencia del anuncio
                    Date fechaPublicacion = anuncio.getFechaPublicacion();
                    int vigencia = Integer.parseInt(anuncio.getVigencia());

                    // Calcular la fecha de expiración
                    LocalDate fechaExpiracion = fechaPublicacion.toLocalDate().plusDays(vigencia);
                    LocalDate fechaActual = LocalDate.now();

                    // Si la fecha actual es posterior a la fecha de expiración, marcar el anuncio como inactivo
                    if (fechaActual.isAfter(fechaExpiracion)) {
                        try {
                            anuncio.setEstado(false);
                            controladora.actualizarAnuncio(anuncio);
                        } catch (Exception ex) {
                            Logger.getLogger(ProsecoAnuncios.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                })
                // Filtrar solo los anuncios que aún estén vigentes (estado = true)
                .filter(Anuncio::isEstado)
                .collect(Collectors.toList());
        
        return anunciosVigentes;
    }

    /**
     * Obtiene los anuncios de tipo "video" que están vigentes.
     * 
     * @return RespuestaProcesoAnuncios con los anuncios de tipo video.
     */
    public RespuestaProcesoAnuncios obtenerAnunciosVideo() {

        // Filtrar anuncios de tipo "video" que estén vigentes
        List<Anuncio> anunciosVideo = anunciosfiltrado.stream()
                .filter(anuncio -> "video".equals(anuncio.getTipoAnuncio()) && anuncio.isEstado())
                .collect(Collectors.toList());

        // Obtener los enlaces de video de los anuncios filtrados
        List<String> links = anunciosVideo.stream()
                .map(Anuncio::getAnuncioVideo) // Obtener el enlace del video
                .collect(Collectors.toList());

        // Asignar la lista de enlaces de video a la respuesta
        respuesta.setDirecciones(links); 
        respuesta.setAnunciosVideo(anunciosVideo);

        return this.respuesta;
    }

    /**
     * Obtiene los anuncios de tipo "imagen" que están vigentes.
     * Convierte las imágenes a formato Base64 para su transmisión.
     * 
     * @return RespuestaProcesoAnuncios con las imágenes en Base64.
     */
    public RespuestaProcesoAnuncios obtenerAnunciosImagenes() {
        
        // Filtrar anuncios de tipo "imagen" que estén vigentes
        List<Anuncio> anunciosImagen = anunciosfiltrado.stream()
                .filter(anuncio -> "imagen".equals(anuncio.getTipoAnuncio()) && anuncio.isEstado())
                .collect(Collectors.toList());

        // Cargar y convertir las imágenes en formato Base64
        List<BufferedImage> imagenes = anunciosImagen.stream()
                .map(anuncio -> {
                    try {
                        // Cargar la imagen desde la ruta especificada en el anuncio
                        return ImageIO.read(new File(anuncio.getRutaImagen()));
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null; // Retornar null si hay un error al cargar la imagen
                    }
                })
                .filter(imagen -> imagen != null) // Filtrar imágenes nulas en caso de error de carga
                .collect(Collectors.toList());

        // Convertir las imágenes a formato Base64
        List<String> imagenesBase64 = imagenes.stream()
                .map(imagen -> {
                    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                        // Convertir la imagen en un flujo de bytes
                        ImageIO.write(imagen, "png", baos);
                        // Codificar los bytes de la imagen en Base64
                        return Base64.getEncoder().encodeToString(baos.toByteArray());
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null; // Retornar null si hay un error en la conversión
                    }
                })
                .filter(base64 -> base64 != null) // Filtrar valores nulos
                .collect(Collectors.toList());

        // Asignar las imágenes en Base64 a la respuesta
        respuesta.setImagenes(imagenesBase64);
        respuesta.setAnunciosImagen(anunciosImagen);

        return this.respuesta;
    }

    public RespuestaProcesoAnuncios registrarHistorial(HistorialEfectividadAnuncios historialEfectividadAnuncios) {
        HistorialEfectividadAnuncios historialEfectividadAnuncio=historialEfectividadAnuncios;
        System.out.println(historialEfectividadAnuncio.getRuta());
        System.out.println(historialEfectividadAnuncio.getIdAnuncio());
         System.out.println(historialEfectividadAnuncio.getFecha());
          System.out.println(historialEfectividadAnuncio.getTipoAnuncio());
           System.out.println(historialEfectividadAnuncio.getUsuario());
           controladora.crearRegistro(historialEfectividadAnuncio);
        return  respuesta;
    }
    
    
    
}
