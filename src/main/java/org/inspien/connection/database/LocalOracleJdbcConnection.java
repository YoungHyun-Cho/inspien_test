package org.inspien.connection.database;

import lombok.RequiredArgsConstructor;
import org.inspien.dto.order.Item;
import org.inspien.dto.order.Order;
import org.inspien.dto.response.DBConnInfo;
import org.inspien.sql.SqlGenerator;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

@RequiredArgsConstructor
public class LocalOracleJdbcConnection extends JdbcConnection {

    private final DBConnInfo dbConnInfo;

    public ArrayList<HashMap<String, String>> find(String query) throws ClassNotFoundException, SQLException {
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

        return arrayList;
    }

    public Integer save(HashMap<String, String> data) throws ClassNotFoundException, SQLException {
        return super.save(dbConnInfo, data);
    }
}
