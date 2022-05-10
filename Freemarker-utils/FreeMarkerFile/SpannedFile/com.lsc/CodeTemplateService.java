package com.lsc.api;

import com.lsc.bean.TAdmin;
import com.lsc.bean.TMenu;

import java.util.List;

/**
 * @Description:
 * @Author: lisc
 * @date: 2022-5-5 17:30:31
 */
public interface CodeTemplateService {
    TCodeTemplate selectLogInfo(String userName, String passWord);

    List<TCodeTemplate> selectListMenus();

    List<TCodeTemplate> listAllAdminByCondition(String condition);

    TCodeTemplate selectById(Integer id);

    void updateAdmin(TCodeTemplate tCodeTemplate);

    void savaAdmin(TCodeTemplate tCodeTemplate);

    boolean checkLogAccount(TCodeTemplate tCodeTemplate);

    boolean checkemail(TCodeTemplate tCodeTemplate);

    void deleteAdmin(Integer id);
}
