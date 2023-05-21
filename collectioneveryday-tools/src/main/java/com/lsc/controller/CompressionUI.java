package com.lsc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lsc.common.FileUtils;
import com.lsc.common.encryptionutils.Base64Util;
import com.lsc.common.encryptionutils.PBEUtil;
import com.lsc.common.ziputils.Bzip2Utils;
import com.lsc.constant.Constant;
import com.lsc.freemarker.entity.ClassBean;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: lisc
 * @date: 2022/8/23
 */
public class CompressionUI {
    private static final Logger log = LoggerFactory.getLogger(CompressionUI.class);
    private static final String PASS_WROD = "cchlqLZ!2cchlqLZ";
    private static final String SALT = "7Ywycfpl";
    /**
     * 压缩文件路径
     */
    private static final String ZIP_PATH_ONE = "D:\\Code\\TestCode\\newzhongchou\\everyday-tools\\src\\main\\resources\\cipher-txt-zip\\";
    /**
     * 临时密文暂存路径
     */
    private static final String TXT_PATH_ONE = "E:\\TestCodeDome\\atcrowdfunding\\collectioneveryday-tools\\src\\main\\resources\\cipher-txt\\";
    private static final String RESULT_TXT_PATH = "E:\\TestCodeDome\\atcrowdfunding\\collectioneveryday-tools\\src\\main\\resources\\result\\";
    private static final String DECRYPTION_TXTS = "E:\\TestCodeDome\\atcrowdfunding\\collectioneveryday-tools\\src\\main\\resources\\decryption-txts\\";

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    @Test
    public void test() throws URISyntaxException {
        List<String> list = Arrays.asList("D:\\Code\\TestCode\\newzhongchou\\everyday-tools\\src\\main\\java\\com\\lsc\\tools\\controoler\\CompressionUI.java",
                "D:\\Code\\TestCode\\newzhongchou\\everyday-tools\\src\\main\\java\\com\\lsc\\tools\\controoler\\CompressionUITwo.java"
        );

        // String compressed = encryptedCompressed(list.get(0), list.get(1));
        decryptionDecompression(TXT_PATH_ONE + "20221118203356.txt");
    }

    /**
     * 加密压缩
     * 1 多个文件,第一行是该文件路径和文件名 回车,然后是该文件内容, 以此类推
     */
    public static String encryptedCompressed(String... paths) {
        byte[] encrypt = null;
        byte[] compress = null;
        byte[] uncompress = null;
        String encode = null;
        // 拼接各个文件的内容
        StringBuffer buffer = new StringBuffer();
        ArrayList<ClassBean> beanArrayList = new ArrayList<>();
        for (String path : paths) {
            ClassBean bean = new ClassBean();
            File file = new File(path);
            if (!file.exists()) {
                log.info("文件或文件夹不存在:{}", path);
                return null;
            }
            String returnString = FileUtils.readFileAllBytesReturnString(file.getPath());
            bean.setFileName(file.getName());
            bean.setPath(file.getPath());
            bean.setContent(returnString);
            beanArrayList.add(bean);
        }
        //    FileUtils.fileWriteIsExistsAppenTherMkdir(TXT_PATH_ONE + sourceFile, encode);
        //System.out.println(JSON.toJSONString(beanArrayList));
        // 压缩
        compress = Bzip2Utils.compress(JSON.toJSONString(beanArrayList).getBytes());
        // 加密
        encrypt = PBEUtil.encrypt(compress, PASS_WROD, SALT.getBytes());
        // 转为base64
        encode = Base64Util.encryptBASE64(encrypt);
        // 输出密文
        FileUtils.fileWriteAdbMkdir(TXT_PATH_ONE + dateFormat.format(new Date()) + Constant.TXT, encode);

        log.info("CompressionUI:encryptedCompressed:end");
        return dateFormat.format(new Date()) + Constant.TXT;
    }

    /**
     * 解密解压
     */
    public static void decryptionDecompression(String path) {
        File file = new File(path);
        // 读取指定路径下的文件
        byte[] bytes = FileUtils.readFileAllBytesReturnBytes(file.getPath());
        // base64解密
        byte[] decode = Base64Util.decryBASE64(new String(bytes));
        // 解密
        byte[] decryptZip = PBEUtil.decrypt(decode, PASS_WROD, SALT.getBytes());
        // 解压
        byte[] uncompress = Bzip2Utils.uncompress(decryptZip);
        // 明文
        String content = new String(uncompress);

        FileUtils.fileWriteAdbMkdir(DECRYPTION_TXTS + "pathAndContent2.txt", content);
        List<ClassBean> classBeans = JSON.parseObject(content, new TypeReference<List<ClassBean>>() {
        });
        classBeans.forEach(bean -> {
            //FileUtils.fileWriteAdbMkdir(RESULT_TXT_PATH + bean.getFileName(), bean.getContent());
            // FileUtils.fileWriteAdbMkdir(bean.getPath() + bean.getFileName(), bean.getContent());
            FileUtils.fileWriteAdbMkdir(bean.getPath(), bean.getContent());
        });
        log.info("CompressionUI:decryptionDecompression:end");
    }
}
