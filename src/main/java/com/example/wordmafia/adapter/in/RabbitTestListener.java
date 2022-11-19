package com.example.wordmafia.adapter.in;

import com.example.wordmafia.model.MessageHelloReceivedEvent;
import com.example.wordmafia.model.MessageHiReceivedEvent;
import com.example.wordmafia.util.EventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;

import static com.example.wordmafia.model.Const.MessageType.HELLO_MESSAGE;
import static com.example.wordmafia.model.Const.MessageType.HI_MESSAGE;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitTestListener {
    private final EventPublisher eventPublisher;

    @org.springframework.amqp.rabbit.annotation.RabbitListener(queues = "sample.queue")
    public void listen(Message message) {
        if (message.getMessageProperties().getType().equals(HI_MESSAGE)) {
            eventPublisher.publish(new MessageHiReceivedEvent(new String(message.getBody(), StandardCharsets.UTF_8), message.getMessageProperties().getHeader("from"), Instant.now()));
        } else if (message.getMessageProperties().getType().equals(HELLO_MESSAGE)) {
            eventPublisher.publish(new MessageHelloReceivedEvent(new String(message.getBody(), StandardCharsets.UTF_8), message.getMessageProperties().getHeader("from"), Instant.now()));
        } else {
            log.info("UNKNOWN MESSAGE.");
        }
    }
}
