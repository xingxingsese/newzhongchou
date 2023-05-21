package com.lsc.controller.permission;

import com.lsc.api.MenuService;
import com.lsc.api.PermissonService;
import com.lsc.bean.TMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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


    @Autowired
    PermissonService permissionService;


    // 获取当前id对应的所有菜单
    @GetMapping("/menu/permisson/get")
    public List<TMenu> getPermissionMenus(@RequestParam("pid") Integer permissionId) {

        List<TMenu> menus = permissionService.getMenusByPermissionId(permissionId);
        return menus;
    }

    /**
     * 为指定权限,分配它可以操作的菜单
     *
     * @param permissionId 权限id
     * @param menuIds      菜单的id集合用,号分割
     * @return
     */
    @PostMapping("/menu/permisson/save")
    public String saveMenuPermissions(Integer permissionId,
                                      String menuIds) {
        menuService.saveMenuPermissions(permissionId, menuIds);
        return "ok";
    }

    /**
     * 查询所有菜单
     */
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
