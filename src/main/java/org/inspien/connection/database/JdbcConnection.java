package org.inspien.connection.database;

import org.inspien.dto.response.DBConnInfo;
import org.inspien.sql.SqlGenerator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public abstract class JdbcConnection {
    Connection connect(DBConnInfo dbConnInfo) throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        String url = "jdbc:oracle:thin:@" + dbConnInfo.getHost() + ":" + dbConnInfo.getPort() + ":" + dbConnInfo.getSid();
        return DriverManager.getConnection(url, dbConnInfo.getUser(), dbConnInfo.getPassword());
    }

    public Integer save(DBConnInfo dbConnInfo, HashMap<String, String> data) throws ClassNotFoundException, SQLException {

        Connection connection = connect(dbConnInfo);
        Statement statement = connection.createStatement();
        String query = SqlGenerator.generateInsertQuery(data);
        System.out.println(query);
        int affectedRows = statement.executeUpdate(query);

        statement.close();
        connection.close();

        return affectedRows;
    }
}
