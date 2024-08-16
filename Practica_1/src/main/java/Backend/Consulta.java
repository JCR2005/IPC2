package Backend;

import Conexion.Base_De_Datos;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author carlosrodriguez
 */
public class Consulta {

    private String numero_tarjeta;

    public void ObtenerNumeroDeTarjeta(String nt) {

        numero_tarjeta = nt;

    }

    public void validar_tarjeta() {

        try {
            ResultSet result;
            Statement statemenInsert = Base_De_Datos.getConnection().createStatement();

            String select = "select * from Datos_Tarjeta where Numero_Tarjeta=" + numero_tarjeta;
            result = statemenInsert.executeQuery(select);
            System.out.println(numero_tarjeta);
            // Mover el cursor al primer resultado
            if (result.next()) {
               String nt = result.getString("Numero_Tarjeta");
               String tt = result.getString("Tipo");
               int l = result.getInt("Limite");
               String n = result.getString("Nombre");
               String d = result.getString("Direccion");
               String e = result.getString("Estado");
               
                crearArchivohtml(nt,tt,l,n,d,e);
            } else {

                JOptionPane.showMessageDialog(null, "Este numero de tarjeta no existe", "Error", JOptionPane.ERROR_MESSAGE);

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Este numero de tarjeta es invalido", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }
    
    
    
    
    public void crearArchivohtml(String nt, String tt, int l, String n, String d, String e) {
        FileWriter escritor = null;
        JFileChooser fileChooser = new JFileChooser();
        File archivo = null;

        // Mostrar el diálogo para elegir el archivo
        int resultado = fileChooser.showSaveDialog(null);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            archivo = fileChooser.getSelectedFile();
            
            // Verificar y agregar la extensión .html si no está presente
            if (!archivo.getName().endsWith(".html")) {
                archivo = new File(archivo.getAbsolutePath() + ".html");
            }
        } else {
            // Si el usuario cancela la operación
            System.out.println("Operación cancelada.");
            return;
        }

        try {
            String contenido = "<!DOCTYPE html>\n" +
                    "<html lang=\"es\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    <title>Información de Tarjeta</title>\n" +
                    "    <style>\n" +
                    "        table {\n" +
                    "            width: 100%;\n" +
                    "            border-collapse: collapse;\n" +
                    "        }\n" +
                    "        table, th, td {\n" +
                    "            border: 1px solid black;\n" +
                    "        }\n" +
                    "        th, td {\n" +
                    "            padding: 8px;\n" +
                    "            text-align: left;\n" +
                    "        }\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "\n" +
                    "    <h1>Detalles de la Tarjeta</h1>\n" +
                    "\n" +
                    "    <table>\n" +
                    "        <thead>\n" +
                    "            <tr>\n" +
                    "                <th>No. Tarjeta</th>\n" +
                    "                <th>Tipo Tarjeta</th>\n" +
                    "                <th>Límite</th>\n" +
                    "                <th>Nombre</th>\n" +
                    "                <th>Dirección</th>\n" +
                    "                <th>Estado de Tarjeta</th>\n" +
                    "            </tr>\n" +
                    "        </thead>\n" +
                    "        <tbody>\n" +
                    "            <tr>\n" +
                    "                <td>"+nt+"</td>\n" +
                    "                <td>"+tt+"</td>\n" +
                    "                <td>$"+l+"</td>\n" +
                    "                <td>"+n+"</td>\n" +
                    "                <td>"+d+"</td>\n" +
                    "                <td>"+e+"</td>\n" +
                    "            </tr>\n" +
                    "        </tbody>\n" +
                    "    </table>\n" +
                    "\n" +
                    "</body>\n" +
                    "</html>";

            // Crear un FileWriter con el archivo seleccionado
            escritor = new FileWriter(archivo);
            // Escribir el contenido en el archivo
            escritor.write(contenido);
            // Cerrar el FileWriter para guardar los datos
            escritor.close();
            System.out.println("Archivo guardado con éxito en: " + archivo.getAbsolutePath());
        } catch (IOException ex) {
            Logger.getLogger(Consulta.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (escritor != null) {
                    escritor.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(Consulta.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


}
