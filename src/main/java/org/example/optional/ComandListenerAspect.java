package org.example.optional;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class ComandListenerAspect {
    @Around("@annotation(ComandListener)")
    public void listenCommand(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Введите конечную точку интервала: ");
        while (true) {
            try {
                joinPoint.proceed();
                break;
            } catch (Exception e) {
                System.out.println("Недопустимое значение. Попробуйте снова");
            }
        }
    }
}
