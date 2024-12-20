package Backend.anuncios;

import JPA.Anuncio;
import JPA.Controladora;
import JPA.Ingreso;
import com.google.gson.Gson;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

import java.time.LocalDate;

import java.time.format.DateTimeFormatter;



/**
 *
 * @author carlosrodriguez
 */
public class CompraAnuncio {

    Controladora controladora = new Controladora();
    private String mensaje = "";
    private Anuncio anunicoEnProceso;
    private Date fechaInicio;

    public String proceso(InputStream imagen, String anuncio) throws IOException, Exception {
        InputStream imagenInputStream = null;
        byte[] imagenBytes = null;
        if (imagen != null) {
            imagenBytes = convertirInputStreamAByteArray(imagen); // Convertir InputStream a byte array
            imagenInputStream = new ByteArrayInputStream(imagenBytes); // Re-creamos el InputStream
        }

        String respuesta = "";
        Anuncio anuncioObjeto = new Anuncio();
        anuncioObjeto = ObtenerAnuncioObjeto(anuncio);
        System.out.println(anuncioObjeto.getPrecio()+"_________________________________________________rtgkgjbwnegbsjedvs graveeeeeeeeeeeeeeeeeeeeeeeeeeeer");

        if (!validarTipoAnuncio(anuncioObjeto)) {
            this.mensaje = "No selecciono el tipo de anuncio";
        } else if (!validarVigencia(anuncioObjeto)) {
            this.mensaje = "ingrese una vigencia valida";
        } else if (!validarFecha(anuncioObjeto)) {
            this.mensaje = "ingrese una fecha valida";
        } else if (!validarAnuncio(anuncioObjeto, imagenInputStream)) {
            this.mensaje = "No se ingreso ningun anuncio";
        } else if (!validarSaldo(anuncioObjeto)) {
            this.mensaje = "saldo insuficiente, por favor recarge";
        } else {
            anuncioObjeto.setIdAnuncio(crearIdAnuncio());
            try {
                if (anuncioObjeto.getTipoAnuncio().endsWith("imagen")) {
                    guardarImagen(new ByteArrayInputStream(imagenBytes), anuncioObjeto);
                }
                guardarAnuncio(anuncioObjeto);  // Solo se ejecutará si guardarImagen no lanza una excepción
                this.mensaje = "Se ha registrado su anuncio correctamente";
            } catch (Exception e) {
                this.mensaje = "Hubo un error al guardar la imagen del anuncio: " + e.getMessage();
                e.printStackTrace();  // O usar logging para registrar el error
            }

  
        }

        return "{\"message\":\" " + this.mensaje + "\"}";
    }

