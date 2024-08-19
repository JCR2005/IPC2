package Backend;

import Conexion.Base_De_Datos;
import Fromted.Ventana_Principal;

import javax.swing.*;

/**
 * La clase {@code Principal_Main} es el punto de entrada de la aplicación.
 * Inicializa la conexión a la base de datos y muestra la ventana principal de la aplicación.
 * 
 * @author carlosrodriguez
 */
public class Principal_Main {

    /** Instancia de la ventana principal de la aplicación. */
    static Ventana_Principal vp = new Ventana_Principal();

    /**
     * Método principal que se ejecuta al iniciar la aplicación.
     * 
     * @param args Argumentos de línea de comandos (no utilizados en este caso).
     */
    public static void main(String[] args) {
        // Inicializa la conexión a la base de datos
        Base_De_Datos connexion = new Base_De_Datos();
        
        // Configura la ventana principal
        vp.setLocationRelativeTo(null); // Centra la ventana en la pantalla
        vp.setVisible(true); // Hace visible la ventana principal
    }
}
