package org.inspien.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DBConnInfo {
    @JsonProperty("HOST")
    private String host;

    @JsonProperty("PORT")
    private Integer port;

    @JsonProperty("USER")
    private String user;

    @JsonProperty("PASSWORD")
    private String password;

    @JsonProperty("SID")
    private String sid;

    @JsonProperty("TABLENAME")
    private String tableName;
}
