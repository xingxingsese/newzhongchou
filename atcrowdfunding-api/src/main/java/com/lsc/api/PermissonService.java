package com.lsc.api;

import com.lsc.bean.TMenu;
import com.lsc.bean.TPermission;
import com.lsc.bean.TPermissionMenu;

import java.util.List;

/**
 * @Description:
 * @Author: lisc
 * @date: 2022/5/23
 */
public interface PermissonService {
    List<TPermission> getAllPermissons();

    String savePermisson(TPermission tPermisson);

    String deletePermisson(Integer id);

    String updatePermisson(TPermission tPermisson);

    TPermission getPermisson(Integer id);

    void assignPermissionForRole(Integer rid, String permissionIds);

    List<TPermission> getRolePermission(Integer userId);

    /**
     * 按照权限id查出它可以操作的所有菜单
     * @param permissionId 权限id
     * @return
     */
    List<TMenu> getMenusByPermissionId(Integer permissionId);
}
