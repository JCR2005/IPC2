/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;

import java.sql.*;

/**
 *
 * @author carlosrodriguez
 */
public class Base_De_Datos {
    
       private static final String URL_MYSQL = "jdbc:mysql://localhost:3306/TARJETAS_CREDITO";
    private static final String USER = "root";
    private static final String PASSWORD = "4170";
    
    private static Connection connection;

    public static Connection getConnection() {
        return connection;
    }
    
    public Base_De_Datos(){
    
        try{
             connection = DriverManager.getConnection(URL_MYSQL, USER, PASSWORD);
             connection.setSchema("TARJETAS_CREDITO");
             System.out.println("Esquema"+connection.getSchema()+"establecido");
        }catch(SQLException e){
            System.out.println("Fallo de conexion");
            e.printStackTrace();
            
        }
       
    
    }
    

}
