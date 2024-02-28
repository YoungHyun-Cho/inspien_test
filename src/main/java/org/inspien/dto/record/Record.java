package org.inspien.dto.record;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
