package com.example.wordmafia.util;

import com.example.wordmafia.model.Event;
import com.example.wordmafia.util.callback.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.TaskExecutor;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

@Slf4j
@Component
public class EventExecutor {

    private final EventInterceptor eventInterceptor;
    private final EventListener eventListener;
    private final EventExceptionHandler eventExceptionHandler;
    private final RetryTemplate retryTemplate;// RecoveryCallback은 여기 등록해서 넘겨줘라
    private final TaskExecutor taskExecutor;

    public EventExecutor() {
        this(null, null, null, null, null);
    }

    public EventExecutor(EventInterceptor eventInterceptor, EventListener eventListener, EventExceptionHandler eventExceptionHandler, RetryTemplate retryTemplate, TaskExecutor taskExecutor) {
        this.eventInterceptor = eventInterceptor != null ? eventInterceptor : new DefaultEventInterceptor();
        this.eventListener = eventListener != null ? eventListener : new DefaultEventListener();
        this.eventExceptionHandler = eventExceptionHandler != null ? eventExceptionHandler : new DefaultExceptionHandler();
        this.retryTemplate = retryTemplate;
        if (taskExecutor != null) {
            this.taskExecutor = taskExecutor;
        } else {
            ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
            threadPoolTaskExecutor.setCorePoolSize(10);
            threadPoolTaskExecutor.setMaxPoolSize(10);
            threadPoolTaskExecutor.initialize();
            this.taskExecutor = threadPoolTaskExecutor;
        }
    }


    public void execute(Collection<EventExecutable> eventExecutables, Event event) {
        if (eventExecutables != null) {
            eventExecutables.forEach(
                    handler ->
                            taskExecutor.execute(
                                    () -> {
                                        Runnable task = () -> {
                                            Object target = handler.target();
                                            Method method = handler.method();
                                            Event eventProcessed = event;
                                            if (eventInterceptor != null) {
                                                eventProcessed = eventInterceptor.intercept(event);
                                            }
                                            if (eventListener != null) {
                                                eventListener.before(eventProcessed);
                                            }
                                            try {
                                                method.invoke(target, eventProcessed);
                                            } catch (InvocationTargetException | IllegalAccessException ex) {
                                                eventExceptionHandler.doOnException(ex, eventProcessed);
                                            } finally {
                                                if (eventListener != null) {
                                                    eventListener.after(eventProcessed);
                                                }
                                            }
                                        };

                                        if (retryTemplate != null) {
                                            retryTemplate.execute(
                                                    context ->
                                                    {
                                                        task.run();
                                                        return null;
                                                    }
                                            );
                                        } else {
                                            task.run();
                                        }
                                    }));
        }
    }
}
