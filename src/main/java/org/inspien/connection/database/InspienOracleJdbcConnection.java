package org.inspien.connection.database;

import lombok.RequiredArgsConstructor;
import org.inspien.dto.response.DBConnInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

@RequiredArgsConstructor
public class InspienOracleJdbcConnection extends JdbcConnection {

    private final DBConnInfo dbConnInfo;

    public void find(String query) throws ClassNotFoundException, SQLException {
        Connection connection = connect(dbConnInfo);

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnsNumber = metaData.getColumnCount();
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

        while (resultSet.next()) {
            HashMap<String, String> hashMap = new HashMap<>();
            for (int i = 1; i <= columnsNumber; i++) {
                hashMap.put(metaData.getColumnName(i), resultSet.getString(i));
            }
            arrayList.add(hashMap);
        }

        resultSet.close();
        statement.close();
        connection.close();

        System.out.println(arrayList);
        System.out.println("🟥 COUNT : " + arrayList.size());
    }

    public Integer save(HashMap<String, String> data) throws ClassNotFoundException, SQLException {
        return super.save(dbConnInfo, data);
    }

    public void update(String query) throws SQLException, ClassNotFoundException {
        Connection connection = connect(dbConnInfo);

        Statement statement = connection.createStatement();
        int rowDeleted = statement.executeUpdate(query);

        System.out.println("🟥 DELETED ROWS : " + rowDeleted);

        statement.close();
        connection.close();
    }
}
