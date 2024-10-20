package Backend.revistas;

import JPA.Controladora;
import JPA.Revista;
import JPA.bloqueoAddsRevista;
import JPA.revistaEtiqueta;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/*
 *
 * @author carlosrodriguez
 */
public class crearRevista {

    private final Controladora controladora = new Controladora();

    public String proceso(Revista revista) throws Exception {
        boolean revistaCreada = false;
        String mensaje = "";

        if (revista.getTitulo().isEmpty()) {
            mensaje = "El titulo esta vacio";
        } else if (revista.getTitulo().length() > 40 || revista.getTitulo().length() < 5) {
            mensaje = "El titulo debe de ser mayor a 5 y menor a 40 caracteres";
        } else if (revista.getDescripcion().isEmpty()) {
            mensaje = "Ingrese una descripcion ya que esta vacia";
        } else if (revista.getDescripcion().length() > 1000) {
            mensaje = "Ingrese una descripcion menor a 1000 caracteres";
        } else if (revista.getFechaPublicacionTexto().isEmpty()) {
            mensaje = "Ingrese una fecha valida";
        } else if (revista.getCategoria().isEmpty()) {
            mensaje = "Ingrese una categoria";
        } else {
            revista.setIdRevista(crearIdAnuncio());
            revista.iniciarValores();
            generarFechaPublicacion(revista);
            controladora.crearRevista(revista);

            darAltaRevistaAnuncios(revista.getIdRevista());
            
            for (String etiqueta : revista.getEtiquetas()) {
                revistaEtiqueta revistaEtiqueta = new revistaEtiqueta(revista.getIdRevista(), etiqueta);
                controladora.guardarTags(revistaEtiqueta);
            }
            revistaCreada = true;
            mensaje = "Se ha creado su revista con exito";
        }

      return "{\"message\":\"" + mensaje + "\",\"verificacion\":\"" + revistaCreada + "\"}";

    }

    private void generarFechaPublicacion(Revista revista) {

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Corrige mm a MM
        LocalDate fechaLocalDate = LocalDate.parse(revista.getFechaPublicacionTexto(), formato);

        // Convertir LocalDate a Date
        Date fechaPublicacion = java.sql.Date.valueOf(fechaLocalDate);
        revista.setFechaPublicacion((java.sql.Date) fechaPublicacion);

    }

    public String crearIdAnuncio() throws Exception {
        String idAdd = "";
        int contador = 0;
        boolean idGenerado = false;

        while (!idGenerado) {

            String idRevista = String.format("%016d", contador);

            if (!controladora.buscarRevista(idRevista)) {

                idGenerado = true;
                idAdd = idRevista;
            } else {
                contador++;
            }

        }

        return idAdd;

    }
    
    
    private void darAltaRevistaAnuncios(String idRevista) throws Exception{
    
        bloqueoAddsRevista bloqueoAddsRevista=new bloqueoAddsRevista(idRevista);
        
        this.controladora.darAltaRevistaAnucios(bloqueoAddsRevista);
    
    }

}
