package com.lsc.common.encryptionutils;

/**
 * @Description: PBE是一种基于口令的加密算法，使用口令代替其他对称加密算法中的密钥，其特点在于口令由用户自己掌管，不借助任何物理媒体；采用随机数（这里我们叫做盐）杂凑多重加密等方法保证数据的安全性。
 * <p>
 * PBE算法是对称加密算法的综合算法，常见算法PBEWithMD5AndDES,使用MD5和DES算法构建了PBE算法。将盐附加在口令上，通过消息摘要算法经过迭代获得构建密钥的基本材料，构建密钥后使用对称加密算法进行加密解密。
 * <p>
 * 算法/密钥长度/默认密钥长度：
 * <p>
 * 1.PBEWithMD5AndDES/56/56
 * <p>
 * 2.PBEWithMD5AndTripleDES/112,168/168
 * <p>
 * 3.PBEWithSHA1AndDESede/112,168/168
 * <p>
 * 4.PBEWithSHA1AndRC2_40/40 to 1024/128
 * <p>
 * 工作模式：CBC
 * <p>
 * 填充方式：PKCS5Padding
 * @Author: lisc
 * @date: 2022/7/23
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.security.Key;
import java.security.SecureRandom;

public class PBEUtil {
    public static final String ALGORITHM = "PBEWITHMD5andDES";
    public static final int ITERATION_COUNT = 100;
    private static final Logger log = LoggerFactory.getLogger(PBEUtil.class);

    public static byte[] initSalt() throws Exception {
        //实例化安全随机数
        SecureRandom random = new SecureRandom();
        return random.generateSeed(8);
    }

    /***
     * 转换密钥
     * @param password 密码
     * @return 密钥
     * @throws Exception
     */
    private static Key toKey(String password) throws Exception {
        //密钥材料
        PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
        //实例化
        SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
        //生成密钥
        return factory.generateSecret(keySpec);
    }

    /***
     * 加密
     * @param data 待加密数据
     * @param password 密钥
     * @param salt
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, String password, byte[] salt) {
        byte[] doFinal = null;
        try {
            //转换密钥
            Key key = toKey(password);
            //实例化PBE参数材料
            PBEParameterSpec spec = new PBEParameterSpec(salt, ITERATION_COUNT);
            //实例化
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            //初始化
            cipher.init(Cipher.ENCRYPT_MODE, key, spec);
            doFinal = cipher.doFinal(data);
        } catch (Exception e) {
            log.error("PBEUtil:encrypt 发生异常 e: {}", e.getStackTrace(), e);

        }
        return doFinal;
    }


    /***
     * 解密
     * @param data 待解密数据
     * @param password 密钥
     * @param salt
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data, String password, byte[] salt) {
        byte[] doFinal = new byte[0];
        try {
            //转换密钥
            Key key = toKey(password);
            //实例化PBE参数材料
            PBEParameterSpec spec = new PBEParameterSpec(salt, ITERATION_COUNT);
            //实例化
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            //初始化
            cipher.init(Cipher.DECRYPT_MODE, key, spec);
            //执行操作
            doFinal = cipher.doFinal(data);
        } catch (Exception e) {
            log.error("PBEUtil:decrypt 发生异常 e: {}", e.getStackTrace(), e);
        }
        return doFinal;
    }


    public static String showByteArray(byte[] data) {
        if (null == data) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (byte b : data) {
            sb.append(b).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("");
        return sb.toString();
    }


    public static void main(String[] args) throws Exception {
        byte[] salt = initSalt();
        System.out.println("salt：" + showByteArray(salt));
        System.out.println("salt String ：" + new String(salt));
        String password = "1111";
        System.out.println("口令：" + password);
        String data = "PBE数据";
        System.out.println("加密前数据：String:" + data);
        System.out.println("加密前数据：byte[]:" + showByteArray(data.getBytes()));

        byte[] encryptData = encrypt(data.getBytes(), password, salt);
        System.out.println("加密后数据：byte[]:" + showByteArray(encryptData));

        byte[] decryptData = decrypt(encryptData, password, salt);
        System.out.println("解密后数据: byte[]:" + showByteArray(decryptData));
        System.out.println("解密后数据: string:" + new String(decryptData));
    }
}

