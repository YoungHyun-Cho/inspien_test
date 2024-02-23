package unit_test.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.inspien.dto.request.RequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class DtoTest {

    private static final String JSON_STRING = "{\"NAME\":\"조영현\",\"PHONE_NUMBER\":\"010-1234-1234\",\"E_MAIL\":\"0hyuncho@gmail.com\"}";
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("DTO -> JSON")
    public void dtoToJson() throws JsonProcessingException {
        RequestDto requestDto = new RequestDto("조영현", "010-1234-1234", "0hyuncho@gmail.com");

        String json = objectMapper.writeValueAsString(requestDto);

        assertThat(json).isEqualTo(JSON_STRING);
    }

    @Test
    @DisplayName("JSON -> DTO")
    public void jsonToDto() throws JsonProcessingException {
        RequestDto requestDto = objectMapper.readValue(JSON_STRING, new TypeReference<RequestDto>() {});

        assertThat(requestDto.getName()).isEqualTo("조영현");
        assertThat(requestDto.getPhoneNumber()).isEqualTo("010-1234-1234");
        assertThat(requestDto.getEmail()).isEqualTo("0hyuncho@gmail.com");
    }
}
