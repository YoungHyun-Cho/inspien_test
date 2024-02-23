package unit_test.client;

import org.inspien.client.RestApiClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class RestApiClientTest {

    @Test
    @DisplayName("요청을 보내면 JSON 형식의 문자열로 응답이 도착해야 한다.")
    public void requestTest() {
        RestApiClient restApiClient = new RestApiClient();
        String json = restApiClient.request();

        assertThat(json)
                .contains("XML_DATA")
                .contains("JSON_DATA")
                .contains("DB_CONN_INFO")
                .contains("FTP_CONN_INFO");
    }
}
