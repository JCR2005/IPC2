/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package Backend;
import Conexion.Base_De_Datos;
import Fromted.Ventana_Principal;

import javax.swing.*;
/**
 *
 * @author carlosrodriguez
 */


public class Principal_Main {
    
    static Ventana_Principal vp = new Ventana_Principal();
    
    public static void main(String[] args) {
        Base_De_Datos connexion= new Base_De_Datos ();
        vp.setLocationRelativeTo(null);
        vp.setVisible(true);
        
    }
    
}

