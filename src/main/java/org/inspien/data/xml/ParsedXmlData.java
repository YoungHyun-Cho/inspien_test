package org.inspien.data.xml;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
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

    // orders와 items를 NL 방식으로 조인하여, 과제 요구 사항(데이터 핸들링)을 수행한다.
    public ArrayList<HashMap<String, String>> handle() {
        ArrayList<HashMap<String, String>> joined = new ArrayList<>();

        for (Order order : orders) {
            for (Item item : items) {
                if (order.getNumber().equals(item.getOrderNumber())) {
                    HashMap<String, String> data = new HashMap<>();
                    data.put("ORDER_NUM", order.getNumber());
                    data.put("ORDER_ID", order.getUserId());
                    data.put("ORDER_DATE", order.getDate());
                    data.put("ORDER_PRICE", order.getPrice());
                    data.put("ORDER_QTY", order.getQuantity());
                    data.put("RECEIVER_NAME", order.getReceiverName());
                    data.put("RECEIVER_NO", order.getReceiverPhone());
                    data.put("ETA_DATE", order.getEtaDate());
                    data.put("DESTINATION", order.getDestination());
                    data.put("DESCIPTION", order.getMessage());
                    data.put("ITEM_NAME", item.getName());
                    data.put("ITEM_QTY", item.getQuantity());
                    data.put("ITEM_PRICE", item.getPrice());
                    data.put("ITEM_SEQ", item.getSequence());
                    data.put("ITEM_COLOR", item.getColor());
                    joined.add(data);
                }
            }
        }

        return joined;
    }
}
