package org.inspien.service;

import lombok.RequiredArgsConstructor;
import org.inspien.connection.database.InspienOracleJdbcConnection;
import org.inspien.connection.database.LocalOracleJdbcConnection;
import org.inspien.dto.order.Item;
import org.inspien.dto.order.Order;
import org.inspien.mapper.Mapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

@RequiredArgsConstructor
public class TestService {
    private final LocalOracleJdbcConnection localOracleJdbcConnection;
    private final InspienOracleJdbcConnection inspienOracleJdbcConnection;

    public void findTestData() throws ClassNotFoundException, SQLException {
        inspienOracleJdbcConnection.find("SELECT * FROM INSPIEN_XMLDATA_INFO WHERE SENDER = \'조영현\'");
    }

    public void createOrder(Order order) throws SQLException, ClassNotFoundException {
        localOracleJdbcConnection.save(Mapper.orderToHashMap(order));
    }

    public void createItem(Item item) throws SQLException, ClassNotFoundException {
        localOracleJdbcConnection.save(Mapper.itemToHashMap(item));
    }

    public ArrayList<HashMap<String, String>> findSalesStatus() throws SQLException, ClassNotFoundException {
        return localOracleJdbcConnection.find("SELECT * FROM ORDERS NATURAL JOIN ITEM ORDER BY ORDER_NUM");
    }

    public void createJoinedData(ArrayList<HashMap<String, String>> joinedData) throws SQLException, ClassNotFoundException {
        for (HashMap<String, String> hashMap : joinedData) {
            hashMap.put("tableName", "INSPIEN_XMLDATA_INFO");
            hashMap.put("SENDER", "조영현");
            hashMap.put("CURRENT_DT", "SYSDATE");
            inspienOracleJdbcConnection.save(hashMap);
        }
    }
}