package org.inspien.exception;

public class FtpConnectionInfoNotExistException extends RuntimeException {
    public FtpConnectionInfoNotExistException() {
        super("Information for FTP connection does not exist.");
    }
}
