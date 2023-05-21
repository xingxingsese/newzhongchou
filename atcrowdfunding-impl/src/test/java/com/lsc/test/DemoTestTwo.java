package com.lsc.test;

import com.lsc.bean.TbUser;
import com.lsc.bean.TestBean;
import javafx.application.Application;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Optional;
import java.util.UUID;

/**
 * @Description:
 * @Author: lisc
 * @date: 2022/5/10
 */
public class DemoTestTwo implements BeanPostProcessor {

    @Test
    public void test() {
        /*String substring = UUID.randomUUID().toString().replace("-", "").substring(0, 11);
        System.out.println("substring = " + substring);*/

        Optional<TbUser> opt = Optional.empty();
        System.out.println("empty = " + opt);

        System.out.println("opt.isPresent() = " + opt.isPresent());

        Optional<TbUser> tbUser = opt.of(new TbUser());
        System.out.println("of = " + tbUser);


        Optional<Object> optional = Optional.ofNullable(null);
        System.out.println("ofNullable = " + optional);

        Object orElse = optional.orElse(new TbUser());
        System.out.println("orElse = " + orElse);

    }

    @Test
    public void test01() {
        // TestBean bean = ApplicationContext().getBean("TestBean", TestBean.class);
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-beans.xml");
        TestBean testBean = applicationContext.getBean("TestBean", TestBean.class);
        System.out.println(testBean);
    }

    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        return null;
    }
}
