package com.atguigu.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
	
	@GetMapping("/main.html")
	public String main(){
		
		
		
		return "main";
	}
	
	
	@GetMapping("/login")
	public String index(){
		
		
		
		return "forward:/index.jsp";
	}
	
	
	//去访问拒绝页面
	@GetMapping("/unauth")
	public String unauth(){
		return "unauth";
	}
	
	
	

}
