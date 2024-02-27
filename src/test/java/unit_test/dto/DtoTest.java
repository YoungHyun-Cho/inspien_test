package unit_test.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.inspien.dto.request.UserInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class DtoTest {

    private static final String JSON_STRING = "{\"NAME\":\"조영현\",\"PHONE_NUMBER\":\"010-1234-1234\",\"E_MAIL\":\"0hyuncho@gmail.com\"}";
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("DTO -> JSON")
    public void dtoToJson() throws JsonProcessingException {
        UserInfo userInfo = new UserInfo("조영현", "010-1234-1234", "0hyuncho@gmail.com");

        String json = objectMapper.writeValueAsString(userInfo);

        assertThat(json).isEqualTo(JSON_STRING);
    }

    @Test
    @DisplayName("JSON -> DTO")
    public void jsonToDto() throws JsonProcessingException {
        UserInfo userInfo = objectMapper.readValue(JSON_STRING, new TypeReference<UserInfo>() {});

        assertThat(userInfo.getName()).isEqualTo("조영현");
        assertThat(userInfo.getPhoneNumber()).isEqualTo("010-1234-1234");
        assertThat(userInfo.getEmail()).isEqualTo("0hyuncho@gmail.com");
    }
}
