package org.inspien.service;

import lombok.RequiredArgsConstructor;
import org.inspien.connection.database.InspienOracleJdbcConnection;
import org.inspien.connection.database.LocalOracleJdbcConnection;
import org.inspien.dto.order.Item;
import org.inspien.dto.order.Order;
import org.inspien.dto.order.SalesStatus;
import org.inspien.dto.request.UserInfo;
import org.inspien.dto.response.DBConnInfo;
import org.inspien.parser.Mapper;

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

    public void createTestData(DBConnInfo dbConnInfo, UserInfo userInfo, SalesStatus salesStatus) throws ClassNotFoundException, SQLException {

        StringBuilder stringBuilder = new StringBuilder();


//        String query =
//                stringBuilder
//                        .append("INSERT INTO ")
//                        .append(dbConnInfo.getTableName())
//                            .append("(")
//                                .append("ORDER_NUM, ").append("ORDER_ID, ").append("ORDER_DATE, ").append("ORDER_PRICE, ").append("ORDER_QTY")
//                                .append("RECEIVER_NAME, ").append("RECEIVER_NO, ")
//                                .append("ETA_DATE, ").append("DESTINATION, ").append("DECIPTION, ")
//                                .append("ITEM_NAME, ").append("ITEM_QTY, ").append("ITEM_PRICE, ").append("ITEM_SEQ, ").append("ITEM_COLOR, ")
//                                .append("SENDER, ").append("CURRENT_DT")
//                            .append(") ")
//                        .append("VALUES")
//                            .append("(")
//                        .append()
//                        .toString();

//        dbConnection.save(dbConnInfo, "INSERT INTO INSPIEN_XMLDATA_INFO ()");
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