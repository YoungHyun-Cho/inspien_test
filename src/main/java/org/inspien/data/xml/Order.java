package org.inspien.data.xml;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;

/*
* # Order.class
*   - XML_DATA의 각 HEADER 데이터를 표현한다.
* */

@Getter
@Setter
@ToString
public class Order {
    private String userId;        // ORDER_ID
    private String number;        // ORDER_NUM
    private String price;         // ORDER_PRICE
    private String quantity;      // ORDER_QTY
    private String date;          // ORDER_DATE
    private String etaDate;       // ETA_DATE
    private String receiverName;  // RECEIVER_NAME
    private String receiverPhone; // RECEIVER_NO
    private String destination;   // DESTINATION
    private String message;       // DESCIPTION
}
