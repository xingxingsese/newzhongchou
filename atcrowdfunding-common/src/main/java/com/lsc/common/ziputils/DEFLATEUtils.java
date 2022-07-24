package com.lsc.common.ziputils;

import java.io.ByteArrayOutputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * @Description:
 * DEFLATE压缩格式
 * DEFLATE是同时使用了LZ77算法与哈夫曼编码（Huffman Coding）的一个无损数据压缩算法。
 *
 * DEFLATE压缩与解压的源代码可以在自由、通用的压缩库zlib上找到，zlib官网：http://www.zlib.net/
 *
 * jdk中对zlib压缩库提供了支持，压缩类Deflater和解压类Inflater，Deflater和Inflater都提供了native方法
 * @Author: lisc
 * @date: 2022/7/23
 */
public class DEFLATEUtils {
    public static byte[] compress(byte input[]) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Deflater compressor = new Deflater(1);
        try {
            compressor.setInput(input);
            compressor.finish();
            final byte[] buf = new byte[2048];
            while (!compressor.finished()) {
                int count = compressor.deflate(buf);
                bos.write(buf, 0, count);
            }
        } finally {
            compressor.end();
        }
        return bos.toByteArray();
    }

    public static byte[] uncompress(byte[] input) throws DataFormatException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Inflater decompressor = new Inflater();
        try {
            decompressor.setInput(input);
            final byte[] buf = new byte[2048];
            while (!decompressor.finished()) {
                int count = decompressor.inflate(buf);
                bos.write(buf, 0, count);
            }
        } finally {
            decompressor.end();
        }
        return bos.toByteArray();
    }
}
