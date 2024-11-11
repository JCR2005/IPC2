package Reportes.jasperReports;

import JPA.Comentario;
import JPA.Suscripciòn;

import java.util.ArrayList;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import java.util.List;
public class ReporteSuscripcionesJasperReport implements JRDataSource {

  
    private  List<Suscripciòn> listaDatos = new ArrayList<>();  // Cambia a List<Comentario>
    private int index;

    public ReporteSuscripcionesJasperReport(List<Suscripciòn> suscripcionesFiltradas) {
        index = -1;
        this.listaDatos = suscripcionesFiltradas;
        
        // Para depuración, imprime todos los IDs de los comentarios cargados.
        for (Suscripciòn suscripciòn : listaDatos) {
            System.out.println("Comentario ID: " + suscripciòn.getIdSuscripcion());
        }
    }

    public ReporteSuscripcionesJasperReport(){}

  

    @Override
    public boolean next() throws JRException {
        index++;
        System.out.println("Índice actual: " + index);  // Para depuración
        return index < listaDatos.size();
    }

    @Override
    public Object getFieldValue(JRField jrField) throws JRException {
        Suscripciòn suscripciòn = listaDatos.get(index);
        String fieldName = jrField.getName();
        return switch (fieldName) {
            case "idSuscripcion" -> suscripciòn.getIdSuscripcion();
            case "idUsuario" -> suscripciòn.getIdUsuario();
            case "idRevista" -> suscripciòn.getIdRevista();
            case "fechaSuscricion" -> suscripciòn.getFechaSuscricion();
            default -> throw new AssertionError("Campo desconocido: " + fieldName);
        };
    }

    public  JRDataSource getDataSource(List<Suscripciòn> suscripcionesFiltradas) {
        return new ReporteSuscripcionesJasperReport(suscripcionesFiltradas);
    }
}
