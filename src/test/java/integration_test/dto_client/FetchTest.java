package integration_test.dto_client;

import org.inspien.client.api.ApacheApiClient;
import org.inspien.client.api.ApiClient;
import org.inspien.data.api.Response;
import org.inspien.util.Mapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class FetchTest {

    private ApiClient apiClient = new ApacheApiClient();

    @Test
    @DisplayName("요청 전송 후 응답으로 전송 받은 JSON 문자열을 DTO로 변환할 수 있어야 한다.")
    public void requestAndParseJson() throws IOException {

        String input =
                "{" +
                        "\"NAME\" : \"조영현\"," +
                        "\"PHONE_NUMBER\" : \"010-9512-8646\"," +
                        "\"E_MAIL\" : \"psyhyun1030@gmail.com\"" +
                "}";
        String url = "http://211.106.171.36:50000/RESTAdapter/RecruitingTest";

        // 인스피언 API 서버로 요청 전송 후, 수신한 응답을 Java Object로 변환
        Response response = Mapper.mapToResponse(apiClient.sendApiRequest(input, url));

        assertThat(response)
                .hasFieldOrProperty("xmlData")
                .hasFieldOrProperty("jsonData")
                .hasFieldOrProperty("dbConnInfo")
                .hasFieldOrProperty("ftpConnInfo");

        assertThat(response.getDbConnInfo())
                .hasFieldOrProperty("host")
                .hasFieldOrProperty("port")
                .hasFieldOrProperty("user")
                .hasFieldOrProperty("password")
                .hasFieldOrProperty("sid")
                .hasFieldOrProperty("tableName");

        assertThat(response.getFtpConnInfo())
                .hasFieldOrProperty("host")
                .hasFieldOrProperty("port")
                .hasFieldOrProperty("user")
                .hasFieldOrProperty("password")
                .hasFieldOrProperty("filePath");
    }
}
