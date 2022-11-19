package com.example.wordmafia.util.callback;


import com.example.wordmafia.model.Event;

public class DefaultEventInterceptor implements EventInterceptor {
    @Override
    public Event intercept(Event event) {
        return event;
    }
}
