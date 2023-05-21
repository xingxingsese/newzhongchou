package com.lsc.service.impl;

import com.lsc.api.AdminService;
import com.lsc.bean.TCodeTemplate;
import com.lsc.bean.TCodeTemplateExample;
import com.lsc.bean.TCodeTemplate;
import com.lsc.bean.TCodeTemplateExample;
import com.lsc.com.lsc.utils.AppUtils;
import com.lsc.mapper.TCodeTemplateMapper;
import com.lsc.mapper.TCodeTemplateMapper;
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
 * @date: 2022-5-5 17:30:31
 */
@Service
public class CodeTemplateServiceImpl implements CodeTemplateService {
    public Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TCodeTemplateMapper tCodeTemplateMapper;


    @Override
    public TCodeTemplate selectLogInfo(String userName, String passWord) {

        TCodeTemplateExample tCodeTemplateExample = new TCodeTemplateExample();
        TCodeTemplateExample.Criteria criteria = tCodeTemplateExample.createCriteria();
        criteria.andLoginacctEqualTo(userName).andUserpswdEqualTo(passWord);
        List<TCodeTemplate> admins = tCodeTemplateMapper.selectByExample(adminExample);
        if (admins != null && admins.size() == 1) {
            return admins.get(0);
        }
        return null;

    }

    @Override
    public List<TCodeTemplate> selectListCodeTemplates() {
        List<TCodeTemplate> tCodeTemplateList = new ArrayList<>();
        TCodeTemplateExample tCodeTemplateExample = new TCodeTemplateExample();
        TCodeTemplateExample.Criteria criteria = tCodeTemplateExample.createCriteria();
        criteria.andPidEqualTo(0);
        List<TCodeTemplate> pTmenu = tCodeTemplateMapper.selectByExample(tCodeTemplateExample);

        for (TCodeTemplate tmenu : pTmenu) {

            TCodeTemplateExample tCodeTemplateExample1 = new TCodeTemplateExample();
            TCodeTemplateExample.Criteria criteria1 = tCodeTemplateExample1.createCriteria();
            criteria1.andPidEqualTo(tmenu.getId());
            List<TCodeTemplate> tCodeTemplates = tCodeTemplateMapper.selectByExample(tCodeTemplateExample1);
            tmenu.settCodeTemplates(tCodeTemplates);
        }
        return pTmenu;
    }

    @Override
    public List<TCodeTemplate> listAllAdminByCondition(String condition) {
        List<TCodeTemplate> tCodeTemplates = null;
        if (StringUtils.isNotBlank(condition)) {
            TCodeTemplateExample example = new TCodeTemplateExample();
            TCodeTemplateExample.Criteria criteria1 = example.createCriteria();
            TCodeTemplateExample.Criteria criteria2 = example.createCriteria();
            TCodeTemplateExample.Criteria criteria3 = example.createCriteria();
            criteria1.andLoginacctLike("%" + condition + "%");
            example.or(criteria2.andUsernameLike("%" + condition + "%"));
            example.or(criteria3.andEmailLike("%" + condition + "%"));
            tCodeTemplates = tCodeTemplateMapper.selectByExample(example);
        } else {
            tCodeTemplates = tCodeTemplateMapper.selectByExample(null);
        }
        return tCodeTemplates;
    }

    @Override
    public TCodeTemplate selectById(Integer id) {

        return tCodeTemplateMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateAdmin(TCodeTemplate admin) {
        tCodeTemplateMapper.updateByPrimaryKeySelective(admin);
    }

    /**
     * 新增tadmin用户
     *
     * @param tCodeTemplate
     */
    @Override
    public void savaAdmin(TCodeTemplate tCodeTemplate) {
        // 检查用户名是否被占用
        boolean email = checkemail(tCodeTemplate);
        AssertUtils.isTrue(email, ExceptionCode.EMAIL_THRER_ARE, ExceptionCode.EMAIL_THRER_ARE.getMsg());
        // 检查邮箱是否被占用
        boolean log = checkLogAccount(tCodeTemplate);
        AssertUtils.isTrue(email, ExceptionCode.ACCOUNT_THRER_ARE, ExceptionCode.ACCOUNT_THRER_ARE.getMsg());
        tCodeTemplate.setUserpswd(AppUtils.getDigestPwd("123456"));
        tCodeTemplate.setCreatetime(AppUtils.getCurrentTime());
        tCodeTemplateMapper.insertSelective(tCodeTemplate);
        logger.info("{} 新增完成 !", tCodeTemplate.getUsername());
    }

    @Override
    public boolean checkLogAccount(TCodeTemplate tCodeTemplate) {
        TCodeTemplateExample tCodeTemplateExample = new TCodeTemplateExample();
        tCodeTemplateExample.createCriteria().andLoginacctEqualTo(tCodeTemplate.getLoginacct());
        long l = tCodeTemplateMapper.countByExample(tCodeTemplateExample);
        return l == 0 ? true : false;
    }

    @Override
    public boolean checkemail(TCodeTemplate tCodeTemplate) {
        TCodeTemplateExample tCodeTemplateExample = new TCodeTemplateExample();
        tCodeTemplateExample.createCriteria().andEmailEqualTo(tCodeTemplate.getEmail());
        long l = tCodeTemplateMapper.countByExample(tCodeTemplateExample);
        return l == 0 ? true : false;
    }

    @Override
    public void deleteAdmin(Integer id) {
        tCodeTemplateMapper.deleteByPrimaryKey(id);
    }
}
