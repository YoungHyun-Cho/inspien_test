package org.inspien;

import lombok.RequiredArgsConstructor;
import org.inspien._config.AppConfigurer;
import org.inspien.client.api.ApiClient;
import org.inspien.client.dbms.OracleDbmsClient;
import org.inspien.client.ftp.FtpClient;
import org.inspien.data.api.Response;
import org.inspien.util.FileHandler;
import org.inspien.util.Mapper;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

/*
* # InspienCodingTest.class
*   - 애플리케이션 실행 흐름을 담당함.
*   - 각 Client들과 의존 관계를 형성함.
* */

@RequiredArgsConstructor
public class InspienCodingTest {

    private final ApiClient apiClient;
    private final OracleDbmsClient oracleDbmsClient;
    private final FtpClient ftpClient;

    public void run() {
        try {
            Response response = getDataAndConnInfo(); // 과제 구현 사항 1️⃣ & 2️⃣
            insertToRemoteDb(response);               // 과제 구현 사항 3️⃣
            uploadToFtpServer(response);              // 과제 구현 사항 4️⃣
        }
        catch (Exception e) {
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        System.out.println("✅ APPLICATION FINISHED");
    }

    // 과제 구현 사항 1️⃣ & 2️⃣
    // 인스피언 서버로 요청 전송 후, 수신한 응답을 Response로 변환하여 리턴한다.
    private Response getDataAndConnInfo() throws IOException {
        String response = apiClient.sendApiRequest(
                AppConfigurer.getUserInfo().serialize(),
                AppConfigurer.getAPI_URL()
        );
        System.out.println("✅ API REQUEST : SUCCESS");
        return Mapper.mapToResponse(response);
    }

    // 과제 구현 사항 3️⃣
    // Order와 Item의 데이터를 핸들링하여 INSPIEN ORACLE DBMS에 삽입한다.
    private void insertToRemoteDb(Response response) throws IOException, ParserConfigurationException, SAXException, SQLException, ClassNotFoundException {
        oracleDbmsClient.setDbConnInfo(response.getDbConnInfo());
        ArrayList<HashMap<String, String>> joined = Mapper.xmlDataToObject(response.getXmlData()).handle();
        oracleDbmsClient.createData(joined);
    }

    // 과제 구현 사항 4️⃣
    // INSPIEN FTP SERVER에 JSON_DATA를 핸들링하여 업로드한다.
    private void uploadToFtpServer(Response response) throws IOException, NoSuchFieldException, IllegalAccessException {
        ftpClient.setFtpConnInfo(response.getFtpConnInfo());
        String content = Mapper.jsonDataToObject(response.getJsonData()).handle();
        String fileName = composeFileName();
        FileHandler.write(AppConfigurer.getFILE_PATH() + "/" + fileName, content);
        ftpClient.upload(fileName, content);
    }

    // 파일의 이름을 'INSPIEN_JSON_이름_YYYYMMDDHHMMSS' 형식으로 구성한다.
    private static String composeFileName() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedDateTime = now.format(formatter);
        return "INSPIEN_JSON_CHOYOUNGHYUN_" + formattedDateTime + ".txt";
    }
}
