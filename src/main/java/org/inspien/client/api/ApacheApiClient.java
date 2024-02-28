package org.inspien.client.api;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class ApacheApiClient implements ApiClient {
    @Override
    public String sendApiRequest(String data, String apiUrl) throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost postRequest = new HttpPost(apiUrl);

        StringEntity stringEntity = new StringEntity(data);
        postRequest.setEntity(stringEntity);
        postRequest.setHeader("Accept", "application/json");
        postRequest.setHeader("Content-type", "application/json");

        HttpResponse response = httpClient.execute(postRequest);
        return EntityUtils.toString(response.getEntity());
    }
}
