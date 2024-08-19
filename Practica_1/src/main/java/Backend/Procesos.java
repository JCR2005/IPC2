package Backend;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * La clase {@code Procesos} gestiona el procesamiento de solicitudes, autorizaciones,
 * movimientos y cancelaciones de tarjetas. Utiliza instancias de otras clases para
 * llevar a cabo estas tareas.
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
    private Listado_De_Tarjetas lt = new Listado_De_Tarjetas();
    private Listado_De_Solicitudes ls = new Listado_De_Solicitudes();
    private Listado_De_Estado_Cuenta lec = new Listado_De_Estado_Cuenta();
    private Archivo_Esntrada ae = new Archivo_Esntrada();

    // Variables para almacenar el estado de la solicitud
    private boolean existeSolicitud;
    private boolean aceptadaSolicitud;

    /**
     * Procesa una solicitud de tarjeta.
     * 
     * @param ns El número de solicitud.
     * @param f La fecha de la solicitud.
     * @param t El tipo de tarjeta.
     * @param n El nombre del solicitante.
     * @param s El límite de la tarjeta.
     * @param d La dirección del solicitante.
     */
    public void procesarSolicitud(String ns, String f, String t, String n, String s, String d) {
        solicitud.obtenerDatosSolicitudFormulario(ns, f, t, n, s, d);
        solicitud.guardarSolicitud();
    }

    /**
     * Procesa la autorización de una solicitud.
     * 
     * @param ns El número de solicitud.
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
     * @return true si existe una solicitud, false en caso contrario.
     */
    public boolean isExisteSolicitud() {
        return existeSolicitud;
    }

    /**
     * Verifica si una solicitud fue aceptada.
     * 
     * @return true si la solicitud fue aceptada, false en caso contrario.
     */
    public boolean isAceptadaSolicitud() {
        return aceptadaSolicitud;
    }

    /**
     * Obtiene la fecha actual en formato yyyy-MM-dd.
     * 
     * @return La fecha actual formateada como una cadena.
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
     * @param nt El número de tarjeta.
     * @param f La fecha del movimiento.
     * @param e El establecimiento donde se realizó el movimiento.
     * @param d La descripción del movimiento.
     * @param m El monto del movimiento.
     * @param tm El tipo de movimiento.
     */
    public void procesarMovimiento(String nt, String f, String e, String d, double m, String tm) {
        movimiento.obtenerDatosMovimientoFormulario(nt, f, e, d, m, tm);
        movimiento.obtenerMontoTotalActual(nt);
        movimiento.definirSaldo(nt);
        movimiento.guardarMovimiento();
    }

    /**
     * Procesa la cancelación de una tarjeta.
     * 
     * @param nt El número de tarjeta.
     */
    public void procesarCancelacion(String nt) {
        cancelacion.recibirDatos(nt);
        cancelacion.desactivarTarjeta();
    }

    /**
     * Realiza una consulta sobre una tarjeta.
     * 
     * @param nt El número de tarjeta.
     */
    public void consulta(String nt) {
        consulta.obtenerNumeroDeTarjeta(nt);
        consulta.validarTarjeta();
    }

    /**
     * Genera un listado de tarjetas basado en los filtros proporcionados.
     * 
     * @param t El tipo de tarjeta.
     * @param n El nombre del cliente.
     * @param m El límite de la tarjeta.
     * @param fp La fecha mínima de la tarjeta.
     * @param fs La fecha máxima de la tarjeta.
     * @param e El estado de la tarjeta.
     */
    public void ListadoTarjetas(String t, String n, double m, String fp, String fs, String e) {
        if (t.equals("Tipo")) {
            t = "";
        }
        if (e.equals("Estado")) {
            e = "";
        }
        lt.reiniciarListas();
        lt.obtener_filtros(t, n, m, fp, fs, e);
        lt.establecerInstruccion();
        lt.darInstruccion();
    }

    /**
     * Genera un listado de solicitudes basado en los filtros proporcionados.
     * 
     * @param t El tipo de solicitud.
     * @param m El límite de la tarjeta.
     * @param fp La fecha mínima de la solicitud.
     * @param fs La fecha máxima de la solicitud.
     * @param e El estado de la solicitud.
     */
    public void ListadoSolicitudes(String t, double m, String fp, String fs, String e) {
        if (t.equals("Tipo")) {
            t = "";
        }
        if (e.equals("Estado")) {
            e = "";
        }
        ls.reiniciarListas();
        ls.obtener_filtros(t, m, fp, fs, e);
        ls.establecerInstruccion();
        ls.darInstruccion();
    }

    /**
     * Genera un listado de estados de cuenta basado en los filtros proporcionados.
     * 
     * @param t El tipo de estado de cuenta.
     * @param i El identificador.
     * @param s El saldo.
     * @param nt El número de tarjeta.
     */
    public void ListadoEstadosCuentas(String t, String i, String s, String nt) {
        if (t.equals("Tipo")) {
            t = "";
        }
        if (s.isEmpty()) {
            s = "0";
        }
        if (i.isEmpty()) {
            i = "0";
        }

        lec.reiniciarListas();
        lec.obtener_filtros(t, i, s, nt);
        lec.establecerInstruccion();
        lec.darInstruccion();
    }

    /**
     * Obtiene los datos del estado de cuenta para una tarjeta específica.
     * 
     * @param nt El número de tarjeta.
     */
    public void datosEstadoCuenta(String nt) {
        lec.reiniciarListasEstado();
        lec.datosCliente(nt);
        lec.datosClienteMovimientos(nt);
    }

    /**
     * Lee un archivo de entrada desde el path especificado.
     * 
     * @param path La ruta del archivo a leer.
     */
    public void Archivo(String path) {
        ae.leerArchivo(path);
    }
}
