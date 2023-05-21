package com.lsc.utils;

import com.alibaba.fastjson.TypeReference;
import com.lsc.bean.CommonUtil;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * @description:test工具
 * @author:
 * @date: 2021/11/18 10:54
 */
public class JunitBusiUtil {

    /**
     * 获取json文件中json串的实例
     *
     * @param jsonPath
     * @param type
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T parseObject(String jsonPath, TypeReference<T> type) throws IOException {
        return CommonUtil.parseObject(FileUtils.readFileToString(new File((JunitBusiUtil.class.getResource(jsonPath)).getPath()), "utf-8"), type);
    }

    public static <T> T parseObject(String jsonPath, Class<T> clazz) throws IOException {
        return CommonUtil.parseObject(FileUtils.readFileToString(new File((JunitBusiUtil.class.getResource(jsonPath)).getPath()), "utf-8"), clazz);
    }

}
