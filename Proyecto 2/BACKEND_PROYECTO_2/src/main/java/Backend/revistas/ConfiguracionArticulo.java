package Backend.revistas;

import JPA.Articulo;
import JPA.Controladora;
import com.google.gson.Gson;
import respuetas.Editor.RespuestaArticulo;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Clase que maneja la configuración y procesamiento de un artículo.
 * 
 * Esta clase incluye funcionalidades para procesar un artículo, validarlo, 
 * guardar los archivos asociados (imagen y PDF), y gestionar su almacenamiento 
 * en el sistema de archivos y en la base de datos.
 * 
 * @author carlosrodriguez
 */
public class ConfiguracionArticulo {

    private RespuestaArticulo respuesta = new RespuestaArticulo();
    private Controladora controladora = new Controladora();

    /**
     * Proceso principal para manejar un artículo.
     * 
     * Este método valida la información del artículo, guarda los archivos asociados
     * (imagen y PDF) y almacena los datos en la base de datos.
     * 
     * @param imagen InputStream que contiene la imagen del artículo (puede ser null).
     * @param pdf InputStream que contiene el PDF del artículo (no puede ser null).
     * @param Articulo String JSON que representa el artículo a procesar.
     * @return RespuestaArticulo objeto que contiene el resultado del procesamiento.
     * @throws IOException si ocurre un error de entrada/salida durante el proceso.
     * @throws Exception si ocurre algún error no esperado.
     */
    public RespuestaArticulo proceso(InputStream imagen, InputStream pdf, String Articulo) throws IOException, Exception {

        try {
            // Convertir el JSON del artículo a un objeto Articulo
            Articulo articulo = ObtenerArticuloObjeto(Articulo);
            byte[] imagenBytes = null;
            byte[] pdfBytes = null;

            // Convertir los InputStream a byte arrays
            if (imagen != null) {
                imagenBytes = convertirInputStreamAByteArray(imagen);
            }
            if (pdf != null) {
                pdfBytes = convertirInputStreamAByteArray(pdf);
            } else {
                respuesta.setMensaje("Seleccione un archivo válido");
                respuesta.setProcesoExitoso(false);
                return respuesta;
            }

            // Validaciones del artículo
            if (!validarNombre(articulo)) {
                respuesta.setMensaje("Ingrese un nombre mayor a 2 caracteres y menor a 32 caracteres");
                respuesta.setProcesoExitoso(false);
                return respuesta;
            } else if (!validarDescripcion(articulo)) {
                respuesta.setMensaje("Ingrese una descripción mayor a 5 caracteres y menor a 1500 caracteres");
                respuesta.setProcesoExitoso(false);
                return respuesta;
            } else if (!validarFecha(articulo)) {
                respuesta.setMensaje("Ingrese una fecha válida");
                respuesta.setProcesoExitoso(false);
                return respuesta;
            } else if (pdfBytes == null) {
                respuesta.setMensaje("Cargue el artículo por favor");
                respuesta.setProcesoExitoso(false);
                return respuesta;
            } else if (!validarRevista(articulo)) {
                respuesta.setMensaje("Seleccione una revista válida");
                respuesta.setProcesoExitoso(false);
                return respuesta;
            }

            // Guardar los archivos asociados (imagen y PDF)
            if (imagenBytes != null) {
                guardarArchivo(new ByteArrayInputStream(imagenBytes), articulo, "imagen");
            }

            if (pdfBytes != null) {
                guardarArchivo(new ByteArrayInputStream(pdfBytes), articulo, "pdf");
            }

            // Guardar el artículo en la base de datos
            guardarArticulo(articulo);
            respuesta.setMensaje("Artículo subido con éxito");
            return respuesta;

        } catch (Exception e) {
            respuesta.setMensaje("¡Oops! Ocurrió un error");
            return respuesta;
        }
    }

