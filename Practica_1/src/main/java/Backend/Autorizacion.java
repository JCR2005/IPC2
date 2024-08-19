package Backend;

import Conexion.Base_De_Datos;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 * La clase Autorizacion maneja la autorización y creación de tarjetas de crédito
 * basadas en el número de solicitud proporcionado. Realiza consultas a la base de datos
 * para validar y aprobar solicitudes de tarjetas, y crea nuevas entradas de tarjetas de crédito.
 * 
 * Autor: Carlos Rodriguez
 */
public class Autorizacion {

    private static final double LIMITE_NACIONAL = 5000;
    private static final double LIMITE_REGIONAL = 10000;
    private static final double LIMITE_INTERNACIONAL = 20000;

    private static final String FORMATO_NACIONAL = "42563102654";
    private static final String FORMATO_REGIONAL = "42563102655";
    private static final String FORMATO_INTERNACIONAL = "42563102656";

    /**
     * Verifica si existe una solicitud con el número dado en la base de datos.
     * 
     * @param numeroSolicitud El número de solicitud a verificar.
     * @return true si la solicitud existe, false en caso contrario.
     */
    public boolean consulta(int numeroSolicitud) {
        String query = "SELECT 1 FROM solicitud WHERE Numero_Solicitud = " + numeroSolicitud;
        
        try (Statement statement = Base_De_Datos.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            return resultSet.next();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Número de solicitud inválido", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     * Acepta o rechaza una solicitud basándose en el número proporcionado y actualiza
     * el estado de la solicitud en la base de datos. Crea una nueva tarjeta si es aceptada.
     * 
     * @param numeroSolicitud El número de solicitud a procesar.
     * @return true si la solicitud es aceptada, false si es rechazada.
     */
    public boolean aceptacion(int numeroSolicitud) {
        String query = "SELECT Salario, Tipo, Nombre, Direccion FROM solicitud WHERE Numero_Solicitud = " + numeroSolicitud;

        try (Statement statement = Base_De_Datos.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            if (resultSet.next()) {
                double salario = resultSet.getDouble("Salario");
                String tipo = resultSet.getString("Tipo");
                String nombre = resultSet.getString("Nombre");
                String direccion = resultSet.getString("Direccion");

                double limite = calcularLimite(tipo, salario);

                boolean aprobada = verificarLimite(tipo, limite);
                actualizarEstadoSolicitud(numeroSolicitud, aprobada);
                creacionTarjeta(tipo, nombre, direccion);

                return aprobada;
            }

        } catch (SQLException e) {
            System.out.println("Error en la aceptación: " + e.getMessage());
        }

        return false;
    }

    /**
     * Calcula el límite basado en el tipo de tarjeta y el salario.
     * 
     * @param tipo El tipo de tarjeta.
     * @param salario El salario del solicitante.
     * @return El límite calculado.
     */
    private double calcularLimite(String tipo, double salario) {
        return salario * 0.6;
    }

    /**
     * Verifica si el límite cumple con los requisitos para el tipo de tarjeta.
     * 
     * @param tipo El tipo de tarjeta.
     * @param limite El límite calculado.
     * @return true si el límite cumple con los requisitos, false en caso contrario.
     */
    private boolean verificarLimite(String tipo, double limite) {
        switch (tipo) {
            case "NACIONAL":
                return limite >= LIMITE_NACIONAL;
            case "REGIONAL":
                return limite >= LIMITE_REGIONAL;
            case "INTERNACIONAL":
                return limite >= LIMITE_INTERNACIONAL;
            default:
                return false;
        }
    }

    /**
     * Actualiza el estado de la solicitud en la base de datos.
     * 
     * @param numeroSolicitud El número de solicitud a actualizar.
     * @param aprobada true si la solicitud fue aprobada, false si fue rechazada.
     */
    private void actualizarEstadoSolicitud(int numeroSolicitud, boolean aprobada) {
        String estado = aprobada ? "ACEPTADA" : "RECHAZADA";
        String query = "UPDATE solicitud SET Estado = '" + estado + "' WHERE Numero_Solicitud = " + numeroSolicitud;

        try (Statement statement = Base_De_Datos.getConnection().createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("Error al actualizar el estado de la solicitud: " + e.getMessage());
        }
    }

    /**
     * Crea una nueva entrada de tarjeta en la base de datos.
     * 
     * @param tipoTarjeta El tipo de la tarjeta (NACIONAL, REGIONAL, INTERNACIONAL).
     * @param nombre El nombre asociado con la tarjeta.
     * @param direccion La dirección asociada con la tarjeta.
     */
    private void creacionTarjeta(String tipoTarjeta, String nombre, String direccion) {
        String formato = obtenerFormato(tipoTarjeta);
        int limite = obtenerLimite(tipoTarjeta);

        try (Statement statement = Base_De_Datos.getConnection().createStatement()) {
            boolean tarjetaGenerada = false;
            int contador = 0;

            while (!tarjetaGenerada) {
                String extension = String.format("%05d", contador);
                String numeroTarjeta = formato + extension;

                if (!existeNumeroTarjeta(statement, numeroTarjeta)) {
                    insertarTarjeta(statement, numeroTarjeta, tipoTarjeta, limite, nombre, direccion);
                    tarjetaGenerada = true;
                } else {
                    contador++;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error en la creación de la tarjeta: " + e.getMessage());
        }
    }

    /**
     * Obtiene el formato de número de tarjeta basado en el tipo.
     * 
     * @param tipo El tipo de tarjeta.
     * @return El formato de número de tarjeta.
     */
    private String obtenerFormato(String tipo) {
        switch (tipo) {
            case "NACIONAL":
                return FORMATO_NACIONAL;
            case "REGIONAL":
                return FORMATO_REGIONAL;
            case "INTERNACIONAL":
                return FORMATO_INTERNACIONAL;
            default:
                throw new IllegalArgumentException("Tipo de tarjeta desconocido: " + tipo);
        }
    }

    /**
     * Obtiene el límite de tarjeta basado en el tipo.
     * 
     * @param tipo El tipo de tarjeta.
     * @return El límite de la tarjeta.
     */
    private int obtenerLimite(String tipo) {
        switch (tipo) {
            case "NACIONAL":
                return (int) LIMITE_NACIONAL;
            case "REGIONAL":
                return (int) LIMITE_REGIONAL;
            case "INTERNACIONAL":
                return (int) LIMITE_INTERNACIONAL;
            default:
                throw new IllegalArgumentException("Tipo de tarjeta desconocido: " + tipo);
        }
    }

    /**
     * Verifica si el número de tarjeta ya existe en la base de datos.
     * 
     * @param statement El statement para ejecutar la consulta.
     * @param numeroTarjeta El número de tarjeta a verificar.
     * @return true si el número de tarjeta ya existe, false en caso contrario.
     * @throws SQLException Si ocurre un error durante la consulta.
     */
    private boolean existeNumeroTarjeta(Statement statement, String numeroTarjeta) throws SQLException {
        String query = "SELECT 1 FROM Datos_Tarjeta WHERE Numero_Tarjeta = '" + numeroTarjeta + "'";
        try (ResultSet resultSet = statement.executeQuery(query)) {
            return resultSet.next();
        }
    }

    /**
     * Inserta una nueva tarjeta en la base de datos.
     * 
     * @param statement El statement para ejecutar la consulta.
     * @param numeroTarjeta El número de tarjeta a insertar.
     * @param tipoTarjeta El tipo de la tarjeta.
     * @param limite El límite de la tarjeta.
     * @param nombre El nombre asociado con la tarjeta.
     * @param direccion La dirección asociada con la tarjeta.
     * @throws SQLException Si ocurre un error durante la inserción.
     */
    private void insertarTarjeta(Statement statement, String numeroTarjeta, String tipoTarjeta, int limite, String nombre, String direccion) throws SQLException {
        String query = "INSERT INTO Datos_Tarjeta (Numero_Tarjeta, Tipo, Limite, Nombre, Direccion, Estado) " +
                       "VALUES ('" + numeroTarjeta + "','" + tipoTarjeta + "','" + limite + "','" + nombre + "','" + direccion + "','ACTIVA')";
        statement.executeUpdate(query);
    }
}
