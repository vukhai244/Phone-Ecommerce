package com.vn.rabbitmq_client.consumer;

import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vn.rabbitmq_client.constants.Constants;
import com.vn.rabbitmq_client.feign.IAccountFeign;
import com.vn.rabbitmq_client.service.EmailService;

@Component
public class Consumer {

    @Autowired
    private EmailService emailService;

    @Autowired
    private IAccountFeign accountFeign;

    @RabbitHandler
    @RabbitListener(queues = Constants.QUEUE_NAME_DEMO)
    public void receiveMessage(String message) {
        System.out.println("Message received from rabbit Server: " + message);

        // Lấy danh sách email từ Account-service thông qua FeignClient
        List<String> userEmails = accountFeign.getAllEmails();

        // Gửi email thông báo khuyến mãi tới tất cả người dùng
        String subject = "Promotion Notification";
        String text = "You have a new promotion: " + message;

        for (String userEmail : userEmails) {
            emailService.sendSimpleMessage(userEmail, subject, text);
        }
    }

}
