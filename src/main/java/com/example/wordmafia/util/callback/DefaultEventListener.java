package com.example.wordmafia.util.callback;


import com.example.wordmafia.model.Event;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.log.LogAccessor;

public class DefaultEventListener implements EventListener {

    private final LogAccessor logger = new LogAccessor(LogFactory.getLog(getClass()));

    @Override
    public void before(Event event) {
        logger.info("%s consuming started. ".formatted(event.type()));
    }

    @Override
    public void after(Event event) {
        logger.info("%s consuming finished. ".formatted(event.type()));
    }
}
