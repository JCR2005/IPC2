package Backend;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * La clase Procesos gestiona el procesamiento de solicitudes, autorizaciones, movimientos y cancelaciones de tarjetas.
 * Utiliza instancias de otras clases para llevar a cabo estas tareas.
 * 
 * @author carlosrodriguez
 */
public class Procesos {

    // Instancias de clases para manejar solicitudes, autorizaciones, movimientos y cancelaciones
    private Solicitud solicitud = new Solicitud();
    private Autorizacion autorizacion = new Autorizacion();
    private Movimiento movimiento = new Movimiento();
    private Cancelacion cancelacion = new Cancelacion();
    private Consulta consulta = new Consulta();

    // Variables para almacenar el estado de la solicitud
    private boolean existeSolicitud;
    private boolean aceptadaSolicitud;

    /**
     * Procesa una solicitud de tarjeta.
     * 
     * @param ns El número de solicitud
     * @param f La fecha de la solicitud
     * @param t El tipo de tarjeta
     * @param n El nombre del solicitante
     * @param s El límite de la tarjeta
     * @param d La dirección del solicitante
     */
    public void procesarSolicitud(int ns, String f, String t, String n, double s, String d) {
        solicitud.obtenerDatosSolicitudFormulario(ns, f, t, n, s, d);
        solicitud.guardarSolicitud();
    }

    /**
     * Procesa la autorización de una solicitud.
     * 
     * @param ns El número de solicitud
     */
    public void procesarAutorizacion(int ns) {
        existeSolicitud = false;
        aceptadaSolicitud = false;

        // Verifica si existe una solicitud
        existeSolicitud = autorizacion.consulta(ns);

        // Si existe, verifica si la solicitud fue aceptada
        if (existeSolicitud) {
            aceptadaSolicitud = autorizacion.aceptacion(ns);
        }
    }

    /**
     * Verifica si existe una solicitud.
     * 
     * @return true si existe una solicitud, false en caso contrario
     */
    public boolean isExisteSolicitud() {
        return existeSolicitud;
    }

    /**
     * Verifica si una solicitud fue aceptada.
     * 
     * @return true si la solicitud fue aceptada, false en caso contrario
     */
    public boolean isAceptadaSolicitud() {
        return aceptadaSolicitud;
    }

    /**
     * Obtiene la fecha actual en formato yyyy-MM-dd.
     * 
     * @return La fecha actual formateada como una cadena
     */
    public String fechaActual() {
        LocalDate fechaActual = LocalDate.now();

        // Formatear la fecha en formato yyyy-MM-dd
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return fechaActual.format(formatter);
    }

    /**
     * Procesa un movimiento de tarjeta.
     * 
     * @param nt El número de tarjeta
     * @param f La fecha del movimiento
     * @param e El establecimiento donde se realizó el movimiento
     * @param d La descripción del movimiento
     * @param m El monto del movimiento
     * @param tm El tipo de movimiento
     */
    public void procesarMovimiento(String nt, String f, String e, String d, double m, String tm) {
        movimiento.obtenerDatosMovimientoFormulario(nt, f, e, d, m, tm);
        movimiento.guardarMovimiento();
    }

    /**
     * Procesa la cancelación de una tarjeta.
     * 
     * @param nt El número de tarjeta
     */
    public void procesarCancelacion(String nt) {
        cancelacion.recibirDatos(nt);
        cancelacion.desactivarTarjeta();
    }

    /**
     * Realiza una consulta sobre una tarjeta.
     * 
     * @param nt El número de tarjeta
     */
    public void consulta(String nt) {
        consulta.obtenerNumeroDeTarjeta(nt);
        consulta.validarTarjeta();
    }
}
