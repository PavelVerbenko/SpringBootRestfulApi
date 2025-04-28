package com.example.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Before("execution(* com.example.services.TaskService.createTask(..))")
    public void logBeforeCreate(JoinPoint joinPoint) {
        log.info("Trying to create a new task: {}", joinPoint.getArgs()[0]);
    }

    @AfterThrowing(
            pointcut = "execution(* com.example.services.TaskService.updateTask(..))",
            throwing = "ex"
    )
    public void logUpdateException(RuntimeException ex) {
        log.error("Task update error: {}", ex.getMessage());
    }

    @AfterReturning(
            pointcut = "execution(* com.example.services.TaskService.getTaskById(..))",
            returning = "result"
    )
    public void logAfterGetById(Object result) {
        log.info("The task was successfully received: {}", result);
    }

    @Around("execution(* com.example.services.TaskService.getAllTasks(..))")
    public Object measureGetAllExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long elapsed = System.currentTimeMillis() - start;

        log.info("Method {} executed in {} ms",
                joinPoint.getSignature().getName(),
                elapsed);

        return result;
    }
}
