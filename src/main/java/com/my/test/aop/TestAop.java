package com.my.test.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TestAop {

	@SuppressWarnings("unchecked")
	@Around(value = "execution(* cn.com.funguide.service..*.*.*(..))")
	public Object doAroundLog(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.println("Test*************");
		
		return null;
	}
}
