package com.codebandit.authsimplified.aop;

import com.codebandit.authsimplified.abstractions.dto.SecureResource;
import com.codebandit.authsimplified.annotations.GetSecureResource;
import com.codebandit.authsimplified.annotations.SecureResourceService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Aspect
public class SimplifiedCRUDServiceInterceptor {
    @Pointcut("within(@com.codebandit.authsimplified.annotations.SecureResourceService *)")
    public void secureResourceAnnotationPointCut() {}

    @Pointcut("execution(public * *(..))")
    public void publicMethodsPointCut() {}

    @Before("secureResourceAnnotationPointCut() && publicMethodsPointCut()")
    public void before(JoinPoint joinPoint) throws InvocationTargetException, IllegalAccessException {
        Object currentExecutingObject = joinPoint.getThis();
        Object [] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method calledMethod = signature.getMethod();
        Method [] methods = calledMethod.getDeclaringClass().getDeclaredMethods();
        List<Method> listOfMethodsWithReqAnn = Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(GetSecureResource.class))
                .collect(Collectors.toList());
        long noOfMethodsWithReqAnn = listOfMethodsWithReqAnn.size();
        if(noOfMethodsWithReqAnn > 1L) {
            throw new RuntimeException("Wrong");
        }
        else {
            Method method = listOfMethodsWithReqAnn.get(0);
            List<Object> secureResources = Arrays.stream(args).filter(arg -> arg instanceof SecureResource).collect(Collectors.toList());
            method.invoke(currentExecutingObject, secureResources.get(0));
        }
    }
}
