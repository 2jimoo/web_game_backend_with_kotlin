package com.example.wordmafia.util;

import com.example.wordmafia.util.reflection.AnnotationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.MethodClassKey;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventExecutableRegistry {
    private final Map<String, Collection<EventExecutable>> eventTargetMethodMap =
            new ConcurrentHashMap<>();
    private final Set<MethodClassKey> targetMethodSet = new HashSet<>();

    protected Collection<EventExecutable> getHandlers(String eventType) {
        return this.eventTargetMethodMap.get(eventType);
    }


    protected void resolveAttribute(Method method, Object target) {
        MethodClassKey methodClassKey = new MethodClassKey(method, target.getClass());
        if (targetMethodSet.contains(methodClassKey)) {
            return;
        }
        Method specificMethod = AopUtils.getMostSpecificMethod(method, target.getClass());
        EventHandler eventHandler =
                AnnotationUtils.findUniqueAnnotation(specificMethod, EventHandler.class);

        if (eventHandler != null) {
            // 중복 허용
            Arrays.stream(eventHandler.eventTypes())
                    .forEach(
                            e -> {
                                Collection<EventExecutable> eventExecutables =
                                        eventTargetMethodMap.getOrDefault(e, new HashSet<>());
                                eventExecutables.add(new EventExecutable(e, target, method));
                                eventTargetMethodMap.put(e, eventExecutables);
                            });
        }
        targetMethodSet.add(methodClassKey);
    }
}
