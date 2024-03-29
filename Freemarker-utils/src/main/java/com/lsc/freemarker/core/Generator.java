package com.lsc.freemarker.core;

import com.alibaba.fastjson.JSONObject;
import com.lsc.freemarker.base.FreeMarkerDataModel;
import com.lsc.freemarker.base.FreeMarkerServiceTemplate;
import com.lsc.freemarker.entity.MockResult;
import com.lsc.freemarker.entity.SetMethodBean;
import com.lsc.freemarker.enums.ResultCodeEnum;
import com.lsc.freemarker.entity.CrudBean;
import com.lsc.freemarker.enums.ResultOutPathEnum;
import com.lsc.freemarker.enums.TemplatePathEnum;
import com.lsc.freemarker.utils.FileUtils;
import com.lsc.freemarker.utils.ReflectionUtil;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by lisc on 2021/12/19
 * 代码生成核心处理类
 * 是用Freemarker 完成文件生成
 * 数据模型 + 模版
 * 数据:
 * 数据模型
 * 模版位置
 * 生成文件的路径
 */

public class Generator {

    private static final Logger log = LoggerFactory.getLogger(Generator.class);
    // 模版路径
    private static final String TEMPLATE_PATH = "D:\\Code\\TestCode\\newzhongchou\\Freemarker-utils\\src\\main\\resources\\FreeMarkerFile\\CodeTemplates";

    // 生成类路径
    private static final String OUT_PATH = "D:\\Code\\TestCode\\newzhongchou\\Freemarker-utils\\src\\main\\resources\\FreeMarkerFile\\SpannedFile\\crud";

    // FreeMarker 连接对象
    private static Configuration cfg = null;

    /**
     * 1 创建FreeMarker的配置类
     * 2 制定模版加载器: 将模版存入缓存中
     * ClassTemplateLoader() 类路径
     * FileTemplateLoader() 文件路径加载器
     * StringTemplateLoader() 文本
     * URLTemplateLoader() url路径
     * WebappTemplateLoader web应用程序下
     * 3 获取模版
     * 4 构造数据模型  map的key就是模版内占位符的key
     * 5 文件输出
     */
    public static String generateCrudCode(String rootPath, String packagePath, String className, String tableName) {
        log.info("Generator:generateCrudCode:rootPath:{},packagePath:{},className:{},tableName:{}", rootPath, packagePath, className, tableName);
        if (StringUtils.isEmpty(rootPath)) {
            log.info("Generator:generateCrudCode: rootPath为null");
            return ResultCodeEnum.FAILURE.getCode();
        }
        String path = StringUtils.EMPTY;
        if (rootPath.contains("iexpbizprod")) {
            path = TEMPLATE_PATH + "\\iexpbizprod";
        } else {
            path = TEMPLATE_PATH + "\\ibizecoprod";
        }
        // 元数据JSON文件路径
        //String jsonTextFile = "E:\\TestCodeDome\\atcrowdfunding\\Freemarker-utils\\FreeMarkerFile\\MetaDataFile";

        // 1 创建FreeMarker的配置类
        Configuration instance = getInstance();

        // 2 指定模版加载器 + 3 获取模版
        generator(instance, path);

        // 4 构造数据模型  map的key就是模版内占位符的key
        // Map<String, Object> dataModel = getDataModel(jsonTextFile);
        CrudBean crudBean = CrudBean.builder()
                .rootPath(rootPath).packagePath(packagePath).className(className).tableName(tableName)
                .build();


        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("date", new Date());
        dataModel.put("mock", crudBean);

        // 5 代码生成  + 文件输出
        return scanAndGenerator(dataModel, path, OUT_PATH);

    }


    /**
     * 给FreeMarker 设置模版加载器, 指定模版路径
     * 2 指定模版加载器
     * ClassTemplateLoader() 类路径
     * FileTemplateLoader() 文件路径加载器
     * StringTemplateLoader() 文本
     * URLTemplateLoader() url路径
     * WebappTemplateLoader web应用程序下
     *
     * @param templatePath 模版路径
     */
    public static void generator(Configuration cfg, String templatePath) {
        try {

            //  指定模版加载器
            FileTemplateLoader ftl = new FileTemplateLoader(new File(templatePath));
            cfg.setTemplateLoader(ftl);
        } catch (IOException e) {
            System.out.println("获取模版路径出错");
        }
    }

