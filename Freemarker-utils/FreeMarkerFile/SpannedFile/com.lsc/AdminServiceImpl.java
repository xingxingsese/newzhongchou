package com.lsc.service.impl;

import com.lsc.api.AdminService;
import com.lsc.bean.TAdmin;
import com.lsc.bean.TAdminExample;
import com.lsc.bean.TMenu;
import com.lsc.bean.TMenuExample;
import com.lsc.com.lsc.utils.AppUtils;
import com.lsc.mapper.TAdminMapper;
import com.lsc.mapper.TMenuMapper;
import com.lsc.common.AssertUtils;
import com.lsc.common.ExceptionCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: lisc
 * @date: 2022-5-5 11:17:54
 */
@Service
public class AdminServiceImpl implements AdminService {
     public Logger logger = LoggerFactory.getLogger(this.getClass());

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
        if (admins != null && admins.size() == 1) {
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

    @Override
    public List<TAdmin> listAllAdminByCondition(String condition) {
        List<TAdmin> tAdmins = null;
        if (StringUtils.isNotBlank(condition)) {
            TAdminExample example = new TAdminExample();
            TAdminExample.Criteria criteria1 = example.createCriteria();
            TAdminExample.Criteria criteria2 = example.createCriteria();
            TAdminExample.Criteria criteria3 = example.createCriteria();
            criteria1.andLoginacctLike("%" + condition + "%");
            example.or(criteria2.andUsernameLike("%" + condition + "%"));
            example.or(criteria3.andEmailLike("%" + condition + "%"));
            tAdmins = tAdminMapper.selectByExample(example);
        } else {
            tAdmins = tAdminMapper.selectByExample(null);
        }
        return tAdmins;
    }

    @Override
    public TAdmin selectById(Integer id) {

        return tAdminMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateAdmin(TAdmin admin) {
        tAdminMapper.updateByPrimaryKeySelective(admin);
    }

    /**
     * 新增tadmin用户
     *
     * @param tAdmin
     */
    @Override
    public void savaAdmin(TAdmin tAdmin) {
        // 检查用户名是否被占用
        boolean email = checkemail(tAdmin);
        AssertUtils.isTrue(email, ExceptionCode.EMAIL_THRER_ARE,ExceptionCode.EMAIL_THRER_ARE.getMsg());
        // 检查邮箱是否被占用
        boolean log = checkLogAccount(tAdmin);
        AssertUtils.isTrue(email, ExceptionCode.ACCOUNT_THRER_ARE,ExceptionCode.ACCOUNT_THRER_ARE.getMsg());
        tAdmin.setUserpswd(AppUtils.getDigestPwd("123456"));
        tAdmin.setCreatetime(AppUtils.getCurrentTime());
        tAdminMapper.insertSelective(tAdmin);
        logger.info("{} 新增完成 !",tAdmin.getUsername());
    }

    @Override
    public boolean checkLogAccount(TAdmin tAdmin) {
        TAdminExample tAdminExample = new TAdminExample();
        tAdminExample.createCriteria().andLoginacctEqualTo(tAdmin.getLoginacct());
        long l = tAdminMapper.countByExample(tAdminExample);
        return l == 0 ? true : false;
    }

    @Override
    public boolean checkemail(TAdmin tAdmin) {
        TAdminExample tAdminExample = new TAdminExample();
        tAdminExample.createCriteria().andEmailEqualTo(tAdmin.getEmail());
        long l = tAdminMapper.countByExample(tAdminExample);
        return l == 0 ? true : false;
    }

    @Override
    public void deleteAdmin(Integer id) {
        tAdminMapper.deleteByPrimaryKey(id);
    }
}
