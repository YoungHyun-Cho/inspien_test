package org.inspien.client.dbms;

import org.inspien.data.api.DbConnInfo;
import org.inspien.util.SqlGenerator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

/*
* # DbmsClient.class
*   - DBMS를 사용하기 위한 공통적인 동작을 추상화한 클래스
*       - LocalOracleDbmsClient와 RemoteOracleDbmsClient가 공통적으로 사용하는 기능을 정의함.
* */

public abstract class DbmsClient {

    // DB 커넥션을 생성한다.
    protected Connection connect(DbConnInfo dbConnInfo) throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        String url = "jdbc:oracle:thin:@" + dbConnInfo.getHost() + ":" + dbConnInfo.getPort() + ":" + dbConnInfo.getSid();
        return DriverManager.getConnection(url, dbConnInfo.getUser(), dbConnInfo.getPassword());
    }

    // 데이터와 DbConnInfo를 입력 받아, DbConnInfo가 가리키는 DBMS에 접근항려 데이터를 삽입한다.
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
