package com.kwetter.frits.accountservice.receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class EventReceiver {

    private Logger log = LoggerFactory.getLogger(EventReceiver.class);

    @RabbitListener(queues = "${kwetter.rabbitmq.queue}")
    public void receive(Object event) {
        System.out.println("received the event!");
        log.info("Received event in service account: {}", event);
    }
}

