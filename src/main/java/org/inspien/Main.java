package org.inspien;

import org.inspien._config.AppConfigurer;

public class Main {
    public static void main(String[] args) {
        InspienCodingTest inspienCodingTest = new InspienCodingTest(
                AppConfigurer.client(),
                AppConfigurer.localOracleDbmsClient(),
                AppConfigurer.remoteOracleDbmsClient(),
                AppConfigurer.ftpClient()
        );

        inspienCodingTest.run();
    }
}