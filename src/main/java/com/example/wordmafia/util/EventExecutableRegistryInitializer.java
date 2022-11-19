package com.example.wordmafia.util;

import com.example.wordmafia.model.Event;
import com.example.wordmafia.util.reflection.AnnotationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.Collection;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventExecutableRegistryInitializer {

    private final ApplicationContext applicationContext;
    private final EventExecutableRegistry targetRegistry;

    @PostConstruct
    protected void resolveEventHandlers() {
        String[] annotatedBeans = applicationContext.getBeanDefinitionNames();
        for (String name : annotatedBeans) {
            if (name.equalsIgnoreCase(this.getClass().getSimpleName())) {
                continue;
            }
            Object object = applicationContext.getBean(name);
            Class<?> targetClass = object.getClass();
            Collection<Method> targetMethods = AnnotationUtils.findAnnotatedMethods(
                    EventHandler.class,
                    targetClass,
                    method -> {
                        Class<?>[] params = method.getParameterTypes();
                        return params.length == 1 && params[0].equals(Event.class);
                    });

            if (!targetMethods.isEmpty()) {
                for (Method method : targetMethods) {
                    targetRegistry.resolveAttribute(method, object);
                }
            }
        }

    }
}
