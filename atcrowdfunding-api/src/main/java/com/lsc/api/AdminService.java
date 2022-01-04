package com.lsc.api;

import com.lsc.bean.TAdmin;
import com.lsc.bean.TMenu;

import java.util.List;

/**
 * @Description:
 * @Author: lisc
 * @date: 2021/12/26
 */
public interface AdminService {
    public TAdmin selectLogInfo(String userName, String passWord);

    List<TMenu> selectListMenus();
}
