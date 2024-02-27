package integration_test.find;

import org.inspien.client.RestApiClient;
import org.inspien.connection.database.InspienOracleJdbcConnection;
import org.inspien.connection.database.LocalOracleJdbcConnection;
import org.inspien.dto.order.SalesStatus;
import org.inspien.dto.response.DBConnInfo;
import org.inspien.dto.response.Response;
import org.inspien.parser.Mapper;
import org.inspien.service.TestService;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;

public class DBFindTest {

    private final DBConnInfo inspienDbConnInfo = new DBConnInfo(
            "211.106.171.36", 11527,
            "inspien01", "inspien01",
            "POS", "INSPIEN_XMLDATA_INFO"
    );
    @Test
    public void createOrderTest() throws ClassNotFoundException, SQLException, SAXException, IOException, ParserConfigurationException {

        RestApiClient restApiClient = new RestApiClient();

        String input =
                "{" +
                        "\"NAME\" : \"조영현\"," +
                        "\"PHONE_NUMBER\" : \"010-9512-8646\"," +
                        "\"E_MAIL\" : \"psyhyun1030@gmail.com\"" +
                        "}";
        String url = "http://211.106.171.36:50000/RESTAdapter/RecruitingTest";

        // 인스피언 서버로 요청 전송 후, 수신한 응답을 Java Object로 변환
        Response response = Mapper.strToResponse(restApiClient.requestDataAndConnInfo(input, url));

        // XML_DATA를 Java Object로 변환
        SalesStatus salesStatus = Mapper.xmlStringToObject(response.getXmlData());

        DBConnInfo dbConnInfo = new DBConnInfo(
                "localhost", 1521,
                "system", "tkfkdgksmswodn",
                "XE", "ORDERS"
        );

        TestService testService = new TestService(new LocalOracleJdbcConnection(dbConnInfo), new InspienOracleJdbcConnection(inspienDbConnInfo));

        testService.findSalesStatus();
    }
}
