package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

public class LogService {

    public static void main(String[] args) {
        var consumer = new KafkaConsumer<>(properties());
        /*List<String> topics = new ArrayList<>();
        topics.add("ECOMMERCE_SEND_EMAIL");
        topics.add("ECOMMERCE_NEW_ORDER");
        consumer.subscribe(topics);*/

        consumer.subscribe(Pattern.compile("ECOMMERCE.*"));
        while(true) {
            var records = consumer.poll(Duration.ofMillis(5000));
            if (!records.isEmpty()) {
                for (var record : records) {
                    System.out.println("----------------");
                    System.out.println("LOG FROM ::: " + record.topic());
                    System.out.println("RECORD INFO ::: key: " + record.key() + " / value: " + record.value() + " / partition: " + record.partition() + " / offset: " + record.offset());
                }

            }
        }
    }

    private static Properties properties() {
        var properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, LogService.class.getSimpleName());
        return properties;
    }
}
