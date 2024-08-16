/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
public class Autorizacion {

    private boolean bandera = false;

    public boolean consulta(int numero_Solicitud) {

        bandera=false;
        String select = "select * from solicitud where Numero_Solicitud='"+numero_Solicitud+"'";

        Statement statemenInsert;
        try {
            statemenInsert = Base_De_Datos.getConnection().createStatement();
             ResultSet result = statemenInsert.executeQuery(select);
              
              if (result.next()) {
                  bandera=true;
              }
        } catch (SQLException ex) {
            Logger.getLogger(Autorizacion.class.getName()).log(Level.SEVERE, null, ex);
        }

      
        
    
    
        return bandera;
    }

    public boolean Aceptacion(int numero_Solicitud) {

        bandera = false;
        try {
            String select = "select * from solicitud where Numero_Solicitud=" + numero_Solicitud;

            Statement statemenInsert = Base_De_Datos.getConnection().createStatement();

            ResultSet result = statemenInsert.executeQuery(select);

            // Mover el cursor al primer resultado
            if (result.next()) {

                double salario = result.getDouble("Salario");
                String tipo = result.getString("Tipo");
                String nombre = result.getString("Nombre");
                String direccion = result.getString("Direccion");
                double limite = salario * 0.6;

                if (tipo.equals("NACIONAL")) {

                    if (limite >= 5000) {
                        System.out.println(" aprobada");
                        bandera = true;
                    }

                } else if (tipo.equals("REGIONAL")) {
                    if (limite >= 10000) {
                        System.out.println(" aprobada");
                        bandera = true;
                    }

                } else if (tipo.equals("INTERNACIONAL")) {
                    if (limite >= 20000) {
                        System.out.println(" aprobada");
                        bandera = true;
                    }
                }

                if (!bandera) {
                    System.out.println("rechazada");
                    String insert = "UPDATE solicitud SET Estado = 'RECHAZADA' where Numero_Solicitud = '"+numero_Solicitud+"'";
                    Statement statemenInser = Base_De_Datos.getConnection().createStatement();
                    int rowsAffected = statemenInser.executeUpdate(insert);
                    creacion_tarjeta(tipo, nombre, direccion);
                } else {
                    String insert = "UPDATE solicitud SET Estado = 'ACEPTADA' where Numero_Solicitud = '"+numero_Solicitud+"'";
                    Statement statemenInser = Base_De_Datos.getConnection().createStatement();
                    int rowsAffected = statemenInser.executeUpdate(insert);
                    creacion_tarjeta(tipo, nombre, direccion);
                }

            }

        } catch (SQLException e) {
            System.out.println("errore");
        }

        return bandera;

    }

    public void creacion_tarjeta(String tipo_Tarjeta, String nombre, String direccion) {
        String Formato = "";
        int limite = 0;
        if (tipo_Tarjeta.equals("NACIONAL")) {
            limite = 5000;
            Formato = "42563102654";
        } else if (tipo_Tarjeta.equals("REGIONAL")) {
            limite = 10000;
            Formato = "42563102655";
        } else if (tipo_Tarjeta.equals("INTERNACIONAL")) {
            limite = 20000;
            Formato = "42563102656";
        }

        String Extension;
        String numeroTarjeta;
        int inicio;
        int contador;
        boolean bandera;
        try {

            bandera = false;
            contador = 0;
            inicio = 0;
            ResultSet result;
            Statement statemenInsert = Base_De_Datos.getConnection().createStatement();
            do {

                Extension = String.format("%05d", inicio + contador);

                numeroTarjeta = Formato + Extension;
                System.out.println(numeroTarjeta);

                String select = "select * from Datos_Tarjeta where Numero_Tarjeta=" + numeroTarjeta;
                result = statemenInsert.executeQuery(select);

                // Mover el cursor al primer resultado
                if (result.next()) {
                    String resultado = result.getString("Numero_Tarjeta");
                    if (resultado.endsWith(String.valueOf(numeroTarjeta))) {
                        System.out.println("Se encontró nut");
                        bandera = true;
                        contador += 1;
                    }
                } else {
                    System.out.println("No se encontró y");
                    try {
                        String insert = "INSERT INTO Datos_Tarjeta (Numero_Tarjeta, Tipo, Limite,Nombre,Direccion,Estado) VALUES ('" + numeroTarjeta + "','" + tipo_Tarjeta + "','" + limite + "','" + nombre + "','" + direccion + "','ACTIVA')";
                        Statement statemenInser = Base_De_Datos.getConnection().createStatement();
                        int rowsAffected = statemenInser.executeUpdate(insert);

                    } catch (SQLException e) {
                        System.out.println("error");
                    }
                    bandera = false;
                    break;
                }

            } while (bandera);

        } catch (SQLException e) {
            System.out.println("errore");
        }

    }

}
