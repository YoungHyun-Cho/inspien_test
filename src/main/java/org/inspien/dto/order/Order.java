package org.inspien.dto.order;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/*
* - Order.class
*   - XML_DATA의 각 HEADER 데이터를 표현함.
*   - HEADER :
*       <HEADER>
*         <ORDER_NUM>1000</ORDER_NUM>
*         <ORDER_ID>kwang001</ORDER_ID>
*         <ORDER_DATE>2021-08-26</ORDER_DATE>
*         <ORDER_PRICE>62000</ORDER_PRICE>
*         <ORDER_QTY>3</ORDER_QTY>
*         <RECEIVER_NAME>김광수</RECEIVER_NAME>
*         <RECEIVER_NO>010-5312-2345</RECEIVER_NO>
*         <ETA_DATE>2021-09-01</ETA_DATE>
*         <DESTINATION>서울시 서초구 방배동 801</DESTINATION>
*         <DESCIPTION>현관앞에 놓아주세요</DESCIPTION>
*       </HEADER>
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
    private String message;       // DESCRIPTION

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
