package org.inspien.client.api;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/*
* # ApacheApiClient.class
*   - 아파치 HttpClient를 사용하여 API 요청을 전송하고, 응답을 수신한다.
* */

public class ApacheApiClient implements ApiClient {

    // 인스피언의 API 서버로 요청을 보내고, 응답을 문자열로 변환하여 리턴한다.
    @Override
    public String sendApiRequest(String data, String apiUrl) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost postRequest = new HttpPost(apiUrl);

        StringEntity stringEntity = new StringEntity(data);
        postRequest.setEntity(stringEntity);
        postRequest.setHeader("Accept", "application/json");
        postRequest.setHeader("Content-type", "application/json");

        HttpResponse response = httpClient.execute(postRequest);
        String responseStr = EntityUtils.toString(response.getEntity());
        httpClient.close();
        return responseStr;
    }
}
