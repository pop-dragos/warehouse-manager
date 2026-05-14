package Presentation;

import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

public class CreateTables {
    public static <T> DefaultTableModel generateTable(List<T> objects) {
        if (objects == null || objects.isEmpty()) {
            return new DefaultTableModel();
        }

        Field[] fields = objects.get(0).getClass().getDeclaredFields();

        Vector<String> columnNames = Arrays.stream(fields)
                .map(Field::getName)
                .collect(Collectors.toCollection(Vector::new));

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