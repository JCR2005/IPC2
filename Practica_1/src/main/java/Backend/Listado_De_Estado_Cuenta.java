package Backend;

import Conexion.Base_De_Datos;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * La clase Listado_De_Estado_Cuenta maneja la recopilación y gestión de datos
 * de transacciones de tarjetas de crédito desde una base de datos MySQL.
 * Proporciona métodos para obtener datos del cliente, filtrar resultados y
 * calcular intereses basados en el tipo de tarjeta.
 *
 * @author carlosrodriguez
 */
public class Listado_De_Estado_Cuenta {

    // Listas para almacenar los datos obtenidos de la base de datos
    private static final ArrayList<String> tipoTarjetas = new ArrayList<>();
    private static final ArrayList<String> descripcion = new ArrayList<>();
    private static final ArrayList<String> establecimieto = new ArrayList<>();
    private static final ArrayList<String> montos = new ArrayList<>();
    private static final ArrayList<String> datosNumericos = new ArrayList<>();
    private static final ArrayList<String> tipoMovimiento = new ArrayList<>();
    private static final ArrayList<Date> fecha = new ArrayList<>();
    private static final ArrayList<String> numeroTarjetas = new ArrayList<>();
    private static final ArrayList<String> datos = new ArrayList<>();
    // Métodos para acceder a las listas encapsuladas

    public static ArrayList<String> getTipoTarjetas() {
        return tipoTarjetas;
    }

    public static ArrayList<String> getDescripcion() {
        return descripcion;
    }

    public static ArrayList<String> getEstablecimieto() {
        return establecimieto;
    }

    public static ArrayList<String> getMontos() {
        return montos;
    }

    public static ArrayList<String> getDatosNumericos() {
        return datosNumericos;
    }

    public static ArrayList<String> getTipoMovimiento() {
        return tipoMovimiento;
    }

    public static ArrayList<Date> getFecha() {
        return fecha;
    }

    public static ArrayList<String> getNumeroTarjetas() {
        return numeroTarjetas;
    }

    public static ArrayList<String> getDatos() {
        return datos;
    }

    // Variables para almacenar información específica de la tarjeta
    private String numeroTarjeta;
    private String tipoTarjeta;
    private double interes;
    private double saldo;
    private String instruccion;
    private String instruccion_1;
    private int limite;

    private static final Logger LOGGER = Logger.getLogger(Listado_De_Estado_Cuenta.class.getName());

    /**
     * Calcula el interés basado en el tipo de tarjeta y el monto de la
     * transacción.
     *
     * @param t El tipo de tarjeta (NACIONAL, REGIONAL, INTERNACIONAL).
     * @param m El monto total de la transacción.
     * @return El valor del interés calculado.
     */
    public double definirInteres(String t, double m) {
        double interes = 0;
        if (t.endsWith("NACIONAL")) {
            interes = 0.012 * m;
        } else if (t.endsWith("REGIONAL")) {
            interes = 0.023 * m;
        } else if (t.endsWith("INTERNACIONAL")) {
            interes = 0.0375 * m;
        }
        return interes;
    }

    /**
     * Obtiene los datos del cliente y de la tarjeta con base en el número de
     * tarjeta.
     *
     * @param nt El número de tarjeta.
     */
    public void datosCliente(String nt) {
        instruccion = "SELECT * FROM Datos_Tarjeta WHERE Numero_Tarjeta = '" + nt + "'";
        instruccion_1 = "SELECT * FROM Movimientos WHERE Numero_Tarjeta = '" + nt + "' ORDER BY Hora DESC LIMIT 1;";

        String tipo = "";
        double monto = 0;
        try (Statement statement = Base_De_Datos.getConnection().createStatement()) {
            ResultSet result = statement.executeQuery(instruccion);

            if (result.next()) {
                datos.add(result.getString("Numero_Tarjeta"));
                datos.add(result.getString("Tipo"));
                tipo = result.getString("Tipo");
                datos.add(result.getString("Nombre"));
                datos.add(result.getString("Direccion"));
            }

            result = statement.executeQuery(instruccion_1);

            if (result.next()) {
                datosNumericos.add(result.getString("Monto_Total"));
                monto = result.getDouble("Monto_Total");
                datosNumericos.add(String.valueOf(definirInteres(tipo, monto)));
                datosNumericos.add(result.getString("Saldo_Total"));
            }

        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error obteniendo datos del cliente", ex);
        }
    }

