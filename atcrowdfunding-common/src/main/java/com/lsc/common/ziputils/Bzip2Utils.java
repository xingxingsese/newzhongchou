package com.lsc.common.ziputils;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @Description: bzip2压缩格式
 * bzip2是Julian Seward开发并按照自由软件／开源软件协议发布的数据压缩算法及程序。
 * <p>
 * Seward在1996年7月第一次公开发布了bzip2 0.15版，在随后几年中这个压缩工具稳定性得到改善并且日渐流行，Seward在2000年晚些时候发布了1.0版。
 * <p>
 * bzip2比传统的gzip的压缩效率更高，但是它的压缩速度较慢。
 * <p>
 * jdk中没有对bzip2实现，但是在commons-compress中进行了实现，maven引入：
 * <dependency>
 * <groupId>org.apache.commons</groupId>
 * <artifactId>commons-compress</artifactId>
 * <version>1.12</version>
 * </dependency>
 * @Author: lisc
 * @date: 2022/7/23
 */
public class Bzip2Utils {
    private static final Logger log = LoggerFactory.getLogger(Bzip2Utils.class);

    public static byte[] compress(byte srcBytes[]) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BZip2CompressorOutputStream bcos = null;
        try {
            bcos = new BZip2CompressorOutputStream(out);

            bcos.write(srcBytes);
            bcos.close();
        } catch (Exception e) {
            log.error("Bzip2Utils:compress 发生异常e:{}", e.getStackTrace(), e);
        }
        return out.toByteArray();
    }

    public static byte[] uncompress(byte[] bytes) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        try {
            BZip2CompressorInputStream ungzip = new BZip2CompressorInputStream(
                    in);
            byte[] buffer = new byte[2048];
            int n;
            while ((n = ungzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }
}
