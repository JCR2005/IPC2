package Backend.administracion;

import JPA.Controladora;
import JPA.Revista;
import respuetas.RespuestaRevistaAdministracion;

/**
 *
 * @author carlosrodriguez
 */
public class ActualizarCostoAsociado {

    private Controladora controladora = new Controladora();

    public RespuestaRevistaAdministracion actualizacionCostoAsociado(String idRevista, String costoAsociado) {

        RespuestaRevistaAdministracion respuesta = new RespuestaRevistaAdministracion();
         respuesta.setMensaje("Se actulizo el costo de  la revista exitosamente");
        try {

            Revista revista = this.controladora.obtenerRevista(idRevista);

            if (revista == null) {
                respuesta.setMensaje("hubo un problema, al parecer esta revista no existe.");
                return respuesta;
            }

            if (!esNumeroValido(costoAsociado)) {
                respuesta.setMensaje("ingrese un costo de ocultacion valido");
                 return respuesta;

            }

            if (!esDistintoCosto(Double.parseDouble(costoAsociado), revista)) {
                respuesta.setCambiosHechos(false);
                respuesta.setMensaje("no se realizo ningun cambio");
                 return respuesta;
            }

            revista.setCostoAsociado(Double.parseDouble(costoAsociado));
            respuesta.setActualizacionCostoAsociadoExitoso(true);
           
            this.controladora.editarRevista(revista);

        } catch (Exception e) {
             respuesta.setActualizacionCostoAsociadoExitoso(false);
            respuesta.setMensaje("Ha ocurrido un error: "); // Incluir el mensaje del error
        }

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

        return costo != revista.getCostoAsociado();
    }
}
