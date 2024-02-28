package org.inspien.client.dbms;

import org.inspien.data.xml.Item;
import org.inspien.data.xml.Order;
import org.inspien.data.api.DbConnInfo;
import org.inspien.exception.DbConnInfoNotExistException;
import org.inspien.util.SqlGenerator;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class LocalOracleDbmsClient extends DbmsClient {

    private DbConnInfo dbConnInfo = null;

    public void setDbConnInfo(DbConnInfo dbConnInfo) {
        this.dbConnInfo = dbConnInfo;
    }

    private void checkDbConnInfo() {
        if (dbConnInfo == null) throw new DbConnInfoNotExistException();
    }

    public Integer createOrder(Order order) throws SQLException, ClassNotFoundException {
        checkDbConnInfo();
        return super.save(dbConnInfo, order.toHashMap());
    }

    public Integer createItem(Item item) throws SQLException, ClassNotFoundException {
        checkDbConnInfo();
        return super.save(dbConnInfo, item.toHashMap());
    }

    public ArrayList<HashMap<String, String>> findOrderItem(HashMap<String, String> data) throws SQLException, ClassNotFoundException {
        checkDbConnInfo();
        return find(SqlGenerator.select(data));
    }

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
