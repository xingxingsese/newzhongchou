package com.lsc.common.ziputils;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Description:
 * @Author: lisc
 * @date: 2022/7/23
 */
public class YasuoTest {

    public static void main(String[] args) throws Exception {

        FileInputStream fis = new FileInputStream(new File("C:\\Users\\zuiho\\Desktop\\源文件文件夹\\zipTest.txt"));
        FileChannel channel = fis.getChannel();
        ByteBuffer bb = ByteBuffer.allocate((int) channel.size());
        channel.read(bb);
        byte[] beforeBytes = bb.array();

        int times = 1;
        System.out.println("压缩前大小：" + beforeBytes.length + " bytes");
        long startTime1 = System.currentTimeMillis();
        byte[] afterBytes = null;
        for (int i = 0; i < times; i++) {
            afterBytes = Bzip2Utils.compress(beforeBytes); // 736320
            // afterBytes = DEFLATEUtils.compress(beforeBytes);
            //  afterBytes = GzipUtils.compress(beforeBytes);
            //  afterBytes = Lz4Utils.compress(beforeBytes);
            //   afterBytes = LzoUtils.compress(beforeBytes);
            //  afterBytes = SnappyUtils.compress(beforeBytes);
            //  afterBytes = ZLibUtils.compress(beforeBytes);
        }
        long endTime1 = System.currentTimeMillis();
        System.out.println("压缩后大小：" + afterBytes.length + " bytes");
        System.out.println("压缩次数：" + times + "，时间：" + (endTime1 - startTime1)
                + "ms");

        byte[] resultBytes = null;
        long startTime2 = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            resultBytes = Bzip2Utils.uncompress(afterBytes); // 381
            //  resultBytes = DEFLATEUtils.uncompress(afterBytes); // 6257
            //  resultBytes = GzipUtils.uncompress(afterBytes); // 3018
            //   resultBytes = Lz4Utils.uncompress(afterBytes); // 59323
            //     resultBytes = LzoUtils.uncompress(afterBytes); // 6611
            //  resultBytes = SnappyUtils.uncompress(afterBytes); // 35959
            //   resultBytes = ZLibUtils.decompress(afterBytes); // 3006
        }
        System.out.println("解压缩后大小：" + resultBytes.length + " bytes");
        long endTime2 = System.currentTimeMillis();
        System.out.println("解压缩次数：" + times + "，时间：" + (endTime2 - startTime2)
                + "ms");
    }
}
