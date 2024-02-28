package org.inspien.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.inspien.data.xml.Item;
import org.inspien.data.xml.Order;
import org.inspien.data.xml.ParsedXmlData;
import org.inspien.data.json.ParsedJsonData;
import org.inspien.data.api.Response;
import org.inspien.enums.CharSet;
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
import java.util.List;

/*
* # Mapper.class
* - API 요청에 대한 응답 결과를 Java Object로 매핑
*   - Serialized response body       -> Deserialize          -> Java Object (Response)
*   - Serialized & Encoded XML_DATA  -> Deserialize & Decode -> Java Object (ParsedXmlData)
*   - Serialized & Encoded JSON_DATA -> Deserialize & Decode -> Java Object (ParsedJsonData)
* */

public class Mapper {

    // API 요청에 대한 직렬화된 응답 바디를 역직렬화하여 Response의 인스턴스로 매핑한다.
    public static Response mapToResponse(String response) throws JsonProcessingException {
        return new ObjectMapper().readValue(response, Response.class);
    }

    // 직렬화, 인코딩된 XML_DATA를 역직렬화, 디코딩하여 ParsedXmlData의 인스턴스로 매핑한다.
    public static ParsedXmlData xmlDataToObject(String base64EncodedString) throws ParserConfigurationException, SAXException, IOException {
        String decodedXml = decode(base64EncodedString, CharSet.EUC_KR);
        Document document = parse(decodedXml);
        List<Order> orders = extractOrders(document);
        List<Item> items = extractItems(document);
        ParsedXmlData parsedXmlData = new ParsedXmlData(orders, items);
        return parsedXmlData;
    }

    // 직렬화, 인코딩된 JSON_DATA를 역직렬화, 디코딩하여 ParsedJsonData의 인스턴스로 매핑한다.
    public static ParsedJsonData jsonDataToObject(String base64EncodedString) throws UnsupportedEncodingException, JsonProcessingException {
        String decodedJson = decode(base64EncodedString, CharSet.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(decodedJson, ParsedJsonData.class);
    }

    // XML_DATA와 JSON_DATA을 디코딩한다.
    private static String decode(String base64EncodedString, CharSet charSet) throws UnsupportedEncodingException {
        byte[] decodedBytes = Base64.getDecoder().decode(base64EncodedString);
        return new String(decodedBytes, charSet.getName());
    }

    // 직렬화된 XML_DATA를 XML 문서로 파싱한다.
    private static Document parse(String decodedStr) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        ByteArrayInputStream input = new ByteArrayInputStream(decodedStr.getBytes(CharSet.UTF_8.getName()));
        return builder.parse(input);
    }

    // 파싱된 XML 문서의 HEADER 요소들을 추출하여 List<Order>로 변환한다.
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
                String content = el.getTextContent();
                setOrderProperties(order, nodeName, content);
            }
            orders.add(order);
        }
        return orders;
    }

    // 파싱된 XML 문서의 DETAIL 요소들을 추출하여 List<Item>으로 변환한다.
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
                String content = el.getTextContent();
                setItemProperties(item, nodeName, content);
            }
            items.add(item);
        }
        return items;
    }

    // nodeName에 해당하는 Order 인스턴스의 필드에 content를 할당해준다.
    private static void setOrderProperties(Order order, String nodeName, String content) {
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
            case "DESCIPTION"    -> order.setMessage(content);
        }
    }

    // nodeName에 해당하는 Item 인스턴스의 필드에 content를 할당해준다.
    private static void setItemProperties(Item item, String nodeName, String content) {
        switch (nodeName) {
            case "ORDER_NUM"  -> item.setOrderNumber(content);
            case "ITEM_SEQ"   -> item.setSequence(content);
            case "ITEM_PRICE" -> item.setPrice(content);
            case "ITEM_QTY"   -> item.setQuantity(content);
            case "ITEM_NAME"  -> item.setName(content);
            case "ITEM_COLOR" -> item.setColor(content.isEmpty() ? "NULL" : content);
        }
    }
}
