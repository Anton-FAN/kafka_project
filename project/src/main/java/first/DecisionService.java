package first;

import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.kstream.ValueTransformerSupplier;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.processor.PunctuationType;
import org.apache.kafka.streams.processor.Punctuator;
import org.apache.kafka.streams.processor.StateStore;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
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
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
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

    public static void main(String[] args) {

        Properties properties = new Properties();
        properties.setProperty(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(StreamsConfig.APPLICATION_ID_CONFIG, "demo-kafka-streams");
        properties.setProperty(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.StringSerde.class.getName());
        properties.setProperty(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.StringSerde.class.getName());

        StreamsBuilder streamsBuilder = new StreamsBuilder();
        KStream<String, String> inputTopic = streamsBuilder.stream("test_topic_xml");

        KStream outputStream = inputTopic.mapValues((key, value) -> {
            try {
                value = makeDecision(value);
            } catch (JAXBException e) {
                e.printStackTrace();
            }
            return value;
        });

        outputStream.to("test_topic_a");
        KafkaStreams kafkaStreams = new KafkaStreams(streamsBuilder.build(), properties);
        kafkaStreams.start();
    }

    static String makeDecision(String xml) throws JAXBException {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        JAXBContext context = JAXBContext.newInstance(Request.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Request request = (Request) unmarshaller.unmarshal(new StringReader(xml));

        String decision = "refused";
        Integer age = new Date().getYear() - request.getBorrower().getDateOfBirth().getYear();
        if (age > 18 && request.getBorrower().getIncomePerMonth() / 2 > request.getBorrower().getExpensesPerMonth() / 2)
            decision = "approved";
        if (age < 18 || request.getBorrower().getIncomePerMonth() < request.getBorrower().getExpensesPerMonth())
            decision = "refused";
        if (age > 18 && request.getBorrower().getIncomePerMonth() >= request.getBorrower().getExpensesPerMonth() && request.getBorrower().getExpensesPerMonth() > request.getBorrower().getIncomePerMonth() / 2)
            decision = "offer: " + request.getBorrower().getIncomePerMonth() / 2;
        request.setDecision(decision);
        try (SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
             Session session = sessionFactory.openSession()
        ) {
            session.beginTransaction();
            session.save(request.getBorrower().getEmployer());
            session.save(request.getBorrower());
            session.save(request);

            session.getTransaction().commit();
        }
        return decision;
    }


}
