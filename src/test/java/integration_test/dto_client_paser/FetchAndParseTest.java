package integration_test.dto_client_paser;

import org.inspien.client.api.JavaApiClient;
import org.inspien.data.xml.Item;
import org.inspien.data.xml.Order;
import org.inspien.data.xml.ParsedXmlData;
import org.inspien.data.json.ParsedJsonData;
import org.inspien.data.api.Response;
import org.inspien.util.Mapper;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

public class FetchAndParseTest {

    @Test
    public void fetchXmlAndParse() throws ParserConfigurationException, SAXException, IOException {
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

        // HEADER가 orders로 정확히 변환되었는지 확인
        Order firstOrder = parsedXmlData.getOrders().get(0);
        assertThat(firstOrder.getUserId()).isEqualTo("kwang001");
        assertThat(firstOrder.getNumber()).isEqualTo("1000");
        assertThat(firstOrder.getPrice()).isEqualTo("62000");
        assertThat(firstOrder.getQuantity()).isEqualTo("3");
        assertThat(firstOrder.getDate()).isEqualTo("2021-08-26");
        assertThat(firstOrder.getEtaDate()).isEqualTo("2021-09-01");
        assertThat(firstOrder.getReceiverName()).isEqualTo("김광수");
        assertThat(firstOrder.getReceiverPhone()).isEqualTo("010-5312-2345");
        assertThat(firstOrder.getDestination()).isEqualTo("서울시 서초구 방배동 801");
        assertThat(firstOrder.getMessage()).isEqualTo("현관앞에 놓아주세요");

        // DETAIL가 items로 정확히 변환되었는지 확인
        Item firstItem = parsedXmlData.getItems().get(0);
        assertThat(firstItem.getOrderNumber()).isEqualTo("1000");
        assertThat(firstItem.getSequence()).isEqualTo("1");
        assertThat(firstItem.getPrice()).isEqualTo("30000");
        assertThat(firstItem.getQuantity()).isEqualTo("1");
        assertThat(firstItem.getName()).isEqualTo("아이폰케이스");
        assertThat(firstItem.getColor()).isEqualTo("흰색");
    }

    @Test
    public void fetchJsonAndParse() throws ParserConfigurationException, SAXException, IOException {
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

        // JSON_DATA를 Java Object로 변환
        ParsedJsonData parsedJsonData = Mapper.jsonDataToObject(response.getJsonData());

//        System.out.println(records);
        System.out.println(response.getFtpConnInfo());
    }
}
