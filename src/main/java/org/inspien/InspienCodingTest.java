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

/*
* # InspienCodingTest.class
*   - 애플리케이션 실행 흐름을 담당함.
*   - 각 Client들과 의존 관계를 형성함.
* */

@RequiredArgsConstructor
public class InspienCodingTest {

    private final ApiClient apiClient;
    private final LocalOracleDbmsClient localOracleDbmsClient;
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
    // Order와 Item의 데이터를 조인하여 INSPIEN ORACLE DBMS에 삽입한다.
    private void insertToRemoteDb(Response response) throws IOException, ParserConfigurationException, SAXException, SQLException, ClassNotFoundException {
        remoteOracleDbmsClient.setDbConnInfo(response.getDbConnInfo());
        insertToLocalDb(Mapper.xmlDataToObject(response.getXmlData()));
        ArrayList<HashMap<String, String>> joined = joinInLocalDb();
        System.out.println("✅ INSPIEN ORACLE DBMS : INSERT START");
        printMyDataSize(true);
        remoteOracleDbmsClient.createData(joined);
        System.out.println("✅ INSPIEN ORACLE DBMS : INSERT SUCCESS");
        printMyDataSize(false);
    }

    // 과제 구현 사항 4️⃣
    // INSPIEN FTP SERVER에 JSON_DATA를 가공하여 업로드한다.
    private void uploadToFtpServer(Response response) throws IOException {
        ftpClient.setFtpConnInfo(response.getFtpConnInfo());
        String content = handleJsonData(response.getJsonData());
        String fileName = composeFileName();
        ftpClient.upload(fileName, content);
    }

    // Local ORACLE DBMS에 Order와 Item의 데이터를 저장한다.
    private void insertToLocalDb(ParsedXmlData parsedXmlData) throws SQLException, ClassNotFoundException {
        System.out.println("✅ LOCAL ORACLE DBMS : INSERT START");
        for (Order order : parsedXmlData.getOrders()) {
            localOracleDbmsClient.createOrder(order);
        }
        System.out.println("   ORDER INSERTED");
        for (Item item : parsedXmlData.getItems()) {
            localOracleDbmsClient.createItem(item);
        }
        System.out.println("   ITEM INSERTED");
        System.out.println("✅ LOCAL ORACLE DBMS : INSERT SUCCESS");
    }

    // Local ORACLE DBMS에서 Order와 Item의 데이터를 조인하여 조회한 데이터를 컬렉션에 담아 리턴한다.
    private ArrayList<HashMap<String, String>> joinInLocalDb() throws SQLException, ClassNotFoundException {
        System.out.println("✅ LOCAL ORACLE DBMS : JOIN START");
        HashMap<String, String> sqlComponent = new HashMap<>();
        sqlComponent.put("tableName", "ORDERS");
        sqlComponent.put("naturalJoin", "ITEM");
        sqlComponent.put("columns", "*");
        ArrayList<HashMap<String, String>> joined = localOracleDbmsClient.findDataBy(sqlComponent);
        System.out.println("✅ LOCAL ORACLE DBMS : JOIN SUCCESS");
        return joined;
    }

    // JSON 문자열을 입력 받아 과제가 구분자(^)와 개행(\n)을 적절히 추가하여 리턴한다.
    private static String handleJsonData(String jsonData) throws JsonProcessingException , UnsupportedEncodingException {
        System.out.println("✅ JSON_DATA : PROCESSING START");
        ParsedJsonData parsedJsonData = Mapper.jsonDataToObject(jsonData);

        StringBuilder stringBuilder = new StringBuilder();
        parsedJsonData.getRecords().stream()
                .forEach(record -> stringBuilder.append(record.serialize()));
        System.out.println("✅ JSON_DATA : PROCESSING SUCCESS");
        return stringBuilder.toString();
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
