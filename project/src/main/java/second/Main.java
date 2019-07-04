package second;

import org.apache.kafka.common.protocol.types.Field;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Main {
    static List<String> data = new ArrayList<>();
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, ParseException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File("project/src/main/resources/doc.xml"));
        Element element = document.getDocumentElement();
        NodeList nodeList = element.getChildNodes();
        process(nodeList);
        Request request = new Request(new SimpleDateFormat("dd/MM/yyyy").parse(data.get(0)), Integer.valueOf(data.get(1)), Integer.valueOf(data.get(2)),
                new Borrower(data.get(3), data.get(4), data.get(5), new SimpleDateFormat("dd/MM/yyyy").parse(data.get(6)), data.get(7), Integer.valueOf(data.get(8)), Integer.valueOf(data.get(9)),
                        new Employer(Integer.valueOf(data.get(10)), data.get(11), Integer.valueOf(data.get(12)))));
    }

    static void process(NodeList nodeList) {

        String value;
        int k = nodeList.getLength();
        for (int i = 0; i < k; i++) {
            if (nodeList.item(i) instanceof Element) {
                value = "";
                if (!nodeList.item(i).getTextContent().trim().isEmpty() && !((Text) nodeList.item(i).getFirstChild()).getData().trim().isEmpty() && !((Text) nodeList.item(i).getFirstChild()).getData().trim().equals("\n")) {
                    Text text = (Text) nodeList.item(i).getFirstChild();
                    value += text.getData().trim();
                    data.add(value);
                    System.out.println(value);
                }
                if (nodeList.item(i).hasChildNodes() && nodeList.item(i) instanceof Element && nodeList.item(i).getChildNodes().getLength() > 1) {
                    NodeList nodeList1 = nodeList.item(i).getChildNodes();
                    process(nodeList1);
                }
            }
        }
    }
}




