package com.lsc.freemarker.core;

import com.alibaba.fastjson.JSONObject;
import com.lsc.freemarker.db.DataBaseConnection;
import com.lsc.freemarker.entity.Table;
import com.lsc.freemarker.enums.ResultOutPathEnum;
import com.lsc.freemarker.enums.TemplatePathEnum;
import freemarker.template.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lisc
 * @Description: 输入表名, 获取数据库表数据结构 生成sql文件
 * @date 2022/11/11 11:01
 */
@Slf4j
public class DataBaseTableToJava {

    @Test
    public void test() {

        tableSqlCodeGeneration("ibep_mp_company_information");
    }

    /**
     * 根据表名获取数据库内表内的列
     *
     * @param tableName
     * @return
     */
    public boolean tableSqlCodeGeneration(String tableName) {
        boolean result = true;
        try {
            // 1 创建FreeMarker的配置类
            Configuration instance = FreeMarkerGenerator.getInstance();

            // 2 指定模版加载器 + 3 获取模版
            FreeMarkerGenerator.generator(instance, TemplatePathEnum.TABLE_SQL_XML.getPath());

            // 4 构造数据模型  map的key就是模版内占位符的key
            Table tableData = DataBaseConnection.getDataBaseTableData(tableName);

            System.out.println(JSONObject.toJSONString(tableData));

            // 把构建的数据传递到模板内
            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("date", new Date());
            dataModel.put("table", tableData);

            // 5 代码生成  + 文件输出
            FreeMarkerGenerator.scanAndGenerator(dataModel, TemplatePathEnum.TABLE_SQL_XML.getPath(), ResultOutPathEnum.TABLE_RESULT_PATH.getPath());
        } catch (Exception e) {
            log.info("生成sql语句文件发生异常", e);
            result = false;
        }
        return result;
    }
}
