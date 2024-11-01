package Backend.Suscritor;

import JPA.Articulo;
import JPA.Controladora;
import JPA.Revista;
import JPA.Suscripciòn;
import JPA.Usuario;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import respuetas.Suscriptor.RespuestaVisualizaciòn;

/**
 *
 * @author carlosrodriguez
 */
public class Visualizacion {

    private RespuestaVisualizaciòn respuesta = new RespuestaVisualizaciòn();
    private Controladora controladora = new Controladora();

    public RespuestaVisualizaciòn obtenerListaDeRevistasSuscritas(String usuario) {
        try {

            obtenerRevistas(usuario);

        } catch (Exception e) {
            this.respuesta.setMensaje("Algo salio mal intenta mas tarde");
            this.respuesta.setProcesoExitoso(false);
        }

        return this.respuesta;
    }

    private void obtenerRevistas(String usuario) {

        List<Revista> revistasAprobadas = this.controladora.obtenerRevistas();
        List<Suscripciòn> suscripciòns = this.controladora.obtenerSuscripciones();

        for (int i = 0; i < revistasAprobadas.size(); i++) {
            boolean suscrito = false;
            for (int j = 0; j < suscripciòns.size(); j++) {

                System.out.println(suscripciòns.get(j).getIdRevista() + "|" + revistasAprobadas.get(i).getIdRevista());
                System.out.println(suscripciòns.get(j).getIdUsuario() + "|" + usuario);

                if (suscripciòns.get(j).getIdRevista().equals(revistasAprobadas.get(i).getIdRevista()) && suscripciòns.get(j).getIdUsuario().equals(usuario)) {
                    suscrito = true;

                }

            }
            if (suscrito && revistasAprobadas.get(i).isAprobacion()) {
                this.respuesta.getRevistas().add(revistasAprobadas.get(i));

            }

        }

        for (int i = 0; i < this.respuesta.getRevistas().size(); i++) {
            System.out.println(this.respuesta.getRevistas().get(i).getIdRevista());
        }

        if (this.respuesta.getRevistas() == null) {
            this.respuesta.setMensaje("Algo salio mal intenta mas tarde");
            this.respuesta.setProcesoExitoso(false);
        }

    }

    public RespuestaVisualizaciòn obtenerListaDeArticulos(String idRevista) {

        obtenerArticulos(idRevista);

        return this.respuesta;
    }

    private void obtenerArticulos(String idRevista) {
        List<Articulo> articulos = this.controladora.obtenerArticulos();

        for (int i = 0; i < articulos.size(); i++) {
            if (articulos.get(i).getIdRevista().equals(idRevista)) {
                this.respuesta.getArticulos().add(articulos.get(i));
            }

        }

    }

 public void cargarPDF(Articulo articulo) throws IOException {
    String rutaPDF = articulo.getRutaPDF();
    File archivoPDF = new File(rutaPDF);
    
    // Verifica si la ruta del archivo es válida
    if (!archivoPDF.exists()) {
        throw new IOException("El archivo no existe en la ruta especificada: " + rutaPDF);
    }

    try (FileInputStream fileInputStream = new FileInputStream(archivoPDF)) {
        // Lee el archivo en bytes
        byte[] pdfBytes = fileInputStream.readAllBytes();
        
        // Convierte los bytes a una cadena Base64
        String base64PDF = Base64.getEncoder().encodeToString(pdfBytes);
        articulo.setPdf(base64PDF); // Asigna la cadena Base64 al objeto Articulo
    } catch (IOException e) {
        // Manejo de excepciones
        throw new IOException("Error al leer el archivo PDF: " + e.getMessage(), e);
    }
}

    public RespuestaVisualizaciòn obtenerArticulo(String idArticulo) throws IOException {
        obtenerArticuloObjeto(idArticulo);
System.out.println("entro2");
        validarArticuloObjeto(respuesta.getArticulo());
        System.out.println("entro3");
        cargarPDF(respuesta.getArticulo());
        System.out.println("entro4" +respuesta.getArticulo().getRutaPDF());

        return respuesta;
    }

    private void obtenerArticuloObjeto(String idArticulo) {

        this.respuesta.setArticulo(this.controladora.obtenerArticulo(Long.parseLong(idArticulo)));
    }

    private void validarArticuloObjeto(Articulo articulo) {
        if (articulo == null) {
            this.respuesta.setMensaje("Al parecer ha ocurrido un error :(, si el problema persiste contacta a soporte.");
        }

    }

}
