package org.inspien._config;

import lombok.Getter;
import org.inspien.client.api.ApacheApiClient;
import org.inspien.client.api.ApiClient;
import org.inspien.client.dbms.RemoteOracleDbmsClient;
import org.inspien.client.ftp.FtpClient;
import org.inspien.data.api.UserInfo;

/*
* # AppConfigurer.class
*   - 애플리케이션의 상수값을 설정하고, 객체를 생성 및 의존 관계를 설정한다.
* */

public class AppConfigurer {

    @Getter
    private static final String API_URL = "http://211.106.171.36:50000/RESTAdapter/RecruitingTest";

    @Getter
    private static final String FILE_PATH = "/Users/0hyuncho/Desktop/Inspien";

    @Getter
    private static final UserInfo userInfo = new UserInfo(
            "조영현", "010-9512-8646", "psyhyun1030@gmail.com"
    );

    // 사용할 API Client의 구현체를 선택할 수 있다.
    // InspienCodingTest가 의존할 ApiClient를 결정하고 생성한다.
    public static ApiClient client() {
        return new ApacheApiClient();
    }

    // RemoteOracleDbmsClient의 인스턴스를 생성한다.
    // 객체를 선택할 필요는 없지만 일관성을 위해 AppConfigurer에서 객체를 생성하도록 하였다.
    public static RemoteOracleDbmsClient remoteOracleDbmsClient() {
        return new RemoteOracleDbmsClient();
    }

    // FtpClient의 인스턴스를 생성한다.
    // 객체를 선택할 필요는 없지만 일관성을 위해 AppConfigurer에서 객체를 생성하도록 하였다.
    public static FtpClient ftpClient() {
        return new FtpClient();
    }
}
