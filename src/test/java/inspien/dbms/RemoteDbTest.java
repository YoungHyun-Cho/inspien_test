package inspien.dbms;

import org.inspien._config.AppConfigurer;
import org.inspien.client.api.ApacheApiClient;
import org.inspien.client.api.ApiClient;
import org.inspien.client.dbms.RemoteOracleDbmsClient;
import org.inspien.data.api.Response;
import org.inspien.util.Mapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import static org.assertj.core.api.Assertions.*;

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
        HashMap<String, String> data = new HashMap<>();
        data.put("tableName", "INSPIEN_XMLDATA_INFO");
        data.put("columns", "*");
        data.put("where", "SENDER = \'조영현\'");
        ArrayList<HashMap<String, String>> result = remoteOracleDbmsClient.findInsertedData(data);

        System.out.println(result);
        System.out.println("COUNT : " + result.size());
    }
}