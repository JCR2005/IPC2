
package Backend;

/**
 *
 * @author carlosrodriguez
 */
public class Procesos {
    
    Solicitud solicitud=new Solicitud();
    
    
    public void Procesar_Solicitud(int ns, String f, String t, String n, double s, String d){
    
        solicitud.Obtener_Datos_Solicitud_Formulario(ns, f, t, n, s, d);
        
    
    }
           
}
