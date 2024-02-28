package inspien.util;

import org.inspien._config.AppConfigurer;
import org.inspien.client.api.ApacheApiClient;
import org.inspien.client.api.ApiClient;
import org.inspien.data.json.Record;
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

public class MapperTest {

    private final ApiClient apiClient = new ApacheApiClient();

    @Test
    public void xmlToObjectTest() throws ParserConfigurationException, SAXException, IOException {

        // 인스피언 서버로 요청 전송 후, 수신한 응답을 Java Object로 변환
        Response response = Mapper.mapToResponse(
                apiClient.sendApiRequest(
                        AppConfigurer.getUserInfo().serialize(),
                        AppConfigurer.getAPI_URL()
                )
        );

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

        // DETAIL이 items로 정확히 변환되었는지 확인
        Item firstItem = parsedXmlData.getItems().get(0);
        assertThat(firstItem.getOrderNumber()).isEqualTo("1000");
        assertThat(firstItem.getSequence()).isEqualTo("1");
        assertThat(firstItem.getPrice()).isEqualTo("30000");
        assertThat(firstItem.getQuantity()).isEqualTo("1");
        assertThat(firstItem.getName()).isEqualTo("아이폰케이스");
        assertThat(firstItem.getColor()).isEqualTo("흰색");
    }

    @Test
    public void jsonToObjectTest() throws IOException {

        // 인스피언 서버로 요청 전송 후, 수신한 응답을 Java Object로 변환
        Response response = Mapper.mapToResponse(
                apiClient.sendApiRequest(
                        AppConfigurer.getUserInfo().serialize(),
                        AppConfigurer.getAPI_URL()
                )
        );

        // JSON_DATA를 Java Object로 변환
        ParsedJsonData parsedJsonData = Mapper.jsonDataToObject(response.getJsonData());

        // List<Record>에서 맨 앞 요소를 기준으로, 데이터가 잘 변환되었는지 확인
        Record record = parsedJsonData.getRecords().get(0);
        assertThat(record.getName()).isEqualTo("Baxter Chang Özbey");
        assertThat(record.getPhone()).isEqualTo("076 2957 1961");
        assertThat(record.getEmail()).isEqualTo("id.enim.Curabitur@Crasdictum.com");
        assertThat(record.getBirthDate()).isEqualTo("1981/09/14");
        assertThat(record.getCompany()).isEqualTo("Sem Institute");
        assertThat(record.getPersonalNumber()).isEqualTo("16550126 7313");
        assertThat(record.getOrganizationNumber()).isEqualTo("978436-9705");
        assertThat(record.getCountry()).isEqualTo("Kenya");
        assertThat(record.getRegion()).isEqualTo("South Sumatra");
        assertThat(record.getCity()).isEqualTo("Palembang");
        assertThat(record.getStreet()).isEqualTo("9980 Lacus. Avenue");
        assertThat(record.getZipCode()).isEqualTo("86867");
        assertThat(record.getCreditCard()).isEqualTo("4539184335316");
        assertThat(record.getGuid()).isEqualTo("5DD3E3BF-A039-B909-326B-460396CD5CF6");
    }
}
