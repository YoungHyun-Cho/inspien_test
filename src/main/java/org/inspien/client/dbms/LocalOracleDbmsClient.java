package org.inspien.client.dbms;

import org.inspien._config.AppConfigurer;
import org.inspien.data.xml.Item;
import org.inspien.data.xml.Order;
import org.inspien.data.api.DbConnInfo;
import org.inspien.util.SqlGenerator;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

/*
* # LocalOracleDbmsClient.class
*   - 로컬 ORACLE DBMS과 소통하며 데이터를 삽입 및 조회함.
* */

public class LocalOracleDbmsClient extends DbmsClient {

    private DbConnInfo dbConnInfo = AppConfigurer.getLocalDbConnInfo();

    // Order 테이블에 데이터를 삽입한다.
    public Integer createOrder(Order order) throws SQLException, ClassNotFoundException {
        return super.save(dbConnInfo, order.toHashMap());
    }

    // Item 테이블에 데이터를 삽입한다.
    public Integer createItem(Item item) throws SQLException, ClassNotFoundException {
        return super.save(dbConnInfo, item.toHashMap());
    }

    // Order, Item, 또는 Order와 Item을 조인하여 조회한다.
    public ArrayList<HashMap<String, String>> findDataBy(HashMap<String, String> sqlComponent) throws SQLException, ClassNotFoundException {
        return find(SqlGenerator.select(sqlComponent));
    }

    // 로컬 ORACLE DBMS에 접근하여 SELECT문을 실행한다.
    private ArrayList<HashMap<String, String>> find(String query) throws ClassNotFoundException, SQLException {
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

        return arrayList;
    }
}
