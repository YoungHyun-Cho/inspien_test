package integration_test.dto_client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.inspien.client.RestApiClient;
import org.inspien.dto.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FetchTest {

    @Test
    @DisplayName("요청 전송 후 응답으로 전송 받은 JSON 문자열을 DTO로 변환할 수 있어야 한다.")
    public void requestAndParseJson() throws JsonProcessingException {
        RestApiClient restApiClient = new RestApiClient();

        String testData =
                "{" +
                        "\"NAME\" : \"조영현\"," +
                        "\"PHONE_NUMBER\" : \"010-9512-8646\"," +
                        "\"E_MAIL\" : \"psyhyun1030@gmail.com\"" +
                "}";
        String testUrl = "http://211.106.171.36:50000/RESTAdapter/RecruitingTest";

        String response = restApiClient.request(testData, testUrl);

        ObjectMapper objectMapper = new ObjectMapper();
        Response responseDto = objectMapper.readValue(response, new TypeReference<Response>() {});

        assertThat(responseDto)
                .hasFieldOrProperty("xmlData")
                .hasFieldOrProperty("jsonData")
                .hasFieldOrProperty("databaseConnInfo")
                .hasFieldOrProperty("ftpConnInfo");

        assertThat(responseDto.getDbConnInfo())
                .hasFieldOrProperty("host")
                .hasFieldOrProperty("port")
                .hasFieldOrProperty("user")
                .hasFieldOrProperty("password")
                .hasFieldOrProperty("sid")
                .hasFieldOrProperty("tableName");

        assertThat(responseDto.getFtpConnInfo())
                .hasFieldOrProperty("host")
                .hasFieldOrProperty("port")
                .hasFieldOrProperty("user")
                .hasFieldOrProperty("password")
                .hasFieldOrProperty("filePath");
    }
}
