package unit_test.serialize;

import org.inspien.client.RestApiClient;
import org.inspien.dto.record.Records;
import org.inspien.dto.response.Response;
import org.inspien.file.FileHandler;
import org.inspien.mapper.Mapper;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class SerializeTest {

    private static final String PATH = "/Users/0hyuncho/Desktop/inspien_code_temp_test";
    private static final String FILE_NAME = "/INSPIEN_JSON_조영현_20240228131834.txt";
    @Test
    public void serializeRecordTest() throws IOException {

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

        // JSON_DATA를 Java Object로 변환
        Records records = Mapper.jsonStringToObject(response.getJsonData());

        StringBuilder stringBuilder = new StringBuilder();
        records.getRecords().stream()
                .forEach(record -> stringBuilder.append(record.serialize()));

        FileHandler.write(PATH + FILE_NAME, stringBuilder.toString());
    }
}
