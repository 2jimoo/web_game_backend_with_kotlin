package com.example.wordmafia.util.callback;

import com.example.wordmafia.model.Event;

public interface EventInterceptor {
    Event intercept(Event event);

}
