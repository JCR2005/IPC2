package Reportes.jasperReports;

import JPA.Comentario;
import JPA.MeGusta;
import JPA.Revista;

import java.util.ArrayList;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import java.util.List;

public class ReporteTop5ComentariosJasperReport implements JRDataSource {

    private List<Revista> idsRevistas = new ArrayList();

    private List<List<Comentario>> listaDatos = new ArrayList<>();  // Cambia a List<Comentario>
    private int index = 0;  // Índice del anunciante actual, comienza en 0
    private int subIndex = -1;  // Índice del ingreso dentro del anunciante actualList<List<Comentario>> comentariosFiltrados, List<Revista> titulos

    public ReporteTop5ComentariosJasperReport(List<List<Comentario>> comentariosFiltrados, List<Revista> titulos) {
        idsRevistas = titulos;
        listaDatos = comentariosFiltrados;

        for (int i = 0; i < idsRevistas.size(); i++) {
            System.out.println(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
            System.out.println(idsRevistas.get(i).getAutor());

        }
    }

    public ReporteTop5ComentariosJasperReport() {
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

        if (index < listaDatos.size()) {
            Revista anuncianteActual = idsRevistas.get(index);  // Título de la revista actual
            List<Comentario> comentarios = listaDatos.get(index);  // Lista de comentarios para esa revista

            // Verifica si hay un comentario válido para el subIndex actual
            Comentario comentarioActual = (subIndex < comentarios.size()) ? comentarios.get(subIndex) : null;
            String d = comentarioActual.getIdRevista();
            // Controla qué campo devolver según el nombre del campo y el índice
            switch (fieldName) {
                case "titulo":
                    // Solo mostramos el título (anuncianteActual) en la primera iteración de la lista de comentarios
                    return (subIndex == 0) ? anuncianteActual.getTitulo() : null;

                case "idComentario":
                    return comentarioActual != null ? comentarioActual.getIdComentario() : null;

                case "idUsuario":
                    return comentarioActual != null ? comentarioActual.getIdUsuario() : null;

                case "fechaRevista":
                    return comentarioActual != null ? anuncianteActual.getFechaPublicacion() : null;

                case "comentario":
                    return comentarioActual != null ? comentarioActual.getComentario() : null;

                case "idRevista":
                    // Devolvemos el idRevista, pero solo si estamos en la primera iteración para evitar repetición
                    return (subIndex == 0 && comentarioActual != null) ? anuncianteActual.getIdRevista() : null;

                case "autor":
                    // Devolvemos el idRevista, pero solo si estamos en la primera iteración para evitar repetición
                    return (subIndex == 0 && comentarioActual != null) ? anuncianteActual.getAutor() : null;

                case "fecha":
                    return comentarioActual != null ? comentarioActual.getFecha() : null;

                default:
                    throw new JRException("Campo desconocido: " + fieldName);
            }
        } else {
            throw new JRException("Índice fuera de límites: " + index);
        }
    }

    public JRDataSource getDataSource(List<List<Comentario>> comentariosFiltrados, List<Revista> titulos) {
        return new ReporteTop5ComentariosJasperReport(comentariosFiltrados, titulos);
    }
}
