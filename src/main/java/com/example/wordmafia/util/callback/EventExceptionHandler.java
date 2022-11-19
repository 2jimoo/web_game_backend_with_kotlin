package com.example.wordmafia.util.callback;

import com.example.wordmafia.model.Event;

public interface EventExceptionHandler {
    void doOnException(Exception e);

    void doOnException(Exception e, Event event);
}
