package com.lsc.common.encryptionutils;

import java.security.MessageDigest;

/**
 * @Description: MD5 是将任意长度的数据字符串转化成短小的固定长度的值的单向操作，
 * 任意两个字符串不应有相同的散列值。因此 MD5 经常用于校验字符串或者文件，
 * 因为如果文件的 MD5 不一样，说明文件内容也是不一样的，如果发现下载的文件和给定的 MD5 值不一样，就要慎重使用。
 * <p>
 * MD5 主要用做数据一致性验证、数字签名和安全访问认证，而不是用作加密。
 * 比如说用户在某个网站注册账户时，输入的密码一般经过 MD5 编码，更安全的做法还会加一层盐（salt），
 * 这样密码就具有不可逆性。然后把编码后的密码存入数据库，下次登录的时候把密码 MD5 编码，然后和数据库中的作对比，这样就提升了用户账户的安全性。
 * <p>
 * 是一种单向加密算法，只能加密不能解密
 * @Author: lisc
 * @date: 2022/7/23
 */


public class MD5Util {

    public static final String KEY_MD5 = "MD5";

    /***
     * MD5加密（生成唯一的MD5值）
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] encryMD5(byte[] data) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
        md5.update(data);
        return md5.digest();
    }

}

