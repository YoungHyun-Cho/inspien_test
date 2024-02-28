package unit_test.ftp;

import org.inspien.connection.ftp.InspienFtpUploader;
import org.inspien.dto.response.FTPConnInfo;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class FtpTest {

    private static final FTPConnInfo ftpConnInfo = new FTPConnInfo(
            "211.106.171.36", 20421, "inspien01", "inspien01", "/"
    );

    private static final String LOCAL_FILE_PATH = "/Users/0hyuncho/Desktop/inspien_code_temp_test";
    private static final String FILE_NAME = "INSPIEN_JSON_CHOYOUNGHYUN_20240228144321.txt";
    private InspienFtpUploader inspienFtpUploader = new InspienFtpUploader(ftpConnInfo);

//    @Test // 🟥 FILE_NAME 확인 후 테스트
//    public void uploadTest() throws IOException {
//        inspienFtpUploader.upload(FILE_NAME, "INSPIEN INSPIRES ME");
//    }

    @Test
    public void downloadTest() {
        inspienFtpUploader.download(FILE_NAME, LOCAL_FILE_PATH + "/" + FILE_NAME + "+ DOWNLOAD");
    }
}
