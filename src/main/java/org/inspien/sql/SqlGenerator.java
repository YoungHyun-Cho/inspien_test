package org.inspien.sql;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SqlGenerator {

//    private static final String SYSDATE = "TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS')";

    public static String generateInsertQuery(HashMap<String, String> data) {
        String tableName = data.get("tableName");
        Set<Map.Entry<String, String>> entrySet = data.entrySet();
        return "INSERT INTO " + tableName + columnsToStr(entrySet) + "VALUES" + valuesToStr(entrySet);
    }

    private static String columnsToStr(Set<Map.Entry<String, String>> entrySet) {
        StringBuilder columns = new StringBuilder();

        columns.append(" (");
        entrySet.stream()
                .filter(entry -> !entry.getKey().equals("tableName"))
                .forEach((entry) -> columns.append(entry.getKey() + ", "));
        columns.delete(columns.length() - 2, columns.length());
        columns.append(") ");

        return columns.toString();
    }

    private static String valuesToStr(Set<Map.Entry<String, String>> entrySet) {
        StringBuilder values = new StringBuilder();

        values.append(" (");
        entrySet.stream()
                .filter(entry -> !entry.getKey().equals("tableName"))
                .forEach(entry -> {
                    String value = entry.getValue();
                    String key = entry.getKey();
                    if (key.equals("CURRENT_DT")) values.append("SYSDATE, ");
                    else if (entry.getKey().contains("_DATE")) values.append("TO_DATE(\'" + value + " 00:00:00\', 'YYYY-MM-DD HH24:MI:SS'), ");
                    else values.append("\'" + value + "\'" + ", ");
                });
        values.delete(values.length() - 2, values.length());
        values.append(")");

        return values.toString();
    }
}
