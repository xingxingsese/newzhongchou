package com.lsc.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @Description:
 * @Author: lisc
 * @date: 2022/4/21
 */
public class commonUtils {
    public String CreationJsonText(Class<?> aclass){
        if (!aclass.isInterface()) {
            return "fail";
        }
        try {
            Object instance = aclass.newInstance();
            String jsonString = JSONObject.toJSONString(instance, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);
            // 希望输出文件到哪个路径
           /* String path = "";
            FileUtils.fileWrite(path,jsonString);*/




        } catch (Exception e) {
            System.out.println("CreationJsonText 创建json文本对象时出错!");
        }
        return "ok";
    }
}
