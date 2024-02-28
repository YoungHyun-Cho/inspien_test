package org.inspien;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.inspien._config.AppConfigurer;
import org.inspien.client.api.ApiClient;
import org.inspien.client.dbms.RemoteOracleDbmsClient;
import org.inspien.client.dbms.LocalOracleDbmsClient;
import org.inspien.client.ftp.FtpClient;
import org.inspien.data.xml.Item;
import org.inspien.data.xml.Order;
import org.inspien.data.xml.ParsedXmlData;
import org.inspien.data.json.ParsedJsonData;
import org.inspien.data.api.Response;
import org.inspien.util.Mapper;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

@RequiredArgsConstructor
public class InspienCodingTest {

    private final ApiClient apiClient;
    private final LocalOracleDbmsClient localOracleDbmsClient;
    private final RemoteOracleDbmsClient remoteOracleDbmsClient;
    private final FtpClient ftpClient;

    public void run() { // 🟥 예외 수정 필요
        try {
            Response response = getDataAndConnInfo(); // 과제 구현 사항 1️⃣ & 2️⃣
            insertToRemoteDb(response);               // 과제 구현 사항 3️⃣
            uploadToFtpServer(response);              // 과제 구현 사항 4️⃣
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 과제 구현 사항 1️⃣ & 2️⃣
    // 인스피언 서버로 요청 전송 후, 수신한 응답을 Response로 변환하여 리턴한다.
    private Response getDataAndConnInfo() throws IOException {
        String json = AppConfigurer.getUserInfo().serialize();
        String response = apiClient.sendApiRequest(json, AppConfigurer.getAPI_URL());
        return Mapper.mapToResponse(response);
    }

    // 과제 구현 사항 3️⃣
    // Order와 Item의 데이터를 조인하여 INSPIEN ORACLE DBMS에 삽입한다.
    private void insertToRemoteDb(Response response) throws IOException, ParserConfigurationException, SAXException, SQLException, ClassNotFoundException {
        localOracleDbmsClient.setDbConnInfo(AppConfigurer.getLocalDbConnInfo());
        remoteOracleDbmsClient.setDbConnInfo(response.getDbConnInfo());
        insertToLocalDb(Mapper.xmlDataToObject(response.getXmlData()));
        ArrayList<HashMap<String, String>> joined = joinInLocalDb();
//        remoteOracleDbmsClient.createJoinedData(joined); // 🟥 임시 주석
    }

    // 과제 구현 사항 4️⃣
    // INSPIEN FTP SERVER에 JSON_DATA를 가공하여 업로드한다.
    private void uploadToFtpServer(Response response) throws IOException {
        ftpClient.setFtpConnInfo(response.getFtpConnInfo());
        String content = handleJsonData(response.getJsonData());
        String fileName = composeFileName();
//        ftpClient.upload(fileName, content); // 🟥 임시 주석
    }

    // Local ORACLE DBMS에 Order와 Item의 데이터를 저장한다.
    private void insertToLocalDb(ParsedXmlData parsedXmlData) throws SQLException, ClassNotFoundException {
        for (Order order : parsedXmlData.getOrders()) {
            localOracleDbmsClient.createOrder(order);
        }
        for (Item item : parsedXmlData.getItems()) {
            localOracleDbmsClient.createItem(item);
        }
    }

    // Local ORACLE DBMS에서 Order와 Item의 데이터를 조인하여 조회한 데이터를 컬렉션에 담아 리턴한다.
    private ArrayList<HashMap<String, String>> joinInLocalDb() throws SQLException, ClassNotFoundException {
        // 조인 후 컬렉션으로 저장
        HashMap<String, String> data = new HashMap<>();
        data.put("tableName", "ORDERS");
        data.put("columns", "*");
        data.put("orderBy", "");
        return localOracleDbmsClient.findOrderItem(data);
    }

    // JSON 문자열을 입력 받아 과제가 구분자(^)와 개행(\n)을 적절히 추가하여 리턴한다.
    private static String handleJsonData(String jsonData) throws JsonProcessingException , UnsupportedEncodingException {
        // Json 데이터를 Java Object로 변환
        ParsedJsonData parsedJsonData = Mapper.jsonDataToObject(jsonData);

        // 구분자와 개행자 추가
        StringBuilder stringBuilder = new StringBuilder();
        parsedJsonData.getRecords().stream()
                .forEach(record -> stringBuilder.append(record.serialize()));

        return stringBuilder.toString();
    }

    // 파일의 이름을 'INSPIEN_JSON_이름_YYYYMMDDHHMMSS' 형식으로 구성한다.
    private static String composeFileName() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedDateTime = now.format(formatter);
        return "INSPIEN_JSON_CHOYOUNGHYUN_" + formattedDateTime + ".txt";
    }
}
