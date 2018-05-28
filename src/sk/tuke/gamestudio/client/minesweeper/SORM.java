package sk.tuke.gamestudio.client.minesweeper;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SORM {

    private Connection connection;

    public SORM(Connection connection) {
        this.connection = connection;
    }

    public String getSelectString(Class<?> clazz) {
        return String.format("SELECT %s FROM %s",
                getColumnsString(clazz, this::getColumnName),
                getTableName(clazz));
    }

    public <T> List<T> select(Class<T> clazz) throws Exception {
        String command = getSelectString(clazz);
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(command)) {
            List<T> result = new ArrayList<>();

            while (rs.next()) {
                T object = clazz.newInstance();
                int index = 1;
                for (Field field : clazz.getDeclaredFields()) {
                    setValueToFieldFromResultSet(rs, object, index, field);
                    index++;
                }
                result.add(object);
            }
            return result;
        }
    }

    public String getInsertString(Class<?> clazz) {
        return String.format("INSERT INTO %s (%s) VALUES (%s)",
                getTableName(clazz),
                getColumnsString(clazz, this::getColumnName),
                getColumnsString(clazz, f -> "?")
        );
    }

    public void insert(Object o) throws Exception {
        Class<?> clazz = o.getClass();
        String command = getInsertString(clazz);

        try (PreparedStatement stmt = connection.prepareStatement(command)) {
            int index = 1;
            for (Field field : clazz.getDeclaredFields()) {
                setValueOfFielToPS(o, stmt, index, field);
                index++;
            }

            stmt.executeUpdate();
        }
    }

    public String getTruncateString(Class<?> clazz) {
        return String.format("TRUNCATE TABLE %s", getTableName(clazz));
    }

    public void truncate(Class<?> clazz) throws Exception {
        String command = getTruncateString(clazz);

        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(command);
        }
    }

    private void setValueOfFielToPS(Object o, PreparedStatement stmt, int index, Field field) throws IllegalAccessException, SQLException {
        field.setAccessible(true);
        Object value = field.get(o);
        stmt.setObject(index, value);
    }

    private String getTableName(Class<?> clazz) {
        return clazz.getSimpleName();
    }

    private String getColumnName(Field field) {
        return field.getName();
    }

    private String getColumnsString(Class<?> clazz, Function<Field, String> mapFunction, String delimiter) {
        return Arrays.stream(clazz.getDeclaredFields())
                .map(mapFunction).collect(Collectors.joining(delimiter));
    }

    private String getColumnsString(Class<?> clazz, Function<Field, String> mapFunction) {
        return getColumnsString(clazz, mapFunction, ", ");
    }

    private <T> void setValueToFieldFromResultSet(ResultSet rs, T object, int index, Field field) throws SQLException, IllegalAccessException {
        field.setAccessible(true);
        Object value = rs.getObject(index);
        field.set(object, value);
    }
}
