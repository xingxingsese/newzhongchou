package com.lsc.controller.permission;

import com.alibaba.druid.support.json.JSONUtils;
import com.lsc.api.AdminService;
import com.lsc.api.RoleService;
import com.lsc.bean.TAdmin;
import com.lsc.bean.TRole;
import com.lsc.common.ExceptionUtils.LscException;
import com.lsc.constant.Constant;
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
import java.util.List;

/**
 * @Description:
 * @Author: lisc
 * @date: 2022/1/23
 */
@Controller
public class AdminCrudController {
    private static final Logger log = LoggerFactory.getLogger(AdminCrudController.class);

    /**
     * adminService注释
     */
    @Autowired
    AdminService adminService;

    /**
     *  角色service
     */
    @Autowired
    RoleService roleService;

    @GetMapping("/user/unAssign/role")
    public String unAssignUserRole(@RequestParam("uid")Integer uid,@RequestParam("rids")String rids){
        roleService.unAssignUserRole(uid,rids);
        return "redirect:/user/assignRole.html?id=" + uid;
    }

    /**
     * 给用户分配指定的角色
     * @param uid
     * @param rids
     * @return
     */
    @GetMapping("/user/assign/role")
    public String assignUserRole(@RequestParam("uid")Integer uid,@RequestParam("rids")String rids){
        roleService.assignUserRole(uid,rids);
        return "redirect:/user/assignRole.html?id=" + uid;
    }
    /**
     * 角色分配页
     * @return
     */
    @GetMapping("/user/assignRole.html")
    public String toAssignRole(@RequestParam("id") Integer id,Model model){
        // 查出客户已有的角色
        List<TRole> roles =  roleService.getUserHasRoles(id);

        // 查出客户未有的角色
        List<TRole> unRoles = roleService.getUserUnRoles(id);

        // 存入model中, 页面使用
        model.addAttribute("roles",roles);
        model.addAttribute("unRoles",unRoles);

        return "permission/user-role";
    }



    @GetMapping("/user/batch/delete")
    public String deleteBacthAdmin(@RequestParam("ids") String ids,
                                   HttpSession session, Model model) {
        String condition = (String) session.getAttribute(Constant.QUERY_CONDITION_KEY);
        Integer pn = (Integer) session.getAttribute("pn");
        model.addAttribute(Constant.QUERY_CONDITION_KEY, condition);
        model.addAttribute("pn", pn);
        if (StringUtils.isNotBlank(ids)) {
            String[] split = ids.split(",");
            for (String s : split) {
                int id = Integer.parseInt(s);
                adminService.deleteAdmin(id);
            }
        }
        return "redirect:/admin/index.html";
    }
    /**
     * 批量删除
     * @param ids
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

    @PostMapping("user/add")
    public String addAdmin(TAdmin tAdmin, Model model) {
        log.info("将要添加的用户为:{}", tAdmin);
        try {
            adminService.savaAdmin(tAdmin);
        } catch (LscException e) {
            model.addAttribute(Constant.PAGE_MSG, e.getMsg());
            log.error("{}添加出错", tAdmin, e);
            return "redirect:/permission/user-add";
        }

        return "redirect:/admin/index.html?pn=" + Integer.MAX_VALUE;
    }


    @GetMapping("user/add.html")
    public String addPage(TAdmin admin) {
        // 来到用户添加页
        return "permission/user-add";
    }

    @GetMapping("/user/edit.html")
    public String toEdiPage(@RequestParam("id") Integer id,
                            Model model) {
        TAdmin admin = adminService.selectById(id);
        model.addAttribute("data", JSONUtils.toJSONString(admin));
        return "permission/user-edit";
    }

    /**
     * 用户修改请求
     *
     * @return
     */
    @PostMapping("/user/update")
    public String adminUpdate(TAdmin admin, HttpSession session, Model model) {
        log.info("接收到:{},修改用户信息", admin);
        adminService.updateAdmin(admin);
        Integer condition = (Integer) session.getAttribute(Constant.QUERY_CONDITION_KEY);
        String pn = (String) session.getAttribute("pn");

        model.addAttribute("pn", pn);
        model.addAttribute(Constant.QUERY_CONDITION_KEY, condition);
        return "redirect:/admin/index.html";
    }
}
