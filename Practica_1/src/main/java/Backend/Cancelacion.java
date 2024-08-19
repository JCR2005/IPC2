package Backend;

import Conexion.Base_De_Datos;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 * La clase Cancelacion maneja la desactivación de tarjetas de crédito.
 * Permite recibir un número de tarjeta y actualizar su estado a inactivo
 * en la base de datos si está activa.
 * 
 * Autor: Carlos Rodriguez
 */
public class Cancelacion {

    private String numeroTarjeta;

    /**
     * Recibe el número de tarjeta para su desactivación.
     * 
     * @param numeroTarjeta El número de tarjeta a cancelar.
     */
    public void recibirDatos(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    /**
     * Desactiva la tarjeta en la base de datos si está activa.
     * Muestra un mensaje de información si la tarjeta ya está cancelada
     * o si el número de tarjeta no existe.
     */
    public void desactivarTarjeta() {
        if (numeroTarjeta == null || numeroTarjeta.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Número de tarjeta no proporcionado", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String selectQuery = "SELECT Estado FROM Datos_Tarjeta WHERE Numero_Tarjeta = '" + numeroTarjeta + "'";
        String updateQuery = "UPDATE Datos_Tarjeta SET Estado = 'CANCELADA' WHERE Numero_Tarjeta = '" + numeroTarjeta + "'";

        try (Statement statement = Base_De_Datos.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(selectQuery)) {

            if (resultSet.next()) {
                String estado = resultSet.getString("Estado");
                if ("AUTORIZADA".equals(estado)) {
                    try (Statement updateStatement = Base_De_Datos.getConnection().createStatement()) {
                        int rowsAffected = updateStatement.executeUpdate(updateQuery);
                        JOptionPane.showMessageDialog(null, "La tarjeta No." + numeroTarjeta + " ha sido cancelada exitosamente", "Información", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Esta tarjeta No." + numeroTarjeta + " ya ha sido cancelada anteriormente", "Información", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Este número de tarjeta no existe", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cancelar la tarjeta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
