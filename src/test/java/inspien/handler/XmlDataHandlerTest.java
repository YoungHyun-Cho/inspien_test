package inspien.handler;

import org.inspien._config.AppConfigurer;
import org.inspien.client.api.ApacheApiClient;
import org.inspien.client.api.ApiClient;
import org.inspien.data.api.Response;
import org.inspien.data.xml.ParsedXmlData;
import org.inspien.util.Mapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.*;

public class XmlDataHandlerTest {
    private static final ApiClient apiClient = new ApacheApiClient();
    private static ParsedXmlData parsedXmlData;

    @BeforeAll
    public static void initiateData() throws IOException, ParserConfigurationException, SAXException {
        Response response = Mapper.mapToResponse(
                apiClient.sendApiRequest(
                        AppConfigurer.getUserInfo().serialize(),
                        AppConfigurer.getAPI_URL()
                )
        );
        parsedXmlData = Mapper.xmlDataToObject(response.getXmlData());
    }

    @Test
    public void xmlDataJoinTest() {
        ArrayList<HashMap<String, String>> joined = parsedXmlData.handle();

        assertThat(joined.size()).isEqualTo(50);

        System.out.println(joined);

        HashMap<String, String> firstEntry = joined.get(0);
        assertThat(firstEntry.size()).isEqualTo(15);
        assertThat(firstEntry.get("ORDER_NUM")).isEqualTo("1000");
        assertThat(firstEntry.get("ORDER_ID")).isEqualTo("kwang001");
        assertThat(firstEntry.get("ORDER_DATE")).isEqualTo("2021-08-26");
        assertThat(firstEntry.get("ORDER_PRICE")).isEqualTo("62000");
        assertThat(firstEntry.get("ORDER_QTY")).isEqualTo("3");
        assertThat(firstEntry.get("RECEIVER_NAME")).isEqualTo("김광수");
        assertThat(firstEntry.get("RECEIVER_NO")).isEqualTo("010-5312-2345");
        assertThat(firstEntry.get("ETA_DATE")).isEqualTo("2021-09-01");
        assertThat(firstEntry.get("DESTINATION")).isEqualTo("서울시 서초구 방배동 801");
        assertThat(firstEntry.get("DESCIPTION")).isEqualTo("현관앞에 놓아주세요");
        assertThat(firstEntry.get("ITEM_NAME")).isEqualTo("아이폰케이스");
        assertThat(firstEntry.get("ITEM_QTY")).isEqualTo("1");
        assertThat(firstEntry.get("ITEM_PRICE")).isEqualTo("30000");
        assertThat(firstEntry.get("ITEM_SEQ")).isEqualTo("1");
        assertThat(firstEntry.get("ITEM_COLOR")).isEqualTo("흰색");
    }
}
