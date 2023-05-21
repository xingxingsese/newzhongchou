package com.lsc.controller.permission;

import com.lsc.api.PermissonService;
import com.lsc.bean.TPermission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description: 权限相关增删改查
 * @Author: lisc
 * @date: 2022/5/2
 */
@RestController
public class PermissonAjaxCrudController {
    private static final Logger log = LoggerFactory.getLogger(PermissonAjaxCrudController.class);

    @Autowired
    PermissonService permissonService;

    @GetMapping("permission/role/get")
    public List<TPermission> getRolePermission(@RequestParam("userId") Integer userId) {
        List<TPermission> list = permissonService.getRolePermission(userId);
        return list;
    }

    /**
     * 给角色分配权限
     *
     * @param rid
     * @param permissionIds
     * @return
     */
    @PostMapping("/permission/role/assign")
    public String roleAssignPermission(@RequestParam("rid") Integer rid,
                                       @RequestParam("permissionIds") String permissionIds) {

        permissonService.assignPermissionForRole(rid, permissionIds);
        return "ok";
    }

    /**
     * 查询所有权限
     *
     * @return
     */
    @GetMapping("/permisson/list")
    public List<TPermission> getAllPermissons() {

        return permissonService.getAllPermissons();
    }

    /**
     * 添加权限
     *
     * @param tPermisson
     * @return
     */
    @PostMapping("/permisson/add")
    public String savePermisson(TPermission tPermisson) {

        return permissonService.savePermisson(tPermisson);
    }

    /**
     * 删除权限
     *
     * @param id
     * @return
     */
    @GetMapping("/permisson/delete")
    public String deletePermisson(Integer id) {
        return permissonService.deletePermisson(id);
    }

    /**
     * 修改权限
     *
     * @param tPermisson
     * @return
     */
    @PostMapping("/permisson/update")
    public String updatePermisson(TPermission tPermisson) {
        return permissonService.updatePermisson(tPermisson);
    }

    /**
     * 根据id查权限
     */
    @GetMapping("permisson/get")
    public TPermission getPermisson(Integer id) {
        return permissonService.getPermisson(id);
    }
}
