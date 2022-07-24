package com.lsc.common.encryptionutils;

import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @Description: RSA算法是一种非对称加密算法，所谓非对称就是该算法需要一对密钥，若使用其中一个加密，则需要用另一个才能解密。
 * 目前它是最有影响力和最常用的公钥加密算法，能够抵抗已知的绝大多数密码攻击。从提出到现今的三十多年里，经历了各种攻击的考验，逐渐为人们接受，普遍认为是目前最优秀的公钥方案之一。
 * <p>
 * 该算法基于一个的数论事实：
 * 将两个大质数相乘十分容易，但是想要对其乘积进行因式分解却极其困难，因此可以将乘积公开作为加密密钥。
 * 由于进行的都是大数计算，RSA 最快的情况也比 DES 慢上好几倍，比对应同样安全级别的对称密码算法要慢 1000 倍左右。
 * 所以 RSA 一般只用于少量数据加密，比如说交换对称加密的密钥。
 * <p>
 * 使用 RSA 加密主要有这么几步：生成密钥对、公开公钥、公钥加密私钥解密、私钥加密公钥解密。
 * @Author: lisc
 * @date: 2022/7/23
 */
public class RsaUtil {
    private static final Logger log = LoggerFactory.getLogger(RsaUtil.class);

    /**
     * 生成密钥对并保存在本地文件中
     *
     * @param algorithm : 算法
     * @param pubPath   : 公钥保存路径
     * @param priPath   : 私钥保存路径
     * @throws Exception
     */
    public static void generateKeyToFile(String algorithm, String pubPath, String priPath) {
        try {
            // 获取密钥对生成器
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
            // 获取密钥对
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            // 获取公钥
            PublicKey publicKey = keyPair.getPublic();
            // 获取私钥
            PrivateKey privateKey = keyPair.getPrivate();
            // 获取byte数组
            byte[] publicKeyEncoded = publicKey.getEncoded();
            byte[] privateKeyEncoded = privateKey.getEncoded();
            // 进行Base64编码
            String publicKeyString = Base64.encode(publicKeyEncoded);
            String privateKeyString = Base64.encode(privateKeyEncoded);
            // 保存文件

            FileUtils.writeStringToFile(new File(pubPath), publicKeyString, Charset.forName("UTF-8"));
            FileUtils.writeStringToFile(new File(priPath), privateKeyString, Charset.forName("UTF-8"));
        } catch (Exception e) {
            log.error("RsaUtil:generateKeyToFile 发生异常 e: {}", e.getStackTrace(), e);
        }

    }

