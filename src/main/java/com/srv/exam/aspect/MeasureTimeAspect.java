package com.srv.exam.aspect;

import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class MeasureTimeAspect {

  @Around("execution(* *(..)) && @annotation(measureTime)")
  public Object log(ProceedingJoinPoint point, MeasureTime measureTime) throws Throwable {

    String traceId = null;
    Pattern p =
        Pattern.compile(
            "^[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}");
    for (Object attribute : point.getArgs()) {
      try {
        if (attribute instanceof String && p.matcher((String) attribute).find()) {
          traceId = (String) attribute;
        }
      } catch (Exception e) {
        log.warn("No Trace ID", e);
      }
    }
    long start = System.currentTimeMillis();
    Object result = point.proceed();
    long end = System.currentTimeMillis() - start;

    // logs only when time taken is more than 500 ms
    if (end > 500) {
      if (null == traceId) {
        log.warn(
            "className={} | methodName={} | timeTaken in ms = {}",
            new Object[] {
              MethodSignature.class.cast(point.getSignature()).getDeclaringTypeName(),
              MethodSignature.class.cast(point.getSignature()).getMethod().getName(),
              end
            });
      } else {
        log.warn(
            "[" + traceId + "]className={} | methodName={} | timeTaken in ms = {}",
            new Object[] {
              MethodSignature.class.cast(point.getSignature()).getDeclaringTypeName(),
              MethodSignature.class.cast(point.getSignature()).getMethod().getName(),
              end
            });
      }
    }
    return result;
  }
}
