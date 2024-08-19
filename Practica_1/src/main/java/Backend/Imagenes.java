package Backend;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * La clase Imagenes maneja la carga y ajuste de imágenes para etiquetas JLabel.
 * 
 * Autor: Carlos Rodriguez
 */
public class Imagenes {
    
    // Ruta de la imagen del logo
    private static final String LOGO_PATH = "Imagenes/logo.png";
    
    /**
     * Establece la imagen del logo en el JLabel proporcionado.
     * 
     * @param label El JLabel en el que se mostrará la imagen del logo.
     */
    public static void setLogo(JLabel label) {
        try {
            // Obtener la URL de la imagen del logo
            URL imageUrl = Imagenes.class.getClassLoader().getResource(LOGO_PATH);

            // Verificar si la URL de la imagen es nula
            if (imageUrl == null) {
                throw new IllegalArgumentException("No se encontró la imagen en la ruta: " + LOGO_PATH);
            }

            // Crear un ImageIcon a partir de la URL
            ImageIcon imageIcon = new ImageIcon(imageUrl);
            // Escalar la imagen para que se ajuste al tamaño del JLabel
            Image scaledImage = imageIcon.getImage().getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
            // Crear un nuevo ImageIcon con la imagen escalada
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            // Establecer el icono escalado en el JLabel
            label.setIcon(scaledIcon);

        } catch (Exception e) {
            // Mostrar un mensaje de error en caso de excepción
            System.err.println("Error al cargar la imagen: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
