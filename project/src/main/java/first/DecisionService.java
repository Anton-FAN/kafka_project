package first;

import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.kstream.ValueTransformerSupplier;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.processor.PunctuationType;
import org.apache.kafka.streams.processor.Punctuator;
import org.apache.kafka.streams.processor.StateStore;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import second.*;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;


import javax.print.Doc;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class DecisionService {
    static List<String> data = new ArrayList<>();

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException, ParseException {

        Properties properties = new Properties();
        properties.setProperty(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(StreamsConfig.APPLICATION_ID_CONFIG, "demo-kafka-streams");
        properties.setProperty(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.StringSerde.class.getName());
        properties.setProperty(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.StringSerde.class.getName());

        StreamsBuilder streamsBuilder = new StreamsBuilder();
        KStream<String, String> inputTopic = streamsBuilder.stream("test_topic_xml");

        KStream outputStream = inputTopic.mapValues((key, value) -> {
            try {
                return makeDecision(value);
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        });


        inputTopic.to("test_topic_xml");
        KafkaStreams kafkaStreams = new KafkaStreams(streamsBuilder.build(), properties);
        kafkaStreams.start();

    }

    static String makeDecision(String xml) throws ParserConfigurationException, ParseException {
        Request request = xmlToClass(xml);
        Integer age = new Date().getYear() - request.getBorrower().getDateOfBirth().getYear();
        if (age > 18 && request.getBorrower().getIncomePerMonth() / 2 > request.getBorrower().getExpensesPerMonth() / 2)
            return "approved";
        if (age < 18 || request.getBorrower().getIncomePerMonth() < request.getBorrower().getExpensesPerMonth())
            return "refused";
        if (age > 18 && request.getBorrower().getIncomePerMonth() >= request.getBorrower().getExpensesPerMonth() && request.getBorrower().getExpensesPerMonth() > request.getBorrower().getIncomePerMonth() / 2)
            return "offer" + request.getBorrower().getIncomePerMonth() / 2;
        return "refused";
    }


    static Request xmlToClass(String xml) throws ParserConfigurationException, ParseException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = convertStringToXMLDocument(xml);
        Element element = document.getDocumentElement();
        NodeList nodeList = element.getChildNodes();
        process(nodeList);
        Request request = new Request(new SimpleDateFormat("dd/MM/yyyy").parse(data.get(0)), Integer.valueOf(data.get(1)), Integer.valueOf(data.get(2)),
                new Borrower(data.get(3), data.get(4), data.get(5), new SimpleDateFormat("dd/MM/yyyy").parse(data.get(6)), data.get(7), Integer.valueOf(data.get(8)), Integer.valueOf(data.get(9)),
                        new Employer(Integer.valueOf(data.get(10)), data.get(11), Integer.valueOf(data.get(12)))));
        return request;
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
//                    System.out.println(value);
                }
                if (nodeList.item(i).hasChildNodes() && nodeList.item(i) instanceof Element && nodeList.item(i).getChildNodes().getLength() > 1) {
                    NodeList nodeList1 = nodeList.item(i).getChildNodes();
                    process(nodeList1);
                }
            }
        }
    }

    private static Document convertStringToXMLDocument(String xmlString) {
        //Parser that produces DOM object trees from XML content
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        //API to obtain DOM Document instance
        DocumentBuilder builder = null;
        try {
            //Create DocumentBuilder with default configuration
            builder = factory.newDocumentBuilder();

            //Parse the content to Document object
            Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
