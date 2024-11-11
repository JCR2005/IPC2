package Reportes.jasperReports;

import JPA.Ingreso;
import JPA.Suscripciòn;

import java.util.ArrayList;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import java.util.List;

public class ReporteAnunciosCompradosJasperReport implements JRDataSource {

    private List<Ingreso> listaDatos = new ArrayList<>();  // Cambia a List<Comentario>
        private List<String> tiposAnuncio = new ArrayList<>();  // Cambia a List<Comentario>
    private int index;

    public ReporteAnunciosCompradosJasperReport(List<Ingreso> ingresoFiltradas ,List<String> tiposAnuncio) {
        index = -1;
        this.tiposAnuncio=tiposAnuncio;
        this.listaDatos = ingresoFiltradas;

        // Para depuración, imprime todos los IDs de los comentarios cargados.
        for (Ingreso ingreso : listaDatos) {
            System.out.println("Comentario ID: " + ingreso.getIdIngerso());
        }
    }

    public ReporteAnunciosCompradosJasperReport() {
    }

    @Override
    public boolean next() throws JRException {
        index++;
        System.out.println("Índice actual: " + index);  // Para depuración
        return index < listaDatos.size();
    }

    @Override
    public Object getFieldValue(JRField jrField) throws JRException {
        String tipoAnuncio=tiposAnuncio.get(index);
        Ingreso ingreso = listaDatos.get(index);
        String fieldName = jrField.getName();
        return switch (fieldName) {
            case "idIngreso" ->
                ingreso.getIdIngerso();
            case "idUsuario" ->
                ingreso.getIdUsuario();
            case "idVinculado" ->
                ingreso.getIdVinculado();
            case "tipoAnuncio" ->
                tipoAnuncio;
            case "monto" ->
                ingreso.getMonto();
            case "proposito" ->
                ingreso.getProposito();
            case "fecha" ->
                ingreso.getFecha();
            default ->
                throw new AssertionError("Campo desconocido: " + fieldName);
        };
    }

    public JRDataSource getDataSource(List<Ingreso> ingresosFiltradas, List<String> tiposAnuncio) {
        return new ReporteAnunciosCompradosJasperReport(ingresosFiltradas,tiposAnuncio);
    }
}
