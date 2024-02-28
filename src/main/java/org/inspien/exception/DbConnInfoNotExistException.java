package org.inspien.exception;

public class DbConnInfoNotExistException extends RuntimeException {
    public DbConnInfoNotExistException() {
        super("Information for database connection does not exist.");
    }
}
