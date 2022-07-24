package com.lsc.common.ziputils;

import org.xerial.snappy.Snappy;

import java.io.IOException;

/**
 * @Description:
 * snappy压缩格式
 * Snappy（以前称Zippy）是Google基于LZ77的思路用C++语言编写的快速数据压缩与解压程序库，并在2011年开源。
 *
 * 它的目标并非最大压缩率或与其他压缩程序库的兼容性，而是非常高的速度和合理的压缩率。
 *
 * maven引入第三方库：
 *  * <dependency>
 *  *     <groupId>org.xerial.snappy</groupId>
 *  *     <artifactId>snappy-java</artifactId>
 *  *     <version>1.1.2.6</version>
 * </dependency>
 * @Author: lisc
 * @date: 2022/7/23
 */
public class SnappyUtils {
    public static byte[] compress(byte srcBytes[]) throws IOException {
        return  Snappy.compress(srcBytes);
    }

    public static byte[] uncompress(byte[] bytes) throws IOException {
        return Snappy.uncompress(bytes);
    }
}
