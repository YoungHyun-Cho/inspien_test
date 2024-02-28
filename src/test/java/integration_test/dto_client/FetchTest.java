package integration_test.dto_client;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.inspien.client.RestApiClient;
import org.inspien.dto.response.Response;
import org.inspien.mapper.Mapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FetchTest {

    @Test
    @DisplayName("요청 전송 후 응답으로 전송 받은 JSON 문자열을 DTO로 변환할 수 있어야 한다.")
    public void requestAndParseJson() throws JsonProcessingException {
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
