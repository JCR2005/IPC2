package Backend.administracion;

import JPA.Controladora;
import JPA.Revista;
import respuetas.RespuestaRevistaAdministracion;

/**
 * Clase encargada de gestionar la actualización del costo asociado a una revista específica.
 * Esta clase permite validar si el costo es válido, si ha cambiado respecto al valor actual 
 * y luego proceder con la actualización en la base de datos.
 * 
 * @author carlosrodriguez
 */
public class ActualizarCostoAsociado {

    private Controladora controladora = new Controladora();

    /**
     * Actualiza el costo asociado a una revista específica.
     * 
     * @param idRevista El identificador de la revista a la que se le actualizará el costo.
     * @param costoAsociado El nuevo costo asociado en formato de texto (String).
     * @return RespuestaRevistaAdministracion con el resultado de la actualización y el mensaje correspondiente.
     */
    public RespuestaRevistaAdministracion actualizacionCostoAsociado(String idRevista, String costoAsociado) {
        RespuestaRevistaAdministracion respuesta = new RespuestaRevistaAdministracion();
        respuesta.setMensaje("Se actualizó el costo de la revista exitosamente.");

        try {
            // Obtiene la revista desde la base de datos usando el id
            Revista revista = this.controladora.obtenerRevista(idRevista);

            // Verifica si la revista existe
            if (revista == null) {
                respuesta.setMensaje("Hubo un problema, al parecer esta revista no existe.");
                return respuesta;
            }

            // Verifica si el costo ingresado es válido
            if (!esNumeroValido(costoAsociado)) {
                respuesta.setMensaje("Ingrese un costo de ocultación válido.");
                return respuesta;
            }

            // Verifica si el costo es diferente al costo actual
            if (!esDistintoCosto(Double.parseDouble(costoAsociado), revista)) {
                respuesta.setCambiosHechos(false);
                respuesta.setMensaje("No se realizó ningún cambio.");
                return respuesta;
            }

            // Actualiza el costo asociado a la revista
            revista.setCostoAsociado(Double.parseDouble(costoAsociado));
            respuesta.setActualizacionCostoAsociadoExitoso(true);

            // Guarda la actualización en la base de datos
            this.controladora.editarRevista(revista);

        } catch (Exception e) {
            // Captura cualquier excepción y establece la respuesta con el mensaje de error
            respuesta.setActualizacionCostoAsociadoExitoso(false);
            respuesta.setMensaje("Ha ocurrido un error: " + e.getMessage()); // Incluir el mensaje del error
        }

        return respuesta;
    }

    /**
     * Verifica si la cadena ingresada puede ser convertida a un número válido mayor que cero.
     * 
     * @param cadena La cadena que representa el costo.
     * @return true si la cadena es un número válido mayor a cero, false en caso contrario.
     */
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

    /**
     * Verifica si el nuevo costo es distinto al costo actual de la revista.
     * 
     * @param costo El nuevo costo asociado.
     * @param revista La revista cuya información se va a comparar.
     * @return true si el costo es distinto al costo actual, false si es igual.
     */
    private boolean esDistintoCosto(double costo, Revista revista) {
        return costo != revista.getCostoAsociado();
    }
}
