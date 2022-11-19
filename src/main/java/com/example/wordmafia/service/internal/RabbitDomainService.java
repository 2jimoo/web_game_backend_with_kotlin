package com.example.wordmafia.service.internal;

import com.example.wordmafia.model.Event;
import com.example.wordmafia.model.EventType;
import com.example.wordmafia.util.EnableEventHandling;
import com.example.wordmafia.util.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@EnableEventHandling
public class RabbitDomainService {
    @EventHandler(eventTypes = EventType.MESSAGE_HI_RECEIVED)
    public void hi(Event event) {
        log.info(event.type());
        log.info("hi,{}. I'm {}", event.data(), event.requestedBy());
    }


    @EventHandler(eventTypes = EventType.MESSAGE_HELLO_RECEIVED)
    public void hello(Event event) {
        log.info(event.type());
        log.info("hello, {}. My name is {}", event.data(), event.requestedBy());
    }
}
