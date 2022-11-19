package com.example.wordmafia.util.callback;

import com.example.wordmafia.model.Event;

public interface EventListener {
    void before(Event event);

    void after(Event event);
}
