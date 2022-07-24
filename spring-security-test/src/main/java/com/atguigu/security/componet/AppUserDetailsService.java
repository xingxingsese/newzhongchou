package com.atguigu.security.componet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.atguigu.security.bean.AdminUserDetails;
import com.atguigu.security.bean.TAdmin;

/**
 * 1、可以自定义用户服务组件，来告诉SpringSecurity怎么去数据库找这个用户及他的详细信息
 * @author lfy
 *
 */
@Component
public class AppUserDetailsService implements UserDetailsService {

	@Autowired
	JdbcTemplate jdbcTemplate;
	/**
	 * 页面提交登录请求的时候，SpringSecurity会获取到页面的提交的用户名，
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		System.out.println("AppUserDetailsService被调用...."+username);
		//1、按照用户名去数据库查出用户信息
		TAdmin admin = jdbcTemplate.queryForObject("select * from `t_admin` where loginacct=?",
				new BeanPropertyRowMapper<TAdmin>(TAdmin.class), 
				username);
		
		//2、将数据库查询出来的信息封装成SpringSecurity指定的类型的对象
		AdminUserDetails userDetails  = new AdminUserDetails();
		userDetails.setUsername(admin.getLoginacct());
		userDetails.setPassword(admin.getUserpswd());
		
		/**
		 *  * @return <code>true</code> if the user's account is valid (ie non-expired),
			 * <code>false</code> if no longer valid (ie expired)
		 */
		userDetails.setAccountNonExpired(true);
		//<code>true</code> if the user is not locked, <code>false</code> otherwise
		userDetails.setAccountNonLocked(true);
		//@return <code>true</code> if the user is enabled, <code>false</code> otherwise
		userDetails.setEnabled(true);
		/**
		 * @return <code>true</code> if the user's credentials are valid (ie non-expired),
		 * <code>false</code> if no longer valid (ie expired)
		 */
		userDetails.setCredentialsNonExpired(true);
		
		
		//去数据库当前用户的所有权限集合
		//
		List<GrantedAuthority> list = AuthorityUtils.createAuthorityList();
		//1、查出当前用户的所有角色：      【1  PM - 项目经理 】           【 2  SE - 软件工程师  】
		List<String> roleFromDb = Arrays.asList("PM - 项目经理","软件工程师");
		List<String> permissioFromDb = Arrays.asList("user:add","user:delete","user:update");
		//2、查出当前用户这些角色对应的所有权限； 【user:add，user:delete， user:update】
		//3、系统里面既想要用角色判断，还想用权限判断。我们就应该给集合里面把所有的角色和权限都放进去
		List<String> myRoles = new ArrayList<String>();
		//自己处理一下角色，给每一个加上前缀即可
		for (String string : roleFromDb) {
			// 哈哈 
			string = "ROLE_"+string;
			myRoles.add(string);
			list.add(new SimpleGrantedAuthority(string));
		}
		
		for (String string : permissioFromDb) {
			list.add(new SimpleGrantedAuthority(string));
		}
		
		userDetails.setAuthorities(list);
		
		
		
		return userDetails;
	}

}
