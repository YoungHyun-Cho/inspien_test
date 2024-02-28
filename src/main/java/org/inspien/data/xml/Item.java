package org.inspien.data.xml;

/*
* # Item.class
*   - XML_DATA의 각 DETAIL 데이터를 표현함.
* */

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class Item {
    private String orderNumber; // ORDER_NUM
    private String sequence;    // ITEM_SEQ
    private String price;       // ITEM_PRICE
    private String quantity;    // ITEM_QTY
    private String name;        // ITEM_NAME
    private String color;       // ITEM_COLOR

    // SQL 생성을 위한 해시맵 변환 코드
    public HashMap<String, String> toHashMap() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("tableName", "ITEM");
        hashMap.put("ORDER_NUM", orderNumber);
        hashMap.put("ITEM_NAME", name);
        hashMap.put("ITEM_QTY", quantity);
        hashMap.put("ITEM_PRICE", price);
        hashMap.put("ITEM_SEQ", sequence);
        hashMap.put("ITEM_COLOR", color);
        return hashMap;
    }

    @Override
    public String toString() {
        return "Item{" +
                "orderNumber=" + orderNumber +
                ", sequence=" + sequence +
                ", price=" + price +
                ", quantity=" + quantity +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
        '}';
    }
}
