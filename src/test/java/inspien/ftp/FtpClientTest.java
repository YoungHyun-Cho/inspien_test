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

import java.io.IOException;

public class FtpClientTest {
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
            if (file.getName().contains("CHOYOUNGHYUN")) System.out.println(file.getName());
        }
    }

    @Test
    public void downloadTest() throws IOException {
        FtpClient ftpClient = new FtpClient();

        ftpClient.setFtpConnInfo(ftpConnInfo);
        ftpClient.download(
                "INSPIEN_JSON_CHOYOUNGHYUN_20240304152026.txt",
                AppConfigurer.getFILE_PATH() + "/DOWNLOAD_TEST.txt"
        );
    }
}
