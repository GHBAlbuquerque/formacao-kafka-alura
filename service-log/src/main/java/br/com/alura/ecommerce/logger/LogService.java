package br.com.alura.ecommerce.logger;

import br.com.alura.ecommerce.consumer.KafkaService;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Map;
import java.util.regex.Pattern;

public class LogService {

    //o log usa uma regEx para o topico, por usa um Pattern.compile
    //requer outro construtor no kafka service
    public static void main(String[] args) {
        var logService = new LogService();
        var service = new KafkaService(logService.getClass().getSimpleName(),
                Pattern.compile("ECOMMERCE.*"),
                logService::parse,
                String.class,
                Map.of(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName())); //posso passar quantas props quiser via map, com chave como nome prop e valor com o que será sobrescrito
        service.run();
    }

    private void parse(ConsumerRecord<String, String> record) {
                System.out.println("----------------");
                System.out.println("LOG FROM ::: " + record.topic());
                System.out.println("RECORD INFO ::: key: " + record.key() + " / value: " + record.value() + " / partition: " + record.partition() + " / offset: " + record.offset());
    }
}
