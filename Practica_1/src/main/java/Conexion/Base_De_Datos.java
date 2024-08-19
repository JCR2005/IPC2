
package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * La clase {@code Base_De_Datos} se encarga de gestionar la conexión con la base de datos MySQL
 * para el esquema "TARJETAS_CREDITO". Proporciona métodos para obtener la conexión y 
 * establecer el esquema de trabajo.
 * 
 * <p>El acceso a la base de datos se realiza utilizando las credenciales especificadas,
 * y se maneja cualquier excepción que pueda ocurrir durante la conexión.</p>
 * 
 * <p>Ejemplo de uso:</p>
 * <pre>
 *     Base_De_Datos db = new Base_De_Datos();
 *     Connection conn = Base_De_Datos.getConnection();
 * </pre>
 * 
 * @author carlosrodriguez
 */
public class Base_De_Datos {

    // URL de la base de datos MySQL
    private static final String URL_MYSQL = "jdbc:mysql://localhost:3306/TARJETAS_CREDITO";
    
    // Usuario para acceder a la base de datos
    private static final String USER = "root";
    
    // Contraseña para acceder a la base de datos
    private static final String PASSWORD = "4170";
    
    // Conexión estática para ser reutilizada en la aplicación
    private static Connection connection;

    /**
     * Devuelve la conexión activa a la base de datos.
     * 
     * @return La conexión activa a la base de datos, o {@code null} si la conexión no ha sido establecida.
     */
    public static Connection getConnection() {
        return connection;
    }

    /**
     * Constructor de la clase {@code Base_De_Datos}.
     * 
     * <p>Establece la conexión a la base de datos utilizando los parámetros especificados y
     * configura el esquema de trabajo.</p>
     */
    public Base_De_Datos() {
        try {
            // Intentar establecer la conexión a la base de datos
            connection = DriverManager.getConnection(URL_MYSQL, USER, PASSWORD);
            
            // Configurar el esquema de trabajo
            connection.setSchema("TARJETAS_CREDITO");
            
            // Mostrar un mensaje de éxito en la consola
            System.out.println("Esquema " + connection.getSchema() + " establecido");
        } catch (SQLException e) {
            // Manejar cualquier excepción de SQL que pueda ocurrir
            System.out.println("Fallo de conexión");
            e.printStackTrace(); // Imprimir el stack trace para depuración
        }
    }
}
