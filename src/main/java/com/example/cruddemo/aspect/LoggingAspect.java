package com.example.cruddemo.aspect;

import com.example.cruddemo.entity.Employee;
import com.example.cruddemo.entity.Student;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@Aspect
@Component
public class LoggingAspect {

    private Logger logger = Logger.getLogger(getClass().getName());

    @Before("com.example.cruddemo.aspect.SupportAspect.forAppFlow()")
    public void before(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        logger.info("=====>>: in @Before: calling method" + methodSignature.toShortString());

        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            logger.info("=====>>: argument: " + arg);
        }
    }

    /*
    Using @AfterReturning advice
     */

    @AfterReturning(
            pointcut = "com.example.cruddemo.aspect.SupportAspect.forAppFlow()",
            returning = "result"
    )
    public void afterReturning(JoinPoint joinPoint, Object result) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        logger.info("=====>>: in @After: calling method" + methodSignature.toShortString());


        logger.info("=====>>result:" + result);
//        if (Objects.nonNull(result)) {
//            System.out.println("\n=====>>> result is: " + result);
//        }
    }
}
