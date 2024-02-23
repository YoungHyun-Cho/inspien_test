package org.inspien.config;

import org.inspien.client.Client;
import org.inspien.client.RestApiClient;

public class AppConfigurer {

    public Client client() {
        return new RestApiClient();
    }
}
