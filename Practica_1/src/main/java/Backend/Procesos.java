package Backend;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author carlosrodriguez
 */
public class Procesos {

    Solicitud solicitud = new Solicitud();
    Autorizacion autorizacion = new Autorizacion();
    Movimiento movimiento = new Movimiento();
    Cancelacion cancelacion = new Cancelacion();
    Consulta consulta=new Consulta();
    private boolean exiteSolicitud;
    private boolean aceptadaSolicitud;

    public void Procesar_Solicitud(int ns, String f, String t, String n, double s, String d) {

        solicitud.Obtener_Datos_Solicitud_Formulario(ns, f, t, n, s, d);
        solicitud.guardarSolicitud();

    }

    public void Procesar_Autorizacion(int ns) {

        exiteSolicitud = false;
        aceptadaSolicitud = false;

        exiteSolicitud = autorizacion.consulta(ns);

        if (exiteSolicitud) {
            aceptadaSolicitud = autorizacion.Aceptacion(ns);
        }

    }

    public boolean isExiteSolicitud() {
        return exiteSolicitud;
    }

    public boolean isAceptadaSolicitud() {
        return aceptadaSolicitud;
    }

    public String fechaActual() {
        LocalDate fechaActual = LocalDate.now();

        // Formatear la fecha en formato yyyy-MM-dd
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fechaFormateada = fechaActual.format(formatter);

        return fechaFormateada;

    }

    public void Procesar_Movimiento(String nt, String f, String e, String d, double m, String tm) {

        movimiento.Obtener_Datos_Movimiento_Formulario(nt, f, e, d, m, tm);
        movimiento.guardarMovimeito();
    }

    public void Procesar_Cancelacion(String nt) {
        cancelacion.recibirDatos(nt);
        cancelacion.desactivar_tarjeta();
    }
    
    
    public void consulta(String nt){
        
        consulta.ObtenerNumeroDeTarjeta(nt);
        consulta.validar_tarjeta();
        
        

    }
}
