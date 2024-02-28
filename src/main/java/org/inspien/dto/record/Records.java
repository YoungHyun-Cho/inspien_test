package org.inspien.dto.record;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Records {
    @JsonProperty("record")
    List<Record> records;

    @Override
    public String toString() {
        return "Records{" +
                "records=" + records +
                '}';
    }
}
