package com.example.utils;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class XMLUtils {

    /**
     * 解析XML文件并返回Document对象
     *
     * @param filePath XML文件路径
     * @return Document对象
     */
    public static Document parseXML(String filePath) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(new File(filePath));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将Document对象保存为XML文件
     *
     * @param document Document对象
     * @param filePath 保存路径
     */
    public static void saveXML(Document document, String filePath) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建新的XML文档
     *
     * @return 新的Document对象
     */
    public static Document createNewXML() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.newDocument();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 添加根节点
     *
     * @param document Document对象
     * @param rootName 根节点名称
     * @return 根节点
     */
    public static Element addRootElement(Document document, String rootName) {
        Element rootElement = document.createElement(rootName);
        document.appendChild(rootElement);
        return rootElement;
    }

    /**
     * 添加子节点
     *
     * @param parent    父节点
     * @param childName 子节点名称
     * @param text      子节点文本内容
     * @return 子节点
     */
    public static Element addChildElement(Element parent, String childName, String text) {
        Document document = parent.getOwnerDocument();
        Element childElement = document.createElement(childName);
        if (text != null) {
            childElement.appendChild(document.createTextNode(text));
        }
        parent.appendChild(childElement);
        return childElement;
    }

    /**
     * 获取节点的文本内容
     *
     * @param element 节点
     * @return 文本内容
     */
    public static String getElementText(Element element) {
        return element.getTextContent();
    }

    /**
     * 设置节点的文本内容
     *
     * @param element 节点
     * @param text    文本内容
     */
    public static void setElementText(Element element, String text) {
        element.setTextContent(text);
    }

    /**
     * 获取节点的属性值
     *
     * @param element     节点
     * @param attributeName 属性名
     * @return 属性值
     */
    public static String getAttribute(Element element, String attributeName) {
        return element.getAttribute(attributeName);
    }

    /**
     * 设置节点的属性值
     *
     * @param element     节点
     * @param attributeName 属性名
     * @param attributeValue 属性值
     */
    public static void setAttribute(Element element, String attributeName, String attributeValue) {
        element.setAttribute(attributeName, attributeValue);
    }

    /**
     * 打印XML文档内容
     *
     * @param document Document对象
     */
    public static void printXML(Document document) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(System.out);
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将 XML 字符串转换为 JSON 字符串
     *
     * @param xml XML 字符串
     * @return JSON 字符串
     */
    public static String xmlToJson(String xml) {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            JsonNode node = xmlMapper.readTree(xml.getBytes());
            ObjectMapper jsonMapper = new ObjectMapper();
            return jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将 JSON 字符串转换为 XML 字符串
     *
     * @param json JSON 字符串
     * @return XML 字符串
     */
    public static String jsonToXml(String json) {
        try {
            ObjectMapper jsonMapper = new ObjectMapper();
            JsonNode node = jsonMapper.readTree(json);
            XmlMapper xmlMapper = new XmlMapper();
            return xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void main(String[] args) {
        // 测试创建、保存和解析 XML 文件
        System.out.println("=== 测试创建、保存和解析 XML 文件 ===");
        Document document = XMLUtils.createNewXML();
        if (document != null) {
            Element root = XMLUtils.addRootElement(document, "root");
            XMLUtils.addChildElement(root, "child", "Hello, XML!");

            // 保存 XML 文件
            String filePath = "test_output.xml";
            XMLUtils.saveXML(document, filePath);
            System.out.println("XML 文件已保存到: " + filePath);

            // 解析 XML 文件
            Document parsedDocument = XMLUtils.parseXML(filePath);
            if (parsedDocument != null) {
                Element parsedRoot = parsedDocument.getDocumentElement();
                System.out.println("解析后的根节点: " + parsedRoot.getNodeName());
                Element parsedChild = (Element) parsedRoot.getElementsByTagName("child").item(0);
                System.out.println("子节点内容: " + XMLUtils.getElementText(parsedChild));
            }

            // 清理测试文件
            new File(filePath).delete();
            System.out.println("测试文件已删除。");
        }

        // 测试修改 XML 内容
        System.out.println("\n=== 测试修改 XML 内容 ===");
        Document modifyDocument = XMLUtils.createNewXML();
        if (modifyDocument != null) {
            Element modifyRoot = XMLUtils.addRootElement(modifyDocument, "root");
            XMLUtils.addChildElement(modifyRoot, "child", "Original Text");

            // 修改子节点内容
            Element child = (Element) modifyRoot.getElementsByTagName("child").item(0);
            XMLUtils.setElementText(child, "Updated Text");
            System.out.println("修改后的子节点内容: " + XMLUtils.getElementText(child));

            // 设置属性
            XMLUtils.setAttribute(child, "id", "1");
            System.out.println("子节点属性值: " + XMLUtils.getAttribute(child, "id"));
        }

        // 测试打印 XML 内容
        System.out.println("\n=== 测试打印 XML 内容 ===");
        Document printDocument = XMLUtils.createNewXML();
        if (printDocument != null) {
            Element printRoot = XMLUtils.addRootElement(printDocument, "root");
            XMLUtils.addChildElement(printRoot, "child", "Test Content");
            System.out.println("打印 XML 内容:");
            XMLUtils.printXML(printDocument);
        }

        // 测试 XML 和 JSON 相互转换
        System.out.println("=== 测试 XML 和 JSON 相互转换 ===");

        // 示例 XML 字符串
        String xml = "<root><name>John</name><age>30</age></root>";
        System.out.println("原始 XML:");
        System.out.println(xml);

        // 将 XML 转换为 JSON
        String json = xmlToJson(xml);
        System.out.println("\n转换后的 JSON:");
        System.out.println(json);

        // 将 JSON 转换回 XML
        String convertedXml = jsonToXml(json);
        System.out.println("\n转换后的 XML:");
        System.out.println(convertedXml);



    }
}