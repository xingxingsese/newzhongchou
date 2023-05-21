package com.lsc.freemarker.base;

import com.lsc.freemarker.core.FreeMarkerGenerator;
import com.lsc.freemarker.entity.MockResult;
import com.lsc.freemarker.enums.ResultOutPathEnum;
import com.lsc.freemarker.enums.TemplatePathEnum;
import freemarker.template.Configuration;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lisc
 * @Description: com.lsc.freemarker.base
 * @date 2022/11/11 18:16
 */
@Slf4j
public class FreeMarkerServiceTemplate {

    private FreeMarkerGenerator freeMarkerGenerator;

    /**
     * 生成代码的模板
     *
     * @param mockResult          结果
     * @param templatePath        模板路径
     * @param resultOutPath       文件生成路径
     * @param freeMarkerDataModel 构建模型对象
     */
    public static void execute(MockResult mockResult, TemplatePathEnum templatePath, ResultOutPathEnum resultOutPath, FreeMarkerDataModel freeMarkerDataModel) {
        try {
            // 1 创建FreeMarker的配置类
            Configuration instance = FreeMarkerGenerator.getInstance();

            // 2 指定模版加载器 + 3 获取模版
            FreeMarkerGenerator.generator(instance, templatePath.getPath());

            // 把构建的数据传递到模板内
            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("date", new Date());
            // 4 构造数据模型  map的key就是模版内占位符的key
            dataModel.put("mock", freeMarkerDataModel.buildModelData());

            // 5 代码生成  + 文件输出
            FreeMarkerGenerator.scanAndGenerator(dataModel, templatePath.getPath(), resultOutPath.getPath());

        } catch (Exception e) {
            mockResult.setSuccess(false);
            mockResult.setErrorMessage(e.getMessage());
            log.info("发生异常", e);
        }
        mockResult.setSuccess(true);
        log.info("FreeMarkerServiceTemplate:execute 执行完毕");
    }
}
