package kr.co.tworld.shop.framework.config;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Logging Aspect Component
 * @author Sangjun, Park
 *
 */
@Component
@Aspect
@Slf4j
public class LoggerAspect {
	
	/**
	 * Declaire pointcut 
	 */
	@Pointcut("execution(* kr.co.tworld.shop..*Controller.*(..))"
			+ " or execution(* kr.co.tworld.shop..*Service.*(..))"
			+ " or execution(* kr.co.tworld.shop..*Mapper.*(..))")
	public void loggingPointcut() {}
	
	/**
	 * Before logging
	 * @param joinPoint
	 */
	@Before("loggingPointcut()")
	public void beforeLogging(final JoinPoint joinPoint) {
		log.debug("Before: {}", joinPoint);
		if(ArrayUtils.isNotEmpty(joinPoint.getArgs())) {
			log.debug("Arguments: {}", 
					Arrays.stream(joinPoint.getArgs()).map(Object::toString).collect(Collectors.joining(",")));
		}
	}
	
	/**
	 * After logging
	 * @param joinPoint
	 * @param result
	 */
	@AfterReturning(pointcut = "loggingPointcut()", returning = "result")
	public void returnLogging(final JoinPoint joinPoint, @Nullable final Object result) {
		log.debug("Completed: {}", joinPoint);
		if(result != null) {
			log.debug("Result: {}", result.toString());
		}
	}

}
