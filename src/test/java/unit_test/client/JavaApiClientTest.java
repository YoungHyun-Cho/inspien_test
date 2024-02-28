package unit_test.client;

import org.inspien.client.api.JavaApiClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

public class JavaApiClientTest {

    @Test
    @DisplayName("요청을 보내면 JSON 형식의 문자열로 응답이 도착해야 한다.")
    public void requestTest() throws IOException {
        JavaApiClient javaApiClient = new JavaApiClient();

        String testData =
                "{" +
                        "\"NAME\" : \"조영현\"," +
                        "\"PHONE_NUMBER\" : \"010-9512-8646\"," +
                        "\"E_MAIL\" : \"psyhyun1030@gmail.com\"" +
                "}";
        String testUrl = "http://211.106.171.36:50000/RESTAdapter/RecruitingTest";

        String response = javaApiClient.sendApiRequest(testData, testUrl);

        assertThat(response)
                .contains("XML_DATA")
                .contains("JSON_DATA")
                .contains("DB_CONN_INFO")
                .contains("FTP_CONN_INFO");
    }
}
