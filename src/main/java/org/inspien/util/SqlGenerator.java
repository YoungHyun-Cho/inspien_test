package org.inspien.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*
* # SqlGenerator.class
*   - 과제 수행에 필요한 SELECT문과 INSERT문을 생성해주는 유틸성 클래스
*   - QueryDSL을 사용해보고자 했으나, 의존성 추가 문제로 사용하지 못하게 되어,
*     간단하게 직접 구현하여 사용함.
* */

public class SqlGenerator {

    // 조회 대상 칼럼, 테이블 이름, 조회 조건, 정렬 조건이 담긴 해시맵을 입력 받아 SELECT 쿼리를 구성해 리턴한다.
    public static String select(HashMap<String, String> data) {
        return new StringBuilder()
                .append("SELECT ")
                .append(data.get("columns"))
                .append(" FROM ")
                .append(data.get("tableName"))
                .append(data.containsKey("where") ? " WHERE " + data.get("where") : "")
                .append(data.containsKey("orderBy") ? " ORDER BY " + data.get("order") : "")
                .toString();
    }

    // { 칼럼 이름 : 값 } 형태의 해시맵을 입력 받아 INSERT 쿼리를 구성해 리턴한다.
    public static String insert(HashMap<String, String> data) {
        String tableName = data.get("tableName");
        Set<Map.Entry<String, String>> entrySet = data.entrySet();
        return "INSERT INTO " + tableName + columnsToStr(entrySet) + "VALUES" + valuesToStr(entrySet);
    }

    // 삽입 대상 칼럼을 추출하여 SQL에 삽입할 수 있는 형태의 문자열로 직렬화한다.
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

    // 삽입 대상 값을 추출하여 SQL에 삽입할 수 있는 형태의 문자열로 직렬화한다.
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
                }
        );
        values.delete(values.length() - 2, values.length());
        values.append(")");

        return values.toString();
    }
}
