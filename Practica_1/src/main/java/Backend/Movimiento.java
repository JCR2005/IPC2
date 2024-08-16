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
public class Movimiento {

    private String numero_tarjeta;
    private double monto;
    private String fecha;
    private String descripcion;
    private String Establecimiento;
    private String tipo_De_Movimiento;

    public void Obtener_Datos_Movimiento_Formulario(String nt, String f, String e, String d, double m, String tm) {

        numero_tarjeta = nt;
        fecha = f;
        descripcion = d;
        Establecimiento = e;
        tipo_De_Movimiento = tm;
        monto = m;

    }

    public void guardarMovimeito() {
        ResultSet result;

        if (tipo_De_Movimiento.length() > 6) {
            JOptionPane.showMessageDialog(null, "No selecciono ningun tipo de movimiento.", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (descripcion.length() > 201) {
            JOptionPane.showMessageDialog(null, "La descripcion supera los 200 caracteres.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {

            try {
                Statement statemenInsert = Base_De_Datos.getConnection().createStatement();
                String select = "select * from Datos_Tarjeta where Numero_Tarjeta= " + numero_tarjeta;
                result = statemenInsert.executeQuery(select);
                if (result.next()) {
                    String resultado = result.getString("Numero_Tarjeta");
                    if (resultado.endsWith(String.valueOf(numero_tarjeta))) {
                        System.out.println("Se encontró nut");

                        resultado = result.getString("Estado");
                        if (resultado.endsWith("INACTIVA")) {
                             JOptionPane.showMessageDialog(null, "En este momento la tarjeta se encuentra inactiva", "Información", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            String insert = "INSERT INTO Movimientos (Numero_Tarjeta, Fecha, Tipo_Movimiento, Descripcion,Establecimiento,Monto) VALUES ('" + numero_tarjeta + "','" + fecha + "','" + tipo_De_Movimiento + "','" + descripcion + "','" + Establecimiento + "','" + monto + "')";

                            int rowsAffected = statemenInsert.executeUpdate(insert);
                            JOptionPane.showMessageDialog(null, "Movimiento exitoso", "Información", JOptionPane.INFORMATION_MESSAGE);
                      
                        }
                    }

                }else{
                    JOptionPane.showMessageDialog(null, "Numero de tarjeta no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Numero de tarjeta no es valido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

}
