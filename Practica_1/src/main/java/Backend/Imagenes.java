/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author carlosrodriguez
 */
public class Imagenes {
    
    // Ruta de la imagen del fondo 1
    private static final String logo = "Imagenes/logo.png";
    
    public static void logo(JLabel label) {
        try {
            URL imageUrl = Imagenes.class.getClassLoader().getResource(logo);

                ImageIcon imagen = new ImageIcon(imageUrl);
                Image imagenEscalada = imagen.getImage().getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH); // Cambia Image.SCALE_DEFAULT por Image.SCALE_SMOOTH para obtener una mejor calidad
                ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);
                label.setIcon(iconoEscalado);

        } catch (Exception e) {
            System.out.println("Error al cargar la imagen: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
