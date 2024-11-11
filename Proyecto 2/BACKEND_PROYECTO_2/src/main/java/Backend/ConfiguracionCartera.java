package Backend;

import JPA.Cartera;
import JPA.Controladora;
import JPA.Usuario;
import com.google.gson.Gson;
import java.io.IOException;

/**
 * Esta clase maneja la configuración de la cartera de un usuario, incluyendo la obtención
 * del saldo actual y la recarga del saldo.
 * 
 * @author carlosrodriguez
 */
public class ConfiguracionCartera {

    private final Controladora controladora = new Controladora();
    private String idCartera = "";
    private double saldo = 0;

    /**
     * Obtiene el saldo y la ID de la cartera de un usuario.
     * 
     * @param usuario El JSON que representa al usuario.
     * @return Una cadena JSON con la ID de la cartera y el saldo actual.
     */
    public String proceso(String usuario) {
        this.idCartera = obtenerIdCartera(usuario);
        this.saldo = obtenerSaldo(idCartera);

        return String.format("{\"cartera\": \"%s\", \"saldo\": \"%f\"}", this.idCartera, this.saldo);
    }

    /**
     * Obtiene la ID de la cartera de un usuario dado.
     * 
     * @param usuario El usuario del que se obtendrá la ID de la cartera.
     * @return La ID de la cartera del usuario.
     */
    private String obtenerIdCartera(String usuario) {
        return controladora.obtenerCartera(usuario);
    }

    /**
     * Obtiene el saldo de una cartera dada su ID.
     * 
     * @param idCartera La ID de la cartera de la que se obtiene el saldo.
     * @return El saldo de la cartera.
     */
    private double obtenerSaldo(String idCartera) {
        return controladora.obtenerSaldo(idCartera);
    }

    /**
     * Recarga el saldo de la cartera de un usuario.
     * 
     * @param usuarioJson El JSON que representa al usuario.
     * @param saldo El monto a recargar en la cartera.
     * @return Una cadena JSON con el mensaje del resultado de la operación.
     * @throws IOException Si ocurre un error durante el procesamiento.
     * @throws Exception Si ocurre un error en el proceso de actualización de la cartera.
     */
    public String recargarSaldo(String usuarioJson, String saldo) throws IOException, Exception {
        String mensaje = "";
        double saldoRecarga = 0;

        // Validar el saldo de recarga
        if (validarRecarga(saldo)) {
            saldoRecarga = Double.parseDouble(saldo);
            Usuario usuario = obtenerUsuarioObjeto(usuarioJson);

            // Obtener el usuario y su cartera completa
            usuario = controladora.obetenerUsuario(usuario);
            Cartera cartera = controladora.obtenerCarteraCompleta(usuario.getIdCartera());

            // Actualizar el saldo de la cartera
            cartera.setSaldo(cartera.getSaldo() + saldoRecarga);

            if (!controladora.actualizarCarteraCompleta(cartera)) {
                mensaje = "Ups, algo salió mal. Intenta más tarde.";
            } else {
                mensaje = String.format("Se acreditó a tu cartera %.2f", saldoRecarga);
            }
        } else {
            mensaje = "Verifica la cantidad ingresada, no es válida.";
        }

        return String.format("{\"mensaje\": \"%s\"}", mensaje);
    }

    /**
     * Convierte el JSON de un usuario a un objeto Usuario.
     * 
     * @param usuario El JSON que representa al usuario.
     * @return El objeto Usuario correspondiente.
     * @throws IOException Si ocurre un error durante la conversión.
     */
    public Usuario obtenerUsuarioObjeto(String usuario) throws IOException {
        Gson gson = new Gson();
        return gson.fromJson(usuario, Usuario.class);
    }

    /**
     * Valida que el saldo de recarga sea un número válido y positivo.
     * 
     * @param saldoRecarga El saldo de recarga en formato de cadena.
     * @return True si el saldo es válido, de lo contrario false.
     */
    private boolean validarRecarga(String saldoRecarga) {
        // Verificar si el saldoRecarga es nulo o vacío
        if (saldoRecarga == null || saldoRecarga.isEmpty()) {
            return false; // Retorna falso si es nulo o vacío
        }

        // Validar utilizando una expresión regular que permita decimales
        if (!saldoRecarga.matches("^[0-9]*\\.?[0-9]+$")) {
            return false; // Retorna falso si no coincide con el patrón
        }

        // Intentar convertir el saldoRecarga a un número y verificar que sea mayor que 0
        try {
            double saldo = Double.parseDouble(saldoRecarga);
            return saldo >= 0; // Retorna true si es mayor o igual a 0
        } catch (NumberFormatException e) {
            return false; // Retorna falso si la conversión falla
        }
    }
}
