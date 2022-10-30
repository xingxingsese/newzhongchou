package com.lsc.test.demo;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author lisc
 * @Description: com.lsc.freemarker.demo
 * @date 2022/10/27 18:01
 */
public class TestAop {
    private ClassPathXmlApplicationContext ac;

    @Before
    public void before() {
        ac = new ClassPathXmlApplicationContext("result/applicationContext.xml");
    }

    @Test
    public void test() {
        try {
            UserService userService = (UserService) ac.getBean("userService");
            userService.insertUser();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
