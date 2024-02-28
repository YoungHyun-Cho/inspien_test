package org.inspien.data.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
* # UserInfo.class
*   - API 요청을 전송할 때 요청 바디에 담을 데이터를 표현한다.
* */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    @JsonProperty("NAME")
    private String name;

    @JsonProperty("PHONE_NUMBER")
    private String phoneNumber;

    @JsonProperty("E_MAIL")
    private String email;

    public String serialize() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }
}