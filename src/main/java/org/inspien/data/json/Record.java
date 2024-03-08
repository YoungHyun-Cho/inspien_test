package org.inspien.data.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.lang.reflect.Field;

/*
* # Record.class
*   - JSON_DATA의 record 배열 내 각 요소를 표현한다.
*   - serialize()를 통해 과제 요구 사항을 구현한다.
* */

@Getter
@ToString
public class Record {
    @JsonProperty("Names")
    private String name;

    @JsonProperty("Phone")
    private String phone;

    @JsonProperty("Email")
    private String email;

    @JsonProperty("BirthDate")
    private String birthDate;

    @JsonProperty("Company")
    private String company;

    @JsonProperty("PersonalNumber")
    private String personalNumber;

    @JsonProperty("OrganisationNumber")
    private String organizationNumber;

    @JsonProperty("Country")
    private String country;

    @JsonProperty("Region")
    private String region;

    @JsonProperty("City")
    private String city;

    @JsonProperty("Street")
    private String street;

    @JsonProperty("ZipCode")
    private String zipCode;

    @JsonProperty("CreditCard")
    private String creditCard;

    @JsonProperty("GUID")
    private String guid;

    // 하나의 Record 인스턴스가 가진 모든 필드를 순서에 맞게 직렬화한다. (Reflection API 사용)
    public String serialize() throws NoSuchFieldException, IllegalAccessException {
        StringBuilder result = new StringBuilder();
        final String[] FIELD_NAMES = {
                "name", "phone", "email", "birthDate", "company",
                "personalNumber", "organizationNumber", "country",
                "region", "city", "street", "zipCode", "creditCard", "guid"
        };

        for (int i = 0; i < FIELD_NAMES.length; i++) {
            Field field = Record.class.getDeclaredField(FIELD_NAMES[i]);
            Object value = field.get(this);

            result.append(value != null ? value.toString() : "");

            if (i < FIELD_NAMES.length - 1) result.append("^");
        }
        result.append("\n");
        return result.toString();
    }
}
