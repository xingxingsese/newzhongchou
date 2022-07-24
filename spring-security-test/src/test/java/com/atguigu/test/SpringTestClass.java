package com.atguigu.test;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.atguigu.security.bean.Hello;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring.xml")
public class SpringTestClass {
	
	
	@Autowired
	ApplicationContext ioc;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Test
	public void test01() {
//		Hello bean1 = ioc.getBean(Hello.class);
//		Hello bean2 = ioc.getBean(Hello.class);
//		Hello bean3 = ioc.getBean(Hello.class);
//		System.out.println(bean1==bean2);
//		System.out.println(bean3==bean2);
		
//		String[] strings = ioc.getBeanNamesForType(Hello.class);
//		System.out.println(Arrays.asList(strings));
		
		Long count = jdbcTemplate.queryForObject("select count(*) from `t_admin`", Long.class);
		System.out.println("总记录是："+count);
	}

}