    /**
     * Obtiene los movimientos de la tarjeta con base en el número de tarjeta.
     *
     * @param nt El número de tarjeta.
     */
    public void datosClienteMovimientos(String nt) {
        instruccion = "SELECT * FROM Movimientos WHERE Numero_Tarjeta = '" + nt + "'";

        try (Statement statement = Base_De_Datos.getConnection().createStatement()) {
            ResultSet result = statement.executeQuery(instruccion);

            while (result.next()) {
                fecha.add(result.getDate("Fecha"));
                tipoMovimiento.add(result.getString("Tipo_Movimiento"));
                descripcion.add(result.getString("Descripcion"));
                establecimieto.add(result.getString("Establecimiento"));
                montos.add(result.getString("Monto"));
            }

        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error obteniendo movimientos del cliente", ex);
        }
    }

    /**
     * Ejecuta la instrucción SQL para obtener la información de las tarjetas.
     */
    public void darInstruccion() {
        try (Statement statement = Base_De_Datos.getConnection().createStatement()) {
            ResultSet result = statement.executeQuery(instruccion);

            while (result.next()) {
                numeroTarjetas.add(result.getString("Numero_Tarjeta"));
                tipoTarjetas.add(result.getString("Tipo"));
            }

        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error ejecutando la instrucción SQL", ex);
        }
    }

    /**
     * Establece la instrucción SQL con base en los filtros aplicados.
     */
    public void establecerInstruccion() {
        instruccion = "SELECT "
                + "Movimientos.Numero_Tarjeta, "
                + "Datos_Tarjeta.Tipo, "
                + "Movimientos.Fecha, "
                + "Movimientos.Tipo_Movimiento, "
                + "Movimientos.Descripcion, "
                + "Movimientos.Establecimiento, "
                + "Movimientos.Monto, "
                + "Movimientos.Monto_Total, "
                + "Movimientos.Saldo_Total "
                + "FROM Movimientos "
                + "INNER JOIN Datos_Tarjeta "
                + "ON Movimientos.Numero_Tarjeta = Datos_Tarjeta.Numero_Tarjeta "
                + "WHERE (Movimientos.Numero_Tarjeta, Movimientos.Hora) IN ("
                + "    SELECT Numero_Tarjeta, MAX(Hora) "
                + "    FROM Movimientos "
                + "    GROUP BY Numero_Tarjeta"
                + ")";

        if (!tipoTarjeta.isEmpty()) {
            instruccion += " AND Datos_Tarjeta.Tipo = '" + tipoTarjeta + "'";
        }

        if (interes > 0) {
            instruccion += " AND Movimientos.Saldo_Total-Monto_Total > " + interes;
        }

        if (!numeroTarjeta.isEmpty()) {
            instruccion += " AND Movimientos.Numero_Tarjeta = '" + numeroTarjeta + "'";
        }

        if (saldo > 0) {
            instruccion += " AND Movimientos.Saldo_Total > " + saldo;
        }

        System.out.println(instruccion);
    }

    /**
     * Establece los filtros para la instrucción SQL y maneja las excepciones en
     * caso de que los valores numéricos no sean válidos.
     *
     * @param t El tipo de tarjeta.
     * @param i El interés.
     * @param s El saldo.
     * @param nt El número de tarjeta.
     */
    public void obtener_filtros(String t, String i, String s, String nt) {
        numeroTarjeta = nt;
        tipoTarjeta = t;

        try {
            interes = Double.parseDouble(i);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Valor de interés inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            saldo = Double.parseDouble(s);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Valor de saldo inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

    /**
     * Reinicia las listas de tarjetas y tipos de tarjetas.
     */
    public void reiniciarListas() {
        numeroTarjetas.clear();
        tipoTarjetas.clear();
    }

    /**
     * Reinicia las listas de estado de cuenta.
     */
    public void reiniciarListasEstado() {
        datos.clear();
        fecha.clear();
        tipoMovimiento.clear();
        descripcion.clear();
        establecimieto.clear();
        montos.clear();
        datosNumericos.clear();
    }
}
