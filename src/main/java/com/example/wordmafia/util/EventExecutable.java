package com.example.wordmafia.util;

import java.lang.reflect.Method;

// String eventType: 확인용
public record EventExecutable(String eventType, Object target, Method method) {
}
