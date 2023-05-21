/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2020 All Rights Reserved.
 */
package com.lsc.freemarker.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lsc.freemarker.entity.FieldBean;
import com.lsc.freemarker.entity.MethodBean;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * 反射工具类
 */

@Slf4j
public class ReflectionUtil {

    /**
     * 获取类下所有自定义属性
     *
     * @param classaa
     */
    public static List<FieldBean> getFieldBeans(Class classaa) {
        // 得到类下所有属性
        Field[] fields = classaa.getDeclaredFields();
        ArrayList<FieldBean> fieldBeans = new ArrayList<>();
        for (Field field : fields) {
            FieldBean fieldBean = new FieldBean();
            // 跳过java自带的类
            if (StringUtils.containsAny(field.getType().getName(), "java.lang.", "java.util.", ".Logger", "$")) {
                continue;
            }
            // 属性类型名
            fieldBean.setFieldType(field.getType().getSimpleName());
            // 属性变量名
            fieldBean.setFieldName(field.getName());
            // 属性全路径
            fieldBean.setFieldPathAndName(field.getType().getName().replace(".", "/"));
            fieldBeans.add(fieldBean);
        }
        return fieldBeans;
    }

    /**
     * 反射当前类下所有的方法
     *
     * @param classaa
     * @return
     */
    public static ArrayList<MethodBean> getMethodBeans(Class classaa, String outPathJson) {
        // 类下的方法集合
        ArrayList<MethodBean> methodList = new ArrayList<>();
        // 循环类下的所有方法
        /**
         * getDeclaredMethods()和getMethods()的方法区别在于, 前者会把Object类的方法变成代理对象, 后者会输出Object方法
         */
        for (Method declaredMethod : classaa.getDeclaredMethods()) {
            if (declaredMethod.getName().contains("$")) {
                continue;
            }
            MethodBean methodBean = new MethodBean();
            // 得到方法名
            methodBean.setMethodName(declaredMethod.getName());
            // 得到方法内的参数
            ArrayList<FieldBean> parameterList = new ArrayList<>();
            if (declaredMethod.getParameterCount() > 0) {
                if (!declaredMethod.isAccessible()) {
                    declaredMethod.setAccessible(true);
                }
                for (Class<?> parameterType : declaredMethod.getParameterTypes()) {
                    if (parameterType.getTypeName().contains("java.lang")) {
                        continue;
                    }
                    int parameterlastIndexOf = parameterType.getTypeName().lastIndexOf('.');
                    String fieldName = parameterType.getName().substring(parameterlastIndexOf + 1);
                    FieldBean fieldBean = new FieldBean();
                    fieldBean.setFieldPathAndName(parameterType.getTypeName().replace(".", "/"));
                    fieldBean.setFieldName(StringBuildUtils.buildLowerCaseStr(fieldName));
                    fieldBean.setFieldType(fieldName);
                    parameterList.add(fieldBean);
                    // 把方法入参输出成json格式文本
                    CreationJsonTxt(parameterType, outPathJson);
                }
            }
            methodBean.setMethodRequestType(parameterList);
            String returnTypeName = declaredMethod.getReturnType().getName();
            if (!"void".equals(returnTypeName)) {

                Class<?> returnType = declaredMethod.getReturnType();
                FieldBean fieldBean = new FieldBean();
                fieldBean.setFieldName(StringBuildUtils.buildLowerCaseStr(returnType.getSimpleName()));
                fieldBean.setFieldType(returnType.getSimpleName());
                fieldBean.setFieldPathAndName(returnType.getTypeName().replace(".", "/"));
                methodBean.setMethodResponseType(Arrays.asList(fieldBean));
                CreationJsonTxt(declaredMethod.getReturnType(), outPathJson);
            }
            methodList.add(methodBean);

        }
        return methodList;
    }

    /**
     * getProxyTarget
     *
     * @param proxy
     * @return
     * @throws Exception
     */
    public static Object getProxyTargetObject(Object proxy) throws Exception {
        if (!AopUtils.isAopProxy(proxy)) {
            return proxy;
        }

        Object target = null;
        if (AopUtils.isJdkDynamicProxy(proxy)) {
            target = getJdkDynamicProxyTargetObject(proxy);
        } else { //cglib
            target = getCglibProxyTargetObject(proxy);
        }
        if (AopUtils.isAopProxy(target)) {
            target = getProxyTargetObject(target);
        }
        return target;
    }

