package com.lsc.controller;

import com.lsc.api.PermissonService;
import com.lsc.bean.TPermission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
