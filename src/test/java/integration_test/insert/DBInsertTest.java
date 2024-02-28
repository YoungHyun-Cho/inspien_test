package integration_test.insert;

import org.inspien.client.api.JavaApiClient;
import org.inspien.client.dbms.RemoteOracleDbmsClient;
import org.inspien.data.xml.Item;
import org.inspien.data.xml.Order;
import org.inspien.data.xml.ParsedXmlData;
import org.inspien.data.api.DbConnInfo;
import org.inspien.data.api.Response;
import org.inspien.util.Mapper;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class DBInsertTest {

    private final DbConnInfo localDbConnInfo = new DbConnInfo(
            "localhost", 1521,
            "system", "tkfkdgksmswodn",
            "XE", "ORDERS"
    );
    private final DbConnInfo inspienDbConnInfo = new DbConnInfo(
            "211.106.171.36", 11527,
            "inspien01", "inspien01",
            "POS", "INSPIEN_XMLDATA_INFO"
    );

    private final RemoteOracleDbmsClient remoteOracleDbmsClient = new RemoteOracleDbmsClient(inspienDbConnInfo);

//    @Test
//    public void dbInsertTest() throws ClassNotFoundException, SQLException {
//
//        UserInfo userInfo = new UserInfo("조영현", "010-9512-8646", "Email");
//        testService.findTestData();
//    }

    @Test
    public void createOrderTest() throws ClassNotFoundException, SQLException, SAXException, IOException, ParserConfigurationException {

        JavaApiClient javaApiClient = new JavaApiClient();

        String input =
                "{" +
                        "\"NAME\" : \"조영현\"," +
                        "\"PHONE_NUMBER\" : \"010-9512-8646\"," +
                        "\"E_MAIL\" : \"psyhyun1030@gmail.com\"" +
                        "}";
        String url = "http://211.106.171.36:50000/RESTAdapter/RecruitingTest";

        // 인스피언 서버로 요청 전송 후, 수신한 응답을 Java Object로 변환
        Response response = Mapper.mapToResponse(javaApiClient.sendApiRequest(input, url));

        // XML_DATA를 Java Object로 변환
        ParsedXmlData parsedXmlData = Mapper.xmlDataToObject(response.getXmlData());

        List<Order> orders = parsedXmlData.getOrders();
        List<Item> items = parsedXmlData.getItems();

//        for (Item item : items) {
//            testService.createItem(item);
//        }

//        for (Item item : items) {
//            testService.createItem(item);
//        }

//        testService.createJoinedData(testService.findSalesStatus());
//        testService.findTestData();

        HashMap<String, String> data = new HashMap<>();
        data.put("tableName", "INSPIEN_XMLDATA_INFO");
        data.put("columns", "*");
        data.put("where", "SENDER = \'조영현\'");
        remoteOracleDbmsClient.findInsertedData(data);
    }
}