    /**
     * 获取FreeMarker对象
     * 1 创建FreeMarker的配置类
     *
     * @return Configuration
     */
    public static Configuration getInstance() {
        if (cfg == null) {
            synchronized (Generator.class) {
                cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
            }
        }
        return cfg;
    }

    /**
     * 返回数据模型
     * 根据json格式文本返回一个map对象
     *
     * @param jsonPath json文件路径
     * @return
     */
    public static Map<String, Object> getDataModel(String jsonPath) {
        String jsonStr = "";
        Map map = new HashMap();
        try {
            File jsonFile = new File(jsonPath);
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile), "utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            map = JSONObject.parseObject(jsonStr, Map.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }


    /**
     * 代码生成
     * 1 扫描模版路径下所有模版
     * 2 对每个模版进行文件生成
     *
     * @param dataModel    数据模型
     * @param templatePath 模版路径
     * @param outPath      输出代码路径
     */
    public static String scanAndGenerator(Map<String, Object> dataModel, String templatePath, String outPath) {
        String result = ResultCodeEnum.SUCCESS.getCode();
        //1  根据模版路径找到此路径下所有模版文件
        List<File> fileList = FileUtils.searchAllFile(new File(templatePath));
        for (File file : fileList) {
            try {
                executeGenertor(dataModel, file, templatePath, outPath);
            } catch (Exception e) {
                result = ResultCodeEnum.FAILURE.getCode();
                log.error("对模版进行代码生成时报错!", e);
            }
        }
        return result;
    }


    /**
     * 对模版进行代码生成
     *
     * @param dataModel    数据模型
     * @param file         模版路径下单个模版文件
     * @param templatePath
     * @param outPath
     * @throws Exception
     */
    public static void executeGenertor(Map<String, Object> dataModel, File file, String templatePath, String outPath) throws Exception {
        // 1 文件路径处理 getAbsolutePath() 获取到当前file的绝对路径
        // E:\TestCodeDome\FreeMarkerUtils\freemarker-template\templates\outtemplates\
        //      ${path1}\${path2}\${path3}\${className}.java
        // 把前面的路径替换成空,只处理后面的路径
        String templateFileName = file.getAbsolutePath().replace(templatePath, "");
        String stringTemplate = processStringTemplate(templateFileName, dataModel);

        // 2 读取文件模版
        Template template = cfg.getTemplate(templateFileName);
        // 指定生成文件的字符集编码
        template.setOutputEncoding("utf-8");

        // 3 创建文件
        File file1 = FileUtils.mkdir(outPath, stringTemplate);

        // 4 文件生成
        FileWriter fw = new FileWriter(file1);
        template.process(dataModel, fw);
        fw.close();
    }

    public static String processStringTemplate(String templateString, Map<String, Object> dataModel) throws Exception {
        StringWriter out = new StringWriter();
        /**
         *  创建字符串模版
         */
        Template template = new Template("StringTemplate", new StringReader(templateString), cfg);
        // 输出到out
        template.process(dataModel, out);
        return out.toString();
    }

    @Test
    public void test() {
        String name = "com.lsc.freemarker.entity.Bean";
        ClassLoader classLoader = new CustomClassLoader();

        MockResult<String> result = new MockResult();
        FreeMarkerServiceTemplate.execute(result, TemplatePathEnum.SET_METHOD_PATH, ResultOutPathEnum.SET_METHOD_PATH, new FreeMarkerDataModel<SetMethodBean>() {
            @Override
            public SetMethodBean buildModelData() throws Exception {
                Class<?> aClass = classLoader.loadClass(name);
                List<Field> fieldList = ReflectionUtil.getDeclaredFields(aClass);

                ArrayList<Method> methods = new ArrayList<>();
                for (Field field : fieldList) {
                    Method method = ReflectionUtil.getObjectSetMethod(aClass, field);
                    if (null != method) {
                        methods.add(method);
                    }
                }
                log.info("set方法执行完毕");
                List<String> methodList = methods.stream().map(Method::getName).collect(Collectors.toList());
                return SetMethodBean.builder().className(aClass.getSimpleName()).methodsName(methodList).build();

            }
        });
    }
}
