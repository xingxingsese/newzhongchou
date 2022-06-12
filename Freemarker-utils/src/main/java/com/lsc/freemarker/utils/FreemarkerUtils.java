package com.lsc.freemarker.utils;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @Description: FreeMarkerDemo
 * @Author: lisc
 * @date: 2022/5/4
 */
public class FreemarkerUtils {



    public void FileTemplateLoader(String filePath) throws Exception {
        // 1 创建FreeMarker的配置类
        Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);

        // 2 制定模版加载器: 将模版存入缓存中
        // ClassTemplateLoader() 类路径
        // FileTemplateLoader() 文件路径加载器
        // StringTemplateLoader() 文本
        // URLTemplateLoader() url路径
        // WebappTemplateLoader web应用程序下
        FileTemplateLoader ftl = new FileTemplateLoader(new File(filePath));
        // 设置模版
        cfg.setTemplateLoader(ftl);

        // 3 获取模版
        Template template = cfg.getTemplate("template01.ftl");
        // 4 构造数据模型  map的key就是模版内占位符的key
        HashMap<String, Object> dateModel = new HashMap<>();
        dateModel.put("username", "何以解忧");
        // 测试if指令
        dateModel.put("flag", 1);
        // 测试list指令
        ArrayList<String> list = new ArrayList<>();
        list.add("星期一");
        list.add("星期二");
        list.add("星期三");
        list.add("星期四");
        list.add("星期五");
        dateModel.put("weeks", list);



        // 5 文件输出
        /**
         * 文件输出 也叫处理模型
         *      参数一: 数据模型
         *      参数二: writer(FileWriter(文件输出), printWriter(控制台输出) )
         */
        // 指定文件输出位置
        /*template.process(dateModel,new FileWriter(
                // 文件输出的位置
                new File("E:\\TestCodeDome\\FreeMarkerUtils\\freemarker-template\\templates\\outtemplates\\a.txt")));
*/
        // 指定输出到控制台
        template.process(dateModel, new PrintWriter(System.out));

    }
}
