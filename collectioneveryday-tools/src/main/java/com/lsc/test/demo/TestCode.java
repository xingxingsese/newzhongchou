package com.lsc.test.demo;

import com.alibaba.fastjson.JSONObject;
import com.lsc.freemarker.utils.CustomClassLoader;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author lisc
 * @Description: com.lsc.freemarker.demo
 * @date 2022/10/10 10:08
 */
public class TestCode {

    @Test
    public void test01() throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

        //    String classPath = "com.ipay.ibizecoprod.core.service.voyage.impl.MpOpenMarketPlaceServiceImpl";
        String classPath = "com.ipay.ibizecoprod.core.service.voyage.impl.MpOpenMarketPlaceServiceImpl";
        // 创建自定义的类加载器
        CustomClassLoader loader = new CustomClassLoader();

        // 使用自定义的类加载器加载TestHelloWorld类
        Class classaa = loader.loadClass(classPath);
        Field[] declaredFields = classaa.getDeclaredFields();
        Method[] declaredMethods = classaa.getDeclaredMethods();
        Method[] methods = classaa.getMethods();

        for (Method declaredMethod : declaredMethods) {
            declaredMethod.setAccessible(true);

            System.out.println("declaredMethod = " + JSONObject.toJSONString(declaredMethod));
       /*     for (Type genericParameterType : declaredMethod.getGenericParameterTypes()) {
                String typeName = genericParameterType.getTypeName();

            }*/
        }

        System.out.println(JSONObject.toJSONString(classaa));
        System.out.println("declaredFields " + JSONObject.toJSONString(declaredFields));
        System.out.println("declaredMethods " + JSONObject.toJSONString(declaredMethods));
        System.out.println("methods = " + JSONObject.toJSONString(methods));

     /*   for (Field field : declaredFields) {
            System.out.println("field.getType().getSimpleName() = " + field.getType().getSimpleName());
            System.out.println("field.getType().getName() = " + field.getType().getName());
            System.out.println("field.getName() = " + field.getName());
        }*/

    }

    @Test
    public void test02() throws Exception {
        File file = new File("D:\\Code\\TestCode\\newzhongchou\\Freemarker-utils\\src\\main\\resources\\FreeMarkerFile\\SpannedFile\\20221013164314\\QueryMarketPlaceApplyListRequest.json");
        if (file.exists()) {
            System.out.println("存在");
        } else {
            System.out.println("不存在,path" + file.getPath());
            boolean mkdirs = file.getParentFile().mkdirs();
            boolean newFile = file.createNewFile();

            System.out.println(mkdirs + " ----  " + newFile);
        }
    }


}
