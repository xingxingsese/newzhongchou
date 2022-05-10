package com.lsc.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.lsc.api.CodeTemplateService;
import com.lsc.bean.TCodeTemplate;
import com.lsc.constant.Constant;
import com.lsc.common.ExceptionUtils.LscException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * @Description: CodeTemplate
 * @Author: lisc
 * @date: 2022-5-5 17:30:31
 */
@Slf4j
public class CodeTemplateCrudController {


    @Autowired
    CodeTemplateService codeTemplateService;

    @GetMapping("/user/batch/delete")
    public String deleteBacthAdmin(@RequestParam("ids")String ids,HttpSession session,Model model){

        codeTemplateService.deleteAdmin(id);

        return "redirect:/admin/index.html";
    }

    @PostMapping("user/add")
    public String addAdmin(TCodeTemplate tCodeTemplate, Model model) {
        log.info("将要添加的用户为:{}", tCodeTemplate);
        try {
        codeTemplateService.savaAdmin(tCodeTemplate);
        } catch (LscException e) {
            model.addAttribute(Constant.PAGE_MSG, e.getMsg());
            log.error("{}添加出错", tCodeTemplate, e);
            return "redirect:/permission/user-add";
        }

        return "redirect:/admin/index.html?pn=" + Integer.MAX_VALUE;
    }


    @GetMapping("user/add.html")
    public String addPage(TCodeTemplate admin) {
        // 来到用户添加页
        return "permission/user-add";
    }

    @GetMapping("/user/edit.html")
    public String toEdiPage(@RequestParam("id") Integer id,Model model) {
        TCodeTemplate admin = codeTemplateService.selectById(id);

        return "permission/user-edit";
    }

    /**
     * 用户修改请求
     *
     * @return
     */
    @PostMapping("/user/update")
    public String adminUpdate(TCodeTemplate admin, HttpSession session, Model model) {
        log.info("接收到:{},修改用户信息", admin);
        codeTemplateService.updateAdmin(admin);

        return "redirect:/admin/index.html";
    }
}
