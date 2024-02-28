package org.inspien.app;

import lombok.RequiredArgsConstructor;
import org.inspien.client.Client;
import org.inspien.connection.database.InspienOracleJdbcConnection;
import org.inspien.connection.database.LocalOracleJdbcConnection;
import org.inspien.connection.ftp.InspienFtpUploader;
import org.inspien.dto.order.Item;
import org.inspien.dto.order.Order;
import org.inspien.dto.order.SalesStatus;
import org.inspien.dto.record.Records;
import org.inspien.dto.response.DBConnInfo;
import org.inspien.dto.response.FTPConnInfo;
import org.inspien.dto.response.Response;
import org.inspien.file.FileHandler;
import org.inspien.mapper.Mapper;
import org.inspien.service.TestService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

@RequiredArgsConstructor
public class InspienCodingTestApp {

    private final DBConnInfo localDbConnInfo = new DBConnInfo(
            "localhost", 1521,
            "system", "tkfkdgksmswodn",
            "XE", "ORDERS"
    );
    private final DBConnInfo inspienDbConnInfo = new DBConnInfo(
            "211.106.171.36", 11527,
            "inspien01", "inspien01",
            "POS", "INSPIEN_XMLDATA_INFO"
    );

    private static final FTPConnInfo ftpConnInfo = new FTPConnInfo(
            "211.106.171.36", 20421, "inspien01", "inspien01", "/"
    );

    private final Client client;
    private final TestService testService = new TestService(
            new LocalOracleJdbcConnection(localDbConnInfo), new InspienOracleJdbcConnection(inspienDbConnInfo)
    );
    private final InspienFtpUploader inspienFtpUploader = new InspienFtpUploader(ftpConnInfo);
    private static final String PATH = "/Users/0hyuncho/Desktop/inspien_code_temp_test";

    public void run() {
        try {
            String input =
                    "{" +
                            "\"NAME\" : \"조영현\"," +
                            "\"PHONE_NUMBER\" : \"010-9512-8646\"," +
                            "\"E_MAIL\" : \"psyhyun1030@gmail.com\"" +
                    "}";
            String url = "http://211.106.171.36:50000/RESTAdapter/RecruitingTest";

            // 인스피언 서버로 요청 전송 후, 수신한 응답을 Java Object로 변환
            Response response = Mapper.strToResponse(client.requestDataAndConnInfo(input, url));

            // XML_DATA를 Java Object로 변환
            SalesStatus salesStatus = Mapper.xmlStringToObject(response.getXmlData());

            // Local ORACLE에 저장 🟥 임시 주석
//            for (Order order : salesStatus.getOrders()) {
//                testService.createOrder(order);
//            }
//            for (Item item : salesStatus.getItems()) {
//                testService.createItem(item);
//            }

            // 조인 후 컬렉션으로 저장
            ArrayList<HashMap<String, String>> joined = testService.findSalesStatus();

            // 인스피언 오라클에 데이터 저장 🟥 임시 주석
//            testService.createJoinedData(joined);

            // ------------------------------------
            // 파일 업로드

            // Json 데이터를 Java Object로 변환
            Records records = Mapper.jsonStringToObject(response.getJsonData());

            // 구분자와 개행자 추가
            StringBuilder stringBuilder = new StringBuilder();
            records.getRecords().stream()
                    .forEach(record -> stringBuilder.append(record.serialize()));

            // 파일 이름
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            String formattedDateTime = now.format(formatter);
            String fileName = "INSPIEN_JSON_CHOYOUNGHYUN_" + formattedDateTime + ".txt";

            // 파일로 저장
            FileHandler.write(PATH + "/" + fileName, stringBuilder.toString());

            // 다시 읽기
            String fileData = FileHandler.read(PATH + "/" + fileName);

            System.out.println(fileName);
            System.out.println(fileData);

            // JSON으로 전송
            inspienFtpUploader.upload(fileName, fileData);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
