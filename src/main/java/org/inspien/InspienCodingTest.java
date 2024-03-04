package org.inspien;

import lombok.RequiredArgsConstructor;
import org.inspien._config.AppConfigurer;
import org.inspien.client.api.ApiClient;
import org.inspien.client.dbms.RemoteOracleDbmsClient;
import org.inspien.client.ftp.FtpClient;
import org.inspien.data.xml.ParsedXmlData;
import org.inspien.data.api.Response;
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
    private final RemoteOracleDbmsClient remoteOracleDbmsClient;
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
        String json = AppConfigurer.getUserInfo().serialize();
        String response = apiClient.sendApiRequest(json, AppConfigurer.getAPI_URL());
        System.out.println("✅ API REQUEST : SUCCESS");
        return Mapper.mapToResponse(response);
    }

    // 과제 구현 사항 3️⃣
    // Order와 Item의 데이터를 핸들링하여 INSPIEN ORACLE DBMS에 삽입한다.
    private void insertToRemoteDb(Response response) throws IOException, ParserConfigurationException, SAXException, SQLException, ClassNotFoundException {
        remoteOracleDbmsClient.setDbConnInfo(response.getDbConnInfo());
        ArrayList<HashMap<String, String>> joined = Mapper.xmlDataToObject(response.getXmlData()).handle();
        System.out.println("✅ INSPIEN ORACLE DBMS : INSERT START");
        printMyDataSize(true);
        remoteOracleDbmsClient.createData(joined);
        System.out.println("✅ INSPIEN ORACLE DBMS : INSERT SUCCESS");
        printMyDataSize(false);
    }

    // 과제 구현 사항 4️⃣
    // INSPIEN FTP SERVER에 JSON_DATA를 핸들링하여 업로드한다.
    private void uploadToFtpServer(Response response) throws IOException {
        ftpClient.setFtpConnInfo(response.getFtpConnInfo());
        String content = Mapper.jsonDataToObject(response.getJsonData()).handle();
        String fileName = composeFileName();
        ftpClient.upload(fileName, content);
    }

    // 파일의 이름을 'INSPIEN_JSON_이름_YYYYMMDDHHMMSS' 형식으로 구성한다.
    private static String composeFileName() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedDateTime = now.format(formatter);
        return "INSPIEN_JSON_CHOYOUNGHYUN_" + formattedDateTime + ".txt";
    }

    // 인스피언의 ORACLE DBMS에 삽입된 나의 데이터의 개수를 계수한다.
    private void printMyDataSize(boolean isBefore) throws SQLException, ClassNotFoundException {
        HashMap<String, String> sqlComponent = new HashMap<>();
        sqlComponent.put("tableName", "INSPIEN_XMLDATA_INFO");
        sqlComponent.put("columns", "*");
        sqlComponent.put("where", "SENDER = \'조영현\'");
        ArrayList<HashMap<String, String>> result = remoteOracleDbmsClient.findDataBy(sqlComponent);
        System.out.println((isBefore ? "   BEFORE " : "   AFTER ") + "INSERT : " + result.size());
    }
}
