package br.com.alura.ecommerce.consumer;

import br.com.alura.ecommerce.domain.Order;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.Map;

public class FraudDetectorService {

    public static void main(String[] args) {
        var fraudService = new FraudDetectorService();
        try(var service = new KafkaService<Order>(fraudService.getClass().getSimpleName(),
                "ECOMMERCE_NEW_ORDER", fraudService::parse, Order.class, Map.of())){
            service.run();
        }
    }

    private void parse(ConsumerRecord<String, Order> record) {
        System.out.println("----------------");
        System.out.println("record:" + record.key() + " / value: " + record.value() + " / partition: " + record.partition() + " / offset: " + record.offset());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Order processed!");
    }
}