    /**
     * 从文件中加载公钥
     *
     * @param algorithm : 算法
     * @param filePath  : 文件路径
     * @return : 公钥
     * @throws Exception
     */
    public static PublicKey loadPublicKeyFromFile(String algorithm, String filePath) {
        String keyString = null;
        try {
            // 将文件内容转为字符串
            keyString = FileUtils.readFileToString(new File(filePath), Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return loadPublicKeyFromString(algorithm, keyString);

    }

    /**
     * 从字符串中加载公钥
     *
     * @param algorithm : 算法
     * @param keyString : 公钥字符串
     * @return : 公钥
     * @throws Exception
     */
    public static PublicKey loadPublicKeyFromString(String algorithm, String keyString) {
        PublicKey publicKey = null;
        try {
            // 进行Base64解码
            byte[] decode = Base64.decode(keyString);
            // 获取密钥工厂
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            // 构建密钥规范
            X509EncodedKeySpec keyspec = new X509EncodedKeySpec(decode);
            // 获取公钥
            publicKey = keyFactory.generatePublic(keyspec);

        } catch (Exception e) {
            log.error("RsaUtil:loadPublicKeyFromString 发生异常 e: {}", e.getStackTrace(), e);
        }
        return publicKey;
    }

    /**
     * 从文件中加载私钥
     *
     * @param algorithm : 算法
     * @param filePath  : 文件路径
     * @return : 私钥
     * @throws Exception
     */
    public static PrivateKey loadPrivateKeyFromFile(String algorithm, String filePath) {
        PrivateKey privateKey = null;
        try {
            // 将文件内容转为字符串
            String keyString = FileUtils.readFileToString(new File(filePath), Charset.forName("UTF-8"));

            privateKey = loadPrivateKeyFromString(algorithm, keyString);
        } catch (Exception e) {
            log.error("RsaUtil:loadPrivateKeyFromFile 发生异常 e: {}", e.getStackTrace(), e);
        }
        return privateKey;
    }

    /**
     * 从字符串中加载私钥
     *
     * @param algorithm : 算法
     * @param keyString : 私钥字符串
     * @return : 私钥
     * @throws Exception
     */
    public static PrivateKey loadPrivateKeyFromString(String algorithm, String keyString) throws Exception {
        // 进行Base64解码
        byte[] decode = Base64.decode(keyString);
        // 获取密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        // 构建密钥规范
        PKCS8EncodedKeySpec keyspec = new PKCS8EncodedKeySpec(decode);
        // 生成私钥
        return keyFactory.generatePrivate(keyspec);

    }

    /**
     * 使用密钥加密数据
     *
     * @param algorithm      : 算法
     * @param input          : 原文
     * @param key            : 密钥
     * @param maxEncryptSize : 最大加密长度(需要根据实际情况进行调整)
     * @return : 密文
     * @throws Exception
     */
    public static String encrypt(String algorithm, String input, Key key, int maxEncryptSize) throws Exception {
        // 获取Cipher对象
        Cipher cipher = Cipher.getInstance(algorithm);
        // 初始化模式(加密)和密钥
        cipher.init(Cipher.ENCRYPT_MODE, key);
        // 将原文转为byte数组
        byte[] data = input.getBytes();
        // 总数据长度
        int total = data.length;
        // 输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        decodeByte(maxEncryptSize, cipher, data, total, baos);
        // 对密文进行Base64编码
        return Base64.encode(baos.toByteArray());

    }

    /**
     * 解密数据
     *
     * @param algorithm      : 算法
     * @param encrypted      : 密文
     * @param key            : 密钥
     * @param maxDecryptSize : 最大解密长度(需要根据实际情况进行调整)
     * @return : 原文
     * @throws Exception
     */
    public static String decrypt(String algorithm, String encrypted, Key key, int maxDecryptSize) {
        // 获取Cipher对象
        ByteArrayOutputStream baos = null;
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            // 初始化模式(解密)和密钥
            cipher.init(Cipher.DECRYPT_MODE, key);
            // 由于密文进行了Base64编码, 在这里需要进行解码
            byte[] data = Base64.decode(encrypted);
            // 总数据长度
            int total = data.length;
            // 输出流
            baos = new ByteArrayOutputStream();

            decodeByte(maxDecryptSize, cipher, data, total, baos);
        } catch (Exception e) {
            log.error("RsaUtil:decrypt 发生异常 e: {}", e.getStackTrace(), e);
        }
        // 输出原文
        return baos.toString();

    }

    /**
     * 分段处理数据
     *
     * @param maxSize : 最大处理能力
     * @param cipher  : Cipher对象
     * @param data    : 要处理的byte数组
     * @param total   : 总数据长度
     * @param baos    : 输出流
     * @throws Exception
     */
    public static void decodeByte(int maxSize, Cipher cipher, byte[] data, int total, ByteArrayOutputStream baos) throws Exception {
        // 偏移量
        int offset = 0;
        // 缓冲区
        byte[] buffer;
        // 如果数据没有处理完, 就一直继续
        while (total - offset > 0) {
            // 如果剩余的数据 >= 最大处理能力, 就按照最大处理能力来加密数据
            if (total - offset >= maxSize) {
                // 加密数据
                buffer = cipher.doFinal(data, offset, maxSize);
                // 偏移量向右侧偏移最大数据能力个
                offset += maxSize;
            } else {
                // 如果剩余的数据 < 最大处理能力, 就按照剩余的个数来加密数据
                buffer = cipher.doFinal(data, offset, total - offset);
                // 偏移量设置为总数据长度, 这样可以跳出循环
                offset = total;
            }
            // 向输出流写入数据
            baos.write(buffer);
        }
    }

    public static void main(String[] args) throws Exception {
        String txt = "123333333333333";
        PublicKey publicKey = RsaUtil.loadPublicKeyFromFile("RSA", "E:\\TestCodeDome\\atcrowdfunding\\atcrowdfunding-common\\src\\main\\resources\\RsaKey\\publicKey.txt");
        String rsa = RsaUtil.encrypt("RSA", txt, publicKey, 1);
        PrivateKey privateKey = RsaUtil.loadPrivateKeyFromFile("RSA", "E:\\TestCodeDome\\atcrowdfunding\\atcrowdfunding-common\\src\\main\\resources\\RsaKey\\privateKey.txt");
        String decrypt = RsaUtil.decrypt("RSA", rsa, privateKey, 1);
        System.out.println(decrypt);
    }
}
