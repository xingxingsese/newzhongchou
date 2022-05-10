package com.lsc.api;

import com.lsc.bean.TAdmin;
import com.lsc.bean.TMenu;

import java.util.List;

/**
 * @Description:
 * @Author: lisc
 * @date: 2022-5-5 11:17:54
 */
public interface AdminService {
    TAdmin selectLogInfo(String userName, String passWord);

    List<TMenu> selectListMenus();

    List<TAdmin> listAllAdminByCondition(String condition);

    TAdmin selectById(Integer id);

    void updateAdmin(TAdmin admin);

    void savaAdmin(TAdmin tAdmin);

    boolean checkLogAccount(TAdmin tAdmin);

    boolean checkemail(TAdmin tAdmin);

    void deleteAdmin(Integer id);
}
