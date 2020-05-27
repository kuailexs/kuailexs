package com.kuailexs.common.aop;

import com.kuailexs.common.annotation.Authority;
import com.kuailexs.common.annotation.AuthorityCode;
import com.kuailexs.common.bean.MethodInfo;
import com.kuailexs.common.bean.User;
import com.kuailexs.common.exception.KuailexsException;
import com.kuailexs.common.tools.TaleUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Component
@Aspect
public class AopAspect {
    private static Logger logger = LoggerFactory.getLogger(AopAspect.class);

    @Before("@annotation(com.kuailexs.common.annotation.Authority)")
    public void before(JoinPoint joinPoint) throws Throwable {
        MethodInfo methodInfo = getMethodInfo(joinPoint);
//        if(!methodInfo.isMapping()){
//            return;
//        }
        checkAuth(joinPoint,methodInfo);
    }

    @Around("execution(public *  com.kuailexs.*.controller.*Controller.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodInfo methodInfo = getMethodInfo(joinPoint);
        Long startTime = System.currentTimeMillis();
        Object obj = joinPoint.proceed();
        Long lengthOfresponse = System.currentTimeMillis() - startTime;
        logger.info("接口处理时间[" + lengthOfresponse + "]");
        return obj;
    }

    /**
     * 检查权限
     * @param joinPoint
     * @param methodInfo
     */
    private void checkAuth(JoinPoint joinPoint, MethodInfo methodInfo) {
        Authority authority = methodInfo.getMethod().getAnnotation(Authority.class);
        if(authority.value() == AuthorityCode.ALL){
            return;
        }
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            User user =  TaleUtils.getLoginUser(request);
            if(user == null){
                throw new KuailexsException("k_0001","请登录后操作！");
            }else {
                if(authority.value() == AuthorityCode.ADMIN && !Integer.valueOf(1).equals(user.getAdmin())){
                    throw new KuailexsException("k_0002","没有权限操作！");
                }
            }
        }catch (Exception e){
            throw new KuailexsException("k_0001","请登录后操作！");
        }
    }

    MethodInfo getMethodInfo(JoinPoint joinPoint){
        MethodInfo methodInfo = new MethodInfo();
        try {
            String targetName = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            Object[] arguments = joinPoint.getArgs();

            methodInfo.setClassName(targetName);
            methodInfo.setMethodName(methodName);
            methodInfo.setArguments(arguments);

            Class targetClass = Class.forName(targetName);
            if(targetClass.getAnnotation(RestController.class) != null){
                methodInfo.setReturnBean(true);
            }
            methodInfo.setClazz(targetClass);
            methodInfo.setClassAnnotations(targetClass.getAnnotations());

            Method[] methods = targetClass.getMethods();
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    Class[] clazzs = method.getParameterTypes();
                    if (clazzs.length == arguments.length ) {
                        methodInfo.setMethod(method);
                        methodInfo.setParameterTypes(clazzs);
                        methodInfo.setReturnType(method.getReturnType());
                        methodInfo.setMethodAnnotations(method.getAnnotations());
                        if( method.getAnnotation(ResponseBody.class) != null){
                            methodInfo.setReturnBean(true);
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return methodInfo;
    }
}
