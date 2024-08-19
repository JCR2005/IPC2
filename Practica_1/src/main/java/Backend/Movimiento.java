package Backend;

import Conexion.Base_De_Datos;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private double montoTotalActual;
    private double saldo;
    private double interes;
    private boolean abono;

    public void obtenerDatosMovimientoFormulario(String nt, String f, String e, String d, double m, String tm) {

        numero_tarjeta = nt;
        fecha = f;
        descripcion = d;
        Establecimiento = e;
        tipo_De_Movimiento = tm;
        monto = m;

        System.out.println(numero_tarjeta);
        System.out.println(fecha);
        System.out.println(descripcion);
        System.out.println(Establecimiento);
        System.out.println(tipo_De_Movimiento);
        System.out.println(monto);

    }

    public void definirSaldo(String nt) {
        ResultSet result;
        Statement statemenInsert;
        String tipo = "";
        saldo = 0;
        try {
            statemenInsert = Base_De_Datos.getConnection().createStatement();
            String select = "select * from Datos_Tarjeta where Numero_Tarjeta= '" + nt + "'";
            result = statemenInsert.executeQuery(select);
            if (result.next()) {
                tipo = result.getString("Tipo");

            }

        } catch (SQLException ex) {
            Logger.getLogger(Movimiento.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            statemenInsert = Base_De_Datos.getConnection().createStatement();
            String select = "select * from Movimientos where Numero_Tarjeta= '" + nt + "'";
            result = statemenInsert.executeQuery(select);
            while (result.next()) {
                saldo = Double.valueOf(result.getString("Saldo_Total"));

            }

        } catch (SQLException ex) {
            Logger.getLogger(Movimiento.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (abono) {

            saldo = saldo - monto;
        } else {
            interes(tipo);
            saldo = montoTotalActual + (interes);
        }

    }

    public void interes(String t) {
        interes = 0;
        if (t.endsWith("NACIONAL")) {
            interes = (montoTotalActual * 0.012);
        } else if (t.endsWith("REGIONAL")) {
            interes = (montoTotalActual * 0.023);
        } else if (t.endsWith("INTERNACIONAL")) {
            interes = (montoTotalActual * 0.0375);

        }

    }

    public void obtenerMontoTotalActual(String nt) {
        ResultSet result;
        abono = false;
        boolean hayMovimientosPrevios = false;
        montoTotalActual = 0;
        System.out.println("entroo");
        try {
            Statement statemenInsert = Base_De_Datos.getConnection().createStatement();
            String select = "select * from Movimientos where Numero_Tarjeta= '" + nt + "'";
            result = statemenInsert.executeQuery(select);
            System.out.println("w2");

            if (tipo_De_Movimiento.endsWith("ABONO")) {
                while (result.next()) {
                    System.out.println("w3");
                    hayMovimientosPrevios = true;
                    if (result.getString("Tipo_Movimiento").endsWith("CARGO")) {
                        montoTotalActual = Double.valueOf(result.getString("Monto_Total"));
                        montoTotalActual -= monto;
                        System.out.println("sssds33-------" + montoTotalActual);
                    } else {
                        montoTotalActual -= Double.valueOf(result.getString("Monto"));

                        System.out.println("sssds22-------" + montoTotalActual);
                    }

                }

                abono = true;

            } else {
                while (result.next()) {
                    System.out.println("w3");
                    hayMovimientosPrevios = true;
                    if (result.getString("Tipo_Movimiento").endsWith("CARGO")) {
                        montoTotalActual = Double.valueOf(result.getString("Monto_Total"));
                        montoTotalActual += monto;
                        System.out.println("cargo " + montoTotalActual);
                    } else {
                        montoTotalActual -= Double.valueOf(result.getString("Monto"));
                        montoTotalActual -= monto;
                        System.out.println("sssds11-------" + montoTotalActual);
                    }

                }

                if (!hayMovimientosPrevios) { // Si no hay movimientos previos
                    montoTotalActual += monto; // Asigna el monto del primer movimiento
                    System.out.println("montoT (primer movimiento) " + montoTotalActual);
                }

            }

        } catch (SQLException ex) {

            Logger.getLogger(Movimiento.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void guardarMovimiento() {
        ResultSet result;

        if (tipo_De_Movimiento.length() > 6) {
            JOptionPane.showMessageDialog(null, "No selecciono ningun tipo de movimiento.", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (descripcion.length() > 201) {
            JOptionPane.showMessageDialog(null, "La descripcion supera los 200 caracteres.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {

            try {
                Statement statemenInsert = Base_De_Datos.getConnection().createStatement();
                String select = "select * from Datos_Tarjeta where Numero_Tarjeta= '" + numero_tarjeta + "'";
                result = statemenInsert.executeQuery(select);
                if (result.next()) {
                    String resultado = result.getString("Numero_Tarjeta");
                    if (resultado.endsWith(String.valueOf(numero_tarjeta))) {
                        System.out.println("Se encontró nut");

                        resultado = result.getString("Estado");
                        if (resultado.endsWith("CANCELADA")) {
                            JOptionPane.showMessageDialog(null, "En este momento la tarjeta se encuentra inactiva", "Información", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            LocalTime horaActual = LocalTime.now();

                            // Convertir la hora a una cadena en el formato HH:mm:ss
                            String horaFormateada = horaActual.toString();
                            String insert = "INSERT INTO Movimientos (Numero_Tarjeta, Fecha, Tipo_Movimiento, Descripcion, Establecimiento, Monto,Monto_Total,Saldo_Total,Hora) "
                                    + "VALUES ('" + numero_tarjeta + "', '" + fecha + "', '" + tipo_De_Movimiento + "', '" + descripcion + "', '" + Establecimiento + "', " + String.format("%.2f", monto) + "," + String.format("%.2f", montoTotalActual) + "," + String.format("%.2f", saldo) + ",'" + horaFormateada+"')";
                            System.out.println("SQL Insert: " + insert);
                            int rowsAffected = statemenInsert.executeUpdate(insert);

                            JOptionPane.showMessageDialog(null, "Movimiento exitoso", "Información", JOptionPane.INFORMATION_MESSAGE);

                        }
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Numero de tarjeta no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Numero de tarjeta no es valido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

}
