package com.lsc.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lsc.api.RoleService;
import com.lsc.bean.TRole;
import com.lsc.constant.Constant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Description: 角色管理页的增删改查 ajax版
 * @Author: lisc
 * @date: 2022/3/27
 */
@RestController
public class RoleAjaxCrudController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RoleService roleService;

    @PostMapping("/role/update")
    public String updataRole(TRole tRole){
        roleService.updataRole(tRole);
        return "ok";
    }
    /**
     * 根据id获取
     * @param id
     * @return
     */

    @GetMapping("role/get")
    public TRole getRole(@RequestParam("id") Integer id) {
        TRole tRole = roleService.getRoleById(id);
        return tRole;
    }


    @GetMapping("/role/delete")
    public String deleteBacthRole(@RequestParam("ids") String ids,
                                  HttpSession session, Model model) {
        String condition = (String) session.getAttribute(Constant.QUERY_CONDITION_KEY);
        Integer pn = (Integer) session.getAttribute("pn");
        model.addAttribute(Constant.QUERY_CONDITION_KEY, condition);
        model.addAttribute("pn", pn);
        if (StringUtils.isNotBlank(ids)) {
            String[] split = ids.split(",");
            for (String s : split) {
                int id = Integer.parseInt(s);
                roleService.deleteRole(id);
            }
            return "ok";
        }
        return "fail";
    }

    /**
     * 新增角色
     *
     * @param tRole
     * @return
     */
    @PostMapping("/role/add")
    public String addRole(TRole tRole) {
        roleService.addRole(tRole);
        return "ok";
    }

    /**
     * 返回给前端json数据
     *
     * @return
     */
    @GetMapping("/role/list")
    public PageInfo<TRole> roleList(@RequestParam(value = "pn", defaultValue = "1") Integer pn,
                                    @RequestParam(value = "ps", defaultValue = Constant.DEFAULT_PAGE_SIZE) Integer ps,
                                    @RequestParam(value = "condition", defaultValue = "") String condition) {

        PageHelper.startPage(pn, ps);
        List<TRole> roles = roleService.getAllRoleCondition(condition);

        return new PageInfo<>(roles, 5);
    }


}
