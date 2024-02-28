package org.inspien.client.api;

import java.io.IOException;

public interface ApiClient {
    String sendApiRequest(String data, String apiUrl) throws IOException;
}
