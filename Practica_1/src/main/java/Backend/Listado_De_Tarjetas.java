package Backend;

import Conexion.Base_De_Datos;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * La clase {@code Listado_De_Tarjetas} gestiona la recuperación y procesamiento
 * de datos relacionados con tarjetas de crédito desde una base de datos MySQL.
 * Proporciona métodos para construir consultas SQL con filtros aplicados,
 * ejecutar estas consultas y almacenar los resultados en listas.
 *
 * @autor carlosrodriguez
 */
public class Listado_De_Tarjetas {

    // Listas estáticas para almacenar los datos obtenidos de la base de datos
    private static ArrayList<String> numeroTarjeta = new ArrayList<>();
    private static ArrayList<String> tipoTarjeta = new ArrayList<>();
    private static ArrayList<String> limite = new ArrayList<>();
    private static ArrayList<String> nombreCliente = new ArrayList<>();
    private static ArrayList<String> direccion = new ArrayList<>();
    private static ArrayList<String> estadoTarjeta = new ArrayList<>();
    private static ArrayList<Date> fecha = new ArrayList<>();

   
    /**
     * Devuelve la lista de números de tarjeta.
     * 
     * @return Una {@code ArrayList<String>} que contiene los números de tarjeta.
     */
    public static ArrayList<String> getNumeroTarjeta() {
        return numeroTarjeta;
    }

    /**
     * Devuelve la lista de tipos de tarjeta.
     * 
     * @return Una {@code ArrayList<String>} que contiene los tipos de tarjeta.
     */
    public static ArrayList<String> getTipoTarjeta() {
        return tipoTarjeta;
    }

    /**
     * Devuelve la lista de límites de tarjeta.
     * 
     * @return Una {@code ArrayList<String>} que contiene los límites de tarjeta.
     */
    public static ArrayList<String> getLimite() {
        return limite;
    }

    /**
     * Devuelve la lista de nombres de clientes.
     * 
     * @return Una {@code ArrayList<String>} que contiene los nombres de clientes.
     */
    public static ArrayList<String> getNombreCliente() {
        return nombreCliente;
    }

    /**
     * Devuelve la lista de direcciones de clientes.
     * 
     * @return Una {@code ArrayList<String>} que contiene las direcciones de clientes.
     */
    public static ArrayList<String> getDireccion() {
        return direccion;
    }

    /**
     * Devuelve la lista de estados de tarjeta.
     * 
     * @return Una {@code ArrayList<String>} que contiene los estados de tarjeta.
     */
    public static ArrayList<String> getEstadoTarjeta() {
        return estadoTarjeta;
    }

    /**
     * Devuelve la lista de fechas asociadas a las tarjetas.
     * 
     * @return Una {@code ArrayList<Date>} que contiene las fechas asociadas a las tarjetas.
     */
    public static ArrayList<Date> getFecha() {
        return fecha;
    }
    // Variables estáticas para almacenar filtros de búsqueda
    private static String tipo;
    private String nombre;
    private static String fechaPrimera;
    private static String fechaSegunda;
    private static double monto;
    private static String estado;
    private static String instruccion;

    /**
     * Ejecuta la instrucción SQL construida en {@code instruccion} y almacena
     * los resultados en las listas estáticas correspondientes.
     *
     * Se asegura de cerrar el {@code ResultSet} y el {@code Statement} después
     * de su uso para evitar fugas de recursos.
     */
    public void darInstruccion() {
        Statement statement = null;
        ResultSet result = null;

        try {
            statement = Base_De_Datos.getConnection().createStatement();
            result = statement.executeQuery(instruccion);

            // Recorre los resultados y almacena los datos en las listas
            while (result.next()) {
                numeroTarjeta.add(result.getString("Numero_Tarjeta"));
                tipoTarjeta.add(result.getString("Tipo"));
                limite.add(result.getString("Limite"));
                nombreCliente.add(result.getString("Nombre"));
                direccion.add(result.getString("Direccion"));
                estadoTarjeta.add(result.getString("Estado"));
                fecha.add(result.getDate("Fecha"));
            }

            // Imprime los números de tarjeta para verificación
            for (String tarjeta : numeroTarjeta) {
                System.out.println(tarjeta);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Listado_De_Tarjetas.class.getName()).log(Level.SEVERE, "Error obteniendo datos de tarjetas", ex);
        } finally {
            try {
                if (result != null) {
                    result.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Listado_De_Tarjetas.class.getName()).log(Level.SEVERE, "Error cerrando recursos", ex);
            }
        }
    }

    /**
     * Construye la instrucción SQL para consultar tarjetas de crédito en la
     * base de datos, aplicando los filtros especificados.
     *
     * Los filtros se añaden a la consulta SQL solo si están presentes.
     */
    public void establecerInstruccion() {
        StringBuilder sb = new StringBuilder("SELECT * FROM Datos_Tarjeta WHERE 1=1");

        if (tipo != null && !tipo.isEmpty()) {
            sb.append(" AND Tipo = '").append(tipo).append("'");
        }

        if (nombre != null && !nombre.isEmpty()) {
            sb.append(" AND Nombre = '").append(nombre).append("'");
        }

        if (monto > 0) {
            sb.append(" AND Limite > ").append(monto);
        }

        if (fechaPrimera != null && !fechaPrimera.isEmpty()) {
            sb.append(" AND Fecha >= '").append(fechaPrimera).append("'");
        }

        if (fechaSegunda != null && !fechaSegunda.isEmpty()) {
            sb.append(" AND Fecha <= '").append(fechaSegunda).append("'");
        }

        if (estado != null && !estado.isEmpty()) {
            sb.append(" AND Estado = '").append(estado).append("'");
        }

        instruccion = sb.toString();
        System.out.println(instruccion); // Imprime la instrucción para depuración
    }

    /**
     * Establece los filtros para la instrucción SQL.
     *
     * @param t El tipo de tarjeta. Puede ser una cadena vacía si no se aplica
     * filtro.
     * @param n El nombre del cliente. Puede ser una cadena vacía si no se
     * aplica filtro.
     * @param m El límite mínimo de la tarjeta. Debe ser mayor que cero para
     * aplicar el filtro.
     * @param fp La fecha inicial en formato 'YYYY-MM-DD'. Puede ser una cadena
     * vacía si no se aplica filtro.
     * @param fs La fecha final en formato 'YYYY-MM-DD'. Puede ser una cadena
     * vacía si no se aplica filtro.
     * @param e El estado de la tarjeta. Puede ser una cadena vacía si no se
     * aplica filtro.
     */
    public void obtener_filtros(String t, String n, double m, String fp, String fs, String e) {
        tipo = t;
        nombre = n;
        fechaPrimera = fp;
        fechaSegunda = fs;
        estado = e;
        monto = m;
    }

    /**
     * Reinicia las listas de datos, vaciando su contenido.
     */
    public void reiniciarListas() {
        numeroTarjeta.clear();
        tipoTarjeta.clear();
        limite.clear();
        nombreCliente.clear();
        direccion.clear();
        estadoTarjeta.clear();
        fecha.clear();
    }
}
