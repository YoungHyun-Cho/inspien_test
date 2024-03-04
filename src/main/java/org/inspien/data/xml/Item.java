package org.inspien.data.xml;

/*
* # Item.class
*   - XML_DATA의 각 DETAIL 데이터를 표현함.
* */

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;

@Getter
@Setter
@ToString
public class Item {
    private String orderNumber; // ORDER_NUM
    private String sequence;    // ITEM_SEQ
    private String price;       // ITEM_PRICE
    private String quantity;    // ITEM_QTY
    private String name;        // ITEM_NAME
    private String color;       // ITEM_COLOR
}
