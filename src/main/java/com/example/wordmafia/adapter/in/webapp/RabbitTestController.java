package com.example.wordmafia.adapter.in.webapp;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.wordmafia.model.Const.MessageType.HI_MESSAGE;
import static com.example.wordmafia.model.Const.RabbiMq.EXCHANGE_NAME;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rabbit/test")
public class RabbitTestController {

    private final RabbitTemplate rabbitTemplate;

    @GetMapping("/hi")
    public String hi() {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setType(HI_MESSAGE);
        messageProperties.setHeader("from", "controller");
        Message message = new Message("spring".getBytes(), messageProperties);
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, "sample.test.#", message);
        return "message sending!";
    }
}
