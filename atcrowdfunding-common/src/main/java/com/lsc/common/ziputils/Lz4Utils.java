package com.lsc.common.ziputils;

import net.jpountz.lz4.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @Description: lz4压缩格式
 * LZ4是一种无损数据压缩算法，着重于压缩和解压缩速度更多wikilz4
 * <p>
 * maven引入第三方库：
 * <dependency>
 * <groupId>net.jpountz.lz4</groupId>
 * <artifactId>lz4</artifactId>
 * <version>1.2.0</version>
 * </dependency>
 * @Author: lisc
 * @date: 2022/7/23
 */
public class Lz4Utils {
    public static byte[] compress(byte srcBytes[]) throws IOException {
        LZ4Factory factory = LZ4Factory.fastestInstance();
        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
        LZ4Compressor compressor = factory.fastCompressor();
        LZ4BlockOutputStream compressedOutput = new LZ4BlockOutputStream(
                byteOutput, 2048, compressor);
        compressedOutput.write(srcBytes);
        compressedOutput.close();
        return byteOutput.toByteArray();
    }

    public static byte[] uncompress(byte[] bytes) throws IOException {
        LZ4Factory factory = LZ4Factory.fastestInstance();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        LZ4FastDecompressor decompresser = factory.fastDecompressor();
        LZ4BlockInputStream lzis = new LZ4BlockInputStream(
                new ByteArrayInputStream(bytes), decompresser);
        int count;
        byte[] buffer = new byte[2048];
        while ((count = lzis.read(buffer)) != -1) {
            baos.write(buffer, 0, count);
        }
        lzis.close();
        return baos.toByteArray();
    }
}
