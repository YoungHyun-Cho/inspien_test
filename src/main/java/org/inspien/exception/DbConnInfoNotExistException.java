package org.inspien.exception;

/*
 * # DbConnInfoNotExistException.class
 *   - 애플리케이션 로직 상의 오류를 표현하기 위한 런타임 예외 클래스
 *   - OracleDbmsClient에 DbConnInfo가 바인딩되지 않은 상태에서
 *     OracleDbmsClient의 public 메서드를 호출하면 해당 예외가 발생함.
 * */

public class DbConnInfoNotExistException extends RuntimeException {
    public DbConnInfoNotExistException() {
        super("Information for database connection does not exist.");
    }
}
