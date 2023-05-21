import com.lsc.common.FileUtils;
import com.lsc.common.encryptionutils.Base64Util;
import com.lsc.common.encryptionutils.PBEUtil;
import com.lsc.common.ziputils.Bzip2Utils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

/**
 * @Description:
 * @Author: lisc
 * @date: 2022/7/24
 */
public class CompressionEncryptionTest {
    private static final Logger log = LoggerFactory.getLogger(CompressionEncryptionTest.class);

    /**
     * 最终目标
     * 对指定文件进行压缩加密,输出加密后文本
     */

    @Test
    public void test01() {
        List<File> files = FileUtils.searchAllFile(new File("C:\\Users\\zuiho\\Desktop\\源文件文件夹"));
        String passWrod = "cchlqLZ!23";
        String salt = "wycfpl";
        byte[] encrypt = null;
        byte[] compress = null;
        byte[] uncompress = null;
        String encode = null;
        for (File file : files) {
            // 获取文件字节码
            byte[] bytes = FileUtils.readFileAllBytesReturnBytes(file.getPath());
            log.info("原文件大小: {}", bytes.length);
            // 压缩
            compress = Bzip2Utils.compress(bytes);
            // 加密
            encrypt = PBEUtil.encrypt(compress, passWrod, salt.getBytes());
            // 转为base64
            encode = Base64Util.encryptBASE64(encrypt);
            log.info("压缩后文件大小: {}", encrypt.length);

            // 输出密文
            log.info("加密后的密文为:{}, \t 长度为:{}", encode, encode.length());

            log.info("**************************");
        }

        byte[] base64 = Base64Util.decryBASE64(encode);
        // 解密
        byte[] decrypt = PBEUtil.decrypt(base64, passWrod, passWrod.getBytes());
        // 解压缩
        uncompress = Bzip2Utils.uncompress(decrypt);
        log.info("解密后:{}", new String(uncompress));

    }


}
