package com.atguigu.security.config;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.JdbcUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.atguigu.security.bean.Hello;
import com.atguigu.security.componet.AppPasswordEncoder;
import com.atguigu.security.componet.AppUserDetailsService;

/**
 * SpringSecurity如何使用
 * 1）、导包
 * 2）、写SpringSecurity的配置类，配置http规则，配置AuthenticationManagerBuilder规则，调用自定义的UserDetailService组件进行用户信息查询
 * 3）、开启基于注解的权限控制功能
 * 4）、确定每一个方法都是什么权限才能访问，标注相应的权限
 * 
 * 效果：
 * 	数据库里面只要用户分配了权限，登录的时候SpringSecurity会查出所有的角色权限，在执行方法的时候，会按照权限控制表达式进行匹配，匹配成功执行，否则access denied；
 *  	权限标识是系统规定全局统一的；
 */


//===================================================
/**
 * 1、要成为配置类，来代替配置文件的功能；
 * 		@Configuration == XML：这个类是一个配置类，相当于以前的配置文件；包扫描的时候扫描上这个配置类才能生效；
 * 2、配置类如何替代以前的配置文件工作；
 * 
 * 
 * @EnableWebMvc：开启springmvc高级设置功能 <mvc:annotation-driven></mvc:annotation-driven>
 * @EnableAspectJAutoProxy：开启切面功能
  *    		未来会见到各种@EnableXXX的注解，开启某种功能
 * @author lfy
 *
 *
 *	1、导包  2、配置了Filter  3、写了配置类开启了Security功能
 *
 * 效果：
 * 	1）、访问任何东西需要登录
 *  2）、登录页都生成了
 *  3）、还加入了_csrf（跨站请求伪造）功能
 *  4）、如果要登录会跳转到 action='/spring-security-test/login' method='POST
 *  5）、登录错误还会回到登录页；spring-security-test/login?error  method="GET"
 *  
 *  
 *  开启基于注解的细粒度权限控制
 */
@EnableWebSecurity //开启webSecurity功能
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class AppSecurityConfig  extends WebSecurityConfigurerAdapter{
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	AppUserDetailsService appUserDetailsService;
	
	@Autowired
	AppPasswordEncoder passwordEncoder;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		//super.configure(auth); //禁用默认的认证规则
		//Security必须给每个用户都有权限;
		//authorities()，指定权限 
		//roles
		//1、添加测试的登录用户
//		auth.inMemoryAuthentication()
//			.withUser("leifengyang").password("123456").authorities("user:add","user:delete").roles("宗师")
//			.and()
//			.withUser("zhangsan").password("123456").roles("大师","宗师");
		//页面登录就会来到SpringSecurity进行认证；
		//登录请求的处理不用写；SpringSecurity会自动来做；将登录发给SprinSecurity规定的登录请求就行；
		//authorities权限、roles角色
		
		//=======================以上是写死的内存中的账号密码==========================
		
		
		//默认规则：底层是对UserDetailService的定制
//		JdbcUserDetailsManagerConfigurer<AuthenticationManagerBuilder> jdbcAuthentication = auth.jdbcAuthentication();//基于数据库的认证方式；
//		
//		//角色其实就是对权限的一种分组啊。
//		jdbcAuthentication.usersByUsernameQuery("select ");//改掉默认使用用户名查询用户的sql
//		jdbcAuthentication.groupAuthoritiesByUsername("");//改掉默认使用用户名查询所在分组权限Authorities的sql
//		jdbcAuthentication.authoritiesByUsernameQuery("");//改掉默认使用用户名查询自己权限Authorities的sql
		
		//自定义UserDetailService；
		
		//我们的appUserDetailsService查出的用户密码是MD5加密的结果
		//org.springframework.security.crypto.password
		auth.userDetailsService(appUserDetailsService)
			.passwordEncoder(passwordEncoder);
		
		
		
		
		
		
		
		
	}
	
	//1、配置http的安全规则
	//2、配置WebSecurity的规则
	//3、配置 AuthenticationManagerBuilder ，认证规则（如何登录....）
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		//super.configure(http);
		/**
		 * http
			.authorizeRequests()
				.anyRequest().authenticated()
				.and()
			.formLogin().and()
			.httpBasic();
		 */
		/**
		 * 	角色和权限的区别：
		 * 	SpringSecurity在访问控制的时候，区别了【Role】和【Authority】，
		 *    	但是UserDetails只有Collection<GrantedAuthority> getAuthorities();
		 *  
		 *  hasRole("宗师")：自动给角色的名插入一个前缀叫  ROLE_； ROLE_宗师
		 *  hasAuthority("宗师")：不会给权限自动插入任何前缀 ； 宗师;
		 *  hasRole("宗师") == hasAuthority("ROLE_宗师");
		 *  
		 *  SpringSecurity在底层将所有的角色和权限全部放在一个集合里面Collection<GrantedAuthority> getAuthorities【权限集合】
		 *  	区别就是：集合里面如果你是角色；就应该以ROLE_开头，如果是权限直接写不用管；
		 *  	
		 *  
		 */
		
		//1、放行静态资源  资源的控制是前面声明的会覆盖后面声明的逻辑；
		//2、基于角色的访问控制
