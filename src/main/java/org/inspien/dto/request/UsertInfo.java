package org.inspien.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UsertInfo {
    @JsonProperty("NAME")
    private String name;

    @JsonProperty("PHONE_NUMBER")
    private String phoneNumber;

    @JsonProperty("E_MAIL")
    private String email;
}