    /**
     * getCglibProxyTargetObject
     *
     * @param proxy
     * @return
     * @throws Exception
     */
    private static Object getCglibProxyTargetObject(Object proxy) throws Exception {
        Field h = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
        h.setAccessible(true);
        Object dynamicAdvisedInterceptor = h.get(proxy);

        Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField("advised");
        advised.setAccessible(true);

        Object target = ((AdvisedSupport) advised.get(dynamicAdvisedInterceptor)).getTargetSource().getTarget();

        return target;
    }

    /**
     * getJdkDynamicProxyTargetObject
     *
     * @param proxy
     * @return
     * @throws Exception
     */
    private static Object getJdkDynamicProxyTargetObject(Object proxy) throws Exception {
        Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
        h.setAccessible(true);
        AopProxy aopProxy = (AopProxy) h.get(proxy);

        Field advised = aopProxy.getClass().getDeclaredField("advised");
        advised.setAccessible(true);

        Object target = ((AdvisedSupport) advised.get(aopProxy)).getTargetSource().getTarget();

        return target;
    }

    /**
     * checkIfCanSkipField
     *
     * @param field
     * @return
     */
    private static Boolean checkIfNeedSkipField(Field field) {
        // 过滤静态属性
        if (Modifier.isStatic(field.getModifiers())) {
            return true;
        }
        // 过滤transient 关键字修饰的属性
        if (Modifier.isTransient(field.getModifiers())) {
            return true;
        }
        return false;
    }

