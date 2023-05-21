package com.lsc.demo;

import com.lsc.common.FileUtils;

import com.lsc.controller.CompressionUIThree;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lisc
 * @Description: com.lsc.freemarker.demo
 * @date 2022/10/28 19:05
 */
public class TestDemo {

    @Test
    public void test() {
        List<File> files = FileUtils.searchAllFile(new File("D:\\Code\\TestCode\\newzhongchou\\everyday-tools\\src\\main\\java\\com\\lsc\\tools\\"));
        List<String> collect = files.stream().map(fi -> fi.getAbsolutePath()).collect(Collectors.toList());
        CompressionUIThree.encryptedCompressed(collect);
    }
}
