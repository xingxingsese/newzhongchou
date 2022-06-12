package com.lsc.service.impl;

import com.lsc.api.FreeMarkerService;
import com.lsc.bean.MockClassBean;
import com.lsc.bean.TCodeTemplate;
import com.lsc.constant.Constant;
import com.lsc.freemarker.core.Generator;
import com.lsc.freemarker.utils.JarLoaderUtils;
import com.lsc.mapper.TCodeTemplateMapper;
import freemarker.template.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: FreeMarker 相关接口实现类
 * @Author: lisc
 * @date: 2022/5/4
 */
@Service
public class FreeMarkerServiceImpl implements FreeMarkerService {

    private static final Logger log = LoggerFactory.getLogger(FreeMarkerServiceImpl.class);

    @Autowired
    TCodeTemplateMapper tCodeTemplateMapper;


    /**
     * 根据 javaBeanJson文件生成代码
     *
     * @param templatePath 模版路径
     * @param outPath      输出文件路径
     * @param jsonTextFile json文本路径
     * @return
     */
    @Override
    public Boolean beanJsonBuildCode(String templatePath, String outPath, Map jsonTextFile) {

        Boolean result = false;

        try {
            // 1 创建FreeMarker的配置类
            Configuration instance = Generator.getInstance();

            // 2 指定模版加载器 + 3 获取模版
            Generator.generator(instance, templatePath);

            // 4 构造数据模型  map的key就是模版内占位符的key
            Map<String, Object> dataModel = jsonTextFile;//Generator.getDataModel(jsonTextFile);
            // 5 代码生成  + 文件输出
            Generator.scanAndGenerator(dataModel, templatePath, outPath);

            result = true;
        } catch (Exception e) {
            log.error("根据json文件生成java代码异常", e);
        }

        return result;
    }

    /**
     * 根据jar包生成指定类的mock代码
     *
     * @param jarPath   jar包路径
     * @param classPath 要生成类的全类名
     * @param outPath   输出mock代码位置
     * @return
     */
    @Override
    public Boolean jarDataBuildMockCode(String jarPath, String classPath, String outPath) {
        Boolean result = false;

        try {
            // 1 创建FreeMarker的配置类
            Configuration instance = Generator.getInstance();

            // 2 指定模版加载器 + 3 获取模版
            Generator.generator(instance, "templatePath");

            // 根据指定jar包路径 需要生成类的全类名获取反射对象
            Class<?> clazz = JarLoaderUtils.getClassObject(jarPath, classPath);
            // 组装数据
            MockClassBean mockClassBean = JarLoaderUtils.buildSourceData(classPath, outPath, clazz);

            // 4 构造数据模型  map的key就是模版内占位符的key
            Map<String, Object> dataModel = new HashMap<String, Object>() {
                {
                    put("mock", mockClassBean);
                    put("date", new Date());

                }
            };

            // 5 代码生成  + 文件输出
            Generator.scanAndGenerator(dataModel, "templatePath", outPath);
            result = true;
        } catch (Exception e) {
            log.error("生成mock代码异常", e);
        }

        return result;
    }

    /**
     * 查询所有模版
     *
     * @return
     */
    @Override
    public List<TCodeTemplate> selectListCodeTemplate() {
        return tCodeTemplateMapper.selectByExample(null);
    }

    /**
     * 根据id查询模版
     *
     * @param id
     * @return
     */
    @Override
    public TCodeTemplate selectById(Integer id) {
        return tCodeTemplateMapper.selectByPrimaryKey(id);
    }

    /**
     * 修改模版
     *
     * @param tCodeTemplate
     */
    @Override
    public String updateCodeTemplate(TCodeTemplate tCodeTemplate) {
        int i = tCodeTemplateMapper.updateByPrimaryKey(tCodeTemplate);
        return i > 0 ? Constant.OK : Constant.FAIL;
    }

    /**
     * 保存模版
     *
     * @param tCodeTemplate
     */
    @Override
    public String savaCodeTemplate(TCodeTemplate tCodeTemplate) {
        int i = tCodeTemplateMapper.insertSelective(tCodeTemplate);
        return i > 0 ? Constant.OK : Constant.FAIL;
    }

    /**
     * 删除模版
     *
     * @param id
     */
    @Override
    public String deleteCodeTemplate(Integer id) {
        int i = tCodeTemplateMapper.deleteByPrimaryKey(id);
        return i > 0 ? Constant.OK : Constant.FAIL;
    }
}
