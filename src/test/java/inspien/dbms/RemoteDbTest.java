package inspien.dbms;

import org.inspien._config.AppConfigurer;
import org.inspien.client.api.ApacheApiClient;
import org.inspien.client.api.ApiClient;
import org.inspien.client.dbms.RemoteOracleDbmsClient;
import org.inspien.data.api.Response;
import org.inspien.util.Mapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class RemoteDbTest {
    private static final ApiClient apiClient = new ApacheApiClient();
    private static final RemoteOracleDbmsClient remoteOracleDbmsClient = new RemoteOracleDbmsClient();
    @BeforeAll
    public static void initiateData() throws IOException {
        Response response = Mapper.mapToResponse(
                apiClient.sendApiRequest(
                        AppConfigurer.getUserInfo().serialize(),
                        AppConfigurer.getAPI_URL()
                )
        );
        remoteOracleDbmsClient.setDbConnInfo(response.getDbConnInfo());
    }

    @Test
    public void fetchMyData() throws ClassNotFoundException, SQLException {
        HashMap<String, String> sqlComponent = new HashMap<>();
        sqlComponent.put("tableName", "INSPIEN_XMLDATA_INFO");
        sqlComponent.put("columns", "*");
        sqlComponent.put("where", "SENDER = \'조영현\'");
        ArrayList<HashMap<String, String>> result = remoteOracleDbmsClient.findDataBy(sqlComponent);

        System.out.println(result);
        System.out.println("COUNT : " + result.size());
    }
}