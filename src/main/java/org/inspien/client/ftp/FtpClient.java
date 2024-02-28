package org.inspien.client.ftp;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.inspien.data.api.FtpConnInfo;
import org.inspien.exception.FtpConnectionInfoNotExistException;

import java.io.*;

public class FtpClient {

    private FtpConnInfo ftpConnInfo = null;

    public void setFtpConnInfo(FtpConnInfo ftpConnInfo) {
        this.ftpConnInfo = ftpConnInfo;
    }

    private void checkDbConnInfo() {
        if (ftpConnInfo == null) throw new FtpConnectionInfoNotExistException();
    }

    public void upload(String fileName, String fileData) throws IOException {
        checkDbConnInfo();
        System.out.println("✅ INSPIEN FTP SERVER : UPLOAD START");
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect(ftpConnInfo.getHost(), ftpConnInfo.getPort());
        ftpClient.login(ftpConnInfo.getUser(), ftpConnInfo.getPassword());
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        ftpClient.enterLocalPassiveMode();

        InputStream inputStream = new ByteArrayInputStream(fileData.getBytes());
        boolean done = ftpClient.storeFile(ftpConnInfo.getFilePath() + fileName, inputStream);

        if (done) System.out.println("✅ INSPIEN FTP SERVER : UPLOAD SUCCESS");
        else System.out.println("❌ INSPIEN FTP SERVER : UPLOAD FAIL");

        System.out.println("    REPLY CODE   : " + ftpClient.getReplyCode());
        System.out.println("    REPLY STRING : " + ftpClient.getReplyString());

        ftpClient.logout();

        if (ftpClient.isConnected()) ftpClient.disconnect();

    }

    public void download(String remoteFileName, String localFilePath) throws IOException {
        checkDbConnInfo();

        OutputStream outputStream = new FileOutputStream(localFilePath);
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect(ftpConnInfo.getHost(), ftpConnInfo.getPort());
        ftpClient.login(ftpConnInfo.getUser(), ftpConnInfo.getPassword());
        ftpClient.enterLocalPassiveMode();

        boolean done = ftpClient.retrieveFile(ftpConnInfo.getFilePath() + remoteFileName, outputStream);
        if (done) System.out.println("🟥 DOWNLOAD SUCCESS");
        else System.out.println("❌ DOWNLOAD FAIL");

        System.out.println("🟥 REPLY CODE   : " + ftpClient.getReplyCode());
        System.out.println("🟥 REPLY STRING : " + ftpClient.getReplyString());

        FTPFile[] files = ftpClient.listFiles();
        for (FTPFile file : files) {
            System.out.println(file.getName());
        }

        outputStream.close();
    }

    public void check(String remoteFileName, String localFilePath) throws IOException {
        checkDbConnInfo();

        OutputStream outputStream = new FileOutputStream(localFilePath);
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect(ftpConnInfo.getHost(), ftpConnInfo.getPort());
        ftpClient.login(ftpConnInfo.getUser(), ftpConnInfo.getPassword());
        ftpClient.enterLocalPassiveMode();

        boolean done = ftpClient.retrieveFile(ftpConnInfo.getFilePath() + remoteFileName, outputStream);
        if (done) System.out.println("🟥 DOWNLOAD SUCCESS");
        else System.out.println("❌ DOWNLOAD FAIL");

        System.out.println("🟥 REPLY CODE   : " + ftpClient.getReplyCode());
        System.out.println("🟥 REPLY STRING : " + ftpClient.getReplyString());

        FTPFile[] files = ftpClient.listFiles();
        for (FTPFile file : files) {
            System.out.println(file.getName());
        }

        outputStream.close();
    }
}
