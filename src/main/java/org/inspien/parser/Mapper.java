package org.inspien.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.inspien.dto.order.Item;
import org.inspien.dto.order.Order;
import org.inspien.dto.order.SalesStatus;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class Mapper {
    private static final String PATH = "/Users/0hyuncho/Desktop/inspien_code_temp_test";

    // 추후 Java Object를 리턴하도록 수정 필요
    public static void jsonStringToObject(String base64EncodedString) throws UnsupportedEncodingException, JsonProcessingException {
        String decodedStr = decode(base64EncodedString, CharSet.UTF_8);

        // 내용 확인용
        System.out.println(decodedStr);

//        Response responseDto = objectMapper.readValue(decodedStr, new TypeReference<Response>() {});
    }

    // 추후 Java Object를 리턴하도록 수정 필요
    public static SalesStatus xmlStringToObject(String base64EncodedString) throws ParserConfigurationException, SAXException, IOException {
        String decodedStr = decode(base64EncodedString, CharSet.EUC_KR);
        Document document = parse(decodedStr);
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
                    case "ORDER_NUM"     -> order.setNumber(Integer.parseInt(content));
                    case "ORDER_PRICE"   -> order.setPrice(Integer.parseInt(content));
                    case "ORDER_QTY"     -> order.setQuantity(Integer.parseInt(content));
                    case "ORDER_DATE"    -> order.setDate(LocalDate.parse(content));
                    case "ETA_DATE"      -> order.setEtaDate(LocalDate.parse(content));
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
                    case "ORDER_NUM"  -> item.setOrderNumber(Integer.parseInt(content));
                    case "ITEM_SEQ"   -> item.setSequence(Integer.parseInt(content));
                    case "ITEM_PRICE" -> item.setPrice(Integer.parseInt(content));
                    case "ITEM_QTY"   -> item.setQuantity(Integer.parseInt(content));
                    case "ITEM_NAME"  -> item.setName(content);
                    case "ITEM_COLOR" -> item.setColor(content);
                }
            }
            items.add(item);
        }
        return items;
    }

    public static Response strToResponse(String response) throws JsonProcessingException {
        return new ObjectMapper().readValue(response, new TypeReference<Response>() {});
    }
}
