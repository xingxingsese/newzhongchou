package com.atguigu.security.controller;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.atguigu.security.bean.TAdmin;

@Controller
public class GongfuController {
    /**
     * 权限表达式：
     *
     * @return
     */
    //  level1/1
    @PreAuthorize("hasAuthority('user:add')")
    @GetMapping("/level1/1")
    public String leve1Pdasdasjklage() {
        return "/level1/1";
    }

    //hasRole('项目经理')  ==  hasAuthority('ROLE_项目经理')

    //@PostAuthorize("hasAuthority('user:update')")
    @PreAuthorize("hasPermission(#id, '2')")
    @GetMapping("/level1/2")
    public String leve1dsakjldjakl2Page(Integer id) {
        System.out.println("方法leve12Page执行。。。");
        return "/level1/2";
    }

    //https://docs.spring.io/spring-security/site/docs/4.2.12.RELEASE/reference/htmlsingle/#overview
    //只能是当前用户修改自己的信息；
    //@PostAuthorize("#admin.username == authentication.name")
    @PreAuthorize("hasRole('PM - 项目经理') and hasAuthority('user:delete')")
    @GetMapping("/level1/3")
    public String leve13Page(TAdmin admin) {
        System.out.println("方法leve13Page执行。。。");
        return "/level1/3";
    }

    //  level1/1
    @GetMapping("/level2/1")
    public String leve121Page() {
        return "/level2/1";
    }

    @GetMapping("/level2/2")
    public String level22Page() {
        return "/level1/2";
    }

    @GetMapping("/level2/3")
    public String leve123Page() {
        return "/level2/3";
    }

    //  level1/1
    @GetMapping("/level3/1")
    public String leve131Page() {
        return "/level3/1";
    }

    @GetMapping("/level3/2")
    public String leve132Page() {
        return "/level3/2";
    }

    @GetMapping("/level3/3")
    public String leve133Page() {
        return "/level1/3";
    }


}
