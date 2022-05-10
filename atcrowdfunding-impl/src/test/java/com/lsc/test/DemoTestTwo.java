package com.lsc.test;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: lisc
 * @date: 2022/5/10
 */
public class DemoTestTwo {

    @Test
    public void test() {
        List<Long> list = new ArrayList();
        Date date = new Date();
        for (int i =0 ;i < 20; i ++){
            long aLong = (long) (Math.random() * 10000);
          //  System.out.println(aLong);
           // System.out.println("Math.random() = " + Math.random()*1000);
           /* date.setTime(System.currentTimeMillis() + i*1000);
            System.out.println("date = " + date);*/
           list.add(i,System.currentTimeMillis() + i*1000);

        }

        int i = 1;
        for (Long o : list) {
            date.setTime(System.currentTimeMillis() + i*1000);
            i++;
            System.out.println(date);

        }

    }

}
