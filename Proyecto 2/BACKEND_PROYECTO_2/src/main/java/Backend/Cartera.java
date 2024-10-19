package Backend;

import JPA.Controladora;
import JPA.Usuario;
import com.google.gson.Gson;
import java.io.IOException;

/**
 *
 * @author carlosrodriguez
 */
public class Cartera {

    Controladora controladora = new Controladora();
    String idCartera = "";
    double saldo = 0;

    public String proceso(String usuario) {

        this.idCartera = obtenerIdCartera(usuario);
        this.saldo = obtenerSaldo(idCartera);

        return "{\"cartera\":\" " + this.idCartera + "\",\"saldo\":\" " + this.saldo + "\"}";

    }

    private String obtenerIdCartera(String usuario) {

        return controladora.obtenerCartera(usuario);

    }

    private double obtenerSaldo(String idCartera) {

        return controladora.obtenerSaldo(idCartera);

    }

    
    
    public String recargarSaldo(String usuarioJson, String saldo) throws IOException, Exception {

        String mensaje = "";
        double saldoRecarga = 0;
        if (validarRecarga(saldo)) {
            saldoRecarga = Double.parseDouble(saldo);
            Usuario usuario = ObtenerUsuarioObjeto(usuarioJson);

            usuario = controladora.obetenerUsuario(usuario);

            if (!controladora.actualizarCartera(usuario.getIdCartera(), saldoRecarga)) {
                  mensaje = "Ups, algo salio mal, intenta mas tarde";
            }else{
                   mensaje = "Se acredito a tu cartera "+saldoRecarga+"";
                    
                   }
        } else {
            mensaje = "Verifica la cantida ingresada, no es valida";

        }

        return "{\"mensaje\":\" " + mensaje + "\"}";

    }

    public Usuario ObtenerUsuarioObjeto(String usuario) throws IOException {
        Gson gson = new Gson();

        return gson.fromJson(usuario, Usuario.class);

    }

        private boolean validarRecarga(String saldoRecarga) {
        // Verifica si el saldoRecarga es nulo o vacío
        if (saldoRecarga == null || saldoRecarga.isEmpty()) {
            return false; // Retorna falso si es nulo o vacío
        }

        // Valida utilizando la expresión regular
        if (!saldoRecarga.matches("^[0-9]*\\.?[0-9]+$")) {
            return false; // Retorna falso si no coincide con el patrón
        }

        // Intenta convertir el String a un double para asegurarte de que es un número válido
        try {
            double saldo = Double.parseDouble(saldoRecarga);
            if (saldo < 0) {
                return false; // Retorna falso si es menor a 0
            }
        } catch (NumberFormatException e) {
            return false; // Retorna falso si la conversión falla
        }

        return true; // Retorna verdadero si todas las validaciones pasan
    }

}
