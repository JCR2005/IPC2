/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import Conexion.Base_De_Datos.*;
/**
 *
 * @author carlosrodriguez
 */
public class Solicitud {
    
    
    private int numero_solicitud;
    private int salario;
    private String nombre;
    private String dirreccion;
    private String fecha;
    private String tipo_Cuneta;
    
    
    
    private void Obtener_Datos_Solicitud_Formulario(){
    
    
    
    
    }
    
     public void guardarSolicitud(int numero_solicitud,String fecha, String tipo, String nombre, double salario, String direccion){
       
        
        try{
            String insert ="INSERT INTO estudiante (Numero_Solicitud, Fecha, Tipo, Nombre,Salario,Dirreccion) VALUES ('" +numero_solicitud+"','"+fecha+"','"+tipo+"','"+nombre+"','"+salario+"','"+direccion+"')"  ;
            Statement statemenInsert= connection.createStatement();
            int rowsAffected=statemenInsert.executeUpdate(insert);
        
        }catch(SQLException e){
            System.out.println("error");
        }
    }
    
}