//		http.authorizeRequests()
//			.antMatchers("/layui/**").permitAll()//所有人都能访问静态资源
//			.antMatchers("/index.jsp").permitAll()
//			.antMatchers("/level1/**").hasRole("学徒")//level1/**所有请求必须有用学徒角色的人才可以访问
//			.antMatchers("/level2/**").hasAnyRole("大师","宗师")///level2/**所有请求，只要拥有"大师"或者"宗师"都可以访问
//			.antMatchers("/level3/**").hasRole("PM - 项目经理")
//			.antMatchers("/level3/**").hasAuthority("user:add")
//			.anyRequest().authenticated();//除了以上说的，剩下请求都得认证通过【登录成功】才行。//这句话一定放在最后。剩下的没映射的人怎么办
		//Specify that URLs are allowed by users who have authenticated and were not"remembered".
		//level1/1：访问的时候会出现 HTTP Status 403 – Forbidden : 访问拒绝；包括不存在的都是无权限
		//layui/layui.js：正常访问
		//index.jsp：正常访问；
		http.authorizeRequests()
		.antMatchers("/layui/**").permitAll()
		.antMatchers("/index.jsp").permitAll()
		//.antMatchers("/**").access("hasPermission(#id, '2') and hasRole('hello')")
		.anyRequest().authenticated();
		/**
		 * 细粒度权限的几种规则；
		 * 
		 * .antMatchers("/level3/**")进行以下规则控制
		 * 
		 * 1）、access()：定制访问规则
		 * 2）、anonymous()：匿名用户可以访问
		 * 3）、authenticated()：必须认证通过才行（登录了才可以）
		 * 4）、denyAll()：所有人都不能访问
		 * 5）、fullyAuthenticated()：完全认证通过才能访问【不能是remember-me（免登陆）进来的】
		 * 6）、hasAnyAuthority("1","2","3")：在我列举的1,2,3权限中至少拥有某一个权限才行
		 * 7）、hasAnyRole("管理员","程序员")：在我列举的"管理员","程序员"角色中至少拥有某一个角色才行
		 * 8）、hasAuthority("1")：必须有拥有1权限才能访问
		 * 9）、hasRole("管理员")：必须拥有这个角色才行
		 * 10）、hasIpAddress()：必须是我指定的ip才能用 ；192.168.0/24（允许具体ip和子网域）
		 * 11）、not().以前的hasXX，authenticated()：对以前做一个反向操作；
		 * 		.antMatchers("/layui/**").not().hasRole("管理员")：要访问layui/**的请求必须不是管理员
		 * 12）、permitAll()：允许所有人访问
		 * 13）、rememberMe()：允许通过remember-me登录进来的访问
		 * 
		 * 
		 */
		
		
		//2、如果没有权限访问，去登录页，让他登录；会来到一个默认登录页
		// GET login：去登录页的地址
		// POST login：真正的表单登录的提交地址；
		// GET login?err：登录出错来的地址
		// GET login?logout： 退出系统的地址
		
		//post index.jsp 来不到SpringSecurity；*.jsp都是tomcat的Servlet处理的；
		
		//访问一个不存在，去自定义登录页，/login的GET方式，/login没有说任何人都能访问，可能导致循环重定向，所以我们需要permitAll();
		//上次直接访问登录页，登录成功会去defaultSuccessUrl指定的地方，如果上次访问的是一个未授权的页面，认证成功会自动来到上次的页面
		http.formLogin().loginPage("/login").defaultSuccessUrl("/main.html").permitAll();
		//我们指定了登录页的地址；
		/**
		 * If "/authenticate" was passed to this method it update the defaults as shown below:
		 * 		•/authenticate GET - the login form（登录页）
				•/authenticate POST - process the credentials and if valid authenticate the user
						（ 处理登录） 
				•/authenticate?error GET - redirect here for failed authentication attempts
						（登录出错默认来到  GET指定的url?error表示登录出来）
				•/authenticate?logout GET - redirect here after successfully logging out
						（退出默认来到  GET指定的url??error表示登录出来）;

		 */
		
		
		//3、禁用csrf
		http.csrf().disable();
		
		//The default is that accessing the URL"/logout 默认的退出url地址
		//4、定制退出功能
