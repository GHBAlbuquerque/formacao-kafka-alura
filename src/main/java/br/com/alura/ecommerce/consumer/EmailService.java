package br.com.alura.ecommerce.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public class EmailService {

    public static void main(String[] args) {
        var emailService = new EmailService();
        try(var service = new KafkaService(emailService.getClass().getSimpleName(), "ECOMMERCE_SEND_EMAIL", emailService::parse)){
            service.run();
        }
    }

    private void parse(ConsumerRecord<String, String> record) {
        System.out.println("----------------");
        System.out.println("record key:" + record.key() + " / value: " + record.value() + " / partition: " + record.partition() + " / offset: " + record.offset());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Email processed!");
    }
}
