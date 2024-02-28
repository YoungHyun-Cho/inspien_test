package org.inspien.data.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
* # DbConnInfo.class
*   - Oracle DBMS 연결에 필요한 데이터를 표현한다.
* */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DbConnInfo {
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