//		http.logout().logoutUrl("/gogogo").logoutSuccessHandler(new LogoutSuccessHandler() {
//			
//			@Override
//			public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
//					throws IOException, ServletException {
//				// TODO Auto-generated method stub
//				//数据库
//				request.getRequestDispatcher("/index.jsp").forward(request, response);
//			}
//		});//退出成功后去哪里
		http.logout().logoutUrl("/gogogo").logoutSuccessUrl("/index.jsp");
		
		
		/**
		 * 默认规则
		 * 
		 *          退出：发送 /logout
		 *   退出成功：默认来 /login?logout
		 *   去登陆页发送： /login
		 *   登录成功：默认  /main.html
		 *   登录失败默认： /login?error
		 * 
		 */
		
		
		//定制全局的异常处理
		//访问拒绝默认效果：出现403页面  /errors/401
		//http.exceptionHandling().accessDeniedPage("/unauth");
		http.exceptionHandling().accessDeniedHandler(new AccessDeniedHandler() {
			
			@Override
			public void handle(HttpServletRequest request, HttpServletResponse response,
					AccessDeniedException accessDeniedException) throws IOException, ServletException {
				//  记录日志，那个请求拒绝了
				System.out.println("访问拒绝了...."+accessDeniedException.getMessage());
				request.setAttribute("msg", accessDeniedException.getMessage());
				request.getRequestDispatcher("/unauth").forward(request, response);
			}
		});
		
		/**
		 *   实现记住我功能：
		 *   if the HTTP parameter named "remember-me"exists;
		 *   	登录的时候只要参数的位置有一个叫remember-me的存在就可以记住我了。
		 *  实现：
		 *  bGVpZmVuZ3lhbmc6MTU2MTM1OTUyNjEwOTozMDBkZDgwYjhiNGRiODhlY2MzNDkxNGY4MGFhZGZjOA
		 *  1）、给我们的登录表单上放一个隐藏的input框，name叫remember-me即可
		 *  <input type="checkbox" name="remember-me" title="记住密码">
		 *  
		 *  效果：
		 *  1）、SpringSecurity会让浏览器保存一个Cookie，cookie的名字叫remember-me；值是一串加密字符串；
		 *  2）、我们的rememeber-me，可以不登录就访问，但是一旦授权页面，又得去登录；
		 *  
		 *  SpringSecurity：现在是基于内存的remeber-me记不住真正的用户信息。基于数据库；
		 *  	默认效果：
		 *  		1）、开启rememeber-me就可以免登录，自己授权了的页面，可以直接访问，不能访问的页面，SpringSecurity会让我们重新登录
		 *  		2）、注意：服务器关闭再打开，由于之前保存的cookie的值之类的对应关系没有了，所以，查不到这个cookie对应的用户，提示重新登录
		 *  	加强效果：【想要优化2，只需要将cookie的值对应的关系之类存在数据库即可】
		 *  		1）、将登录状态保存在数据库即可；
		 *  
		 *  	
		 *  实现原理： Map("bGVpZmVuZ3lhbmc6MTU2MTM1OTUyNjEwOTozMDBkZDgwYjhiNGRiODhlY2MzNDkxNGY4MGFhZGZjOA","dsadaa")
		 *  
		 *  
		 *  规则：
		 *  	Cookie的名：remember-me
		 *  	表单提交的参数名：remember-me
		 *  改规则：
		 *  	rememberMeCookieName：指定cookie用的名 （不需要做什么，服务器会让浏览器保存这个名的cookie）
		 *  	rememberMeParameter:指定参数用的名 （需要改页面<input type="checkbox" name="haha"）
		 *  以前的tokenRepository：是使用 InMemoryTokenRepositoryImpl，可以切换为JdbcTokenRepositoryImpl
		 */
		JdbcTokenRepositoryImpl jdbcTokenRepositoryImpl = new JdbcTokenRepositoryImpl();
		jdbcTokenRepositoryImpl.setJdbcTemplate(jdbcTemplate);
		//jdbcTokenRepositoryImpl.setCreateTableOnStartup(createTableOnStartup);
		
		/**
		 * SpringSecurity：操作数据库保存记住我令牌的默认规则如下
		 * 1）、创建令牌： insert into persistent_logins (username, series, token, last_used) values(?,?,?,?)
		 * 2）、更新令牌：update persistent_logins set token = ?, last_used = ? where series = ?
		 * 3）、获取令牌：select username,series,token,last_used from persistent_logins where series = ?
		 * 4）、删除令牌：delete from persistent_logins where username = ?
		 * 表自己创建出来；
		 * 
		 * 我们要做什么？
		 * 	1）、创建出JdbcTokenRepositoryImpl对象，注入jdbcTemplate，让他能操作数据库即可
		 *  2）、按照security的规则创建出表
		 * 	PersistentTokenRepository
		 * 
		 * 1）、可以SpringSecurity的默认功能
		 * 2）、默认功能的细节可以订单；
		 * 3）、完全定制。实现接口即可；
		 * 		如：AccessDeniedHandler、PersistentTokenRepository、xxxx
		 * 			 
		 */
		http.rememberMe()
			.rememberMeCookieName("login-user")
			.rememberMeParameter("haha")
			.tokenRepository(jdbcTokenRepositoryImpl);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 	bean默认的组件是单实例
	 * 	<bean id="haha" class="com.atguigu.security.bean.Hello">
			<property name="name" value="666"></property>
	 *	</bean>
	 */
	
	//
	 //给容器中加入一个组件和bean标签是一样的；，
	//组件的类型(bean class)是方法的返回值类型，组件的id默认是方法名，组件这个真正的对象是自己创建的；
	//scope="prototype"
//	@Bean("hello")
//	public Hello hellooadsasda() {
//		System.out.println("getHello运行创建了一个hello....");
//		Hello hello = new Hello();
//		hello.setName("123456");
//		return hello;
//	}
}
