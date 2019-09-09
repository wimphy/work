package com.example.demo;

import org.springframework.amqp.core.*;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class DemoApplication {
    static final String topicExchangeName = "spring-boot-exchange";
    static final String queueName = "spring-boot";
    private static ConfigurableApplicationContext context;

    @RequestMapping("/home")
    String home() {
        return "Hello World!";
    }

    //docker-compose up in docker-compose.yml dir
    @RequestMapping("/add")
    String add() {
        String msg = "Hello World!";
        MessageSender sender = context.getBean(MessageSender.class);
        sender.send(msg);
        return "Message [" + msg + "] has been sent.";
    }

    @RequestMapping("/poll")
    String poll() {
        MessageReceiver receiver = context.getBean(MessageReceiver.class);
        Message message = receiver.poll();
        return message.toString();
    }

    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("foo.bar.#");
    }

//    @Bean
//    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
//                                             MessageListenerAdapter listenerAdapter) {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        container.setQueueNames(queueName);
//        container.setMessageListener(listenerAdapter);
//        return container;
//    }

//    @Bean
//    MessageListenerAdapter listenerAdapter(MessageReceiver receiver) {
//        return new MessageListenerAdapter(receiver, "receiveMessage");
//    }

    public static void main(String[] args) {
//        context.scan("com.example.demo");
//        context.refresh();
        context = SpringApplication.run(DemoApplication.class, args);

        //context.close();
    }

}
