package com.example.wordmafia.model;

import java.time.Instant;

public interface Event {
    String type();

    Object data();

    String requestedBy();

    Instant requestedAt();
}
