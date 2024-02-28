package org.inspien.data.xml;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

/*
* # ParsedXmlData.class
*   - Mapper에 의해 XML_DATA가 ParsedXmlData의 인스턴스로 매핑된다.
* */

@Getter
@AllArgsConstructor
public class ParsedXmlData {
    private List<Order> orders;
    private List<Item> items;
}
