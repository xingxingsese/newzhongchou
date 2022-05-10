package com.lsc.controller;

import com.lsc.api.MenuService;
import com.lsc.bean.TMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description: 菜单相关增删改查
 * @Author: lisc
 * @date: 2022/5/2
 */
@RestController
public class MenuAjaxCrudController {
    private static final Logger log = LoggerFactory.getLogger(MenuAjaxCrudController.class);

    @Autowired
    MenuService menuService;

    @GetMapping("/menu/list")
    public List<TMenu> getAllMenus() {

        return menuService.getAllMenus();
    }

    /**
     * 添加菜单
     *
     * @param tMenu
     * @return
     */
    @PostMapping("/menu/add")
    public String saveMenu(TMenu tMenu) {

        return menuService.saveMenu(tMenu);
    }

    /**
     * 删除菜单
     *
     * @param id
     * @return
     */
    @GetMapping("/menu/delete")
    public String deleteMenu(Integer id) {
        return menuService.deleteMenu(id);
    }

    /**
     * 修改菜单
     *
     * @param tMenu
     * @return
     */
    @PostMapping("/menu/update")
    public String updateMenu(TMenu tMenu) {
        return menuService.updateMenu(tMenu);
    }

    /**
     * 根据id查菜单
     */
    @GetMapping("menu/get")
    public TMenu getMenu(Integer id) {
        return menuService.getMenu(id);
    }
}
