package com.asgnmt.ems.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class ServicesAOPLogging {

	/**
	 * This logger tracks the execution time of all APIs, it can be used for improving api performance
	 * @param proceedingJoinPoint
	 * @return
	 * @throws Throwable
	 */
    @Around("execution(* com.asgnmt.ems.controllers..*(..)))")
    public Object profileAllMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable 
    {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();
        LogUtils.debug(log, className+":: execution of method "+methodName+" START");
        
        String[] paramNames=methodSignature.getParameterNames();
        for (int i=0;i<paramNames.length;i++) {
        	String name=paramNames[i];
            LogUtils.debug(log, "Parameter["+i+"] Name="+name);
        }
        
        Object[] arguments = proceedingJoinPoint.getArgs();
        for (int i=0;i<arguments.length;i++) {
        	Object object=arguments[i];
            LogUtils.debug(log, "Parameter["+i+"] Value="+object);
        }
        
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object result = proceedingJoinPoint.proceed();
        stopWatch.stop();
        LogUtils.debug(log, "Response::"+result);
        LogUtils.debug(log, className+":: execution of method "+methodName+" END, Execution Time :: " + stopWatch.getTotalTimeMillis() + " ms");
        return result;
    }
    
    @AfterThrowing(pointcut = "execution(* com.asgnmt.ems.controllers..*(..)))", throwing = "ex")
    public void logError(Exception ex) {
    	LogUtils.debug(log, "Exception Message::"+ex.getMessage());
    	LogUtils.debug(log, "Exception Cause::"+ex.getCause());
    }
}
