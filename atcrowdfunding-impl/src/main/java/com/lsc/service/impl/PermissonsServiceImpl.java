package com.lsc.service.impl;

import com.lsc.api.PermissonService;
import com.lsc.bean.TPermission;
import com.lsc.constant.Constant;
import com.lsc.mapper.TPermissionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        log.info("添加权限完毕result:{}",result);
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
        log.info("删除权限完毕result:{}",result);
        return String.valueOf(result);
    }

    @Override
    public String updatePermisson(TPermission tPermisson) {
        int result = tPermissionMapper.updateByPrimaryKey(tPermisson);
        log.info("修改权限完毕result:{}",result);
        return result > 0 ? Constant.OK : Constant.FAIL;
    }

    @Override
    public TPermission getPermisson(Integer id) {
        TPermission tPermisson = tPermissionMapper.selectByPrimaryKey(id);
        log.info("查询权限完毕result:{}",tPermisson);
        return tPermisson;
    }
}
