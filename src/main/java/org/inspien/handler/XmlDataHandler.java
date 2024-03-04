package org.inspien.handler;

import org.inspien.data.xml.Item;
import org.inspien.data.xml.Order;
import org.inspien.data.xml.ParsedXmlData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class XmlDataHandler {
    public static ArrayList<HashMap<String, String>> join(List<Order> orders, List<Item> items) {
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
