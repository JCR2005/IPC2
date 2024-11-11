package Reportes.jasperReports;

import JPA.Comentario;
import JPA.Ingreso;
import JPA.Suscripciòn;

import java.util.ArrayList;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import java.util.List;

public class ReportePagosJasperReport implements JRDataSource {

    private List<Ingreso> listaDatos = new ArrayList<>();  // Cambia a List<Comentario>
    private int index;

    public ReportePagosJasperReport(List<Ingreso> ingresosFiltradas) {
        index = -1;
        this.listaDatos = ingresosFiltradas;

        // Para depuración, imprime todos los IDs de los comentarios cargados.
        for (Ingreso ingreso : listaDatos) {
            System.out.println("Comentario ID: " + ingreso.getIdIngerso());
        }
    }

    public ReportePagosJasperReport() {
    }

    @Override
    public boolean next() throws JRException {
        index++;
        System.out.println("Índice actual: " + index);  // Para depuración
        return index < listaDatos.size();
    }

    @Override
    public Object getFieldValue(JRField jrField) throws JRException {
        Ingreso ingreso = listaDatos.get(index);
        String fieldName = jrField.getName();
        return switch (fieldName) {
            case "idIngerso" ->
                ingreso.getIdIngerso();
            case "monto" ->
                ingreso.getMonto();
            case "idVinculado" ->
                ingreso.getIdVinculado();
            case "proposito" ->
                ingreso.getProposito();
            case "fecha" ->
                ingreso.getFecha();
            default ->
                throw new AssertionError("Campo desconocido: " + fieldName);
        };
    }

    public JRDataSource getDataSource(List<Ingreso> ingresosFiltradas) {
        return new ReportePagosJasperReport(ingresosFiltradas);
    }
}
