package com.lsc.service.impl;

import com.lsc.api.PermissonService;
import com.lsc.bean.*;
import com.lsc.constant.Constant;
import com.lsc.mapper.TMenuMapper;
import com.lsc.mapper.TPermissionMapper;
import com.lsc.mapper.TRolePermissionMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: lisc
 * @date: 2022/5/2
 */
@Service
public class PermissonsServiceImpl implements PermissonService {
    private static final Logger log = LoggerFactory.getLogger(PermissonsServiceImpl.class);

    @Autowired
    TPermissionMapper tPermissionMapper;

    @Autowired
    TRolePermissionMapper tRolePermissionMapper;

    @Autowired
    TMenuMapper menuMapper;

    /**
     * 查出所有权限
     *
     * @return
     */
    @Override
    public List<TPermission> getAllPermissons() {
        return tPermissionMapper.selectByExample(null);
    }

    /**
     * 添加权限
     *
     * @param tPermisson
     * @return
     */
    @Override
    public String savePermisson(TPermission tPermisson) {
        int result = tPermissionMapper.insert(tPermisson);
        log.info("添加权限完毕result:{}", result);
        return result > 0 ? Constant.OK : Constant.FAIL;
    }

    /**
     * 删除权限
     *
     * @param id
     * @return
     */
    @Override
    public String deletePermisson(Integer id) {
        int result = tPermissionMapper.deleteByPrimaryKey(id);
        log.info("删除权限完毕result:{}", result);
        return String.valueOf(result);
    }

    @Override
    public String updatePermisson(TPermission tPermisson) {
        int result = tPermissionMapper.updateByPrimaryKey(tPermisson);
        log.info("修改权限完毕result:{}", result);
        return result > 0 ? Constant.OK : Constant.FAIL;
    }

    @Override
    public TPermission getPermisson(Integer id) {
        TPermission tPermisson = tPermissionMapper.selectByPrimaryKey(id);
        log.info("查询权限完毕result:{}", tPermisson);
        return tPermisson;
    }

    @Override
    public void assignPermissionForRole(Integer rid, String permissionIds) {
        List<Integer> integerList = null;
        if (StringUtils.isNotBlank(permissionIds)) {
            integerList = Arrays.asList(permissionIds.split(",")).stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
        }
        TRolePermissionExample permissionidNotIn = new TRolePermissionExample();
        permissionidNotIn.createCriteria().andRoleidEqualTo(rid);
        tRolePermissionMapper.deleteByExample(permissionidNotIn);
        tRolePermissionMapper.insertPermissioinToRoleBath(rid, integerList);
    }

    @Override
    public List<TPermission> getRolePermission(Integer userId) {

        List<TPermission> tRolePermissions = tPermissionMapper.queryRolePermission(userId);
        return tRolePermissions;
    }

    //获取当前权限对应的所有菜单
    @Override
    public List<TMenu> getMenusByPermissionId(Integer permissionId) {

        return menuMapper.getMenusByPermissionId(permissionId);
    }
}
