package org.inspien;

import org.inspien.app.ClientApp;
import org.inspien.client.RestApiClient;
import org.inspien.config.AppConfigurer;

public class Main {
    public static void main(String[] args) {
        AppConfigurer appConfigurer = new AppConfigurer();
        ClientApp clientApp = new ClientApp(appConfigurer.client()); // DI
        clientApp.run();
    }
}