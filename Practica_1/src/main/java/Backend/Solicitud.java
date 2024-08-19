package Backend;

import Conexion.Base_De_Datos;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Clase que maneja las solicitudes de cuentas, validando y guardando la información
 * en la base de datos.
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

    /**
     * Obtiene y valida los datos del formulario de solicitud.
     * 
     * @param ns Número de solicitud como String
     * @param f Fecha de la solicitud
     * @param t Tipo de cuenta
     * @param n Nombre del solicitante
     * @param s Salario del solicitante como String
     * @param d Dirección del solicitante
     */
    public void obtenerDatosSolicitudFormulario(String ns, String f, String t, String n, String s, String d) {
        try {
            numero_solicitud = Integer.parseInt(ns);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Número de solicitud no legible.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            salario = Double.parseDouble(s);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Salario no legible.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        fecha = f;
        nombre = n;
        tipo_Cuenta = t;
        direccion = d;
    }

    /**
     * Verifica si hay errores en los datos ingresados.
     */
    public void errore() {
        if (tipo_Cuenta.equals("Tipo de Cuenta ...")) {
            JOptionPane.showMessageDialog(null, "Elija tipo de cuenta.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (direccion.isEmpty() || nombre.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Llene todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

    /**
     * Guarda la solicitud en la base de datos.
     */
    public void guardarSolicitud() {
        // Mostrar datos de la solicitud en la consola para depuración
        System.out.println(numero_solicitud);
        System.out.println(fecha);
        System.out.println(nombre);
        System.out.println(salario);
        System.out.println(direccion);

        // Verificar errores antes de guardar
        errore();

        // Crear y ejecutar la consulta de inserción
        String insert = "INSERT INTO solicitud (Numero_Solicitud, Fecha, Tipo, Nombre, Salario, Direccion, Estado) " +
                         "VALUES ('" + numero_solicitud + "','" + fecha + "','" + tipo_Cuenta + "','" + nombre + "','" + salario + "','" +
                         direccion.replace("\n", "").replace("\r", "") + "','EN PROCESO')";

        try {
            Statement statemenInsert = Base_De_Datos.getConnection().createStatement();
            System.out.println(insert);
            int rowsAffected = statemenInsert.executeUpdate(insert);

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Solicitud exitosa", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            // Manejar error en la inserción
            String select = "SELECT * FROM solicitud WHERE Numero_Solicitud = '" + numero_solicitud + "'";
            try {
                Statement statementSelect = Base_De_Datos.getConnection().createStatement();
                ResultSet result = statementSelect.executeQuery(select);

                if (result.next()) {
                    JOptionPane.showMessageDialog(null, "Número de solicitud ocupado.", "Solicitud no enviada", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Sugerencias:\n*Formato de fecha yyyy-mm-dd.\n*Llene todos los campos.", "Solicitud no enviada", JOptionPane.ERROR_MESSAGE);
                    System.out.println("error");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Solicitud.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
