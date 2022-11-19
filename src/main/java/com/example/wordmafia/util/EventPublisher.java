package com.example.wordmafia.util;

import com.example.wordmafia.model.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventPublisher {
    private final EventExecutableRegistry targetRegistry;
    private final EventExecutor eventExecutor;

    public void publish(Event event) {
        Collection<EventExecutable> handlers = targetRegistry.getHandlers(event.type());
        eventExecutor.execute(handlers, event);
    }

}
