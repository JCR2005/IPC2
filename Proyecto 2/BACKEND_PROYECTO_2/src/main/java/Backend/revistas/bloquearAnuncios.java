/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.revistas;

import JPA.Controladora;
import JPA.Revista;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author carlosrodriguez
 */
public class bloquearAnuncios {
    
    
    private Controladora controladora = new Controladora();
    private List<Revista> revistas = new ArrayList<>();

    
      public Map<String, List<?>> procesoLista(String usuario) {

        obtenerRevistas(usuario);

        // Usamos un Map para enviar múltiples arreglos
        Map<String, List<?>> response = new HashMap<>();

        response.put("listaAnuncios", this.revistas);

        return response;
    }
      
      private void obtenerRevistas(String usuario) {
        List<Revista> revistas = this.controladora.obtenerRevistas();

        this.revistas = revistas.stream()
                .filter(revista -> revista.getAutor().endsWith(usuario))
                .filter(revista -> revista.isAnuncios())
                .collect(Collectors.toList());

    }
    
}
