package com.lsc.bean;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: lisc
 * @date: 2023/2/27
 */
@Service
public class TestBean {
    private User user;

    public void setUser(User user) {
        this.user = user;
    }
}
