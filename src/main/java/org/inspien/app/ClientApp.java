package org.inspien.app;

import lombok.RequiredArgsConstructor;
import org.inspien.client.Client;
import org.inspien.client.RestApiClient;

@RequiredArgsConstructor
public class ClientApp {

    private final Client client;

    public void run() {
        String sendData =
                "{" +
                        "\"NAME\" : \"조영현테스트\"," +
                        "\"PHONE_NUMBER\" : \"010-9512-8646\"," +
                        "\"E_MAIL\" : \"psyhyun1030@gmail.com\"" +
                        "}";
        String apiUrl = "http://211.106.171.36:50000/RESTAdapter/RecruitingTest";


    }
}
