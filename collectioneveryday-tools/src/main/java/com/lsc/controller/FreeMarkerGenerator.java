package com.lsc.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lsc.freemarker.entity.FieldBean;
import com.lsc.freemarker.entity.FreeMarkerDataBean;
import com.lsc.freemarker.entity.MethodBean;
import com.lsc.freemarker.utils.CustomClassLoader;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author lisc
 * @Description: com.lsc.freemarker.core
 * @date 2022/10/9 14:22
 */
public class FreeMarkerGenerator {

    private static final Logger log = LoggerFactory.getLogger(FreeMarkerGenerator.class);

    // FreeMarker 连接对象
    private static Configuration cfg = null;

    // 模版路径
    private static String TEMPLATE_PATH = "D:\\Code\\TestCode\\newzhongchou\\Freemarker-utils\\src\\main\\resources\\FreeMarkerFile\\CodeTemplates";


    private static String MOCK_TEMPLATE_PATH = "D:\\Code\\TestCode\\newzhongchou\\Freemarker-utils\\src\\main\\resources\\FreeMarkerFile\\mock-templates";


    private static String OUT_ROOT_PATH = "D:\\Code\\TestCode\\newzhongchou\\Freemarker-utils\\src\\main\\resources\\FreeMarkerFile\\SpannedFile\\";


    public static void main(String[] args) {
        try {
            FreeMarkerGenerator freeMarkerGenerator = new FreeMarkerGenerator();
            freeMarkerGenerator.mockCodeGenerator("com.ipay.iexpbizprod.partner.imgs.impl.RevivalMerchantRpcFacadeImpl");
        } catch (Exception e) {
            System.out.println("main方法catch" + e);
            e.printStackTrace();
        }
    }

    @Test
    public void test() throws ClassNotFoundException {
        ClassLoader loader = this.getClass().getClassLoader();
        Class<?> loadClass = loader.loadClass("com.ipay.ibizecoprod.dal.ibizecoprod.dataobject.IbepFormDataRecordDO");
        CreationJsonTxt(loadClass);
    }

    /**
     * 生成mock代码
     *
     * @param classPath
     */
    public static String mockCodeGenerator(String classPath) {
        String generatorPath = StringUtils.EMPTY;
        try {
            DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            String dateTime = df.format(new Date());

            String outPath = OUT_ROOT_PATH + dateTime;

            // 1 创建FreeMarker的配置类
            Configuration instance = getInstance();

            // 2 指定模版加载器 + 3 获取模版
            generator(instance, MOCK_TEMPLATE_PATH);
            // 构建参数
            FreeMarkerDataBean freeMarkerDataBean = classPathCreateMock(classPath, outPath);

            if (freeMarkerDataBean == null) {
                return generatorPath = "生成错误,请确认路径是否正确";
            }
            // 4 构造数据模型  map的key就是模版内占位符的key
            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("date", new Date());
            dataModel.put("mock", freeMarkerDataBean);

            // 5 代码生成  + 文件输出
            generatorPath = scanAndGenerator(dataModel, MOCK_TEMPLATE_PATH, outPath);

        } catch (Throwable e) {
            log.info("mockCodeGenerator方法异常 : {}", JSONObject.toJSONString(e), e);
        } finally {
            log.info("代码生成完成!");
        }
        return generatorPath;
    }

    /**
     * 获取FreeMarker对象
     * 1 创建FreeMarker的配置类
     *
     * @return Configuration
     */
    public static Configuration getInstance() {
        if (cfg == null) {
            synchronized (FreeMarkerGenerator.class) {
                cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
            }
        }
        return cfg;
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
            log.info("获取模版路径出错, templatePath:{}", templatePath, e);
        }
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
        //1  根据模版路径找到此路径下所有模版文件
        List<File> fileList = FileUtils.searchAllFile(new File(templatePath));
        for (File file : fileList) {
            try {
                executeGenertor(dataModel, file, templatePath, outPath);
            } catch (Exception e) {
                log.error("对模版进行代码生成时报错! dataModel:{}", dataModel, e);
            }
        }
        return outPath;
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

    /**
     * 根据给定的类,反射出他所有的方法
     *
     * @param classPath
     * @return
     */
    public static FreeMarkerDataBean classPathCreateMock(String classPath, String outPathJson) {
        // 返回结果bean
        FreeMarkerDataBean classBean = new FreeMarkerDataBean();

        // 创建自定义的类加载器
        CustomClassLoader loader = new CustomClassLoader();
        try {
            // 使用自定义的类加载器加载TestHelloWorld类
            Class classaa = loader.loadClass(classPath);
            if (null == classaa) {
                log.info("classPathCreateMock 未加载到相关class文件");
                return null;
            }
            // 获取全类名
            String classPathName = classaa.getName();

            int lastIndexOf = classPathName.lastIndexOf('.');
            // 得到类名
            classBean.setClassName(classPathName.substring(lastIndexOf + 1));
            // 得到包名
            classBean.setClassPath(classPathName.substring(0, lastIndexOf));

            /**
             * 获取类下所有自定义属性
             */
            List<FieldBean> fieldBeans = ReflectionUtil.getFieldBeans(classaa);
            classBean.setFieldBeanList(fieldBeans);

            // 得到类下所有的方法及方法出入参
            ArrayList<MethodBean> methodList = ReflectionUtil.getMethodBeans(classaa, outPathJson);
            classBean.setMethodBeanList(methodList);


        } catch (IllegalArgumentException e) {
            log.info("本地仓库此路径不存在,程序结束", e);
        } catch (NoClassDefFoundError error) {
            log.info("NoClassDefFoundError ", error);
            try {
                // 把全类名多余的信息过滤掉
                int indexOf = error.getMessage().indexOf("(");
                String errorClassName = error.getMessage().substring(0, indexOf).trim();
                loader.loadClass(errorClassName);
            } catch (ClassNotFoundException e) {
                log.info("二次查询依然没有找到所需对象:{}", e);
            }
        } catch (Throwable e) {
            log.info("Throwable  执行 异常类型: {}", e);
        }
        log.info("classPathCreateMock 执行结束, classBean:{}", JSONObject.toJSONString(classBean));
        return classBean;
    }


    private void CreationJsonTxt(Class<?> aclass) {
        if (!aclass.isInterface()) {
            Object instance = null;
            try {
                instance = aclass.newInstance();
                String contex = JSONObject.toJSONString(instance, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);
                FileUtils.fileWrite("D:\\Code\\TestCode\\newzhongchou\\Freemarker-utils\\FreeMarkerFile\\SpannedFile\\json", aclass.getSimpleName() + ".json", contex);
            } catch (Exception e) {
                log.error("CreationJsonTxt 创建json文本对象时出错!!!e:{}", e);
            }
        }
    }


}
