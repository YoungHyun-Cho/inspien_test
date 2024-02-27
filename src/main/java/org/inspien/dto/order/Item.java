package org.inspien.dto.order;

/*
* - Item.class
*   - XML_DATA의 각 DETAIL 데이터를 표현함.
*   - DETAIL :
*       <DETAIL>
*         <ORDER_NUM>1000</ORDER_NUM>
*         <ITEM_SEQ>1</ITEM_SEQ>
*         <ITEM_NAME>아이폰케이스</ITEM_NAME>
*         <ITEM_QTY>1</ITEM_QTY>
*         <ITEM_COLOR>흰색</ITEM_COLOR>
*         <ITEM_PRICE>30000</ITEM_PRICE>
*       </DETAIL>
* */

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Item {
    private String orderNumber; // ORDER_NUM
    private String sequence;    // ITEM_SEQ
    private String price;       // ITEM_PRICE
    private String quantity;    // ITEM_QTY
    private String name;        // ITEM_NAME
    private String color;       // ITEM_COLOR

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
