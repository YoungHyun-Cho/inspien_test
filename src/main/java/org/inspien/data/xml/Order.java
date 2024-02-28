package org.inspien.data.xml;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

/*
* # Order.class
*   - XML_DATA의 각 HEADER 데이터를 표현한다.
* */

@Getter
@Setter
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

    // SQL 생성을 위한 해시맵 변환 코드
    public HashMap<String, String> toHashMap() {
        HashMap<String, String> hashMap = new HashMap();
        hashMap.put("tableName", "ORDERS");
        hashMap.put("ORDER_NUM", number);
        hashMap.put("ORDER_ID", userId);
        hashMap.put("ORDER_DATE", date);
        hashMap.put("ORDER_PRICE", price);
        hashMap.put("ORDER_QTY", quantity);
        hashMap.put("RECEIVER_NAME", receiverName);
        hashMap.put("RECEIVER_NO", receiverPhone);
        hashMap.put("ETA_DATE", etaDate);
        hashMap.put("DESTINATION", destination);
        hashMap.put("DESCIPTION", message);
        return hashMap;
    }

    @Override
    public String toString() {
        return "Order {" +
                "userId='" + userId + '\'' +
                ", number=" + number +
                ", price=" + price +
                ", quantity=" + quantity +
                ", date=" + date +
                ", etaDate=" + etaDate +
                ", receiverName='" + receiverName + '\'' +
                ", receiverPhone='" + receiverPhone + '\'' +
                ", destination='" + destination + '\'' +
                ", message='" + message + '\'' +
        '}';
    }
}
