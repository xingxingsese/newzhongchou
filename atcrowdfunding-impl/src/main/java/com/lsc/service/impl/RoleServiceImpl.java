package com.lsc.service.impl;

import com.lsc.api.RoleService;
import com.lsc.bean.TAdminRoleExample;
import com.lsc.bean.TRole;
import com.lsc.bean.TRoleExample;
import com.lsc.mapper.TAdminRoleMapper;
import com.lsc.mapper.TRoleMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 查询角色
 * @Author: lisc
 * @date: 2022/3/27
 */
@Service
public class RoleServiceImpl implements RoleService {


    @Autowired
    TRoleMapper tRoleMapper;

    @Autowired
    TAdminRoleMapper tAdminRoleMapper;

    /**
     * 查询所有角色
     *
     * @return
     */
    @Override
    public List<TRole> getAllRole() {

        return tRoleMapper.selectByExample(null);
    }

    /**
     * 带查询条件的sql
     *
     * @param condition
     */
    @Override
    public List<TRole> getAllRoleCondition(String condition) {
        TRoleExample tRoleExample = null;
        if (StringUtils.isNotBlank(condition)) {
            tRoleExample = new TRoleExample();
            TRoleExample.Criteria criteria1 = tRoleExample.createCriteria();
            criteria1.andNameLike("%" + condition + "%");
        }
        return tRoleMapper.selectByExample(tRoleExample);
    }

    /**
     * 添加角色
     *
     * @param tRole
     */
    @Override
    public void addRole(TRole tRole) {
        tRoleMapper.insertSelective(tRole);

    }

    /**
     * 删除角色
     *
     * @param id
     */
    @Override
    public void deleteRole(int id) {
        tRoleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public TRole getRoleById(Integer id) {
        return tRoleMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updataRole(TRole tRole) {
        tRoleMapper.updateByPrimaryKeySelective(tRole);
    }

    /**
     * 查询用户已有角色
     *
     * @param id
     * @return
     */
    @Override
    public List<TRole> getUserHasRoles(Integer id) {
        return tRoleMapper.getUserHasRoles(id);
    }


    /**
     * 查询用户未有角色
     *
     * @param id
     * @return
     */
    @Override
    public List<TRole> getUserUnRoles(Integer id) {

        return tRoleMapper.getUserUnRoles(id);
    }

    /**
     * 批量插入
     *
     * @param uid
     * @param rids
     */
    @Override
    public void assignUserRole(Integer uid, String rids) {
        List<String> roleList = new ArrayList();
        if (StringUtils.isNotBlank(rids)) {
             roleList = Arrays.asList(rids.split(","));

        }
        TAdminRoleExample tAdminRoleExample = new TAdminRoleExample();
        tAdminRoleExample.createCriteria().andAdminidEqualTo(uid).andRoleidIn(roleList);
        // 先删后插
        tAdminRoleMapper.deleteByExample(tAdminRoleExample);
        tAdminRoleMapper.insertSelectiveBath(uid, roleList);
    }

    /**
     * 删除觉得
     * @param uid
     * @param rids
     */
    @Override
    public void unAssignUserRole(Integer uid, String rids) {
        List<String> roleList = new ArrayList();
        if (StringUtils.isNotBlank(rids)) {
            roleList = Arrays.asList(rids.split(","));
           // List<Integer> collect = Arrays.asList(rids.split(",")).stream().map(Integer::parseInt).collect(Collectors.toList());

        }
        TAdminRoleExample tAdminRoleExample = new TAdminRoleExample();
        tAdminRoleExample.createCriteria().andAdminidEqualTo(uid).andRoleidIn(roleList);
        //
        tAdminRoleMapper.deleteByExample(tAdminRoleExample);
    }
}
