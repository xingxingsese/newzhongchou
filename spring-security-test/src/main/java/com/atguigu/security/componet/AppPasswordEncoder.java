package com.atguigu.security.componet;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

/**
 * SpringSecurity默认的编码器  PasswordEncoder PlainTextPasswordEncoder（123.equals(123)）
 *
 * @author lfy
 */
@Component
public class AppPasswordEncoder implements PasswordEncoder {

    /**
     * 纯明文怎么加密
     */
    @Override
    public String encode(CharSequence rawPassword) {
        // TODO Auto-generated method stub
        String md5DigestAsHex = DigestUtils.md5DigestAsHex(rawPassword.toString().getBytes());
        return md5DigestAsHex;
    }

    /**
     * 怎么匹配
     * rawPassword:纯明文密码
     * encodedPassword：密文
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        // TODO Auto-generated method stub
        // 123456  djaskldjaskldjakl
        String md5DigestAsHex = DigestUtils.md5DigestAsHex(rawPassword.toString().getBytes());
        return md5DigestAsHex.equalsIgnoreCase(encodedPassword);
    }

}
