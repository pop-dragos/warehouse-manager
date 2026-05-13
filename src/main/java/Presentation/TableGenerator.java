package Presentation;

import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

public class TableGenerator {

    /**
     * Generează un model de tabel folosind Reflexia și Java Streams.
     * @param objects Lista de obiecte (Client, Product, etc.)
     * @return DefaultTableModel gata de a fi pus într-un JTable
     */
    public static <T> DefaultTableModel generateTable(List<T> objects) {
        if (objects == null || objects.isEmpty()) {
            return new DefaultTableModel();
        }

        // 1. Extragerea antetului (Column Names) folosind Reflection + Streams
        // Luăm câmpurile clasei (ex: id, name, price...)
        Field[] fields = objects.get(0).getClass().getDeclaredFields();

        // Cerință Lambda/Stream: transformăm array-ul de Fields într-un Vector de String-uri (numele coloanelor)
        Vector<String> columnNames = Arrays.stream(fields)
                .map(Field::getName)
                .collect(Collectors.toCollection(Vector::new));

        // 2. Extragerea datelor (Rows) folosind Reflection + Streams + Lambda
        // Fiecare obiect din listă devine un Vector de obiecte (un rând în tabel)
        Vector<Vector<Object>> data = objects.stream()
                .map(obj -> {
                    Vector<Object> row = new Vector<>();
                    Arrays.stream(fields).forEach(field -> {
                        try {
                            field.setAccessible(true);
                            Object value = field.get(obj);
                            row.add(value);
                        } catch (IllegalAccessException e) {
                            row.add("N/A");
                        }
                    });
                    return row;
                })
                .collect(Collectors.toCollection(Vector::new));

        return new DefaultTableModel(data, columnNames);
    }
}