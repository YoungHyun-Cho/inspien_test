package org.inspien.data.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
* # Response.class
*   - Mapper에 의해 API 요청에 대한 응답이 Reponse의 인스턴스로 변환된다.
* */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    @JsonProperty("XML_DATA")
    private String xmlData;

    @JsonProperty("JSON_DATA")
    private String jsonData;

    @JsonProperty("DB_CONN_INFO")
    private DbConnInfo dbConnInfo;

    @JsonProperty("FTP_CONN_INFO")
    private FtpConnInfo ftpConnInfo;
}
