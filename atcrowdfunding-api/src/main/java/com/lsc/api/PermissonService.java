package com.lsc.api;

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
}
