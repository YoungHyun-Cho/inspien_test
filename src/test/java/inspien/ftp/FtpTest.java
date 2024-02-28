package inspien.ftp;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.inspien._config.AppConfigurer;
import org.inspien.client.api.ApacheApiClient;
import org.inspien.client.api.ApiClient;
import org.inspien.client.ftp.FtpClient;
import org.inspien.data.api.FtpConnInfo;
import org.inspien.data.api.Response;
import org.inspien.util.Mapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class FtpTest {
    private static final String FILE_NAME = "INSPIEN_JSON_CHOYOUNGHYUN_20240228215012.txt";
    private static final ApiClient apiClient = new ApacheApiClient();
    private static FtpConnInfo ftpConnInfo;

    @BeforeAll
    public static void initiateData() throws IOException {
        Response response = Mapper.mapToResponse(
                apiClient.sendApiRequest(
                        AppConfigurer.getUserInfo().serialize(),
                        AppConfigurer.getAPI_URL()
                )
        );
        ftpConnInfo = response.getFtpConnInfo();
    }

    @Test
    public void fetchMyData() throws IOException {
        FTPClient apacheFtpClient = new FTPClient();
        apacheFtpClient.connect(ftpConnInfo.getHost(), ftpConnInfo.getPort());
        apacheFtpClient.login(ftpConnInfo.getUser(), ftpConnInfo.getPassword());
        apacheFtpClient.enterLocalPassiveMode();
        FTPFile[] files = apacheFtpClient.listFiles();
        for (FTPFile file : files) {
            if (file.getName().equals(FILE_NAME)) System.out.println("🟥 FILE_NAME : " + file.getName());
        }
    }
}
