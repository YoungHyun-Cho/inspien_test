package org.inspien.data.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/*
* # FtpConnInfo.class
*   - FTP 연결에 필요한 데이터를 표현한다.
* */

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FtpConnInfo {
    @JsonProperty("HOST")
    private String host;

    @JsonProperty("PORT")
    private Integer port;

    @JsonProperty("USER")
    private String user;

    @JsonProperty("PASSWORD")
    private String password;

    @JsonProperty("FILE_PATH")
    private String filePath;
}
