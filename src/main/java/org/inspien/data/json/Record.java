package org.inspien.data.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/*
* # Record.class
*   - JSON_DATA의 record 배열 내 각 요소를 표현한다.
*   - serialize()를 통해 과제 요구 사항을 구현한다.
* */

@Getter
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

    // 하나의 Record 인스턴스가 가진 모든 필드를 순서에 맞게 직렬화한다.
    public String serialize() {
        return new StringBuilder()
                .append(name).append("^")
                .append(phone).append("^")
                .append(email).append("^")
                .append(birthDate).append("^")
                .append(company).append("^")
                .append(personalNumber).append("^")
                .append(organizationNumber).append("^")
                .append(country).append("^")
                .append(region).append("^")
                .append(city).append("^")
                .append(street).append("^")
                .append(zipCode).append("^")
                .append(creditCard).append("^")
                .append(guid).append("\n")
                .toString();
    }

    @Override
    public String toString() {
        return "Record{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", company='" + company + '\'' +
                ", personalNumber='" + personalNumber + '\'' +
                ", organizationNumber='" + organizationNumber + '\'' +
                ", country='" + country + '\'' +
                ", region='" + region + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", creditCard='" + creditCard + '\'' +
                ", guid='" + guid + '\'' +
        '}';
    }
}
