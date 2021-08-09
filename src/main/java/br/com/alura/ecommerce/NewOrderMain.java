package br.com.alura.ecommerce;

import br.com.alura.ecommerce.dispatcher.KafkaDispatcher;
import br.com.alura.ecommerce.domain.Email;
import br.com.alura.ecommerce.domain.Order;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

//PRODUCER
public class NewOrderMain{

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        try(var dispatcherOrder = new KafkaDispatcher<Order>(); var dispatcherEmail = new KafkaDispatcher<Email>()){
            for(int i=0; i<10;i++) {
                var userId = UUID.randomUUID().toString();
                var orderId = UUID.randomUUID().toString();
                var amount = BigDecimal.valueOf(Math.random()*1000+1);
                var order = new Order(userId, orderId, amount);
                dispatcherOrder.send("ECOMMERCE_NEW_ORDER", userId, order);

                var email = new Email("teste@teste.com.br", "Welcome! We are processing your order.") ;
                dispatcherEmail.send("ECOMMERCE_SEND_EMAIL", userId, email);
            }
        }
    }

}
