package com.lsc.test;

import com.alibaba.fastjson.JSON;
import com.lsc.api.AdminService;
import com.lsc.bean.TAdmin;
import com.lsc.bean.TAdminRole;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: lisc
 * @date: 2021/12/28
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-beans.xml", "classpath*:spring-mybatis.xml"})
public class DemoTest {

    @Autowired
    AdminService adminService;

    @Test
    public void test() {
        boolean result1 = true;
        System.out.println(Boolean.TRUE.equals(result1));
        System.out.println("Boolean.TRUE = " + Boolean.TRUE);
        System.out.println("result1 = " + result1);
    }

    public TAdmin buildTadmin(TAdmin tAdmin){
        tAdmin.setLoginacct("loginacct");
        tAdmin.setId(1);
        return tAdmin;
    }


    @Test
    public void getClassSetAndGetMethodTest() {

        TAdmin tAdmin = new TAdmin();
        Class<? extends TAdmin> aClass = tAdmin.getClass();
        String name = aClass.getName();
        System.out.println("name = " + name);
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            PropertyDescriptor pd = null;
            try {
                pd = new PropertyDescriptor(declaredField.getName(), aClass);
            } catch (IntrospectionException e) {
                e.printStackTrace();
            }
            Method writeMethod = pd.getWriteMethod();
            Method readMethod = pd.getReadMethod();

            System.out.println("writeMethod = " + writeMethod);
            System.out.println("writeMethod.getName() = " + writeMethod.getName());

            System.out.println("readMethod = " + readMethod);
            System.out.println("readMethod.getName() = " + readMethod.getName());

        }
        System.out.println("------------------------------");
        System.out.println("获取父类属性");


        // 获取父类
        Class<?> superclass = aClass.getSuperclass();
        if (superclass.getName().equals("java.lang.Object")) {
            return;
        }
        // 获取父类所有属性
        Field[] superFields = superclass.getDeclaredFields();
        for (Field superField : superFields) {
            superField.setAccessible(true);
            PropertyDescriptor pd = null;
            try {
                pd = new PropertyDescriptor(superField.getName(), superclass);
            } catch (IntrospectionException e) {
                e.printStackTrace();
            }
            Method writeMethod = pd.getWriteMethod();
            Method readMethod = pd.getReadMethod();

            System.out.println("***writeMethod = " + writeMethod);
            System.out.println("***writeMethod.getName() = " + writeMethod.getName());

            System.out.println("***readMethod = " + readMethod);
            System.out.println("***readMethod.getName() = " + readMethod.getName());
        }


    }

    public TAdminRole build(TAdminRole role) {
        TAdmin tAdmin = new TAdmin();
        if (role.getClass().equals(tAdmin.getClass())) {
            System.out.println(role.getClass() + " :" + true);
        } else {

            System.out.println("tAdmin.getClass() = " + tAdmin.getClass());
            System.out.println("role.getClass() = " + role.getClass());
        }
        return null;
    }

    /**
     * 浅拷贝
     *
     * @param k
     * @param sclass
     * @param <K>
     * @param <V>
     * @return
     */
    public <K, V> V buildKV(K k, Class<V> sclass) {
        System.out.println("k.getClass() = " + k.getClass());
        System.out.println("v.getClass() = " + sclass);
        V interfaces = null;
        try {
            interfaces = sclass.newInstance();
            BeanUtils.copyProperties(k, interfaces);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return interfaces;
    }


}

