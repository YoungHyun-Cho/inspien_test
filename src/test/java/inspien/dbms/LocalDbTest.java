package inspien.dbms;

import org.inspien._config.AppConfigurer;
import org.inspien.client.api.ApacheApiClient;
import org.inspien.client.api.ApiClient;
import org.inspien.client.dbms.LocalOracleDbmsClient;
import org.inspien.client.dbms.RemoteOracleDbmsClient;
import org.inspien.data.xml.Item;
import org.inspien.data.xml.ParsedXmlData;
import org.inspien.data.api.Response;
import org.inspien.util.Mapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.*;

public class LocalDbTest extends LocalOracleDbmsClient {

    private static final ApiClient apiClient = new ApacheApiClient();
    private static final LocalOracleDbmsClient localOracleDbmsClient = new LocalOracleDbmsClient();
    private static final RemoteOracleDbmsClient remoteOracleDbmsClient = new RemoteOracleDbmsClient();
    private static ParsedXmlData parsedXmlData;

    @BeforeAll
    public static void initiateData() throws IOException, ParserConfigurationException, SAXException {
        Response response = Mapper.mapToResponse(
                apiClient.sendApiRequest(
                        AppConfigurer.getUserInfo().serialize(),
                        AppConfigurer.getAPI_URL()
                )
        );
        remoteOracleDbmsClient.setDbConnInfo(response.getDbConnInfo());
        parsedXmlData = Mapper.xmlDataToObject(response.getXmlData());
    }

    @Test
    public void findTest() throws ClassNotFoundException, SQLException {
        HashMap<String, String> data = new HashMap<>();
        data.put("tableName", "ORDERS");
        data.put("columns", "*");
        data.put("where", "ORDER_NUM=1000 AND ORDER_QTY=3");
        data.put("orderBy", "ORDER_NUM");

        HashMap<String, String> orderItem = localOracleDbmsClient.findDataBy(data).get(0);

        assertThat(orderItem.get("RECEIVER_NO")).isEqualTo("010-5312-2345");
        assertThat(orderItem.get("ORDER_ID")).isEqualTo("kwang001");
        assertThat(orderItem.get("DESTINATION")).isEqualTo("서울시 서초구 방배동 801");
    }

    @Test
    public void insertTest() throws SQLException, ClassNotFoundException {
        Item item = new Item();
        item.setOrderNumber("1000");
        item.setSequence("6789");
        item.setPrice("1234");
        item.setQuantity("4321");
        item.setName("마라샹궈");
        item.setColor("빨간색");

        Integer affectedRows = localOracleDbmsClient.createItem(item);

        assertThat(affectedRows).isEqualTo(1);
    }
}
