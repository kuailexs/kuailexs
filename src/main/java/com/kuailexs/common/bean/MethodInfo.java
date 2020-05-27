package com.kuailexs.common.bean;

import lombok.Data;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Controller 中 请求方法信息
 * @Author ：dhl
 * @Date ：Created in 14:17 2020/5/27
 * @Description：
 * @Modified By：
 * @Version:
 */
@Data
public class MethodInfo {
    String methodName;
    Method method;
    Annotation[] methodAnnotations;
    String className;
    Class clazz;
    Annotation[] classAnnotations;
    boolean returnBean = false;
    Object[] arguments;
    Class[] parameterTypes;
    Class returnType;

    public boolean isMapping(){
        if(methodAnnotations != null && methodAnnotations.length > 0){
            for(Annotation annotation : methodAnnotations){
                if(annotation.getClass().getName().endsWith("Mapping")){
                    return true;
                }
            }
        }
        return false;
    }
}
