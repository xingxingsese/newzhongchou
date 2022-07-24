package com.atguigu.security.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.alibaba.druid.pool.DruidDataSource;
import com.mysql.jdbc.Driver;

@Configuration
public class AppDataSourceConfig {
	
	/**'
	 * <bean class="org.springframework.jdbc.core.JdbcTemplate">
		<property name="dataSource" ref=""></property>
	</bean>
	 * @return
	 */
	//方法参数位置的值会从ioc中获取(DataSource dataSource)
	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		return jdbcTemplate;
	}
	
	
	//配置数据库连接池
	@Bean
	public DataSource druid() {
		DruidDataSource source = new DruidDataSource();
		source.setUrl("jdbc:mysql://192.168.128.130:3306/security");
		source.setUsername("root");
		source.setPassword("123456");
		source.setDriverClassName("com.mysql.jdbc.Driver");
		return source;
	}

}
