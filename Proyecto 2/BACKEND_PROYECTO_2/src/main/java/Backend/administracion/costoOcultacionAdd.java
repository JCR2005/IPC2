package Backend.administracion;

import JPA.Controladora;
import JPA.Revista;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import respuetas.respuestaAprobacionRevista;

/**
 *
 * @author carlosrodriguez
 */
public class costoOcultacionAdd {

    private Controladora controladora = new Controladora();

    private List<Revista> revistas = new ArrayList<>();

    public Map<String, List<?>> procesoLista() {

        obtenerRevistas();

        // Usamos un Map para enviar múltiples arreglos
        Map<String, List<?>> response = new HashMap<>();

        response.put("listaAnuncios", this.revistas);

        return response;
    }

    private void obtenerRevistas() {
        List<Revista> revistas = this.controladora.obtenerRevistas();

        // Filtrar las revistas no aprobadas y asignarlas a this.revistas
        this.revistas = revistas.stream()
                .filter(revista -> revista.isAprobacion()) // Filtra las revistas no aprobadas
                .collect(Collectors.toList()); // Colecciona el resultado en una nueva lista
    }

    public respuestaAprobacionRevista procesoCambioDeEstado(String idRevista, String costoOcultacion) {
        String mensaje = "";
        respuestaAprobacionRevista respuesta = new respuestaAprobacionRevista();
        boolean aprobacionValida = true;
        try {
            // Obtener el anuncio
            Revista revista = this.controladora.obtenerRevista(idRevista);

            // Verificar si el anuncio existe
            if (revista == null) {
                respuesta.setExisteRevista(false);
                mensaje = "hubo un problema, al parecer esta revista no existe.";
                aprobacionValida = false;

            }

            if (!esNumeroValido(costoOcultacion)) {
                respuesta.setCostoOcultacion(false);
                mensaje = "ingrese un costo de ocultacion valido";
                aprobacionValida = false;

            }

            if (!esDistintoCosto(Double.parseDouble(costoOcultacion), revista)) {
                 mensaje = "no se realizo ningun cambio";
                  aprobacionValida = false;
            }

            if (aprobacionValida) {
                revista.setAprobacion(true);
                revista.setCostoOcultacion(Double.parseDouble(costoOcultacion));
                mensaje = "Se actulizo el costo de  la revista exitosamente";
                this.controladora.editarRevista(revista);

            }

        } catch (Exception e) {
            mensaje = "Ha ocurrido un error: "; // Incluir el mensaje del error
        }
        respuesta.setMensaje(mensaje);
        return respuesta;
    }

    private boolean esNumeroValido(String cadena) {
        if (cadena == null || cadena.isEmpty()) {
            return false; // Retorna false si la cadena es nula o vacía
        }

        try {
            // Intenta convertir a double
            double numero = Double.parseDouble(cadena);
            // Retorna true si el número es mayor a 0
            return numero > 0;
        } catch (NumberFormatException e) {
            return false; // Retorna false si ocurre una excepción al parsear
        }
    }

    private boolean esDistintoCosto(double costo, Revista revista) {
        
        if (costo==revista.getCostoOcultacion()){
            return false;
        }
        
        return true;
    }
}
