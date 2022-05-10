package com.lsc.service.impl;

import com.lsc.api.MenuService;
import com.lsc.bean.TMenu;
import com.lsc.constant.Constant;
import com.lsc.mapper.TMenuMapper;
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
public class MenuServiceImpl implements MenuService {
    private static final Logger log = LoggerFactory.getLogger(MenuServiceImpl.class);

    @Autowired
    TMenuMapper tMenuMapper;

    /**
     * 查出所有菜单
     *
     * @return
     */
    @Override
    public List<TMenu> getAllMenus() {
        return tMenuMapper.selectByExample(null);
    }

    /**
     * 添加菜单
     *
     * @param tMenu
     * @return
     */
    @Override
    public String saveMenu(TMenu tMenu) {
        int result = tMenuMapper.insert(tMenu);
        log.info("添加菜单完毕result:{}",result);
        return result > 0 ? Constant.OK : Constant.FAIL;
    }

    /**
     * 删除菜单
     *
     * @param id
     * @return
     */
    @Override
    public String deleteMenu(Integer id) {
        int result = tMenuMapper.deleteByPrimaryKey(id);
        log.info("删除菜单完毕result:{}",result);
        return String.valueOf(result);
    }

    @Override
    public String updateMenu(TMenu tMenu) {
        int result = tMenuMapper.updateByPrimaryKey(tMenu);
        log.info("修改菜单完毕result:{}",result);
        return result > 0 ? Constant.OK : Constant.FAIL;
    }

    @Override
    public TMenu getMenu(Integer id) {
        TMenu tMenu = tMenuMapper.selectByPrimaryKey(id);
        log.info("查询菜单完毕result:{}",tMenu);
        return tMenu;
    }
}
