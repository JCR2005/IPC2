package Backend.Suscritor;

import JPA.Controladora;
import JPA.Revista;
import JPA.Suscripciòn;
import java.sql.Date;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import respuetas.Suscriptor.RespuetaSuscripciòn;

/**
 *
 * @author carlosrodriguez
 */
public class CrearSuscripciòn {

    private Controladora controladora = new Controladora();

    private RespuetaSuscripciòn respuesta = new RespuetaSuscripciòn();

    public RespuetaSuscripciòn obtenerListaDeRevistas(String usuario) {
        try {
            
            obtenerRevistas(usuario);
            
        } catch (Exception e) {
            this.respuesta.setMensaje("Algo salio mal intenta mas tarde");
            this.respuesta.setProcesoExitoso(false);
        }

        return this.respuesta;
    }

    private void obtenerRevistas(String usuario) {
        
         List<Revista> revistasAprobadas=this.controladora.obtenerRevistas();
         List<Suscripciòn> suscripciòns=this.controladora.obtenerSuscripciones();
        
        for (int i = 0; i <revistasAprobadas.size() ; i++) {
              boolean suscrito=false;
            for (int j = 0; j < suscripciòns.size(); j++) {
                     System.out.println("entro");
                     
                     System.out.println(suscripciòns.get(j).getIdRevista()+"|"+revistasAprobadas.get(i).getIdRevista());
                     System.out.println(suscripciòns.get(j).getIdUsuario()+"|"+usuario);
                     
                if (suscripciòns.get(j).getIdRevista().equals(revistasAprobadas.get(i).getIdRevista()) &&suscripciòns.get(j).getIdUsuario().equals(usuario)) {
                    suscrito=true;
                 
                }
              
            }
            if (!suscrito &&revistasAprobadas.get(i).isAprobacion() ) {
                   this.respuesta.getRevistas().add(revistasAprobadas.get(i));
            }
            
        }
        
        for (int i = 0; i <     this.respuesta.getRevistas().size(); i++) {
            System.out.println(    this.respuesta.getRevistas().get(i).getIdRevista());
        }

        if (this.respuesta.getRevistas() == null) {
            this.respuesta.setMensaje("Algo salio mal intenta mas tarde");
            this.respuesta.setProcesoExitoso(false);
        }
        
    }

    public RespuetaSuscripciòn suscribirRevista(Suscripciòn suscripciòn) {

        try {

            if (!validarExistenciaUsuario(suscripciòn.getIdUsuario())) {
                this.respuesta.setProcesoExitoso(false);
                this.respuesta.setMensaje("No se encontro el usuario");
                return this.respuesta;
            }
            if (!generarFechaPublicacion(suscripciòn)) {
                this.respuesta.setProcesoExitoso(false);
                this.respuesta.setMensaje("Ingrese una fecha valida");
                return this.respuesta;
            }

            this.controladora.crearSuscripciòn(suscripciòn);
            this.respuesta.setMensaje("Te has suscrito a esta revista exitosamente");
            return this.respuesta;

        } catch (Exception e) {
             this.respuesta.setProcesoExitoso(false);
                this.respuesta.setMensaje("Ociurrio algo inesperado, contacta a soporte si el problem persiste");
            return this.respuesta;
        }

    }

    private boolean generarFechaPublicacion(Suscripciòn suscripción) {

        try {
            // Ajuste para manejar el formato con zona horaria
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
            OffsetDateTime fechaOffsetDateTime = OffsetDateTime.parse(suscripción.getFechaSuscripcionTexto(), formato);

            // Convertir OffsetDateTime a LocalDate
            LocalDate fechaLocalDate = fechaOffsetDateTime.toLocalDate();

            // Convertir LocalDate a Date para MySQL
            Date fechaPublicacion = java.sql.Date.valueOf(fechaLocalDate);
            suscripción.setFechaSuscricion((java.sql.Date) fechaPublicacion);

            return true;
        } catch (Exception e) {
            return false;
        }

    }

    private boolean validarExistenciaUsuario(String idUsuario) throws Exception {
        return this.controladora.buscarUsuarios(idUsuario);
    }

}
