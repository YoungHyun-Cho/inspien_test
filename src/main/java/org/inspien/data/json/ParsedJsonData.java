package org.inspien.data.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.inspien.util.Mapper;

import java.io.UnsupportedEncodingException;
import java.util.List;

/*
* # ParsedJsonData.class
*   - Mapper에 의해 JSON_DATA가 ParsedJsonData의 인스턴스로 매핑된다.
* */

@Getter
@ToString
public class ParsedJsonData {
    @JsonProperty("record")
    List<Record> records;

    // records의 각 요소로부터 serialize()를 호출하여, 과제 요구 사항(데이터 핸들링)을 수행한다.
    public String handle() throws NoSuchFieldException, IllegalAccessException {
        StringBuilder stringBuilder = new StringBuilder();
        for (Record record : records) {
            stringBuilder.append(record.serialize());
        }
        return stringBuilder.toString();
    }
}
