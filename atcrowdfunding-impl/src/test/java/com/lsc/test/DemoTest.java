package com.lsc.test;

import com.lsc.com.lsc.utils.AppUtils;
import org.junit.Test;

/**
 * @Description:
 * @Author: lisc
 * @date: 2021/12/28
 */
public class DemoTest {

    @Test
    public void test(){
        String digestPwd = AppUtils.getDigestPwd("123456");
        System.out.println(digestPwd);
    }
}
