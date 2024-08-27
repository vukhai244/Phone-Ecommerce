package com.vn.rabbitmq_client.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vn.rabbitmq_client.constants.Constants;

@RestController
public class ProducerController {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("/message-pushing")
    public ResponseEntity<Object> sendMessage(
            @RequestParam(value = "message", defaultValue = "Testing push message") String messsage) {
        rabbitTemplate.convertAndSend(Constants.QUEUE_NAME_DEMO, messsage);
        return new ResponseEntity<>("Message pushed: " + messsage, HttpStatus.OK);
    }
}
