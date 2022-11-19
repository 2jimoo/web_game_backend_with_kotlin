package com.example.wordmafia.util.callback;

import com.example.wordmafia.model.Event;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.log.LogAccessor;

public class DefaultExceptionHandler implements EventExceptionHandler {
    private final LogAccessor logger = new LogAccessor(LogFactory.getLog(getClass()));

    @Override
    public void doOnException(Exception e) {

    }

    @Override
    public void doOnException(Exception e, Event event) {
        logger.error("Handling event is failed.");
    }
}
