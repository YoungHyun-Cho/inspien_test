package org.inspien.client.api;

import java.io.IOException;

/*
* # ApiClient.interface
*   - JavaApiClient와 ApacheApiClient를 추상화한 인터페이스
*   - 두 구현 클래스의 교체를 용이하게 해준다.
* */

public interface ApiClient {

    // API URL과 데이터를 입력 받아, API URL로 데이터를 전송하고 응답을 수신하여 문자열로 리턴한다.
    String sendApiRequest(String data, String apiUrl) throws IOException;
}
