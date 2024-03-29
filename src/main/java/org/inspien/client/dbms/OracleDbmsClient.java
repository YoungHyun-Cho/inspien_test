package org.inspien.client.dbms;

import org.inspien._config.AppConfigurer;
import org.inspien.data.api.DbConnInfo;
import org.inspien.exception.DbConnInfoNotExistException;
import org.inspien.util.SqlGenerator;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

/*
* # OracleDbmsClient.class
*   - 인스피언의 ORACLE DBMS과 소통하며 데이터를 삽입 및 조회함.
* */

public class OracleDbmsClient {

    private DbConnInfo dbConnInfo = null;

    // 외부로부터 DbConnInfo를 입력 받아 바인딩해주는 Setter
    public void setDbConnInfo(DbConnInfo dbConnInfo) {
        this.dbConnInfo = dbConnInfo;
    }

    // DbConnInfo가 null인 상태에서 호출되면 예외를 발생시킨다.
    private void checkDbConnInfo() {
        if (dbConnInfo == null) throw new DbConnInfoNotExistException();
    }

    // 인스피언의 ORACLE DBMS에서 SELECT문 실행을 위한 public interface
    // 데이터가 잘 입력되었는지 확인하기 위해 사용한다.
    public ArrayList<HashMap<String, String>> findDataBy(HashMap<String, String> sqlComponent) throws ClassNotFoundException, SQLException {
        checkDbConnInfo();
        return find(SqlGenerator.select(sqlComponent));
    }

    // 인스피언의 ORACLE DBMS에서 INSERT문 실행을 위한 public interface
    // 각 데이터에 SENDER와 CURRENT_DT 정보를 추가하고, 삽입 대상 테이블 이름을 지정한다.
    public void createData(ArrayList<HashMap<String, String>> dataList) throws SQLException, ClassNotFoundException {
        checkDbConnInfo();
        System.out.println("✅ INSPIEN ORACLE DBMS : INSERT START");
        printMyDataSize(true);
        for (HashMap<String, String> data : dataList) {
            data.put("tableName", dbConnInfo.getTableName());
            data.put("SENDER", AppConfigurer.getUserInfo().getName());
            data.put("CURRENT_DT", "SYSDATE");
            save(data);
        }
        System.out.println("✅ INSPIEN ORACLE DBMS : INSERT SUCCESS");
        printMyDataSize(false);
    }

    // 인스피언의 ORACLE DBMS에 접근하여 SELECT문을 실행한다.
    private ArrayList<HashMap<String, String>> find(String query) throws ClassNotFoundException, SQLException {
        Connection connection = connect();

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnsNumber = metaData.getColumnCount();
        ArrayList<HashMap<String, String>> result = new ArrayList<>();

        while (resultSet.next()) {
            HashMap<String, String> data = new HashMap<>();
            for (int i = 1; i <= columnsNumber; i++) {
                data.put(metaData.getColumnName(i), resultSet.getString(i));
            }
            result.add(data);
        }

        resultSet.close();
        statement.close();
        connection.close();

        return result;
    }

    // 데이터와 DbConnInfo를 입력 받아, DbConnInfo가 가리키는 DBMS에 접근하여 데이터를 삽입한다.
    private Integer save(HashMap<String, String> data) throws ClassNotFoundException, SQLException {
        Connection connection = connect();
        Statement statement = connection.createStatement();
        String query = SqlGenerator.insert(data);
        int affectedRows = statement.executeUpdate(query);

        statement.close();
        connection.close();

        return affectedRows;
    }

    // DB 커넥션을 생성한다.
    private Connection connect() throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        String url = "jdbc:oracle:thin:@" + dbConnInfo.getHost() + ":" + dbConnInfo.getPort() + ":" + dbConnInfo.getSid();
        return DriverManager.getConnection(url, dbConnInfo.getUser(), dbConnInfo.getPassword());
    }

    // 인스피언의 ORACLE DBMS에 삽입된 나의 데이터의 개수를 계수한다.
    private void printMyDataSize(boolean isBefore) throws SQLException, ClassNotFoundException {
        HashMap<String, String> sqlComponent = new HashMap<>();
        sqlComponent.put("tableName", dbConnInfo.getTableName());
        sqlComponent.put("columns", "*");
        sqlComponent.put("where", "SENDER = \'" + AppConfigurer.getUserInfo().getName() + "\'");
        ArrayList<HashMap<String, String>> result = findDataBy(sqlComponent);
        System.out.println((isBefore ? "   BEFORE " : "   AFTER ") + "INSERT : " + result.size());
    }
}