package Backend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 * Clase que maneja la lectura de archivos y el procesamiento de solicitudes, movimientos,
 * autorizaciones, cancelaciones y consultas de tarjeta.
 * 
 * @autor carlosrodriguez
 */
public class Archivo_Esntrada {

    // Instancias de las clases correspondientes para manejar solicitudes, movimientos, etc.
    private final Solicitud solicitud = new Solicitud();
    private final Autorizacion autorizacion = new Autorizacion();
    private final Movimiento movimiento = new Movimiento();
    private final Cancelacion cancelacion = new Cancelacion();
    private final Consulta consulta = new Consulta();

    /**
     * Lee el contenido de un archivo y lo procesa.
     * 
     * @param direccionArchivo Ruta del archivo a leer
     * @return Contenido del archivo como cadena
     */
    public String leerArchivo(String direccionArchivo) {
        StringBuilder contenido = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(direccionArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                contenido.append(linea).append(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Manejo de excepción específico puede ser añadido aquí si es necesario
        }

        procesar(contenido.toString());
        return contenido.toString();
    }

    /**
     * Procesa el contenido leído del archivo según el tipo de operación.
     * 
     * @param r Contenido del archivo como cadena
     */
    public void procesar(String r) {
        // Dividir el contenido en partes usando el delimitador de paréntesis
        String[] partes = r.split("\\(");

        // Determinar el tipo de operación y llamar al método correspondiente
        if (partes[0].endsWith("SOLICITUD")) {
            solicitud(partes[1]);
        } else if (partes[0].endsWith("MOVIMIENTO")) {
            movimiento(partes[1]);
        } else if (partes[0].endsWith("CONSULTAR_TARJETA")) {
            consulta(partes[1]);
        } else if (partes[0].endsWith("AUTORIZACION_TARJETA")) {
            autorizacion(partes[1]);
        } else if (partes[0].endsWith("CANCELACION_TARJETA")) {
            cancelacion(partes[1]);
        }

        if (partes.length != 6) {
            // Asegurarse de que la cadena se ha dividido correctamente en 6 partes
            // Puede añadir manejo de errores aquí si es necesario
        }
    }

    /**
     * Procesa la solicitud y guarda los datos en la base de datos.
     * 
     * @param r Datos de la solicitud como cadena
     */
    public void solicitud(String r) {
        // Limpiar y dividir el contenido en partes
        String contenido = r.replace("SOLICITUD(", "").replace(");", "").replace("\"", "").replace("”", "").replace("\r", "");
        String[] campos = contenido.split(",");

        // Mostrar datos para depuración
        for (String campo : campos) {
            System.out.println(campo);
        }

        // Obtener datos del formulario y guardar la solicitud
        solicitud.obtenerDatosSolicitudFormulario(campos[0], campos[1].replace("/", "-"), campos[2], campos[3], campos[4], campos[5]);
        solicitud.guardarSolicitud();
    }

    /**
     * Procesa el movimiento y actualiza los datos en la base de datos.
     * 
     * @param r Datos del movimiento como cadena
     */
    public void movimiento(String r) {
        // Limpiar y dividir el contenido en partes
        String contenido = r.replace("MOVIMIENTO(", "").replace(");", "").replace("”", "").replace("\"", "");
        String[] campos = contenido.split(",");

        // Mostrar datos para depuración
        for (String campo : campos) {
            System.out.println(campo);
        }

        // Obtener datos del formulario y guardar el movimiento
        movimiento.obtenerDatosMovimientoFormulario(campos[0].replace("-", "").replace(" ", ""), campos[1].replace("/", "-"), campos[4], campos[3], Double.valueOf(campos[5]), campos[2].replaceAll("\\s", ""));
        movimiento.obtenerMontoTotalActual(campos[0].replace("-", "").replace(" ", ""));
        movimiento.definirSaldo(campos[0].replace("-", "").replace(" ", ""));
        movimiento.guardarMovimiento();
    }

    /**
     * Consulta la información de la tarjeta.
     * 
     * @param r Datos de la tarjeta como cadena
     */
    public void consulta(String r) {
        // Limpiar y dividir el contenido en partes
        String contenido = r.replace("CONSULTAR_TARJETA(", "").replace(");", "").replace("”", "").replace("\"", "");
        String[] campos = contenido.split(",");

        // Mostrar datos para depuración
        for (String campo : campos) {
            System.out.println(campo);
        }

        // Obtener datos de la tarjeta y validar
        consulta.obtenerNumeroDeTarjeta(campos[0].replace("-", "").replace(" ", ""));
        consulta.validarTarjeta();
    }

    /**
     * Procesa la cancelación de una tarjeta.
     * 
     * @param r Datos de la cancelación como cadena
     */
    public void cancelacion(String r) {
        // Limpiar y dividir el contenido en partes
        String contenido = r.replace("CANCELACION_TARJETA(", "").replace(");", "").replace("”", "").replace("\"", "");
        String[] campos = contenido.split(",");

        // Mostrar datos para depuración
        for (String campo : campos) {
            System.out.println(campo);
        }

        // Recibir datos de la cancelación y desactivar la tarjeta
        cancelacion.recibirDatos(campos[0].replace("-", "").replace(" ", ""));
        cancelacion.desactivarTarjeta();
    }

    private boolean existeSolicitud;
    private boolean aceptadaSolicitud;

    /**
     * Procesa la autorización de una tarjeta.
     * 
     * @param r Datos de la autorización como cadena
     */
    public void autorizacion(String r) {
        // Limpiar el contenido
        String contenido = r.replace("AUTORIZACION_TARJETA(", "").replace(");", "").replace("”", "").replace("\"", "");
        int solicitudId = Integer.parseInt(contenido.replace("\n", "").replace("\r", ""));

        existeSolicitud = autorizacion.consulta(solicitudId);

        if (existeSolicitud) {
            aceptadaSolicitud = autorizacion.aceptacion(solicitudId);
        }

        if (!existeSolicitud) {
            JOptionPane.showMessageDialog(null, "La solicitud indicada no existe", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            if (aceptadaSolicitud) {
                JOptionPane.showMessageDialog(null, "Solicitud aceptada.", "Información", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Solicitud rechazada", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
