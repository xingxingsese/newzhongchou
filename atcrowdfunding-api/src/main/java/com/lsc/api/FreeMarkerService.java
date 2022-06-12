package com.lsc.api;

import com.lsc.bean.TCodeTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: FreeMarker 相关接口
 * @Author: lisc
 * @date: 2022/5/4
 */
public interface FreeMarkerService {



    /**
     * 根据 javaBeanJson文件生成代码
     * @param templatePath 模版路径
     * @param outPath 输出文件路径
     * @param jsonTextFile json文本路径
     * @return
     */
    Boolean beanJsonBuildCode(String templatePath, String outPath, Map jsonTextFile);

    /**
     * 根据jar包生成指定类的mock代码
     * @param jarPath jar包路径
     * @param classPath 要生成类的全类名
     * @param outPath 输出mock代码位置
     * @return
     */
    Boolean jarDataBuildMockCode(String jarPath,String classPath,String outPath);


    /**
     * 查询所有模版
     * @return
     */
    List<TCodeTemplate> selectListCodeTemplate();

    /**
     * 根据id查询模版
     * @param id
     * @return
     */
    TCodeTemplate selectById(Integer id);

    /**
     * 修改模版
     * @param tCodeTemplate
     */
    String updateCodeTemplate(TCodeTemplate tCodeTemplate);

    /**
     * 保存模版
     * @param tCodeTemplate
     */
    String savaCodeTemplate(TCodeTemplate tCodeTemplate);

    /**
     * 删除模版
     * @param id
     */
    String deleteCodeTemplate(Integer id);
}
