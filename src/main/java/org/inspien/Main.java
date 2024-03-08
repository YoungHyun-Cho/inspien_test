package org.inspien;

import org.inspien._config.AppConfigurer;

/*
* # Main.class
*   - 애플리케이션 실행을 위한 Entry Point
* */

public class Main {
    public static void main(String[] args) {

        // AppConfigurer부터 의존 객체를 주입 받는다.
        InspienCodingTest inspienCodingTest = new InspienCodingTest(
                AppConfigurer.apiClient(),
                AppConfigurer.oracleDbmsClient(),
                AppConfigurer.ftpClient()
        );

        // 애플리케이션 실행
        inspienCodingTest.run();
    }
}