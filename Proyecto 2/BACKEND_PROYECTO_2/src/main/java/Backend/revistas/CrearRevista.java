package Backend.revistas;

import JPA.Controladora;
import JPA.Revista;
import JPA.bloqueoAddsRevista;
import JPA.revistaEtiqueta;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import respuetas.Editor.RespuestaCreaciòn;

/**
 * Clase encargada del proceso de creación de una revista en el sistema.
 * Realiza la validación de los datos, creación del ID único para la revista,
 * asignación de fecha de publicación y la persistencia en la base de datos.
 */
public class CrearRevista {

    // Respuesta del proceso de creación
    private RespuestaCreaciòn respuesta = new RespuestaCreaciòn();
    
    // Instancia de la clase Controladora para interactuar con la base de datos
    private final Controladora controladora = new Controladora();

    /**
     * Método principal que procesa la creación de una nueva revista.
     * 
     * @param revista Objeto de tipo Revista con los datos a crear
     * @return RespuestaCreaciòn con el resultado del proceso
     * @throws Exception Si ocurre un error durante el proceso de creación
     */
    public RespuestaCreaciòn proceso(Revista revista) throws Exception {

        // Validaciones de los campos obligatorios
        if (revista.getTitulo().isEmpty()) {
            respuesta.setMensaje("El título está vacío.");
        } else if (revista.getTitulo().length() > 40 || revista.getTitulo().length() < 5) {
            respuesta.setMensaje("El título debe ser mayor a 5 y menor a 40 caracteres.");
        } else if (revista.getDescripcion().isEmpty()) {
            respuesta.setMensaje("La descripción está vacía.");
        } else if (revista.getDescripcion().length() > 1000 || revista.getDescripcion().length()<5) {
            respuesta.setMensaje("La descripción no puede exceder los 1000 caracteres y tiene que esxeder a 5 o mas caracteres");
        } else if (revista.getFechaPublicacionTexto().isEmpty()) {
            respuesta.setMensaje("Ingrese una fecha válida.");
        } else if (revista.getCategoria().isEmpty()) {
            respuesta.setMensaje("Ingrese una categoría.");
        } else {
            // Si las validaciones son correctas, se procede con la creación
            revista.setIdRevista(crearIdAnuncio()); // Generar ID único para la revista
            revista.iniciarValores(); // Inicializar valores de la revista
            generarFechaPublicacion(revista); // Asignar la fecha de publicación
            controladora.crearRevista(revista); // Guardar la revista en la base de datos

            // Dar de alta los anuncios asociados a la revista
            darAltaRevistaAnuncios(revista.getIdRevista());

            // Guardar las etiquetas asociadas a la revista
            for (String etiqueta : revista.getEtiquetas()) {
                revistaEtiqueta nuevaEtiqueta = new revistaEtiqueta(revista.getIdRevista(), etiqueta);
                controladora.guardarTags(nuevaEtiqueta);
            }

            // Respuesta de éxito
            this.respuesta.setProcesoExitoso(true);
            respuesta.setMensaje("Se ha creado su revista con éxito.");
        }

        return respuesta;
    }

    /**
     * Genera la fecha de publicación a partir de un texto con formato 'yyyy-MM-dd'.
     * 
     * @param revista Objeto Revista donde se almacenará la fecha de publicación
     */
    private void generarFechaPublicacion(Revista revista) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaLocalDate = LocalDate.parse(revista.getFechaPublicacionTexto(), formato);
        
        // Convertir LocalDate a Date
        Date fechaPublicacion = java.sql.Date.valueOf(fechaLocalDate);
        revista.setFechaPublicacion(fechaPublicacion);
    }

    /**
     * Crea un ID único para la revista mediante un contador.
     * El ID tiene un formato de 16 dígitos, comenzando desde 0 y asegurando que no se repita.
     * 
     * @return El ID único generado para la revista
     * @throws Exception Si ocurre un error al intentar generar el ID
     */
    public String crearIdAnuncio() throws Exception {
        String id = "";
        int contador = 0;
        boolean idGenerado = false;

        // Buscar un ID único que no exista en el sistema
        while (!idGenerado) {
            String idRevista = String.format("%016d", contador); // Formateo a 16 dígitos

            // Verificar si el ID ya existe
            if (!controladora.buscarRevista(idRevista)) {
                idGenerado = true;
                id = idRevista; // ID único encontrado
            } else {
                contador++; // Incrementar contador si el ID ya existe
            }
        }

        return id;
    }

    /**
     * Da de alta los anuncios asociados a la revista en el sistema.
     * 
     * @param idRevista El ID de la revista para la cual se dará de alta los anuncios
     * @throws Exception Si ocurre un error al dar de alta los anuncios
     */
    private void darAltaRevistaAnuncios(String idRevista) throws Exception {
        bloqueoAddsRevista bloqueo = new bloqueoAddsRevista(idRevista);
        controladora.darAltaRevistaAnucios(bloqueo); // Registrar bloqueo de anuncios para la revista
    }
}
