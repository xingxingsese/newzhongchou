package com.lsc.api;

import com.lsc.bean.TRole;

import java.util.List;

/**
 * @Description:
 * @Author: lisc
 * @date: 2022/3/27
 */
public interface RoleService {
    List<TRole> getAllRole();

    List<TRole> getAllRoleCondition(String condition);

    void addRole(TRole tRole);

    void deleteRole(int id);
}