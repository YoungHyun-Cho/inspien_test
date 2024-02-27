package unit_test.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.inspien.dto.request.UsertInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class DtoTest {

    private static final String JSON_STRING = "{\"NAME\":\"조영현\",\"PHONE_NUMBER\":\"010-1234-1234\",\"E_MAIL\":\"0hyuncho@gmail.com\"}";
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("DTO -> JSON")
    public void dtoToJson() throws JsonProcessingException {
        UsertInfo usertInfo = new UsertInfo("조영현", "010-1234-1234", "0hyuncho@gmail.com");

        String json = objectMapper.writeValueAsString(usertInfo);

        assertThat(json).isEqualTo(JSON_STRING);
    }

    @Test
    @DisplayName("JSON -> DTO")
    public void jsonToDto() throws JsonProcessingException {
        UsertInfo usertInfo = objectMapper.readValue(JSON_STRING, new TypeReference<UsertInfo>() {});

        assertThat(usertInfo.getName()).isEqualTo("조영현");
        assertThat(usertInfo.getPhoneNumber()).isEqualTo("010-1234-1234");
        assertThat(usertInfo.getEmail()).isEqualTo("0hyuncho@gmail.com");
    }
}
