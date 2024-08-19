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
 * La clase {@code Movimiento} maneja la lógica para gestionar y guardar movimientos
 * en una tarjeta de crédito. Incluye métodos para obtener datos de movimientos,
 * calcular saldos y intereses, y guardar los movimientos en la base de datos.
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

    /**
     * Obtiene los datos del movimiento desde un formulario y los almacena en los atributos.
     * 
     * @param nt Número de tarjeta.
     * @param f Fecha del movimiento.
     * @param e Establecimiento donde se realizó el movimiento.
     * @param d Descripción del movimiento.
     * @param m Monto del movimiento.
     * @param tm Tipo de movimiento (abono o cargo).
     */
    public void obtenerDatosMovimientoFormulario(String nt, String f, String e, String d, double m, String tm) {
        numero_tarjeta = nt;
        fecha = f;
        descripcion = d;
        Establecimiento = e;
        tipo_De_Movimiento = tm;
        monto = m;

        // Imprime los datos del movimiento para verificación
        System.out.println(numero_tarjeta);
        System.out.println(fecha);
        System.out.println(descripcion);
        System.out.println(Establecimiento);
        System.out.println(tipo_De_Movimiento);
        System.out.println(monto);
    }

    /**
     * Calcula y define el saldo actual de la tarjeta considerando el monto del movimiento.
     * 
     * @param nt Número de tarjeta para la cual se debe calcular el saldo.
     */
    public void definirSaldo(String nt) {
        ResultSet result;
        Statement statemenInsert;
        String tipo = "";
        saldo = 0;
        try {
            statemenInsert = Base_De_Datos.getConnection().createStatement();
            String select = "SELECT * FROM Datos_Tarjeta WHERE Numero_Tarjeta = '" + nt + "'";
            result = statemenInsert.executeQuery(select);
            if (result.next()) {
                tipo = result.getString("Tipo");
            }

        } catch (SQLException ex) {
            Logger.getLogger(Movimiento.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            statemenInsert = Base_De_Datos.getConnection().createStatement();
            String select = "SELECT * FROM Movimientos WHERE Numero_Tarjeta = '" + nt + "'";
            result = statemenInsert.executeQuery(select);
            while (result.next()) {
                saldo = Double.parseDouble(result.getString("Saldo_Total"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(Movimiento.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Ajusta el saldo según el tipo de movimiento
        if (abono) {
            saldo -= monto;
        } else {
            interes(tipo);
            saldo = montoTotalActual + interes;
        }
    }

    /**
     * Calcula el interés basado en el tipo de tarjeta.
     * 
     * @param t Tipo de tarjeta (NACIONAL, REGIONAL, INTERNACIONAL).
     */
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

    /**
     * Obtiene el monto total actual considerando los movimientos previos y el nuevo movimiento.
     * 
     * @param nt Número de tarjeta para la cual se debe calcular el monto total actual.
     */
    public void obtenerMontoTotalActual(String nt) {
        ResultSet result;
        abono = false;
        boolean hayMovimientosPrevios = false;
        montoTotalActual = 0;
        try {
            Statement statemenInsert = Base_De_Datos.getConnection().createStatement();
            String select = "SELECT * FROM Movimientos WHERE Numero_Tarjeta = '" + nt + "'";
            result = statemenInsert.executeQuery(select);

            // Ajusta el monto total actual basado en el tipo de movimiento
            if (tipo_De_Movimiento.endsWith("ABONO")) {
                while (result.next()) {
                    hayMovimientosPrevios = true;
                    if (result.getString("Tipo_Movimiento").endsWith("CARGO")) {
                        montoTotalActual = Double.parseDouble(result.getString("Monto_Total"));
                        montoTotalActual -= monto;
                    } else {
                        montoTotalActual -= Double.parseDouble(result.getString("Monto"));
                    }
                }
                abono = true;
            } else {
                while (result.next()) {
                    hayMovimientosPrevios = true;
                    if (result.getString("Tipo_Movimiento").endsWith("CARGO")) {
                        montoTotalActual = Double.parseDouble(result.getString("Monto_Total"));
                        montoTotalActual += monto;
                    } else {
                        montoTotalActual -= Double.parseDouble(result.getString("Monto"));
                        montoTotalActual -= monto;
                    }
                }

                if (!hayMovimientosPrevios) { // Si no hay movimientos previos
                    montoTotalActual += monto; // Asigna el monto del primer movimiento
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Movimiento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Guarda el movimiento en la base de datos si todos los datos son válidos.
     */
    public void guardarMovimiento() {
        ResultSet result;

        if (tipo_De_Movimiento.length() > 6) {
            JOptionPane.showMessageDialog(null, "No selecciono ningun tipo de movimiento.", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (descripcion.length() > 200) {
            JOptionPane.showMessageDialog(null, "La descripcion supera los 200 caracteres.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                Statement statemenInsert = Base_De_Datos.getConnection().createStatement();
                String select = "SELECT * FROM Datos_Tarjeta WHERE Numero_Tarjeta = '" + numero_tarjeta + "'";
                result = statemenInsert.executeQuery(select);
                if (result.next()) {
                    String resultado = result.getString("Numero_Tarjeta");
                    if (resultado.endsWith(String.valueOf(numero_tarjeta))) {
                        resultado = result.getString("Estado");
                        if (resultado.endsWith("CANCELADA")) {
                            JOptionPane.showMessageDialog(null, "En este momento la tarjeta se encuentra inactiva", "Información", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            LocalTime horaActual = LocalTime.now();
                            String horaFormateada = horaActual.toString();
                            String insert = "INSERT INTO Movimientos (Numero_Tarjeta, Fecha, Tipo_Movimiento, Descripcion, Establecimiento, Monto, Monto_Total, Saldo_Total, Hora) "
                                    + "VALUES ('" + numero_tarjeta + "', '" + fecha + "', '" + tipo_De_Movimiento + "', '" + descripcion + "', '" + Establecimiento + "', " + String.format("%.2f", monto) + ", " + String.format("%.2f", montoTotalActual) + ", " + String.format("%.2f", saldo) + ", '" + horaFormateada + "')";
                            int rowsAffected = statemenInsert.executeUpdate(insert);
                            JOptionPane.showMessageDialog(null, "Movimiento exitoso", "Información", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Número de tarjeta no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Número de tarjeta no es válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
