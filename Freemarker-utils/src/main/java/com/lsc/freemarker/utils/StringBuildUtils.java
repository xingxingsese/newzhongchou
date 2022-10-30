package com.lsc.freemarker.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @Description:
 * @Author: lisc
 * @date: 2022/5/11
 */
public class StringBuildUtils {

    /**
     * 我们都知道在ascii 码表中 a的ASCIII码值是65⽽A的ASCII码值是 97 两个之间的差值是 32，所以，如果我们需要把⼀个⼩写字母转化成
     * ⼤写字母，那么我们只需要把这个 char 字符的ascii码值减上 32，转成⼩写同理。
     */

    /**
     * 将字符串首字母转大写
     * @param str
     * @return
     */
    public static String buildUpperCaseStr(String str){

        // 判断首字母是否为小写
        if(!Character.isLowerCase(str.charAt(0))){
            return "null";
        }
        char[] chars = str.toCharArray();
        // 进行字母的ascli编码前移
        chars[0] -=32;
        return String.valueOf(chars);
    }

    /**
     * 将字符串首字母转小写
     * @param str
     * @return
     */
    public static String buildLowerCaseStr(String str){
        // 判断首字母是否为大写
        if(!Character.isUpperCase(str.charAt(0))){
            return "null";
        }
        // 进行字母的ascli编码前移
        char[] chars = str.toCharArray();
        chars[0] +=32;
        return String.valueOf(chars);
    }

    /**
     * 把class类转换为Json格式的文件
     * @param obj
     * @param url
     */
    public static void classJsonString(Object obj,String url){
        String contex = JSONObject.toJSONString(obj, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);
        FileUtils.fileWrite(url, obj.getClass().getSimpleName()+ ".json", contex);
    }
}
