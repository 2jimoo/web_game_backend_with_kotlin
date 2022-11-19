package com.example.wordmafia.util.reflection;

import org.springframework.core.annotation.AnnotationConfigurationException;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.annotation.RepeatableContainers;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

public final class AnnotationUtils {

    private AnnotationUtils() {
    }

    public static <A extends Annotation> A findUniqueAnnotation(
            Method method, Class<A> annotationType) {
        MergedAnnotations mergedAnnotations =
                MergedAnnotations.from(
                        method, MergedAnnotations.SearchStrategy.TYPE_HIERARCHY, RepeatableContainers.none());
        if (hasDuplicate(mergedAnnotations, annotationType)) {
            throw new AnnotationConfigurationException(
                    "Found more than one annotation of type "
                            + annotationType
                            + " attributed to "
                            + method
                            + " Please remove the duplicate annotations and publishByExecutor a bean to handle your authorization logic.");
        }
        return org.springframework.core.annotation.AnnotationUtils.findAnnotation(method, annotationType);
    }

    private static <A extends Annotation> boolean hasDuplicate(
            MergedAnnotations mergedAnnotations, Class<A> annotationType) {
        boolean alreadyFound = false;
        for (MergedAnnotation<Annotation> mergedAnnotation : mergedAnnotations) {
            if (mergedAnnotation.getType() == annotationType) {
                if (alreadyFound) {
                    return true;
                }
                alreadyFound = true;
            }
        }
        return false;
    }


    public static <A extends Annotation> Collection<Method> findAnnotatedMethods(Class<A> annotationType, Class<?> clazz, Predicate<Method> targetMethodCondition) {
        final AtomicReference<Set<Method>> targetMethods = new AtomicReference<>(new HashSet<>());
        ReflectionUtils.doWithMethods(
                clazz,
                method -> {
                    // Spring CGLIB proxy?, 순수클래스? 같이 떠서 동시 등록됨
                    Annotation[] annotations = method.getDeclaredAnnotations();
                    if (annotations.length == 0) return;

                    Annotation annotation =
                            AnnotationUtils.findUniqueAnnotation(method, annotationType);
                    if (annotation != null) {
                        if (targetMethodCondition.test(method))
                            targetMethods.get().add(method);
                    }
                });

        return new HashSet<>(targetMethods.get());
    }
}
