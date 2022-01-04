package com.lsc.service.impl;

import com.lsc.api.AdminService;
import com.lsc.bean.TAdmin;
import com.lsc.bean.TAdminExample;
import com.lsc.bean.TMenu;
import com.lsc.bean.TMenuExample;
import com.lsc.mapper.TAdminMapper;
import com.lsc.mapper.TMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: lisc
 * @date: 2021/12/26
 */
@Service
public class adminServiceImpl implements AdminService {

    @Autowired
    TAdminMapper tAdminMapper;

    @Autowired
    TMenuMapper tMenuMapper;

    @Override
    public TAdmin selectLogInfo(String userName, String passWord) {

        TAdminExample adminExample = new TAdminExample();
        TAdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andLoginacctEqualTo(userName).andUserpswdEqualTo(passWord);
        List<TAdmin> admins = tAdminMapper.selectByExample(adminExample);
        if (admins != null&& admins.size() == 1) {
            return admins.get(0);
        }
        return null;
    }

    @Override
    public List<TMenu> selectListMenus() {
        List<TMenu> tMenuList = new ArrayList<>();
        TMenuExample tMenuExample = new TMenuExample();
        TMenuExample.Criteria criteria = tMenuExample.createCriteria();
        criteria.andPidEqualTo(0);
        List<TMenu> pTmenu = tMenuMapper.selectByExample(tMenuExample);

        for (TMenu tmenu : pTmenu) {

            TMenuExample tMenuExample1 = new TMenuExample();
            TMenuExample.Criteria criteria1 = tMenuExample1.createCriteria();
            criteria1.andPidEqualTo(tmenu.getId());
            List<TMenu> tMenus = tMenuMapper.selectByExample(tMenuExample1);
            tmenu.settMenus(tMenus);
        }
        return pTmenu;
    }
}