    // Método para convertir InputStream a byte array
    private byte[] convertirInputStreamAByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, bytesRead);
        }
        return buffer.toByteArray();
    }

    private void guardarAnuncio(Anuncio anuncio) throws Exception {

        this.anunicoEnProceso=anuncio;
        this.fechaInicio=anuncio.getFechaPublicacion();
        anuncio.setEstado(true);
        generarFechaPublicacion(anuncio);
        generarFechaFinalizacion(anuncio);
        cobro(anuncio.getPrecio(), controladora.obtenerCartera(anuncio.getUsuario()));
           guardarRegistroPago();
           
        controladora.crearAnuncio(anuncio);
        imprimir(anuncio);

    }
    
    private void guardarRegistroPago() {
        try {
            double monto = this.anunicoEnProceso.getPrecio();
            Ingreso ingreso = new Ingreso(monto, convertStringToSqlDate(this.anunicoEnProceso.getFechaPublicacionTexto()), this.anunicoEnProceso.getUsuario(), "Adquisicion de espacio publicitario",this.anunicoEnProceso.getIdAnuncio());
            this.controladora.crearIngreso(ingreso);
        } catch (Exception e) {
        }

    }

  public  Date convertStringToSqlDate(String dateString) {
        try {
            // Especifica el formato de fecha que se espera en el String
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            // Convierte el String a un java.util.Date
            java.util.Date utilDate = format.parse(dateString);
            // Convierte el java.util.Date a java.sql.Date
            return new Date(utilDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Manejo básico de error
        }
    }
    private void cobro(double precio, String id) throws Exception {

        double cobro = controladora.obtenerSaldo(id) - precio;

        controladora.actualizarCartera(id, cobro);
    }

    public String crearIdAnuncio() throws Exception {
        String idAdd = "";
        int contador = 0;
        boolean idGenerado = false;

        while (!idGenerado) {

            String idAnuncio = String.format("%016d", contador);

            if (!controladora.buscarAnuncio(idAnuncio)) {

                idGenerado = true;
                idAdd = idAnuncio;
            } else {
                contador++;
            }

        }

        return idAdd;

    }

    public boolean guardarImagen(InputStream imagen, Anuncio anuncio) {
        String nombreArchivo = anuncio.getIdAnuncio();
        FileOutputStream fos = null;
        try {
            // Asegurarse de que el nombre del archivo termine en .png
            if (!nombreArchivo.toLowerCase().endsWith(".png")) {
                nombreArchivo += ".png"; // Agregar la extensión .png si no está presente
            }

            // Obtener la ruta del directorio de Documentos del usuario
            String rutaDocumentos = System.getProperty("user.home") + File.separator + "Documentos/Proyecto 2/Proyecto 2/BACKEND_PROYECTO_2/src/main/resources";

            anuncio.setRutaImagen(rutaDocumentos + File.separator + nombreArchivo);
            // Crear el archivo en la ruta de Documentos
            File archivo = new File(rutaDocumentos, nombreArchivo);
            fos = new FileOutputStream(archivo);

            // Leer desde el InputStream y escribir en el FileOutputStream
            byte[] buffer = new byte[1024];
            int bytesLeidos;

            while ((bytesLeidos = imagen.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesLeidos);
            }

            return true; // La imagen se ha guardado correctamente
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Fallo al guardar la imagen
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

    public void imprimir(Anuncio anu) {
        System.out.println(anu.getIdAnuncio());
        System.out.println(anu.getUsuario());
        System.out.println(anu.getTipoAnuncio());
        System.out.println(anu.getVigencia());
        System.out.println(anu.getFechaPublicacion());
        System.out.println(anu.getFechaFinalizacion());
        System.out.println(anu.getAnuncioTexto());
        System.out.println(anu.getAnuncioVideo());
        System.out.println(anu.isEstado());
        System.out.println(anu.getPrecio());
        System.out.println(anu.getRutaImagen());

    }

    private void generarFechaPublicacion(Anuncio anuncio) {

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Corrige mm a MM
        LocalDate fechaLocalDate = LocalDate.parse(anuncio.getFechaPublicacionTexto(), formato);

        // Convertir LocalDate a Date
        Date fechaPublicacion = java.sql.Date.valueOf(fechaLocalDate);
        anuncio.setFechaPublicacion((java.sql.Date) fechaPublicacion);

    }

    private void generarFechaFinalizacion(Anuncio anuncio) {
        int vigenciaAnuncio = Integer.parseInt(anuncio.getVigencia());
        vigenciaAnuncio = vigenciaAnuncio + 1;
        // Obtener la fecha de publicación como java.sql.Date
        java.sql.Date fechaPublicacion = anuncio.getFechaPublicacion();

        // Convertir java.sql.Date a LocalDate
        LocalDate fechaLocal = fechaPublicacion.toLocalDate();

        // Sumar los días de vigencia
        LocalDate fechaFinalizacion = fechaLocal.plusDays(vigenciaAnuncio);

        // Convertir LocalDate de nuevo a java.sql.Date
        java.sql.Date fechaFinalizacionSql = java.sql.Date.valueOf(fechaFinalizacion);

        // Establecer la fecha de finalización en el anuncio
        anuncio.setFechaFinalizacion(fechaFinalizacionSql);
    }

    public Anuncio ObtenerAnuncioObjeto(String anuncio) throws IOException {
        Gson gson = new Gson();

        // Convertir el JSON a un objeto de la clase Anuncio
        Anuncio anuncioObjeto = gson.fromJson(anuncio, Anuncio.class);

        return anuncioObjeto;

    }

    public boolean validarAnuncioImagen(InputStream imagen) throws IOException {
        boolean anuncioValido = false;

        byte[] imagenBytes = null;
        if (imagen != null) {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            int bytesRead;
            while ((bytesRead = imagen.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, bytesRead);
            }
            imagenBytes = buffer.toByteArray();

            if (imagenBytes.length > 0) {
                anuncioValido = true;
            }
        }

        return anuncioValido;
    }

    private boolean validarTipoAnuncio(Anuncio anuncio) {
        boolean tipoAnuncioValido = false;

        if (!anuncio.getTipoAnuncio().isEmpty()) {

            if (anuncio.getTipoAnuncio().endsWith("texto")) {
                tipoAnuncioValido = true;
            } else if (anuncio.getTipoAnuncio().endsWith("video")) {
                tipoAnuncioValido = true;
            } else if (anuncio.getTipoAnuncio().endsWith("imagen")) {
                tipoAnuncioValido = true;
            }

        }

        return tipoAnuncioValido;
    }

    private boolean validarVigencia(Anuncio anuncio) {
        boolean tipoVigenciaValido = false;

        if (!anuncio.getVigencia().isEmpty()) {
            try {
                int vigencia = Integer.parseInt(anuncio.getVigencia());
                if (vigencia > 0) {
                    tipoVigenciaValido = true;
                }
            } catch (NumberFormatException e) {
                // No es un número válido, tipoVigenciaValido seguirá siendo false
            }
        }

        return tipoVigenciaValido;
    }

    private boolean validarFecha(Anuncio anuncio) {
        boolean tipoFechaValido = false;
        System.out.println(anuncio.getFechaPublicacionTexto());
        if (!anuncio.getFechaPublicacionTexto().isEmpty() && anuncio.getFechaPublicacionTexto().matches("\\d{4}-\\d{2}-\\d{2}")) {

            tipoFechaValido = true;

        }

        return tipoFechaValido;
    }

    private boolean validarAnuncio(Anuncio anuncio, InputStream imagen) throws IOException {

        boolean anuncioValido = false;

        if (anuncio.getTipoAnuncio().endsWith("texto")) {
            anuncioValido = ValidaAnuncioTexto(anuncio.getAnuncioTexto());

        } else if (anuncio.getTipoAnuncio().endsWith("imagen")) {
            anuncioValido = validarAnuncioImagen(imagen);
        } else if (anuncio.getTipoAnuncio().endsWith("video")) {
            anuncioValido = ValidaAnuncioVideo(anuncio.getAnuncioVideo());
        }

        return anuncioValido;

    }

    private boolean ValidaAnuncioTexto(String anuncio) {
        boolean anuncioValido = false;

        if (!anuncio.isEmpty()) {
            anuncioValido = true;
        }
        return anuncioValido;
    }

    private boolean ValidaAnuncioVideo(String anuncio) {
        boolean anuncioValido = false;

        if (!anuncio.isEmpty()) {
            anuncioValido = true;
        }
        return anuncioValido;
    }

    private boolean validarSaldo(Anuncio anuncio) throws Exception {

        String cartera = controladora.obtenerCartera(anuncio.getUsuario());

        double saldo = controladora.obtenerSaldo(cartera);

        anuncio.setPrecio(obtenerPrecio(anuncio.getTipoAnuncio(), anuncio.getVigencia()));
        if (saldo >= anuncio.getPrecio()) {
            System.out.println(anuncio.getPrecio()+"_________________________________________preciooooooooooooooooooooooooooooooooo");
            return true;

        }

        return false;
    }

    private double obtenerPrecio(String id, String vigencia) {

        int vigenciaEntera = Integer.parseInt(vigencia);

        return (controladora.obtnerPrecioAnuncio(id) * vigenciaEntera);

    }

}
