package com.lsc.component;

import com.lsc.bean.TAdmin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class MyUserDetail extends User {
    //当前的这个用户
    private TAdmin admin;

    private static final long serialVersionUID = 1L;

    public MyUserDetail(String username,
                        String password,

                        Collection<? extends GrantedAuthority> authorities,
                        TAdmin admin) {
        super(username, password, true, true, true, true, authorities);
        this.admin = admin;
    }

    public TAdmin getAdmin() {
        return admin;
    }

    public void setAdmin(TAdmin admin) {
        this.admin = admin;
    }


}
