package org.inspien.client.dbms;

import lombok.RequiredArgsConstructor;
import org.inspien.data.api.DbConnInfo;
import org.inspien.exception.DbConnInfoNotExistException;
import org.inspien.util.SqlGenerator;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class RemoteOracleDbmsClient extends DbmsClient {

    private DbConnInfo dbConnInfo = null;

    public void setDbConnInfo(DbConnInfo dbConnInfo) {
        this.dbConnInfo = dbConnInfo;
    }

    private void checkDbConnInfo() {
        if (dbConnInfo == null) throw new DbConnInfoNotExistException();
    }


    public void findInsertedData(HashMap<String, String> data) throws ClassNotFoundException, SQLException {
        checkDbConnInfo();
        find(SqlGenerator.select(data));
    }

    public void createJoinedData(ArrayList<HashMap<String, String>> joinedData) throws SQLException, ClassNotFoundException {
        checkDbConnInfo();
        for (HashMap<String, String> hashMap : joinedData) {
            hashMap.put("tableName", "INSPIEN_XMLDATA_INFO");
            hashMap.put("SENDER", "조영현");
            hashMap.put("CURRENT_DT", "SYSDATE");
            super.save(dbConnInfo, hashMap);
        }
    }

    private void find(String query) throws ClassNotFoundException, SQLException {
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
}
