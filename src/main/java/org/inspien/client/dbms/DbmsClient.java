package org.inspien.client.dbms;

import org.inspien.data.api.DbConnInfo;
import org.inspien.util.SqlGenerator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public abstract class DbmsClient {
    protected Connection connect(DbConnInfo dbConnInfo) throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        String url = "jdbc:oracle:thin:@" + dbConnInfo.getHost() + ":" + dbConnInfo.getPort() + ":" + dbConnInfo.getSid();
        return DriverManager.getConnection(url, dbConnInfo.getUser(), dbConnInfo.getPassword());
    }

    protected Integer save(DbConnInfo dbConnInfo, HashMap<String, String> data) throws ClassNotFoundException, SQLException {
        Connection connection = connect(dbConnInfo);
        Statement statement = connection.createStatement();
        String query = SqlGenerator.insert(data);
        int affectedRows = statement.executeUpdate(query);

        statement.close();
        connection.close();

        return affectedRows;
    }
}
