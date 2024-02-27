package org.inspien.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FTPConnInfo {
    @JsonProperty("HOST")
    private String host;

    @JsonProperty("PORT")
    private int port;

    @JsonProperty("USER")
    private String user;

    @JsonProperty("PASSWORD")
    private String password;

    @JsonProperty("FILE_PATH")
    private String filePath;
}
