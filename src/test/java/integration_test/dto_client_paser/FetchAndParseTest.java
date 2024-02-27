package integration_test.dto_client_paser;

import org.inspien.client.RestApiClient;
import org.inspien.dto.order.Item;
import org.inspien.dto.order.Order;
import org.inspien.dto.order.SalesStatus;
import org.inspien.dto.response.Response;
import org.inspien.parser.Mapper;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

public class FetchAndParseTest {

    @Test
    public void fetchXmlAndParse() throws ParserConfigurationException, SAXException, IOException {
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

        // HEADER가 orders로 정확히 변환되었는지 확인
        Order firstOrder = salesStatus.getOrders().get(0);
        assertThat(firstOrder.getUserId()).isEqualTo("kwang001");
        assertThat(firstOrder.getNumber()).isEqualTo(1000);
        assertThat(firstOrder.getPrice()).isEqualTo(62000);
        assertThat(firstOrder.getQuantity()).isEqualTo(3);
        assertThat(firstOrder.getDate()).isEqualTo(LocalDate.parse("2021-08-26"));
        assertThat(firstOrder.getEtaDate()).isEqualTo(LocalDate.parse("2021-09-01"));
        assertThat(firstOrder.getReceiverName()).isEqualTo("김광수");
        assertThat(firstOrder.getReceiverPhone()).isEqualTo("010-5312-2345");
        assertThat(firstOrder.getDestination()).isEqualTo("서울시 서초구 방배동 801");
        assertThat(firstOrder.getMessage()).isEqualTo("현관앞에 놓아주세요");

        // DETAIL가 items로 정확히 변환되었는지 확인
        Item firstItem = salesStatus.getItems().get(0);
        assertThat(firstItem.getOrderNumber()).isEqualTo(1000);
        assertThat(firstItem.getSequence()).isEqualTo(1);
        assertThat(firstItem.getPrice()).isEqualTo(30000);
        assertThat(firstItem.getQuantity()).isEqualTo(1);
        assertThat(firstItem.getName()).isEqualTo("아이폰케이스");
        assertThat(firstItem.getColor()).isEqualTo("흰색");

        // 일단 Connection Info부터 확인해보자.
        System.out.println("HOST       : " + response.getDbConnInfo().getHost());
        System.out.println("USER       : " + response.getDbConnInfo().getUser());
        System.out.println("SID        : " + response.getDbConnInfo().getSid());
        System.out.println("PASSWORD   : " + response.getDbConnInfo().getPassword());
        System.out.println("TABLE_NAME : " + response.getDbConnInfo().getTableName());
        System.out.println("PORT       : " + response.getDbConnInfo().getPort());
    }
}
