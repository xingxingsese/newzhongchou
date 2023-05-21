package com.lsc.freemarker.demo;

import com.lsc.freemarker.core.FreeMarkerGenerator;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * @author lisc
 * @Description: com.lsc.freemarker.demo
 * @date 2022/10/31 16:10
 */
public class ReflectionUtilTest {

    @Test
    public void test() {
        FreeMarkerGenerator freeMarkerGenerator = new FreeMarkerGenerator();
        Class<? extends FreeMarkerGenerator> aClass = freeMarkerGenerator.getClass();
        for (Method method : aClass.getMethods()) {
            method.setAccessible(true);
            Class<?> returnType = method.getReturnType();
            System.out.println("returnType.getSimpleName() = " + returnType.getSimpleName());
            System.out.println("returnType.getName() = " + returnType.getName());
            System.out.println("returnType.getTypeName() = " + returnType.getTypeName());
            System.out.println("returnType.getCanonicalName() = " + returnType.getCanonicalName());
            System.out.println("returnType.getTypeParameters() = " + returnType.getTypeParameters());
            System.out.println();
        }
    }
}
