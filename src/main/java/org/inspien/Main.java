package org.inspien;

import org.inspien.app.InspienCodingTestApp;
import org.inspien.config.AppConfigurer;

public class Main {
    public static void main(String[] args) {
        AppConfigurer appConfigurer = new AppConfigurer();
        InspienCodingTestApp inspienCodingTestApp = new InspienCodingTestApp(appConfigurer.client()); // DI
        inspienCodingTestApp.run();
    }
}