package com.example.cruddemo.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class SupportAspect {
    @Pointcut("execution(* com.example.cruddemo.controller.*.*(..))")
    public void forControllerPackage() { }

    @Pointcut("execution(* com.example.cruddemo.service.*.*(..))")
    public void forServicePackage() { }

    @Pointcut("execution(* com.example.cruddemo.dao_old.*.*(..))")
    public void forDaoPackage() { }

    // exclude getter/setter methods
    @Pointcut("forControllerPackage() || forServicePackage() forDaoPackage()")
    public void forAppFlow() { }
}
