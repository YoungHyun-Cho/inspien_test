package org.inspien.client.ftp;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.inspien.data.api.FtpConnInfo;
import org.inspien.exception.FtpConnectionInfoNotExistException;

import java.io.*;

/*
* # FtpClient.class
*   - 인스피언의 FTP Server와 소통하는 역할을 담당함.
*   - 업로드, 다운로드 구현
*       - 다운로드는 권한 문제로 동작하지 않음.
* */

public class FtpClient {

    private FtpConnInfo ftpConnInfo = null;
    private static final String NAME = "CHOYOUNGHYUN";

    // 외부로부터 FtpConnInfo를 입력 받아 바인딩해주는 Setter
    public void setFtpConnInfo(FtpConnInfo ftpConnInfo) {
        this.ftpConnInfo = ftpConnInfo;
    }

    // FtpConnInfo가 null인 상태에서 호출되면 예외를 발생시킨다.
    private void checkFtpConnInfo() {
        if (ftpConnInfo == null) throw new FtpConnectionInfoNotExistException();
    }

    // FTP Server로 파일을 업로드하고, 실행 결과와 파일 목록을 출력한다.
    public void upload(String fileName, String fileData) throws IOException {
        checkFtpConnInfo();
        System.out.println("✅ INSPIEN FTP SERVER : UPLOAD START");

        FTPClient ftpClient = new FTPClient();
        connect(ftpClient);

        InputStream inputStream = new ByteArrayInputStream(fileData.getBytes());
        boolean done = ftpClient.storeFile(ftpConnInfo.getFilePath() + fileName, inputStream);

        if (done) System.out.println("✅ INSPIEN FTP SERVER : UPLOAD SUCCESS");
        else System.out.println("❌ INSPIEN FTP SERVER : UPLOAD FAIL");

        printReplyInfo(ftpClient);
        printMyFileList(ftpClient, fileName);

        ftpClient.logout();

        if (ftpClient.isConnected()) ftpClient.disconnect();
    }

    // FTP Server로부터 파일을 다운로드하고, 실행 결과와 파일 목록을 출력한다.
    public void download(String remoteFileName, String localFilePath) throws IOException {
        checkFtpConnInfo();

        FTPClient ftpClient = new FTPClient();
        connect(ftpClient);

        OutputStream outputStream = new FileOutputStream(localFilePath);
        boolean done = ftpClient.retrieveFile(ftpConnInfo.getFilePath() + remoteFileName, outputStream);

        if (done) System.out.println("✅ INSPIEN FTP SERVER : DOWNLOAD SUCCESS");
        else System.out.println("❌ INSPIEN FTP SERVER : DOWNLOAD FAIL");

        printReplyInfo(ftpClient);

        outputStream.close();
    }

    // FtpConnInfo의 데이터를 사용하여 FTP Server와 연결을 시작한다.
    private void connect(FTPClient ftpClient) throws IOException {
        ftpClient.connect(ftpConnInfo.getHost(), ftpConnInfo.getPort());
        ftpClient.login(ftpConnInfo.getUser(), ftpConnInfo.getPassword());
        ftpClient.enterLocalPassiveMode();
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
    }

    // 업로드 및 다운로드 실행의 결과 정보를 출력한다.
    private void printReplyInfo(FTPClient ftpClient) {
        System.out.println("   REPLY CODE   : " + ftpClient.getReplyCode());
        System.out.println("   REPLY STRING : " + ftpClient.getReplyString());
    }

    // 파일 목록을 출력한다.
    private void printMyFileList(FTPClient ftpClient, String fileName) throws IOException {
        System.out.println("🔻 MY FILE LIST");
        FTPFile[] files = ftpClient.listFiles();
        for (FTPFile file : files) {
            if (file.getName().equals(fileName)) System.out.println("   " + file.getName() + " <- NEW");
            else if (file.getName().contains(NAME)) System.out.println("   " + file.getName());
        }
        System.out.println();
    }
}
