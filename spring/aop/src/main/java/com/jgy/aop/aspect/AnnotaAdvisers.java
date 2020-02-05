package com.jgy.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.ReactiveAdapter;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.http.HttpRequest;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.lang.reflect.Method;

@Aspect
@Slf4j
public class AnnotaAdvisers extends AnnotaPointcuts {

    @Nullable
    private final ReactiveAdapterRegistry reactiveAdapterRegistry;

    /**
     * Reactive Streams API present on the classpath?
     */
    private static final boolean reactiveStreamsPresent =
            ClassUtils.isPresent("org.reactivestreams.Publisher", AnnotaAdvisers.class.getClassLoader());

    public AnnotaAdvisers() {
        if (reactiveStreamsPresent) {
            this.reactiveAdapterRegistry = ReactiveAdapterRegistry.getSharedInstance();
        }
        else {
            this.reactiveAdapterRegistry = null;
        }
    }

    @Pointcut("@annotation(com.jgy.aop.annotation.Annota)")
    public void annota() {}

    @Around("requestMapping() && annota()")
    public Object aroundAnnota(ProceedingJoinPoint jointPoint) throws Throwable {
        log.info("Executing advisor....");
        Mono.subscriberContext()
                .filter(c -> c.hasKey(ServerWebExchange.class))
                .map(c -> c.<ServerWebExchange>get(ServerWebExchange.class))
                .map(ServerWebExchange::getRequest)
                .map(HttpRequest::getURI)
                .subscribe(uri -> log.debug(uri.getPath()));

        MethodSignature methodSignature = (MethodSignature) jointPoint.getSignature();
        return proceed(methodSignature.getMethod(), jointPoint);
    }

    private Object proceed(Method method, ProceedingJoinPoint jointPoint) throws Throwable {
        ReactiveAdapter adapter = this.reactiveAdapterRegistry.getAdapter(method.getReturnType());
        try {
            if (adapter != null) {
                if (Mono.class.isAssignableFrom(method.getReturnType()))
                    return (Mono<?>) jointPoint.proceed();
                else return adapter.toPublisher(jointPoint.proceed());
            }
        }
        catch (Throwable ex) {
                return Mono.error(ex);
        }
        return jointPoint.proceed();
    }
}


