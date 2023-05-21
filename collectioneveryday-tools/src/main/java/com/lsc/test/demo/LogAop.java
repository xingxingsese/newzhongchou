package com.lsc.test.demo;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author lisc
 * @Description: 切入点文档: https://blog.csdn.net/weixin_43611145/article/details/102728814
 * apo 切面介绍文档: https://blog.csdn.net/weixin_43611145/article/details/89084019
 * @date 2022/10/27 17:52
 */
@Component
@Aspect
public class LogAop {
    public Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 切入点
     * execution(<修饰符模式>?<返回类型模式><方法名模式>(<参数模式>)<异常模式>?)
     */
    @Pointcut("execution(* com.lsc..*(..))")
    public void ponitCut() {

    }

    /**
     * 异常通知，在目标方法抛出异常之后执行，意味着如果此通知被执行，则@AfterReturning不会被执行。
     * 此注解有一个throwing属性，使用了命名绑定模式(下文有介绍)，定义异常类型并接收异常对象。
     * 注意：
     * 1、如果目标方法自己try- catch了异常，而没有继续往外抛，则不会进入此通知。
     * 2、@AfterThrowing虽然处理异常，但它不会阻止异常传播到上一级调用者，如果没有catch，则会导致jvm终止。
     *
     * @param e
     */
    @AfterThrowing(throwing = "e", pointcut = "ponitCut()")
    public void exceptionAdvice(JoinPoint jp, Throwable e) {
        System.out.println("异常通知" + e);
    }

    /**
     * 标注了@SystemLog 这个注解都会拦截
     */
    @Pointcut("@annotation(SystemLog)")
    public void ponitCutAnnotation() {

    }

    /**
     * 满足 ponitCutAnnotation() 方法和 ponitCut() 这个方法的切入点都会拦截
     */
    @Pointcut("ponitCutAnnotation() || ponitCut()")
    public void collectPointcut() {

    }


    /**
     * @Before("ponitCut()") 基于ponitCut()的切入点 进行前置通知
     */
    @Before("ponitCut()")
    public void beforeAdvice() {
        System.out.println("前置通知: beforeAdvice");
    }

    @After("ponitCut()")
    public void afterAdvice() {
        System.out.println("后置通知: afterAdvice");
    }


    //环绕通知。注意要有ProceedingJoinPoint参数传入
    @Around("collectPointcut()")
    public void around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("注解类型环绕通知..环绕前");


        pjp.proceed();//执行方法
        // 当前拦截的对象
        Class<?> currentClass = pjp.getTarget().getClass();
        //获取拦截类
        String className = currentClass.getName();

        //获取拦截的方法名
        MethodSignature msig = (MethodSignature) pjp.getSignature();

        Method currentMethod = currentClass.getMethod(msig.getName(), msig.getParameterTypes());
        String methodName = currentMethod.getName();


        //获取拦截方法的参数
        Object[] params = pjp.getArgs();
        String param = JSON.toJSONString(params);
        System.out.println("拦截方法的入参参数为：" + param);

        //获取注解
        SystemLog annotation = currentMethod.getAnnotation(SystemLog.class);
        System.out.println("拦截方法注解上LogType值为：" + annotation.LogType());

        log.info("AOP切面: {}.{}({})", className, methodName, param);

        System.out.println("注解类型环绕通知..环绕后");
    }
}
