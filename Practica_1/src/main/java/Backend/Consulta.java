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
 * La clase Consulta maneja la obtención y validación de información de tarjetas
 * de crédito desde la base de datos y la generación de un archivo HTML con
 * los detalles de la tarjeta.
 * 
 * Autor: Carlos Rodriguez
 */
public class Consulta {

    private String numeroTarjeta;

    /**
     * Recibe el número de tarjeta para su validación.
     * 
     * @param numeroTarjeta El número de tarjeta a validar.
     */
    public void obtenerNumeroDeTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    /**
     * Valida la tarjeta consultando su existencia en la base de datos y
     * genera un archivo HTML con los detalles de la tarjeta si es válida.
     */
    public void validarTarjeta() {
        if (numeroTarjeta == null || numeroTarjeta.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Número de tarjeta no proporcionado", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String selectQuery = "SELECT * FROM Datos_Tarjeta WHERE Numero_Tarjeta = '" + numeroTarjeta + "'";

        try (Statement statement = Base_De_Datos.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(selectQuery)) {

            if (resultSet.next()) {
                String nt = resultSet.getString("Numero_Tarjeta");
                String tt = resultSet.getString("Tipo");
                int l = resultSet.getInt("Limite");
                String n = resultSet.getString("Nombre");
                String d = resultSet.getString("Direccion");
                String e = resultSet.getString("Estado");

                crearArchivoHtml(nt, tt, l, n, d, e);
            } else {
                JOptionPane.showMessageDialog(null, "Este número de tarjeta no existe", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al validar la tarjeta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Crea un archivo HTML con los detalles de la tarjeta.
     * 
     * @param nt El número de tarjeta.
     * @param tt El tipo de tarjeta.
     * @param l El límite de la tarjeta.
     * @param n El nombre del titular de la tarjeta.
     * @param d La dirección del titular de la tarjeta.
     * @param e El estado de la tarjeta.
     */
    public void crearArchivoHtml(String nt, String tt, int l, String n, String d, String e) {
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
                "    <h1>Detalles de la Tarjeta</h1>\n" +
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
                "                <td>" + nt + "</td>\n" +
                "                <td>" + tt + "</td>\n" +
                "                <td>$" + l + "</td>\n" +
                "                <td>" + n + "</td>\n" +
                "                <td>" + d + "</td>\n" +
                "                <td>" + e + "</td>\n" +
                "            </tr>\n" +
                "        </tbody>\n" +
                "    </table>\n" +
                "</body>\n" +
                "</html>";

        try (FileWriter escritor = new FileWriter(archivo)) {
            escritor.write(contenido);
            System.out.println("Archivo guardado con éxito en: " + archivo.getAbsolutePath());
        } catch (IOException ex) {
            Logger.getLogger(Consulta.class.getName()).log(Level.SEVERE, "Error al guardar el archivo", ex);
        }
    }
}
