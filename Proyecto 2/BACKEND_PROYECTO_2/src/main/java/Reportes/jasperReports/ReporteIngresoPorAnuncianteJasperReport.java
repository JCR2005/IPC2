package Reportes.jasperReports;

import JPA.Ingreso;
import JPA.Usuario;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class ReporteIngresoPorAnuncianteJasperReport implements JRDataSource {

    private List<List<Ingreso>> listaDatos = new ArrayList<>();  // Lista de ingresos agrupados por anunciante
    private List<Usuario> anunciantes = new ArrayList<>();       // Lista de anunciantes
    private List<Double> ganaciasPorAnunciante = new ArrayList<>();  // Lista de ganancias por anunciante

    private int index = 0;  // Índice del anunciante actual, comienza en 0
    private int subIndex = -1;  // Índice del ingreso dentro del anunciante actual

    // Constructor vacío
    public ReporteIngresoPorAnuncianteJasperReport() {}

    // Constructor con parámetros para inicializar los datos
    public ReporteIngresoPorAnuncianteJasperReport(List<List<Ingreso>> ingresosFiltradas, List<Usuario> anunciantes, List<Double> ganaciasPorAnunciante) {
        this.listaDatos = ingresosFiltradas;
        this.anunciantes = anunciantes;
        this.ganaciasPorAnunciante = ganaciasPorAnunciante;
    }

@Override
public boolean next() throws JRException {
    subIndex++;

    // Saltar al siguiente anunciante si no hay ingresos para el actual
    while (index < listaDatos.size() && listaDatos.get(index).isEmpty()) {
        index++;
        subIndex = 0;  // Reiniciar subIndex para el nuevo anunciante
    }

    // Si alcanzamos el final de los ingresos para el anunciante actual, avanzamos al siguiente anunciante
    if (subIndex >= listaDatos.get(index).size()) {
        index++;
        subIndex = 0;

        // Verificamos si hemos alcanzado el final de la lista de anunciantes
        while (index < listaDatos.size() && listaDatos.get(index).isEmpty()) {
            index++;
        }

        // Si hemos llegado al final de la lista, no hay más datos
        if (index >= listaDatos.size()) {
            return false;
        }
    }

    return true;  // Todavía hay datos que procesar
}


    @Override
    public Object getFieldValue(JRField jrField) throws JRException {
        String fieldName = jrField.getName();

        // Verificamos que el index sea válido antes de acceder a las listas
        if (index < listaDatos.size()) {
            Usuario anuncianteActual = anunciantes.get(index);
            Double gananciaActual = ganaciasPorAnunciante.get(index);
            List<Ingreso> ingresosAnunciante = listaDatos.get(index);

            // Obtenemos el ingreso actual
            Ingreso ingreso = (subIndex < ingresosAnunciante.size()) ? ingresosAnunciante.get(subIndex) : null;

            // Retornamos el valor correspondiente según el campo solicitado
            switch (fieldName) {
                case "idUsuario":
                    return (subIndex == 0) ? anuncianteActual.getUsuario() : null;  // Solo mostrar el anunciante en la primera fila
                case "ingresoTotal":
                    return (subIndex == 0) ? gananciaActual : null;  // Solo mostrar la ganancia total en la primera fila
                case "idIngerso":
                    return ingreso != null ? ingreso.getIdIngerso() : null;  // Mostrar el ingreso actual
                     case "separacion":
                         break;
                case "monto":
                    return ingreso != null ? ingreso.getMonto() : null;  // Mostrar el monto del ingreso
                case "tipoAnuncio":
                    return ingreso != null ? ingreso.getIdVinculado() : null;  // Mostrar el tipo de anuncio
                case "proposito":
                    return ingreso != null ? ingreso.getProposito() : null;  // Mostrar el propósito del ingreso
                case "fecha":
                    return ingreso != null ? ingreso.getFecha() : null;  // Mostrar la fecha del ingreso
                default:
                    throw new JRException("Campo desconocido: " + fieldName);
            }
        } else {
            throw new JRException("Índice fuera de límites: " + index);
        }
        return null;
    }

    // Método para obtener la instancia de JRDataSource para el reporte
    public JRDataSource getDataSource(List<List<Ingreso>> ingresosFiltradas, List<Usuario> anunciantes, List<Double> ganaciasPorAnunciante) {
        return new ReporteIngresoPorAnuncianteJasperReport(ingresosFiltradas, anunciantes, ganaciasPorAnunciante);
    }
}
