package Backend.Suscritor;

import JPA.Articulo;
import JPA.Controladora;
import JPA.Revista;
import JPA.Suscripciòn;
import JPA.bloqueoAddsRevista;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;
import respuetas.Suscriptor.RespuestaVisualizaciòn;

/**
 * Clase encargada de gestionar la visualización de revistas y artículos para el suscriptor.
 * Incluye la obtención de revistas suscritas, artículos dentro de las revistas y la carga de archivos PDF.
 * 
 * <p>Esta clase también maneja la validación de suscripciones, la carga de PDFs en formato Base64 y
 * la verificación de bloqueos de anuncios asociados a cada revista.</p>
 * 
 * @author carlosrodriguez
 */
public class Visualizacion {

    private RespuestaVisualizaciòn respuesta = new RespuestaVisualizaciòn();
    private Controladora controladora = new Controladora();

    /**
     * Obtiene la lista de revistas suscritas por un usuario específico.
     * 
     * @param usuario El identificador del usuario suscriptor.
     * @return RespuestaVisualizacion con la lista de revistas suscritas.
     */
    public RespuestaVisualizaciòn obtenerListaDeRevistasSuscritas(String usuario) {
        try {
            obtenerRevistas(usuario);
        } catch (Exception e) {
            this.respuesta.setMensaje("Algo salió mal, intenta más tarde.");
            this.respuesta.setProcesoExitoso(false);
        }
        return this.respuesta;
    }

    /**
     * Obtiene las revistas a las cuales un usuario está suscrito, validando que la revista esté aprobada.
     * 
     * @param usuario El identificador del usuario suscriptor.
     */
    private void obtenerRevistas(String usuario) {
        List<Revista> revistasAprobadas = this.controladora.obtenerRevistas();
        List<Suscripciòn> suscripciòns = this.controladora.obtenerSuscripciones();

        // Iteramos sobre las revistas aprobadas y suscripciones
        for (int i = 0; i < revistasAprobadas.size(); i++) {
            boolean suscrito = false;

            for (int j = 0; j < suscripciòns.size(); j++) {
                if (suscripciòns.get(j).getIdRevista().equals(revistasAprobadas.get(i).getIdRevista()) &&
                    suscripciòns.get(j).getIdUsuario().equals(usuario)) {
                    suscrito = true;
                }
            }

            // Si está suscrito y la revista está aprobada, la añadimos a la respuesta
            if (suscrito && revistasAprobadas.get(i).isAprobacion()) {
                this.respuesta.getRevistas().add(revistasAprobadas.get(i));
            }
        }

        // Si no se encontraron revistas, se indica un error
        if (this.respuesta.getRevistas().isEmpty()) {
            this.respuesta.setMensaje("Algo salió mal, intenta más tarde.");
            this.respuesta.setProcesoExitoso(false);
        }
    }

    /**
     * Obtiene la lista de artículos de una revista específica.
     * 
     * @param idRevista El identificador de la revista.
     * @return RespuestaVisualizacion con la lista de artículos.
     * @throws Exception Si ocurre un error al obtener los artículos.
     */
    public RespuestaVisualizaciòn obtenerListaDeArticulos(String idRevista) throws Exception {
        obtenerArticulos(idRevista);
        obtenerRevista(idRevista);
        return this.respuesta;
    }

    /**
     * Obtiene los artículos de una revista.
     * 
     * @param idRevista El identificador de la revista.
     */
    private void obtenerArticulos(String idRevista) {
        List<Articulo> articulos = this.controladora.obtenerArticulos();

        // Iteramos sobre los artículos para seleccionar los que pertenecen a la revista
        for (Articulo articulo : articulos) {
            if (articulo.getIdRevista().equals(idRevista)) {
                this.respuesta.getArticulos().add(articulo);
            }
        }
    }

