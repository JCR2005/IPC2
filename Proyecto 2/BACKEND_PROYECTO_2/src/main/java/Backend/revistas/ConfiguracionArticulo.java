package Backend.revistas;

import JPA.Articulo;
import JPA.Controladora;
import java.io.ByteArrayInputStream;
import com.google.gson.Gson;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import respuetas.Editor.RespuestaArticulo;

/**
 *
 * @author carlosrodriguez
 */
public class ConfiguracionArticulo {

    RespuestaArticulo respuesta = new RespuestaArticulo();
    Controladora controladora = new Controladora();

    public RespuestaArticulo proceso(InputStream imagen, InputStream pdf, String Articulo) throws IOException, Exception {

        try {
            Articulo articulo = new Articulo();
            articulo = ObtenerArticuloObjeto(Articulo);
            byte[] imagenBytes = null;
            byte[] pdfBytes = null;

            if (imagen != null) {
                imagenBytes = convertirInputStreamAByteArray(imagen); // Convertir InputStream a byte array
            }
            if (pdf != null) {
                pdfBytes = convertirInputStreamAByteArray(pdf); // Convertir InputStream a byte array
            } else {
                this.respuesta.setMensaje("Seleccione un archivo valido");
                this.respuesta.setProcesoExitoso(false);
                return respuesta;
            }

            if (!validarNombre(articulo)) {
                this.respuesta.setMensaje("Ingrese un nombre mayor a 2 caracteres y menor a 32 caracteres");
                this.respuesta.setProcesoExitoso(false);
                return respuesta;
            } else if (!validarDescripcion(articulo)) {
                this.respuesta.setMensaje("Ingrese una descripcion mayor a 5 caracteres y menor a 1500 caracteres");
                this.respuesta.setProcesoExitoso(false);
                return respuesta;
            } else if (!validarFecha(articulo)) {
                this.respuesta.setMensaje("Ingrese una fecha valida");
                this.respuesta.setProcesoExitoso(false);
                return respuesta;
            } else if (pdfBytes == null) {
                this.respuesta.setMensaje("carge el articulo por favor");
                this.respuesta.setProcesoExitoso(false);
                return respuesta;
            }

            if (imagenBytes != null) {
                guardarArchivo(new ByteArrayInputStream(imagenBytes), articulo, "imagen");
            }

            if (pdfBytes != null) {
                guardarArchivo(new ByteArrayInputStream(pdfBytes), articulo, "pdf");
            }

            guardarArticulo(articulo);
            this.respuesta.setMensaje("articulo Subido con Exito");
            return respuesta;

        } catch (Exception e) {
            this.respuesta.setMensaje("oops! ocurrio un error");
            return respuesta;
        }

    }

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
    
    public static void convertirByteArrayAPdf(byte[] pdfBytes, String outputPath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(outputPath)) {
            fos.write(pdfBytes);
            fos.flush(); // Asegúrate de que todos los datos se escriban en el archivo.
        } catch (IOException e) {
            throw new IOException("Error al escribir el archivo PDF: " + e.getMessage(), e);
        }
    }

    private byte[] convertirInputStreamAByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, bytesRead);
        }
        return buffer.toByteArray();
    }

    public Articulo ObtenerArticuloObjeto(String articulo) throws IOException {
        Gson gson = new Gson();

        // Convertir el JSON a un objeto de la clase Anuncio
        Articulo anuncioObjeto = gson.fromJson(articulo, Articulo.class);

        return anuncioObjeto;

    }

    private boolean validarNombre(Articulo articulo) {

        String nombre = articulo.getNombre();

        if (nombre != null && nombre.length() > 2 && nombre.length() < 32) {
            return true; // El nombre es válido
        } else {
            return false; // El nombre no es válido
        }
    }

    private boolean validarDescripcion(Articulo articulo) {

        String descripcion = articulo.getDescripcion();

        if (descripcion != null && descripcion.length() > 10 && descripcion.length() < 1500) {
            return true; // El nombre es válido
        } else {
            return false; // El nombre no es válido
        }
    }

    private boolean validarFecha(Articulo articulo) {
        boolean tipoFechaValido = false;
        System.out.println(articulo.getFechaCreacionTexto());
        if (!articulo.getFechaCreacionTexto().isEmpty() && articulo.getFechaCreacionTexto().matches("\\d{4}-\\d{2}-\\d{2}")) {

            tipoFechaValido = true;

        }

        return tipoFechaValido;
    }

    private void guardarArticulo(Articulo articulo) {
        generarFechaPublicacion(articulo);
        this.controladora.guardarArticulo(articulo);

    }

    private void generarFechaPublicacion(Articulo articulo) {

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Corrige mm a MM
        LocalDate fechaLocalDate = LocalDate.parse(articulo.getFechaCreacionTexto(), formato);

        // Convertir LocalDate a Date
        Date fechaPublicacion = java.sql.Date.valueOf(fechaLocalDate);
        articulo.setFecha((java.sql.Date) fechaPublicacion);

    }

}
