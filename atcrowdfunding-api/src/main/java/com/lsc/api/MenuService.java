package com.lsc.api;

import com.lsc.bean.TMenu;

import java.util.List;

/**
 * @Description: 菜单
 * @Author: lisc
 * @date: 2022/5/2
 */
public interface MenuService {
    List<TMenu> getAllMenus();

    /**
     * 添加菜单
     * @param tMenu
     * @return
     */
    String saveMenu(TMenu tMenu);

    String deleteMenu(Integer id);

    String updateMenu(TMenu tMenu);

    TMenu getMenu(Integer id);

    //给指定id分配它可以操作的菜单
    void saveMenuPermissions(Integer permissionId, String menuIds);
}
