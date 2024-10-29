package Backend.administracion;

import JPA.Controladora;
import JPA.CostosGlobales;
import respuetas.RespuestaRevistaAdministracion;

/**
 *
 * @author carlosrodriguez
 */
public class CostoGlobal {

    private Controladora controladora = new Controladora();
    private RespuestaRevistaAdministracion respuesta = new RespuestaRevistaAdministracion();

    public RespuestaRevistaAdministracion obtenerCostoGlobalAsociado() {
        this.respuesta.setCostosGlobales(controladora.obtenerCostoAsociado());
        if (this.respuesta.getCostosGlobales() == null) {
            this.respuesta.setMensaje("Fallo al encontrar consto asociado");
            this.respuesta.setProcesoExitoso(false);
        }

        return this.respuesta;
    }

    public RespuestaRevistaAdministracion actualizarCostoAsociadoGlobal(String costoAsociado) {

          this.respuesta.setCostosGlobales(controladora.obtenerCostoAsociado());
        respuesta.setMensaje("Se actulizo el costo exitosamente");
        try {

            if (!esNumeroValido(costoAsociado)) {
                respuesta.setProcesoExitoso(false);
                respuesta.setMensaje("ingrese un costo asociado valido");
                return respuesta;

            }

            if (!esDistintoCosto(Double.parseDouble(costoAsociado))) {
                respuesta.setCambiosHechos(false);
                respuesta.setMensaje("no se realizo ningun cambio");
                return respuesta;
            }

            this.respuesta.getCostosGlobales().setCosto(Double.parseDouble(costoAsociado));

            this.controladora.editarCostoGlobal(this.respuesta.getCostosGlobales());

        } catch (Exception e) {
            respuesta.setProcesoExitoso(false);
            respuesta.setMensaje("Ha ocurrido un error: "); // Incluir el mensaje del error
        }

        return this.respuesta;
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

    private boolean esDistintoCosto(double costo) {

        return costo != this.respuesta.getCostosGlobales().getCosto();
    }
}
