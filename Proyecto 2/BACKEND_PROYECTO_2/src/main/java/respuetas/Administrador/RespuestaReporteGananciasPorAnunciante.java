package respuetas.Administrador;



import JPA.Ingreso;
import JPA.Usuario;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author carlosrodriguez
 */
public class RespuestaReporteGananciasPorAnunciante {

    private String mensaje;
    List<Usuario> anunciantes = new ArrayList<>();
      List<Double> ganaciasPorAnunciante = new ArrayList<>();
        List<List<Ingreso>> ingresos = new ArrayList<>();
        List<List<String>> tiposDeAnuncio = new ArrayList<>();
        private String pdf;
    private boolean procesoExitoso;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<Usuario> getAnunciantes() {
        return anunciantes;
    }

    public void setAnunciantes(List<Usuario> anunciantes) {
        this.anunciantes = anunciantes;
    }

    public boolean isProcesoExitoso() {
        return procesoExitoso;
    }

    public void setProcesoExitoso(boolean procesoExitoso) {
        this.procesoExitoso = procesoExitoso;
    }

    public List<List<Ingreso>> getIngresos() {
        return ingresos;
    }

    public void setIngresos(List<List<Ingreso>> ingresos) {
        this.ingresos = ingresos;
    }

    public List<List<String>> getTiposDeAnuncio() {
        return tiposDeAnuncio;
    }

    public void setTiposDeAnuncio(List<List<String>> tiposDeAnuncio) {
        this.tiposDeAnuncio = tiposDeAnuncio;
    }

    public List<Double> getGanaciasPorAnunciante() {
        return ganaciasPorAnunciante;
    }

    public void setGanaciasPorAnunciante(List<Double> ganaciasPorAnunciante) {
        this.ganaciasPorAnunciante = ganaciasPorAnunciante;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

  
    
    

}
