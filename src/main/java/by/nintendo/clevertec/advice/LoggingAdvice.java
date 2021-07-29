package by.nintendo.clevertec.advice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;

@RequiredArgsConstructor()
@Aspect
@Slf4j
@Component
public class LoggingAdvice {
    private final HttpServletRequest request;

    @Pointcut("execution( public * by.nintendo.clevertec.service.impl.*.*(..) )")
    public void callAtService() {
    }

    @Pointcut("execution(public * by.nintendo.clevertec.controller.*.*.*(..) )")
    public void callAtControllers() {
    }

    @Pointcut("execution(public * by.nintendo.clevertec.util.converter.*.*(..) )")
    public void callAtProtoConverter() {
    }

    @Pointcut("execution(public * by.nintendo.clevertec.util.*.*(..) )")
    public void callAtUtil() {
    }

    @Before("callAtControllers()")
    public void beforeCallControllers() {
        Map<String, String[]> parameterMap = request.getParameterMap();
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            stringBuilder.append(entry.getKey())
                    .append(Arrays.toString(entry.getValue()));
        }
        if (stringBuilder.length() != 0) {
            log.info("Request {} on URL: {} with parameters {}", request.getMethod(), request.getRequestURI(), stringBuilder.toString());

        } else {
            log.info("Request {} on URL:{}", request.getMethod(), request.getRequestURI());
        }
    }

    @Before("callAtService()||callAtUtil()||callAtProtoConverter()")
    public void beforeCallService(JoinPoint pjp) {
        log.info("In method {} with parameters: {}!", pjp.getSignature().getName(), pjp.getArgs());
    }

    @After("callAtService()||callAtUtil()||callAtProtoConverter()")
    public void afterCallService(JoinPoint pjp) {
        log.info("Finished method {}().", pjp.getSignature().getName());
    }

    @AfterReturning(value = "callAtService()", returning = "result")
    public void afterReturningCallAtServiceMethods(JoinPoint joinPoint, Object result) {
        log.info("Result: {}", result);
    }

    @AfterThrowing(pointcut = "callAtControllers()||callAtUtil()||callAtProtoConverter()||callAtService()", throwing = "exception")
    public void afterThrowingCallAtServiceMethods(Throwable exception) {
        log.warn("Exception: {}", exception.getLocalizedMessage());
    }

}
