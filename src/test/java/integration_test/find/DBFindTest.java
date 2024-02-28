package integration_test.find;

import org.inspien.client.api.JavaApiClient;
import org.inspien.client.dbms.LocalOracleDbmsClient;
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

public class DBFindTest {

    private final DbConnInfo inspienDbConnInfo = new DbConnInfo(
            "211.106.171.36", 11527,
            "inspien01", "inspien01",
            "POS", "INSPIEN_XMLDATA_INFO"
    );

    private DbConnInfo localDbConnInfo = new DbConnInfo(
            "localhost", 1521,
            "system", "tkfkdgksmswodn",
            "XE", "ORDERS"
    );

    private LocalOracleDbmsClient localOracleDbmsClient = new LocalOracleDbmsClient(localDbConnInfo);

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

        HashMap<String, String> data = new HashMap<>();
        data.put("tableName", "ORDERS");
        data.put("columns", "*");
        data.put("where", "ORDER_NUM=1000 AND ORDER_QTY=3");
        data.put("orderBy", "ORDER_NUM");
        System.out.println(localOracleDbmsClient.findOrderItem(data));
    }
}
