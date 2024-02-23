package org.inspien.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {
    @JsonProperty("XML_DATA")
    private String xmlData;

    @JsonProperty("JSON_DATA")
    private String jsonData;

    @JsonProperty("DB_CONN_INFO")
    private DatabaseConnInfoDto databaseConnInfo;

    @JsonProperty("FTP_CONN_INFO")
    private FtpConnInfoDto ftpConnInfo;
}
