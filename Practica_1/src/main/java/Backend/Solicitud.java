/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import Conexion.Base_De_Datos;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
/**
 *
 * @author carlosrodriguez
 */
public class Solicitud {
    
    
    private int numero_solicitud;
    private double salario;
    private String nombre;
    private String direccion;
    private String fecha;
    private String tipo_Cuenta;
    
    
    
    public void obtenerDatosSolicitudFormulario(int ns,String f, String t, String n, double s, String d){
        
        numero_solicitud = ns;
        fecha = f;
        nombre = n;
        tipo_Cuenta = t;
        salario = s;
        direccion = d;
        
        if (numero_solicitud<=0 ) {
            
        }
    }
    
 
    
     public void guardarSolicitud(){
       
             
        try{
            String insert ="INSERT INTO solicitud (Numero_Solicitud, Fecha, Tipo, Nombre,Salario,Direccion,Estado) VALUES ('" +numero_solicitud+"','"+fecha+"','"+tipo_Cuenta+"','"+nombre+"','"+salario+"','"+direccion+"','EN PROCESO')"  ;
            Statement statemenInsert= Base_De_Datos.getConnection().createStatement();
            int rowsAffected=statemenInsert.executeUpdate(insert);
        
        }catch(SQLException e){
            System.out.println("error");
        }
    }
    
}
