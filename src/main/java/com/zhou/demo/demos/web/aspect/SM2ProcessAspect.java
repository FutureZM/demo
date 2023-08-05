package com.zhou.demo.demos.web.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 处理SM2验签和解密的逻辑
 */
@Aspect
@Component
public class SM2ProcessAspect {

    @Around("@annotation(com.zhou.demo.demos.web.aspect.annotation.SM2Process)")
    public Object sm2Process(ProceedingJoinPoint joinPoint) throws Throwable {
//        // 获取目标方法的方法名
//        String methodName = joinPoint.getSignature().getName();
//
//        // 获取目标方法的参数列表
//        Object[] args = joinPoint.getArgs();
//
//        // 打印方法名和参数信息
//        System.out.println("---> in aspect");

        // 执行目标方法
        Object result = joinPoint.proceed();

        // 在方法执行后打印日志或进行其他处理
        return result;
    }
}
