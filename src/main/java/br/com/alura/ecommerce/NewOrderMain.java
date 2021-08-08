package br.com.alura.ecommerce;

import br.com.alura.ecommerce.dispatcher.KafkaDispatcher;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

//PRODUCER
public class NewOrderMain{

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        try(var dispatcher = new KafkaDispatcher()){
            for(int i=0; i<10;i++) {
                var key = UUID.randomUUID().toString();
                var value = "orderNumber: 378974, value: 2892";
                dispatcher.send("ECOMMERCE_NEW_ORDER", key, value);

                var email = "Welcome! We are processing your order.";
                dispatcher.send("ECOMMERCE_SEND_EMAIL", key, email);
            }
        }

    }

}
