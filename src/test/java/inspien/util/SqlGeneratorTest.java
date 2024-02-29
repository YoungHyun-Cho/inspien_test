package inspien.util;

import org.inspien.util.SqlGenerator;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.*;

public class SqlGeneratorTest {
    @Test
    public void selectTest() {
        HashMap<String, String> sqlComponent = new HashMap<>();
        sqlComponent.put("tableName", "ORDERS");
        sqlComponent.put("naturalJoin", "ITEM");
        sqlComponent.put("columns", "*");
        sqlComponent.put("orderBy", "ORDER_NUM");

        String query = SqlGenerator.select(sqlComponent);

        assertThat(query).isEqualTo("SELECT * FROM ORDERS NATURAL JOIN ITEM ORDER BY ORDER_NUM");
    }

    @Test
    public void insertTest() {
        HashMap<String, String> data = new HashMap<>();
        data.put("tableName", "ORDERS");
        data.put("ORDER_NUM", "1");
        data.put("ORDER_ID", "0hyunCho");
        data.put("ORDER_PRICE", "10000");

        String query = SqlGenerator.insert(data);

        assertThat(query).isEqualTo("INSERT INTO ORDERS (ORDER_NUM, ORDER_ID, ORDER_PRICE) VALUES ('1', '0hyunCho', '10000')");
    }
}
