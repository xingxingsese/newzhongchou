package com.lsc.test;

import com.lsc.api.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.SimpleFormatter;

/**
 * @Description:
 * @Author: lisc
 * @date: 2021/12/28
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-beans.xml","classpath*:spring-mybatis.xml"})
public class DemoTest {

    @Autowired
    AdminService adminService;
    @Test
    public void test(){
        System.out.println(adminService);
    }


}

