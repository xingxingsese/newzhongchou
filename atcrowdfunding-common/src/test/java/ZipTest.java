import com.alibaba.fastjson.JSONObject;
import com.lsc.common.FileUtils;
import com.lsc.common.encryptionutils.Base64Util;
import com.lsc.common.encryptionutils.PBEUtil;
import com.lsc.common.ziputils.Bzip2Utils;
import com.lsc.common.ziputils.ZipUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * @Description:
 * @Author: lisc
 * @date: 2022/7/25
 */
public class ZipTest {

    private static final String PATH = "C:\\Users\\zuiho\\Desktop\\压缩后\\AssertUtils.java";
    private static final String PATH2 = "C:\\Users\\zuiho\\Desktop\\压缩后\\";
    /**
     * 输入多个url地址,压缩zip,压缩,加密
     */
    @Test
    public void test() {
        String passWrod = "cchlqLZ!23";
        String salt = "wycfpl!@";
        byte[] encrypt = null;
        byte[] compress = null;
        byte[] uncompress = null;
        String encode = null;
        File file = new File(PATH);
        byte[] bytes = FileUtils.readFileAllBytesReturnBytes(file.getPath());
        // 压缩
        compress = Bzip2Utils.compress(bytes);
        // 加密
        encrypt = PBEUtil.encrypt(compress, passWrod, salt.getBytes());
        // 转为base64
        encode = Base64Util.encryptBASE64(encrypt);
        // 输出密文
        FileUtils.fileWriteAdbMkdir(PATH2+file.getName(),encode);
        ZipUtils.fileToZip(PATH2,PATH2,"yasuo");

        byte[] zipBytes = FileUtils.readFileAllBytesReturnBytes(PATH2+"yasuo.zip");
        // 加密
        byte[] encryptZIP = PBEUtil.encrypt(zipBytes, passWrod, salt.getBytes());
        // 转为base64
        String encodeZIP = Base64Util.encryptBASE64(encryptZIP);
        // 输出密文
        FileUtils.fileWriteAdbMkdir(PATH2+"xxxxxxxx.txt",encode);
    }

    @Test
    public void test02() throws IOException {

    }
}