    /**
     * 利用Java反射根据类的名称获取属性信息和父类的属性信息
     *
     * @param obj
     * @return
     */
    public static Map<String, Field> getAllFieldList(Object obj) {
        Map<String, Field> fieldMap = new LinkedHashMap<>();
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (checkIfNeedSkipField(field)) {
                continue;
            }
            fieldMap.put(field.getName(), field);
        }
        getFieldInParentClazz(clazz, fieldMap);
        return fieldMap;
    }

    /**
     * findFieldInTarget
     *
     * @param obj
     * @param fieldName
     * @return
     */
    public static Field getFieldInTarget(Object obj, String fieldName) {
        Map<String, Field> fieldMap = getAllFieldList(obj);
        if (fieldMap.containsKey(fieldName)) {
            return fieldMap.get(fieldName);
        }
        return null;
    }

    /**
     * 递归所有父类属性
     *
     * @param clazz
     * @param fieldMap
     */
    private static void getFieldInParentClazz(Class<?> clazz, Map<String, Field> fieldMap) {
        Class<?> superClazz = clazz.getSuperclass();
        if (superClazz != null) {
            Field[] superFields = superClazz.getDeclaredFields();
            for (Field field : superFields) {
                if (checkIfNeedSkipField(field)) {
                    continue;
                }
                fieldMap.put(field.getName(), field);
            }
            getFieldInParentClazz(superClazz, fieldMap);
        }
    }

    /**
     * checkIfSetMethodExists
     *
     * @param clazz
     * @param field
     * @return
     */
    public static Method checkIfSetMethodExists(Class<?> clazz, Field field) {
        String fieldName = field.getName();
        String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        Method method = getMethodInTarget(clazz, methodName);
        return method;
    }

    /**
     * getMethodInTarget
     *
     * @param clazz
     * @param methodName
     * @return
     */
    public static Method getMethodInTarget(Class<?> clazz, String methodName) {
        Method method = null;
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            if (declaredMethod.getName().contains(methodName)) {
                method = declaredMethod;
                break;
            }
        }

        if (method == null) {
            Class<?> superClazz = clazz.getSuperclass();
            if (superClazz != null) {
                method = getMethodInTarget(superClazz, methodName);
            }
        }
        return method;
    }

    //从当前实例到父类中找目标名、目标入参的方法,找到后执行并返回结果
    public static Method getTargetMethod(Class cla, String metName, Class[] args) throws Exception {
        try {
            //非空继续
            if (cla != null) {
                Class tempClass = cla;
                //当父类为null的时候说明到达了最上层的父类(Object类).
                while (tempClass != null) {
                    //所有已声明的方法,包括 private 级
                    Method method = tempClass.getDeclaredMethod(metName, args);
                    if (method != null) {
                        method.setAccessible(true);
                        return method;
                    }
                    //得到父类,然后赋给自己
                    tempClass = tempClass.getSuperclass();
                }
            }
        } catch (Exception e) {
            throw e;
        }
        throw new Exception("没找到期望方法,方法名:" + metName);
    }

    /**
     * 取实例obj的自身与父的所有字段
     *
     * @param obj 实例
     * @return
     */
    public static List<Field> getSelfAndAllParentAllFild(Object obj) {
        List<Field> fieldList = new ArrayList<>();
        //非空继续
        if (obj != null) {
            Class tempClass = obj.getClass();
            //当父类为null的时候说明到达了最上层的父类(Object类).
            while (tempClass != null) {
                //获取 tempClass 自身4中修饰的 字段, 不包含父类
                fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
                //得到父类,然后赋给自己
                tempClass = tempClass.getSuperclass();
            }
        }
        return fieldList;
    }

    /**
     * 取实例obj的自身所有属性不包含父类
     *
     * @param obj 实例
     * @return
     */
    public static List<Field> getSelfAllFild(Object obj) {
        List<Field> fieldList = new ArrayList<>();
        //非空继续
        if (obj != null) {
            Class tempClass = obj.getClass();
            //当父类为null的时候说明到达了最上层的父类(Object类).
            while (tempClass != null) {
                //获取 tempClass 自身4中修饰的 字段, 不包含父类
                fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
                //得到父类,然后赋给自己
                tempClass = null;
            }
        }
        return fieldList;
    }

    /**
     * 实例目标字段设值
     *
     * @param ins
     * @param fieldName
     * @param val
     * @throws IllegalAccessException
     */
    public static void setFieldValue(Object ins, String fieldName, Object val) throws IllegalAccessException {
        Field field = getFieldInTarget(ins, fieldName);
        //AssertUtil.notNull(field, BizeBizprodErrorCode.PARAMETER_ILLEGAL, "fieldName:"+fieldName+" not exist ");
        field.setAccessible(true);
        field.set(ins, val);

    }


    /**
     * 根据Class对象,反射出对象的json文本
     *
     * @param aclass      类对象
     * @param outPathJson 文本输出路径
     */
    public static void CreationJsonTxt(Class<?> aclass, String outPathJson) {

        try {
            if (!aclass.isInterface() && !aclass.isEnum()) {
                Object instance = aclass.newInstance();
                String contex = JSONObject.toJSONString(instance, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);
                String path = outPathJson + "\\json\\";
                FileUtils.fileWrite(path, aclass.getSimpleName() + ".json", contex);
            }
        } catch (Exception e) {
            log.error("CreationJsonTxt 创建json文本对象时出错!!!", e);
        }

    }

    /**
     * 根据Class对象,反射出对象的json文本
     *
     * @param aclass      类对象
     * @param outPathJson 文本输出路径
     */
    public static String CreationJsonTxt(Class<?> aclass) {
        String contex = StringUtils.EMPTY;
        try {
            if (!aclass.isInterface() && !aclass.isEnum()) {
                Object instance = aclass.newInstance();
                contex = JSONObject.toJSONString(instance, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);

            }
        } catch (Exception e) {
            log.error("CreationJsonTxt 创建json文本对象时出错!!!", e);
        }
        return contex;
    }

    /**
     * 循环遍历指定类的全部属性(不包含父类)
     * 判断是否有属性非空 如果有属性非空 return true
     *
     * @param obj 目标对象实例
     * @return
     */
    public static boolean objectAllFieldContainIsNotEmpty(Object obj) {
        Class<?> aClass = obj.getClass();
        try {
            // 获取当前类所有的属性, 不包含父类
            List<Field> fieldList = getDeclaredFields(aClass);
            for (Field field : fieldList) {
                field.setAccessible(true);
                // 获取当前属性的值
                Object fieldValue = field.get(obj);
                // 校验当前值是否为null
                if (!ObjectUtils.isEmpty(fieldValue)) {
                    return true;
                }
            }
        } catch (Exception e) {
            log.error(String.format("ReflectUtil:getObjectAllFieldValueIsNotNull:busiFlg:system exception: msg:%s stack:", e));
        }
        return false;
    }

    /**
     * 获取 cla 自身的所有字段
     *
     * @param cla
     * @return
     */
    public static List<Field> getDeclaredFields(Class<?> cla) {
        List<Field> fieldList = OptionalUtil.ofBoolean(cla == null, cla, val -> new ArrayList<>(), val -> new ArrayList<>(Arrays.asList(val.getDeclaredFields())));
        fieldList.forEach(field -> field.setAccessible(true));
        return fieldList;
    }

    /**
     * 获取指定对象,指定属性的set方法
     *
     * @param obj
     * @param field
     * @return
     */
    public static Method getObjectSetMethod(Object obj, Field field) {
        Class<?> clazz = obj.getClass();
        String fieldName = field.getName();
        String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        Method method = getMethodInTarget(clazz, methodName);
        return method;
    }

    /**
     * 获取指定对象,指定属性的set方法
     *
     * @param obj
     * @param field
     * @return
     */
    public static Method getObjectSetMethod(Class clazz, Field field) {
        String fieldName = field.getName();
        String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        Method method = getMethodInTarget(clazz, methodName);
        log.info("getObjectSetMethod:结束");
        return method;
    }
}