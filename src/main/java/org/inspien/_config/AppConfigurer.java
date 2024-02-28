package org.inspien._config;

import lombok.Getter;
import org.inspien.client.api.ApacheApiClient;
import org.inspien.client.api.ApiClient;
import org.inspien.client.dbms.LocalOracleDbmsClient;
import org.inspien.client.dbms.RemoteOracleDbmsClient;
import org.inspien.client.ftp.FtpClient;
import org.inspien.data.api.DbConnInfo;
import org.inspien.data.api.UserInfo;

public class AppConfigurer {

    @Getter
    private static final String API_URL = "http://211.106.171.36:50000/RESTAdapter/RecruitingTest";

    @Getter
    private static final UserInfo userInfo = new UserInfo(
            "조영현", "010-9512-8646", "psyhyun1030@gmail.com"
    );

    @Getter
    private static final DbConnInfo localDbConnInfo = new DbConnInfo(
            "localhost", 1521,
            "system", "tkfkdgksmswodn",
            "XE", "ORDERS"
    );

    public static ApiClient client() {
        return new ApacheApiClient();
    }

    public static LocalOracleDbmsClient localOracleDbmsClient() {
        return new LocalOracleDbmsClient();
    }

    public static RemoteOracleDbmsClient remoteOracleDbmsClient() {
        return new RemoteOracleDbmsClient();
    }

    public static FtpClient ftpClient() {
        return new FtpClient();
    }
}
