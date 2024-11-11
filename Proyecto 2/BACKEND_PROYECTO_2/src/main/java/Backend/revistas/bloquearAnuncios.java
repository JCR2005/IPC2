package Backend.revistas;

import JPA.Controladora;
import JPA.Ingreso;
import JPA.Revista;
import JPA.bloqueoAddsRevista;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import respuetas.respuestaBloqueoDeAdds;

/**
 *
 * @author carlosrodriguez
 */
public class bloquearAnuncios {

    private Controladora controladora = new Controladora();
    private List<Revista> revistas = new ArrayList<>();

    private respuestaBloqueoDeAdds respuesta = new respuestaBloqueoDeAdds();
    private bloqueoAddsRevista revista;
    private Revista revistaEnProceso;
    private int vigencia;
    private Date fechaBloqueo;
    private String usuario;
    private double saldo;

//obtencions de revistas__________________________________________________________________________________
    public Map<String, List<?>> procesoLista(String usuario) throws Exception {
        verificarVencimiento();
        obtenerRevistas(usuario);

        // Usamos un Map para enviar múltiples arreglos
        Map<String, List<?>> response = new HashMap<>();

        response.put("listaAnuncios", this.revistas);

        return response;
    }

 private void obtenerRevistas(String usuario) {

    this.revistas = this.controladora.obtenerRevistas().stream()
            .filter(revista -> revista.getAutor().endsWith(usuario))
            .filter(revista -> revista.isAprobacion())
            .filter(revista -> {
                bloqueoAddsRevista revistaEnProceso = this.controladora.obetenerRevistaEnProcesos(revista.getIdRevista());
                return revistaEnProceso != null && revistaEnProceso.isBloqueoAddsRevista();
            })
            .collect(Collectors.toList());
}
 

   public void verificarVencimiento() throws Exception {

    List<bloqueoAddsRevista> lista = this.controladora.obtenerListaBloqueoAnuncios();

    for (int i = 0; i < lista.size(); i++) {

        // Obtener la fecha de bloqueo
        Date fechaBloqueo = lista.get(i).getFechaBloqueo();

        if (fechaBloqueo != null) {
            // Convertir java.sql.Date a LocalDate
            LocalDate fechaLocal = fechaBloqueo.toLocalDate();

            // Calcular la fecha de vencimiento sumando los días de vigencia
            LocalDate fechaVencimiento = fechaLocal.plusDays(lista.get(i).getVigencia());

            // Comparar la fecha actual con la fecha de vencimiento
            if (LocalDate.now().isAfter(fechaVencimiento)) {
                lista.get(i).setBloqueoAddsRevista(true); // Cambiar el estado a verdadero si ha vencido
            } else {
                lista.get(i).setBloqueoAddsRevista(false); // Mantener bloqueado si no ha vencido
            }

            actualizar(lista.get(i));
        } else {
            System.out.println("Fecha de bloqueo es null para el elemento en el índice: " + i);
            // Puedes realizar alguna acción adicional aquí si deseas manejar específicamente los nulos
        }
    }
}

    

    private void actualizar(bloqueoAddsRevista bloqueoAddsRevista) throws Exception {
        this.controladora.bloquearAdds(bloqueoAddsRevista);
    }

    //__________________________________________________________________________________________________
    //proceso bloqueo de adds________________________________________________________________________________
    public respuestaBloqueoDeAdds procesoBloqueoAdds(String idRevista, String fecha, String vigencia) {

        try {

            if (!validarExistenciaRevista(idRevista)) {
                this.respuesta.setExisteRevista(false);
                this.respuesta.setMensaje("La revista no existe.");
                return this.respuesta;
            }

            if (!validarNumeroEnteroMayorCero(vigencia)) {
                this.respuesta.setVigenciaValida(false);
                this.respuesta.setMensaje("ingrese una vigencia valida");
                return this.respuesta;
            }

            if (!validarFecha(fecha)) {
                this.respuesta.setFechaValida(false);
                this.respuesta.setMensaje("ingrese una fecha valida");
                return this.respuesta;
            }

            if (!validarSaldo(idRevista)) {
                this.respuesta.setSaldoValido(false);
                this.respuesta.setMensaje("saldo insuficiente, recarge su cartera");
                return this.respuesta;
            }

            bloquearAdds();
            return this.respuesta;
            // Crear mensaje de éxito
        } catch (Exception e) {
            return this.respuesta;
        }

    }

    private boolean validarExistenciaRevista(String idRevista) {
        this.revista = this.controladora.obetenerRevistaEnProcesos(idRevista);
        return this.revista != null;
    }

    private boolean validarNumeroEnteroMayorCero(String numero) {
        try {
            int num = Integer.parseInt(numero);
            this.vigencia = num;
            return num > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean validarFecha(String fecha) {
        try {
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate fechaLocalDate = LocalDate.parse(fecha, formato);
            this.fechaBloqueo = java.sql.Date.valueOf(fechaLocalDate);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean validarSaldo(String idRevista) {
        this.revistaEnProceso = this.controladora.obtenerRevista(idRevista);
        this.usuario = revistaEnProceso.getAutor();
        this.saldo = this.controladora.obtenerSaldo(this.controladora.obtenerCartera(this.usuario));
        return this.saldo >= (revistaEnProceso.getCostoOcultacion() * this.vigencia);
    }

    private void guardarRegistroPago() {
        try {
            double monto = this.revistaEnProceso.getCostoOcultacion() * this.vigencia;
            Ingreso ingreso = new Ingreso(monto, this.fechaBloqueo, this.usuario, "Bloqueo de anuncios",this.revistaEnProceso.getIdRevista());
            this.controladora.crearIngreso(ingreso);
        } catch (Exception e) {
        }

    }

    private boolean bloquearAdds() {
        try {
            guardarCambios();
            descontarSaldo();
            guardarRegistroPago();
            respuesta.setProcesoExitoso(true);
            respuesta.setMensaje("Tus suscriptores disfrutarán de " + revistaEnProceso.getTitulo() + " sin anuncios!!!");
            return true;
        } catch (Exception e) {
            try {
                devolucionSaldo();
            } catch (Exception ex) {
                respuesta.setProcesoExitoso(false);
                respuesta.setMensaje("Se ha producido un error, no se pudo realizar el reembolso correctamente. Por favor, comunícate con nuestro soporte técnico y proporciona este código.");
                Logger.getLogger(bloqueoAddsRevista.class.getName()).log(Level.SEVERE, "Error en el reembolso: ", ex);
            }
            respuesta.setProcesoExitoso(false);
            respuesta.setMensaje("Se ha producido un error, pero no te preocupes, no se ha descontado nada de tu saldo.");
            Logger.getLogger(bloqueoAddsRevista.class.getName()).log(Level.SEVERE, "Error en bloquearAdds: ", e);
            return false;
        }
    }

    private void guardarCambios() throws Exception {
        this.revista.setVigencia(this.vigencia);
        this.revista.setFechaBloqueo(this.fechaBloqueo);
        this.revista.setBloqueoAddsRevista(false);
        this.controladora.bloquearAdds(revista);
    }

    private void descontarSaldo() throws Exception {
        double nuevoSaldo = (this.saldo - (revistaEnProceso.getCostoOcultacion() * this.vigencia));

        this.controladora.actualizarCartera(this.controladora.obtenerCartera(this.usuario), nuevoSaldo);
    }

    private void devolucionSaldo() throws Exception {
        this.controladora.actualizarCartera(this.controladora.obtenerCartera(this.usuario), this.saldo);
    }

//__________________________________________________________________________________________________
}
