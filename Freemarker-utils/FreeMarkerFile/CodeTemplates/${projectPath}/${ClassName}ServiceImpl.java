package ${projectPath}.service.impl;

import com.lsc.api.AdminService;
import com.lsc.bean.${MapClassName};
import com.lsc.bean.${MapClassName}Example;
import com.lsc.bean.${MapClassName};
import com.lsc.bean.${MapClassName}Example;
import com.lsc.com.lsc.utils.AppUtils;
import com.lsc.mapper.${MapClassName}Mapper;
import com.lsc.mapper.${MapClassName}Mapper;
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
 * @date: ${date?datetime}
 */
@Service
public class ${ClassName}ServiceImpl implements ${ClassName}Service {
     public Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ${MapClassName}Mapper ${MapClassName?uncap_first}Mapper;



    @Override
    public ${MapClassName} selectLogInfo(String userName, String passWord) {

        ${MapClassName}Example ${MapClassName?uncap_first}Example = new ${MapClassName}Example();
        ${MapClassName}Example.Criteria criteria = ${MapClassName?uncap_first}Example.createCriteria();
        criteria.andLoginacctEqualTo(userName).andUserpswdEqualTo(passWord);
        List<${MapClassName}> admins = ${MapClassName?uncap_first}Mapper.selectByExample(adminExample);
        if (admins != null && admins.size() == 1) {
            return admins.get(0);
        }
        return null;

    }

    @Override
    public List<${MapClassName}> selectLis${MapClassName?uncap_first}s() {
        List<${MapClassName}> ${MapClassName?uncap_first}List = new ArrayList<>();
        ${MapClassName}Example ${MapClassName?uncap_first}Example = new ${MapClassName}Example();
        ${MapClassName}Example.Criteria criteria = ${MapClassName?uncap_first}Example.createCriteria();
        criteria.andPidEqualTo(0);
        List<${MapClassName}> pTmenu = ${MapClassName?uncap_first}Mapper.selectByExample(${MapClassName?uncap_first}Example);

        for (${MapClassName} tmenu : pTmenu) {

            ${MapClassName}Example ${MapClassName?uncap_first}Example1 = new ${MapClassName}Example();
            ${MapClassName}Example.Criteria criteria1 = ${MapClassName?uncap_first}Example1.createCriteria();
            criteria1.andPidEqualTo(tmenu.getId());
            List<${MapClassName}> ${MapClassName?uncap_first}s = ${MapClassName?uncap_first}Mapper.selectByExample(${MapClassName?uncap_first}Example1);
            tmenu.set${MapClassName?uncap_first}s(${MapClassName?uncap_first}s);
        }
        return pTmenu;
    }

    @Override
    public List<${MapClassName}> listAllAdminByCondition(String condition) {
        List<${MapClassName}> ${MapClassName?uncap_first}s = null;
        if (StringUtils.isNotBlank(condition)) {
            ${MapClassName}Example example = new ${MapClassName}Example();
            ${MapClassName}Example.Criteria criteria1 = example.createCriteria();
            ${MapClassName}Example.Criteria criteria2 = example.createCriteria();
            ${MapClassName}Example.Criteria criteria3 = example.createCriteria();
            criteria1.andLoginacctLike("%" + condition + "%");
            example.or(criteria2.andUsernameLike("%" + condition + "%"));
            example.or(criteria3.andEmailLike("%" + condition + "%"));
            ${MapClassName?uncap_first}s = ${MapClassName?uncap_first}Mapper.selectByExample(example);
        } else {
            ${MapClassName?uncap_first}s = ${MapClassName?uncap_first}Mapper.selectByExample(null);
        }
        return ${MapClassName?uncap_first}s;
    }

    @Override
    public ${MapClassName} selectById(Integer id) {

        return ${MapClassName?uncap_first}Mapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateAdmin(${MapClassName} admin) {
        ${MapClassName?uncap_first}Mapper.updateByPrimaryKeySelective(admin);
    }

    /**
     * 新增tadmin用户
     *
     * @param ${MapClassName?uncap_first}
     */
    @Override
    public void savaAdmin(${MapClassName} ${MapClassName?uncap_first}) {
        // 检查用户名是否被占用
        boolean email = checkemail(${MapClassName?uncap_first});
        AssertUtils.isTrue(email, ExceptionCode.EMAIL_THRER_ARE,ExceptionCode.EMAIL_THRER_ARE.getMsg());
        // 检查邮箱是否被占用
        boolean log = checkLogAccount(${MapClassName?uncap_first});
        AssertUtils.isTrue(email, ExceptionCode.ACCOUNT_THRER_ARE,ExceptionCode.ACCOUNT_THRER_ARE.getMsg());
        ${MapClassName?uncap_first}.setUserpswd(AppUtils.getDigestPwd("123456"));
        ${MapClassName?uncap_first}.setCreatetime(AppUtils.getCurrentTime());
        ${MapClassName?uncap_first}Mapper.insertSelective(${MapClassName?uncap_first});
        logger.info("{} 新增完成 !",${MapClassName?uncap_first}.getUsername());
    }

    @Override
    public boolean checkLogAccount(${MapClassName} ${MapClassName?uncap_first}) {
        ${MapClassName}Example ${MapClassName?uncap_first}Example = new ${MapClassName}Example();
        ${MapClassName?uncap_first}Example.createCriteria().andLoginacctEqualTo(${MapClassName?uncap_first}.getLoginacct());
        long l = ${MapClassName?uncap_first}Mapper.countByExample(${MapClassName?uncap_first}Example);
        return l == 0 ? true : false;
    }

    @Override
    public boolean checkemail(${MapClassName} ${MapClassName?uncap_first}) {
        ${MapClassName}Example ${MapClassName?uncap_first}Example = new ${MapClassName}Example();
        ${MapClassName?uncap_first}Example.createCriteria().andEmailEqualTo(${MapClassName?uncap_first}.getEmail());
        long l = ${MapClassName?uncap_first}Mapper.countByExample(${MapClassName?uncap_first}Example);
        return l == 0 ? true : false;
    }

    @Override
    public void deleteAdmin(Integer id) {
        ${MapClassName?uncap_first}Mapper.deleteByPrimaryKey(id);
    }
}
