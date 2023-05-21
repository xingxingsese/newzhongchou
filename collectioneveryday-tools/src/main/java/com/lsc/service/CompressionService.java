package com.lsc.tools.service;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lisc
 * @Description: com.lsc.tools.service
 * @date 2022/9/27 18:32
 */

public interface CompressionService {
    /**
     * 加密压缩
     *
     * @param paths 文件的路径list
     * @return
     */
    String encryptedCompressed(List<String> paths, String outPath);

    /**
     * 解密解压
     */
    void decryptionDecompression(String path, String outPath);
}
