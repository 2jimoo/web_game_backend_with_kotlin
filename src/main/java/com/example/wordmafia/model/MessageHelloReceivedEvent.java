package com.example.wordmafia.model;

import java.time.Instant;

public record MessageHelloReceivedEvent(String data, String requestedBy, Instant requestedAt) implements Event {
    @Override
    public String type() {
        return EventType.MESSAGE_HELLO_RECEIVED;
    }
}
