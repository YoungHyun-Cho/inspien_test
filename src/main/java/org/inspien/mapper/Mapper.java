package org.inspien.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.inspien.dto.order.Item;
import org.inspien.dto.order.Order;
import org.inspien.dto.order.SalesStatus;
import org.inspien.dto.record.Records;
import org.inspien.dto.response.Response;
import org.inspien.type.CharSet;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

public class Mapper {

    public static Records jsonStringToObject(String base64EncodedString) throws UnsupportedEncodingException, JsonProcessingException {
        String decodedJson = decode(base64EncodedString, CharSet.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(decodedJson, Records.class);
    }

    public static SalesStatus xmlStringToObject(String base64EncodedString) throws ParserConfigurationException, SAXException, IOException {
        String decodedXml = decode(base64EncodedString, CharSet.EUC_KR);
        Document document = parse(decodedXml);
        List<Order> orders = extractOrders(document);
        List<Item> items = extractItems(document);
        SalesStatus salesStatus = new SalesStatus(orders, items);
        return salesStatus;
    }

    private static String decode(String base64EncodedString, CharSet charSet) throws UnsupportedEncodingException {
        byte[] decodedBytes = Base64.getDecoder().decode(base64EncodedString);
        return new String(decodedBytes, charSet.getName());
    }

    private static Document parse(String decodedStr) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        ByteArrayInputStream input = new ByteArrayInputStream(decodedStr.getBytes("UTF-8"));
        return builder.parse(input);
    }

    private static List<Order> extractOrders(Document document) {
        NodeList nodeList = document.getElementsByTagName("HEADER");
        List<Order> orders = new ArrayList<>();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentNode = nodeList.item(i);
            NodeList childList = currentNode.getChildNodes();
            Order order = new Order();

            for (int j = 0; j < childList.getLength(); j++) {
                Node el = childList.item(j);
                String nodeName = el.getNodeName();
                if (nodeName.equals("#text")) continue;
                String content = el.getTextContent();

                switch (nodeName) {
                    case "ORDER_ID"      -> order.setUserId(content);
                    case "ORDER_NUM"     -> order.setNumber(content);
                    case "ORDER_PRICE"   -> order.setPrice(content);
                    case "ORDER_QTY"     -> order.setQuantity(content);
                    case "ORDER_DATE"    -> order.setDate(content);
                    case "ETA_DATE"      -> order.setEtaDate(content);
                    case "RECEIVER_NAME" -> order.setReceiverName(content);
                    case "RECEIVER_NO"   -> order.setReceiverPhone(content);
                    case "DESTINATION"   -> order.setDestination(content);
                    case "DESCIPTION"    -> order.setMessage(content); // XML 소스에 오타 존재
                }
            }
            orders.add(order);
        }
        return orders;
    }

    private static List<Item> extractItems(Document document) {
        NodeList nodeList = document.getElementsByTagName("DETAIL");
        List<Item> items = new ArrayList<>();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentNode = nodeList.item(i);
            NodeList childList = currentNode.getChildNodes();
            Item item = new Item();

            for (int j = 0; j < childList.getLength(); j++) {
                Node el = childList.item(j);
                String nodeName = el.getNodeName();
                if (nodeName.equals("#text")) continue;
                String content = el.getTextContent();

                switch (nodeName) {
                    case "ORDER_NUM"  -> item.setOrderNumber(content);
                    case "ITEM_SEQ"   -> item.setSequence(content);
                    case "ITEM_PRICE" -> item.setPrice(content);
                    case "ITEM_QTY"   -> item.setQuantity(content);
                    case "ITEM_NAME"  -> item.setName(content);
                    case "ITEM_COLOR" -> item.setColor(content.isEmpty() ? "NULL" : content);
                }
            }
            items.add(item);
        }
        return items;
    }

    public static Response strToResponse(String response) throws JsonProcessingException {
        return new ObjectMapper().readValue(response, new TypeReference<Response>() {});
    }

    public static HashMap<String, String> orderToHashMap(Order order) {
        HashMap<String, String> hashMap = new HashMap();
        hashMap.put("tableName", "ORDERS");
        hashMap.put("ORDER_NUM", order.getNumber());
        hashMap.put("ORDER_ID", order.getUserId());
        hashMap.put("ORDER_DATE", order.getDate());
        hashMap.put("ORDER_PRICE", order.getPrice());
        hashMap.put("ORDER_QTY", order.getQuantity());
        hashMap.put("RECEIVER_NAME", order.getReceiverName());
        hashMap.put("RECEIVER_NO", order.getReceiverPhone());
        hashMap.put("ETA_DATE", order.getEtaDate());
        hashMap.put("DESTINATION", order.getDestination());
        hashMap.put("DESCIPTION", order.getMessage());
        return hashMap;
    }

    public static HashMap<String, String> itemToHashMap(Item item) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("tableName", "ITEM");
        hashMap.put("ORDER_NUM", item.getOrderNumber());
        hashMap.put("ITEM_NAME", item.getName());
        hashMap.put("ITEM_QTY", item.getQuantity());
        hashMap.put("ITEM_PRICE", item.getPrice());
        hashMap.put("ITEM_SEQ", item.getSequence());
        hashMap.put("ITEM_COLOR", item.getColor());
        return hashMap;
    }
}
