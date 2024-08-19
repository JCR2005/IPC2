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
 *
 * @author carlosrodriguez
 */
public class Listado_De_Solicitudes {

    public static ArrayList<String> numero_solicitud = new ArrayList<>();
    public static ArrayList<String> tipoTarjeta = new ArrayList<>();
    public static ArrayList<String> salarios = new ArrayList<>();
    public static ArrayList<String> nombreCliente = new ArrayList<>();
    public static ArrayList<String> direccion = new ArrayList<>();
    public static ArrayList<String> estadoTarjeta = new ArrayList<>();
    public static ArrayList<Date> fecha = new ArrayList<>();

    static String tipo;
   
    static String fechaPrimera;
    static String fechaSegunda;
    static double salario;
    static String estado;
    static String instruccion;

    static Statement statemenInsert;

    public void darInstruccion() {

        try {
            String select = instruccion;

            statemenInsert = Base_De_Datos.getConnection().createStatement();
            ResultSet result = statemenInsert.executeQuery(select);

            while (result.next()) {
                numero_solicitud.add(result.getString("Numero_Solicitud"));
                tipoTarjeta.add(result.getString("Tipo"));
                salarios.add(result.getString("Salario"));
                nombreCliente.add(result.getString("Nombre"));
                direccion.add(result.getString("Direccion"));
                estadoTarjeta.add(result.getString("Estado"));
                fecha.add(result.getDate("Fecha"));

            }

            for (int i = 0; i < numero_solicitud.size(); i++) {
                System.out.println(numero_solicitud.get(i));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Autorizacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void establecerInstruccion() {

        instruccion = "select * from solicitud where 1=1";
        if (!tipo.isEmpty()) {
            instruccion = instruccion + " and Tipo = '" + tipo + "'";

        }
       
        if (salario>0) {
            instruccion = instruccion + " and Salario > " + salario ;
        }
        if (!fechaPrimera.isEmpty()) {

            instruccion = instruccion + " and Fecha >= '" + fechaPrimera + "'";
        }
        if (!fechaSegunda.isEmpty()) {
            instruccion = instruccion + " and Fecha <= '" + fechaSegunda + "'";
        }
        if (!estado.isEmpty()) {
            instruccion = instruccion + " and Estado = '" + estado + "'";
        }

        System.out.println(instruccion);

    }

    /**
     *
     * @param t
     * @param n
     * @param m
     * @param fp
     * @param fs
     * @param e
     */
    public void obtener_filtros(String t, double m, String fp, String fs, String e) {

        tipo = t;
      
        fechaPrimera = fp;
        fechaSegunda = fs;
        estado = e;
        salario = m;
      

    }

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