    /**
     * Guarda un archivo en el sistema de archivos.
     * 
     * Este método guarda una imagen o un PDF relacionado con el artículo
     * en una ubicación determinada, creando un nombre único si el archivo ya existe.
     * 
     * @param archivo InputStream que representa el archivo a guardar.
     * @param articulo El artículo relacionado con el archivo.
     * @param tipoArchivo Tipo de archivo ("imagen" o "pdf").
     * @return true si el archivo fue guardado correctamente, false si hubo un error.
     */
    public boolean guardarArchivo(InputStream archivo, Articulo articulo, String tipoArchivo) {
        String nombreArchivo = articulo.getNombre() + articulo.getIdRevista();
        FileOutputStream fos = null;

        try {
            // Asegurarse de que el nombre del archivo tenga la extensión correcta
            if ("pdf".equalsIgnoreCase(tipoArchivo) && !nombreArchivo.toLowerCase().endsWith(".pdf")) {
                nombreArchivo += ".pdf";
            } else if ("imagen".equalsIgnoreCase(tipoArchivo) && !nombreArchivo.toLowerCase().endsWith(".png")) {
                nombreArchivo += ".png";
            }

            // Obtener la ruta del directorio de Documentos del usuario
            String rutaDocumentos = System.getProperty("user.home") + File.separator + "Documentos/Proyecto 2/Proyecto 2/BACKEND_PROYECTO_2/src/main/resources/articulos";

            // Verificar si el archivo ya existe y agregar sufijo si es necesario
            File archivoDestino = new File(rutaDocumentos, nombreArchivo);
            int contador = 1;
            while (archivoDestino.exists()) {
                String nombreBase = nombreArchivo.substring(0, nombreArchivo.lastIndexOf('.'));
                String extension = nombreArchivo.substring(nombreArchivo.lastIndexOf('.'));
                archivoDestino = new File(rutaDocumentos, nombreBase + "_" + contador + extension);
                contador++;
            }

            // Crear el FileOutputStream para el archivo destino
            fos = new FileOutputStream(archivoDestino);

            // Leer desde el InputStream y escribir en el FileOutputStream
            byte[] buffer = new byte[1024];
            int bytesLeidos;
            while ((bytesLeidos = archivo.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesLeidos);
            }

            // Establecer la ruta en la propiedad correspondiente
            if ("pdf".equalsIgnoreCase(tipoArchivo)) {
                articulo.setRutaPDF(archivoDestino.getAbsolutePath()); // Ruta para PDF
            } else if ("imagen".equalsIgnoreCase(tipoArchivo)) {
                articulo.setRutaImagen(archivoDestino.getAbsolutePath()); // Ruta para Imagen
            }

            return true; // El archivo se ha guardado correctamente
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Fallo al guardar el archivo
        } finally {
            // Cerrar el FileOutputStream
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Convierte un arreglo de bytes (PDF) en un archivo físico.
     * 
     * @param pdfBytes El arreglo de bytes que representa el archivo PDF.
     * @param outputPath La ruta donde se guardará el archivo PDF.
     * @throws IOException si ocurre un error durante la escritura del archivo.
     */
    public static void convertirByteArrayAPdf(byte[] pdfBytes, String outputPath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(outputPath)) {
            fos.write(pdfBytes);
            fos.flush(); // Asegúrate de que todos los datos se escriban en el archivo.
        } catch (IOException e) {
            throw new IOException("Error al escribir el archivo PDF: " + e.getMessage(), e);
        }
    }

    /**
     * Convierte un InputStream a un arreglo de bytes.
     * 
     * @param inputStream El InputStream a convertir.
     * @return El arreglo de bytes resultante.
     * @throws IOException si ocurre un error durante la conversión.
     */
    private byte[] convertirInputStreamAByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, bytesRead);
        }
        return buffer.toByteArray();
    }

    /**
     * Convierte un string JSON a un objeto Articulo.
     * 
     * @param articulo El string JSON que representa un artículo.
     * @return El objeto Articulo convertido.
     * @throws IOException si ocurre un error durante la conversión.
     */
    public Articulo ObtenerArticuloObjeto(String articulo) throws IOException {
        Gson gson = new Gson();
        return gson.fromJson(articulo, Articulo.class);
    }

    /**
     * Valida que el nombre del artículo tenga entre 2 y 32 caracteres.
     * 
     * @param articulo El artículo cuyo nombre se valida.
     * @return true si el nombre es válido, false si no lo es.
     */
    private boolean validarNombre(Articulo articulo) {
        String nombre = articulo.getNombre();
        return nombre != null && nombre.length() > 2 && nombre.length() < 32;
    }

    /**
     * Valida que la descripción del artículo tenga entre 5 y 1500 caracteres.
     * 
     * @param articulo El artículo cuya descripción se valida.
     * @return true si la descripción es válida, false si no lo es.
     */
    private boolean validarDescripcion(Articulo articulo) {
        String descripcion = articulo.getDescripcion();
        return descripcion != null && descripcion.length() > 5 && descripcion.length() < 1500;
    }

    /**
     * Valida que la fecha de creación del artículo tenga el formato correcto (yyyy-MM-dd).
     * 
     * @param articulo El artículo cuya fecha de creación se valida.
     * @return true si la fecha es válida, false si no lo es.
     */
    private boolean validarFecha(Articulo articulo) {
        return articulo.getFechaCreacionTexto() != null && 
               articulo.getFechaCreacionTexto().matches("\\d{4}-\\d{2}-\\d{2}");
    }

    /**
     * Guarda el artículo en la base de datos.
     * 
     * @param articulo El artículo a guardar en la base de datos.
     */
    private void guardarArticulo(Articulo articulo) {
        generarFechaPublicacion(articulo);
        controladora.guardarArticulo(articulo);
    }

    /**
     * Genera la fecha de publicación para el artículo a partir de su fecha de creación.
     * 
     * @param articulo El artículo al que se le asignará la fecha de publicación.
     */
    private void generarFechaPublicacion(Articulo articulo) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaLocalDate = LocalDate.parse(articulo.getFechaCreacionTexto(), formato);
        Date fechaPublicacion = java.sql.Date.valueOf(fechaLocalDate);
        articulo.setFecha(fechaPublicacion);
    }

    /**
     * Valida que el artículo esté asociado a una revista existente.
     * 
     * @param articulo El artículo cuyo ID de revista se valida.
     * @return true si la revista existe, false si no existe.
     */
    private boolean validarRevista(Articulo articulo) {
        return controladora.buscarRevista(articulo.getIdRevista());
    }
}
