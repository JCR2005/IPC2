
package Backend;

import Conexion.Base_De_Datos;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author carlosrodriguez
 */
public class Listado_De_Tarjetas {
    
        String tipo;
       String nombre;
       String fecha_primera;
       String fecha_segunda;
       String estado;
     Statement statemenInsert;
    
    public void obtener_filtros(String t, String n, String fp, String fs, String e){
        tipo="";
        nombre="";
        fecha_primera="";
        fecha_segunda="";
        estado="";
      
        
    try {
        
            String select = "select * from Datos_Tarjeta where "+tipo+Nombre+fecha_primera+fecha_segunda+estado ;
            statemenInsert = Base_De_Datos.getConnection().createStatement();
             ResultSet result = statemenInsert.executeQuery(select);
              
              if (result.next()) {
                 
              }
              
              
        } catch (SQLException ex) {
            Logger.getLogger(Autorizacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    
    }
    
    public void c(String t, String n, String fp, String fs, String e){
     
        if (t.isEmpty()) {
            tipo="";
        }else{
            tipo="Tipo = '"+t+"' and";
        }
        
        if (n.isEmpty()) {
            nombre="";
        }else{
            nombre="Nombre = '"+n+"' and";
        }
        
        if (fp.isEmpty()) {
            fecha_primera="";
        }else{
            fecha_primera="Tipo = '"+t+"' and";
        }
        
        if (t.isEmpty()) {
            tipo="";
        }else{
            tipo="Tipo = '"+t+"' and";
        }
        
        if (t.isEmpty()) {
            tipo="";
        }else{
            tipo="Tipo = '"+t+"' and";
        }
    
    
    
    }
    
    
    
}
