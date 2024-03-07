package inspien.client;

import org.inspien._config.AppConfigurer;
import org.inspien.client.api.ApacheApiClient;
import org.inspien.client.api.ApiClient;
import org.inspien.data.api.Response;
import org.inspien.util.Mapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class ApiClientTest {

    private ApiClient apiClient = new ApacheApiClient();

    @Test
    @DisplayName("요청 전송 후 응답으로 전송 받은 JSON 문자열을 DTO로 변환할 수 있어야 한다.")
    public void requestAndParseJson() throws IOException {

        Response response = Mapper.mapToResponse(
                apiClient.sendApiRequest(
                        AppConfigurer.getUserInfo().serialize(),
                        AppConfigurer.getAPI_URL()
                )
        );

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
