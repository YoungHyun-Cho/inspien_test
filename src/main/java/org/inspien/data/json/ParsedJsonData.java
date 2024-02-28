package org.inspien.data.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/*
* # ParsedJsonData.class
*   - Mapper에 의해 JSON_DATA가 ParsedJsonData의 인스턴스로 매핑된다.
* */

@Getter
public class ParsedJsonData {
    @JsonProperty("record")
    List<Record> records;

    @Override
    public String toString() {
        return "Records{" +
                "records=" + records +
        '}';
    }
}
