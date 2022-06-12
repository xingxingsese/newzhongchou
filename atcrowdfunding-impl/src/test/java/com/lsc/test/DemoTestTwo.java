package com.lsc.test;

import com.lsc.bean.TbUser;
import org.junit.Test;

import java.util.Optional;
import java.util.UUID;

/**
 * @Description:
 * @Author: lisc
 * @date: 2022/5/10
 */
public class DemoTestTwo {

    @Test
    public void test() {
        /*String substring = UUID.randomUUID().toString().replace("-", "").substring(0, 11);
        System.out.println("substring = " + substring);*/

        Optional<TbUser> opt = Optional.empty();
        System.out.println("empty = " + opt);

        System.out.println("opt.isPresent() = " + opt.isPresent());

        Optional<TbUser> tbUser = opt.of(new TbUser());
        System.out.println("of = " + tbUser);


        Optional<Object> optional = Optional.ofNullable(null);
        System.out.println("ofNullable = " + optional);

        Object orElse = optional.orElse(new TbUser());
        System.out.println("orElse = " + orElse);

    }

}
