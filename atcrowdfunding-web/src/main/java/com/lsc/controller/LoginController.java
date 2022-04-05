package com.lsc.controller;

import com.lsc.bean.TMenu;
import com.lsc.com.lsc.utils.AppUtils;
import com.lsc.constant.Constant;
import com.lsc.api.AdminService;
import com.lsc.bean.TAdmin;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

/**
 * @Description:
 * @Param:
 * @Author: lisc
 * @date:
 */
@Controller
public class LoginController {
    Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    AdminService adminService;

    /**
     * 批量删除
     * @param id
     * @param session
     * @param model
     * @return
     */
    @GetMapping("/user/batch/delete")
    public String deleteBatchAdmin(@RequestParam("ids") String ids,HttpSession session,Model model){
        model.addAttribute(Constant.QUERY_CONDITION_KEY, session.getAttribute(Constant.QUERY_CONDITION_KEY));
        model.addAttribute("pn", session.getAttribute("pn"));
        if (StringUtils.isNotBlank(ids)){
            String[] split = ids.split(",");
            try {
                for (String id : split) {
                    adminService.deleteAdmin(Integer.parseInt(id));
                }
            } catch (NumberFormatException e) {
                log.error("删除用户：{} 时出现异常",ids,e);
            }
        }

        return "redirect:admin/index.html";
    }
    /**
     * 单个删除
     * @param id
     * @param session
     * @param model
     * @return
     */
    @GetMapping("/user/delete")
    public String deleteAdmin(@RequestParam("id") Integer id,HttpSession session,Model model){


        model.addAttribute(Constant.QUERY_CONDITION_KEY, session.getAttribute(Constant.QUERY_CONDITION_KEY));
        model.addAttribute("pn", session.getAttribute("pn"));
        adminService.deleteAdmin(id);
        return "redirect:admin/index.html";
    }

    @PostMapping("/doLogin")
    public String login(@RequestParam("username") String userName, @RequestParam("password")String passWord,
                        HttpSession httpSession, Model model){
        log.debug("userName: {}, passWord:{}",userName,passWord);
        TAdmin tAdmin = adminService.selectLogInfo(userName, AppUtils.getDigestPwd(passWord));
        if (tAdmin == null){
            model.addAttribute(Constant.PAGE_MSG,"帐号或密码错误,请重新输入");
            model.addAttribute("username",userName);
            log.debug("用户:{} 密码错误 {}",userName,passWord);
            return "forward:/login.jsp";
        }
        httpSession.setAttribute(Constant.LOGIN_USER_SESSION_KEY,tAdmin);

        return "redirect:/main.html";
    }

    @GetMapping("/main.html")
    public String manPage(HttpSession httpSession,Model model){
        Object sessionAttribute = httpSession.getAttribute(Constant.LOGIN_USER_SESSION_KEY);
        if (sessionAttribute == null){
            model.addAttribute(Constant.PAGE_MSG,"请先登录");
            return "forward:/login.jsp";
        }
        List<TMenu> menuList  = adminService.selectListMenus();
        httpSession.setAttribute(Constant.MENU_SESSION_KEY,menuList);

        return "main";
    }
}
