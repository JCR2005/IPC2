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
 * La clase {@code Listado_De_Solicitudes} se encarga de gestionar y procesar las solicitudes
 * de tarjetas de crédito desde una base de datos MySQL. Proporciona métodos para establecer
 * filtros de búsqueda, ejecutar consultas SQL y almacenar los resultados en listas.
 * 
 * @author carlosrodriguez
 */
public class Listado_De_Solicitudes {

    // Listas estáticas para almacenar los datos obtenidos de la base de datos
    private static ArrayList<String> numero_solicitud = new ArrayList<>();
    private static ArrayList<String> tipoTarjeta = new ArrayList<>();
    private static ArrayList<String> salarios = new ArrayList<>();
    private static ArrayList<String> nombreCliente = new ArrayList<>();
    private static ArrayList<String> direccion = new ArrayList<>();
    private static ArrayList<String> estadoTarjeta = new ArrayList<>();
    private static ArrayList<Date> fecha = new ArrayList<>();

    // Variables estáticas para almacenar filtros de búsqueda
    private static String tipo;
    private static String fechaPrimera;
    private static String fechaSegunda;
    private static double salario;
    private static String estado;
    private static String instruccion;

    public static ArrayList<String> getNumero_solicitud() {
        return numero_solicitud;
    }

    public static ArrayList<String> getTipoTarjeta() {
        return tipoTarjeta;
    }

    public static ArrayList<String> getSalarios() {
        return salarios;
    }

    public static ArrayList<String> getNombreCliente() {
        return nombreCliente;
    }

    public static ArrayList<String> getDireccion() {
        return direccion;
    }

    public static ArrayList<String> getEstadoTarjeta() {
        return estadoTarjeta;
    }

    public static ArrayList<Date> getFecha() {
        return fecha;
    }

  

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
                numero_solicitud.add(result.getString("Numero_Solicitud"));
                tipoTarjeta.add(result.getString("Tipo"));
                salarios.add(result.getString("Salario"));
                nombreCliente.add(result.getString("Nombre"));
                direccion.add(result.getString("Direccion"));
                estadoTarjeta.add(result.getString("Estado"));
                fecha.add(result.getDate("Fecha"));
            }

            // Imprime los números de solicitud para verificación
            for (String solicitud : numero_solicitud) {
                System.out.println(solicitud);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Listado_De_Solicitudes.class.getName()).log(Level.SEVERE, "Error obteniendo datos de solicitudes", ex);
        } finally {
            try {
                if (result != null) result.close();
                if (statement != null) statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(Listado_De_Solicitudes.class.getName()).log(Level.SEVERE, "Error cerrando recursos", ex);
            }
        }
    }

    /**
     * Construye la instrucción SQL para consultar solicitudes de tarjetas de crédito
     * en la base de datos, aplicando los filtros especificados.
     * 
     * Los filtros son añadidos a la consulta SQL si están presentes.
     */
    public void establecerInstruccion() {
        StringBuilder sb = new StringBuilder("SELECT * FROM solicitud WHERE 1=1");

        if (tipo != null && !tipo.isEmpty()) {
            sb.append(" AND Tipo = '").append(tipo).append("'");
        }

        if (salario > 0) {
            sb.append(" AND Salario > ").append(salario);
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
     * @param t El tipo de tarjeta. Puede ser una cadena vacía si no se aplica filtro.
     * @param m El salario mínimo. Debe ser mayor que cero para aplicar el filtro.
     * @param fp La fecha inicial en formato 'YYYY-MM-DD'. Puede ser una cadena vacía si no se aplica filtro.
     * @param fs La fecha final en formato 'YYYY-MM-DD'. Puede ser una cadena vacía si no se aplica filtro.
     * @param e El estado de la tarjeta. Puede ser una cadena vacía si no se aplica filtro.
     */
    public void obtener_filtros(String t, double m, String fp, String fs, String e) {
        tipo = t;
        salario = m;
        fechaPrimera = fp;
        fechaSegunda = fs;
        estado = e;
    }

    /**
     * Reinicia las listas de datos, vaciando su contenido.
     */
    public void reiniciarListas() {
        numero_solicitud.clear();
        tipoTarjeta.clear();
        salarios.clear();
        nombreCliente.clear();
        direccion.clear();
        estadoTarjeta.clear();
        fecha.clear();
    }
}
