package org.zerock.aop;



import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

@Component
@Log4j
@Aspect
public class LogAdvice {
	// SampleService 클래스의 메서드 호출 시간을 로깅하는 어드바이스
	@Around("execution(* org.zerock.service.SampleService*.*(..))" )
	public Object logTime(ProceedingJoinPoint pjp) {
		long start = System.currentTimeMillis();
		log.info("Target : " + pjp.getTarget());
		log.info("Param : " + Arrays.toString(pjp.getArgs()));
		Object result = null;
		
		try {
			result = pjp.proceed();
		}catch(Throwable e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		log.info("TIME : " + (end-start));
		return result;
	}
	
	// SampleService 클래스의 메서드 호출 이전에 로그 출력
	@Before( "execution(* org.zerock.service.SampleService*.*(..))" )
	public void logBefore() {
		log.info("----------------------------------");
	}
	
	// SampleService 클래스의 doAdd 메서드 호출 이전에 인자 값 로깅
	@Before( "execution(* org.zerock.service.SampleService*.doAdd(String, String)) && args(str1,str2)" )
	public void logBeforeWithParam(String str1, String str2) {
		log.info("str1 : " + str1);
		log.info("str2 : " + str2);
	}
	
	// SampleService 클래스의 메서드 실행 중 예외 발생 시 로그 출력
	@AfterThrowing(pointcut =  "execution(* org.zerock.service.SampleService*.*(..))", throwing = "exception" )
	public void logException(Exception exception) {
		log.info("Exceiption-------------");
		log.info("Exceiption : " + exception);
	}
	
	
	
}
