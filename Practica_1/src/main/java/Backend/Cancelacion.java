package Backend;

import Conexion.Base_De_Datos;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author carlosrodriguez
 */
public class Cancelacion {

    private String numero_tarjeta;

    public void recibirDatos(String nt) {

        numero_tarjeta = nt;

    }

    public void desactivar_tarjeta() {

        try {
            ResultSet result;
            Statement statemenInsert = Base_De_Datos.getConnection().createStatement();

            String select = "select * from Datos_Tarjeta where Numero_Tarjeta=" + numero_tarjeta;
            result = statemenInsert.executeQuery(select);
            System.out.println(numero_tarjeta);
            // Mover el cursor al primer resultado
            if (result.next()) {
                String resultado = result.getString("Estado");
                if (resultado.equals("ACTIVA")) {
                    String insert = "UPDATE Datos_Tarjeta SET Estado = 'INACTIVA' where Numero_Tarjeta =" + numero_tarjeta;
                    Statement statemenInser = Base_De_Datos.getConnection().createStatement();
                    int rowsAffected = statemenInser.executeUpdate(insert);
                    
                    JOptionPane.showMessageDialog(null, "La tarjeta No."+numero_tarjeta+" ha sido cancelada exitosamente", "Información", JOptionPane.INFORMATION_MESSAGE);
                } else {

                    JOptionPane.showMessageDialog(null, "Esta tarjeta No."+numero_tarjeta+" ya ha sido cancelada anteriormente", "Información", JOptionPane.INFORMATION_MESSAGE);

                }

            } else {

                JOptionPane.showMessageDialog(null, "Este numero de tarjeta no existe", "Error", JOptionPane.ERROR_MESSAGE);

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Este numero de tarjeta es invalido", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

}
