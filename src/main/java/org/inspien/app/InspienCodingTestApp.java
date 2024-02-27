package org.inspien.app;

import lombok.RequiredArgsConstructor;
import org.inspien.client.Client;
import org.inspien.connection.database.InspienOracleJdbcConnection;
import org.inspien.connection.database.LocalOracleJdbcConnection;
import org.inspien.dto.order.Item;
import org.inspien.dto.order.Order;
import org.inspien.dto.order.SalesStatus;
import org.inspien.dto.response.DBConnInfo;
import org.inspien.dto.response.Response;
import org.inspien.parser.Mapper;
import org.inspien.service.TestService;
import java.util.ArrayList;
import java.util.HashMap;

@RequiredArgsConstructor
public class InspienCodingTestApp {

    private final DBConnInfo localDbConnInfo = new DBConnInfo(
            "localhost", 1521,
            "system", "tkfkdgksmswodn",
            "XE", "ORDERS"
    );
    private final DBConnInfo inspienDbConnInfo = new DBConnInfo(
            "211.106.171.36", 11527,
            "inspien01", "inspien01",
            "POS", "INSPIEN_XMLDATA_INFO"
    );
    private final Client client;
    private final TestService testService = new TestService(
            new LocalOracleJdbcConnection(localDbConnInfo), new InspienOracleJdbcConnection(inspienDbConnInfo)
    );

    public void run() {
        try {
            String input =
                    "{" +
                            "\"NAME\" : \"조영현\"," +
                            "\"PHONE_NUMBER\" : \"010-9512-8646\"," +
                            "\"E_MAIL\" : \"psyhyun1030@gmail.com\"" +
                    "}";
            String url = "http://211.106.171.36:50000/RESTAdapter/RecruitingTest";

            // 인스피언 서버로 요청 전송 후, 수신한 응답을 Java Object로 변환
            Response response = Mapper.strToResponse(client.requestDataAndConnInfo(input, url));

            // XML_DATA를 Java Object로 변환
            SalesStatus salesStatus = Mapper.xmlStringToObject(response.getXmlData());

            // Local ORACLE에 저장 후 조인
            for (Order order : salesStatus.getOrders()) {
                testService.createOrder(order);
            }
            for (Item item : salesStatus.getItems()) {
                testService.createItem(item);
            }

            ArrayList<HashMap<String, String>> joined = testService.findSalesStatus();

            testService.createJoinedData(joined);

            // Inspien ORACLE DBMS로 INSERT

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
