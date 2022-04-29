package com.lsc.service.impl;

import com.lsc.api.RoleService;
import com.lsc.bean.TRole;
import com.lsc.bean.TRoleExample;
import com.lsc.mapper.TRoleMapper;
import com.lsc.utils.AssertUtils;
import com.lsc.utils.ExceptionCode;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.spi.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 查询角色
 * @Author: lisc
 * @date: 2022/3/27
 */
@Service
public class RoleServiceImpl implements RoleService {


    @Autowired
    TRoleMapper tRoleMapper;

    /**
     * 查询所有角色
     * @return
     */
    @Override
    public List<TRole> getAllRole() {

        return  tRoleMapper.selectByExample(null);
    }

    /**
     * 带查询条件的sql
     * @param condition
     */
    @Override
    public List<TRole> getAllRoleCondition(String condition) {
        TRoleExample tRoleExample = null;
        if (StringUtils.isNotBlank(condition)) {
           tRoleExample = new TRoleExample();
            TRoleExample.Criteria criteria1 = tRoleExample.createCriteria();
            criteria1.andNameLike("%"+condition+"%");
        }
        return tRoleMapper.selectByExample(tRoleExample);
    }

    /**
     * 添加角色
     * @param tRole
     */
    @Override
    public void addRole(TRole tRole) {
        tRoleMapper.insertSelective(tRole);

    }

    /**
     * 删除角色
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
}
