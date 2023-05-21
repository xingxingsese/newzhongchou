package com.lsc.service.impl;

import com.alibaba.fastjson.JSON;

import com.alibaba.fastjson.TypeReference;
import com.lsc.common.Constant;
import com.lsc.common.FileUtils;
import com.lsc.common.encryptionutils.Base64Util;
import com.lsc.common.encryptionutils.PBEUtil;
import com.lsc.common.ziputils.Bzip2Utils;

import com.lsc.freemarker.entity.ClassBean;
import com.lsc.tools.service.CompressionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lisc
 * @Description: com.lsc.tools.service.impl
 * @date 2022/9/27 18:32
 */
@Service
public class CompressionServiceImpl implements CompressionService {

    private static final Logger log = LoggerFactory.getLogger(CompressionServiceImpl.class);
    private static final String PASS_WROD = "cchlqLZ!2cchlqLZ";
    private static final String SALT = "7Ywycfpl";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");


    @Override
    public String encryptedCompressed(List<String> paths, String outPath) {
        log.info("CompressionServiceImpl:encryptedCompressed paths:{}, outPath: {}", paths, outPath);
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
        byte[] compress = Bzip2Utils.compress(JSON.toJSONString(beanArrayList).getBytes());
        // 加密
        byte[] encrypt = PBEUtil.encrypt(compress, PASS_WROD, SALT.getBytes());
        // 转为base64
        String encode = Base64Util.encryptBASE64(encrypt);
        String format = dateFormat.format(new Date());
        // 输出密文
        FileUtils.fileWriteAdbMkdir(outPath + format + Constant.TXT, encode);

        log.info("CompressionUI:encryptedCompressed:end");
        return format + Constant.TXT;

    }

    /**
     * 解密解压
     */
    @Override
    public void decryptionDecompression(String path, String outPath) {
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

        List<ClassBean> classBeans = JSON.parseObject(content, new TypeReference<List<ClassBean>>() {
        });
        classBeans.forEach(bean -> {
            FileUtils.fileWriteAdbMkdir(outPath + bean.getFileName(), bean.getContent());
        });
        log.info("CompressionUI:decryptionDecompression:end");
    }
}
