package com.chatserver.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoggingAspect {

	static long START_TIME = System.currentTimeMillis();

	@Pointcut("execution(* com.chatserver.controller.*.*(..))")
	public void apiMethods() {

	}

	@Before("apiMethods()")
	public void logMethodExecution(JoinPoint joinPoint) {
		String methodName = joinPoint.getSignature().getName();
		String className = joinPoint.getTarget().getClass().getSimpleName();
		log.info("Executing method {} in class {} and start time {} ", methodName, className, START_TIME);
	}

	@After("apiMethods()")
	public void logApiExecutionTime(JoinPoint joinPoint) {
		String methodName = joinPoint.getSignature().getName();
		String className = joinPoint.getTarget().getClass().getSimpleName();
		long endTime = System.currentTimeMillis();
		long executionTime = endTime - START_TIME;
		log.info("Method {} in class {} took {} milliseconds to execute", methodName, className, executionTime);
	}
}