    /**
     * Carga un archivo PDF en formato Base64 desde el sistema de archivos.
     * 
     * @param articulo El artículo que contiene la ruta al archivo PDF.
     * @throws IOException Si ocurre un error al leer el archivo PDF.
     */
    public void cargarPDF(Articulo articulo) throws IOException {
        String rutaPDF = articulo.getRutaPDF();
        File archivoPDF = new File(rutaPDF);

        // Verifica si el archivo PDF existe
        if (!archivoPDF.exists()) {
            throw new IOException("El archivo no existe en la ruta especificada: " + rutaPDF);
        }

        try (FileInputStream fileInputStream = new FileInputStream(archivoPDF)) {
            // Lee el archivo y lo convierte a Base64
            byte[] pdfBytes = fileInputStream.readAllBytes();
            String base64PDF = Base64.getEncoder().encodeToString(pdfBytes);
            articulo.setPdf(base64PDF); // Asigna la cadena Base64 al artículo
        } catch (IOException e) {
            // Manejo de excepciones al leer el archivo
            throw new IOException("Error al leer el archivo PDF: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene un artículo específico a partir de su identificador.
     * 
     * @param idArticulo El identificador del artículo.
     * @return RespuestaVisualizacion con el artículo y su contenido en formato Base64.
     * @throws IOException Si ocurre un error al obtener el artículo o cargar su PDF.
     */
    public RespuestaVisualizaciòn obtenerArticulo(String idArticulo) throws IOException {
        obtenerArticuloObjeto(idArticulo);
        validarArticuloObjeto(respuesta.getArticulo());
        cargarPDF(respuesta.getArticulo());
        return respuesta;
    }

    /**
     * Obtiene el objeto Artículo a partir de su identificador.
     * 
     * @param idArticulo El identificador del artículo.
     */
    private void obtenerArticuloObjeto(String idArticulo) {
        this.respuesta.setArticulo(this.controladora.obtenerArticulo(Long.parseLong(idArticulo)));
    }

    /**
     * Valida si el artículo obtenido es nulo. Si es nulo, se configura un mensaje de error.
     * 
     * @param articulo El artículo a validar.
     */
    private void validarArticuloObjeto(Articulo articulo) {
        if (articulo == null) {
            this.respuesta.setMensaje("Al parecer ha ocurrido un error :(, si el problema persiste contacta a soporte.");
        }
    }

    /**
     * Obtiene la información de la revista a partir de su identificador y verifica los bloqueos de anuncios.
     * 
     * @param idRevista El identificador de la revista.
     * @throws Exception Si ocurre un error al obtener la revista o procesar los bloqueos.
     */
    private void obtenerRevista(String idRevista) throws Exception {
        try {
            this.respuesta.setRevista(this.controladora.obtenerRevista(idRevista));

            // Si no se encuentra la revista, se lanza una excepción
            if (this.respuesta.getRevista() == null) {
                throw new Exception("Revista no encontrada con el ID: " + idRevista);
            }

            // Obtiene los bloqueos de anuncios de la revista
            List<bloqueoAddsRevista> bloques = this.controladora.obtenerListaBloqueoAnuncios();

            if (bloques == null || bloques.isEmpty()) {
                throw new Exception("No se encontraron bloqueos de anuncios.");
            }

            Date fechaActual = new Date(System.currentTimeMillis());
            List<bloqueoAddsRevista> bloqueosNoExpirados = new ArrayList<>();

            // Procesa cada bloqueo y verifica su vigencia
            for (bloqueoAddsRevista bloqueo : bloques) {
                try {
                    Date fechaBloqueo = bloqueo.getFechaBloqueo();
                    int vigencia = bloqueo.getVigencia();

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(fechaBloqueo);
                    calendar.add(Calendar.DAY_OF_MONTH, vigencia);
                    Date fechaLimite = new Date(calendar.getTimeInMillis());

                    if (fechaLimite.before(fechaActual)) {
                        bloqueo.setBloqueoAddsRevista(false); // Bloqueo expirado
                    } else {
                        bloqueosNoExpirados.add(bloqueo); // Bloqueo no expirado
                    }

                    // Actualiza el bloqueo
                    this.controladora.actualizarBloqueoAdds(bloqueo);

                } catch (Exception e) {
                    // Manejo de errores durante el procesamiento de bloqueos
                    System.err.println("Error al procesar bloqueo: " + e.getMessage());
                    continue;
                }
            }

            // Verifica si existe un bloqueo de anuncios para la revista específica
            boolean encontrado = false;
            for (bloqueoAddsRevista bloqueo : bloqueosNoExpirados) {
                if (bloqueo.getIdRevista().equals(idRevista)) {
                    respuesta.setAnuncios(bloqueo.isBloqueoAddsRevista());
                    encontrado = true;
                    break;
                }
            }

            // Si no se encuentra el bloqueo, se asigna un valor predeterminado
            if (!encontrado) {
                respuesta.setAnuncios(false);
            }

        } catch (Exception e) {
            // Manejo de errores al obtener la revista
            System.err.println("Error al obtener la revista: " + e.getMessage());
            throw new Exception("Hubo un problema al obtener la revista o los bloqueos.");
        }
    }
}
