package com.lsc.test.demo;

import com.lsc.freemarker.utils.Constants;
import com.lsc.freemarker.utils.SystemLog;
import org.springframework.stereotype.Service;

/**
 * @author lisc
 * @Description: com.lsc.freemarker.demo
 * @date 2022/10/27 18:00
 */
@Service
public class UserService {
    @SystemLog
    public void insertUser() {
        try {
            System.out.println("插入用户成功" + 10/0);
        } catch (Exception e) {
            System.out.println("插入用户异常了");
        }

    }

    @SystemLog(LogType = Constants.LogType.Insert)
    public boolean updateUser() {
        System.out.println("更新用户成功");
        return true;
    }
}
