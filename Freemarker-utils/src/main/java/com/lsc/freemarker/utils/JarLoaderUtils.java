package com.lsc.freemarker.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

/**
 * @Description: 类加载器工具类 主要用于反射
 * @Author: lisc
 * @date: 2022/5/9
 */
@NoArgsConstructor
@Slf4j
public class JarLoaderUtils {


    /**
     * 根据jar包路径,加载路径下所有的jar包,根据指定的class名 获取反射的class对象
     *
     * @param jarPath       jar包路径
     * @param classNamePath 要反射的class全路径
     * @return
     */
    public static Class<?> getClassObject(String jarPath, String classNamePath) {
        Class<?> loadClass = null;
        try {
            // 获取委托的系统类加载器
            URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();

            File file = new File(jarPath);

            // 递归遍历路径下的jar包
            List<File> files = FileUtils.searchAllJarFile(file);
            // 遍历得到的jar包路径
            for (File file1 : files) {
                // 把指定路径下的jar文件添加到虚拟机
                addUrl(file1, classLoader);
            }
            loadClass = classLoader.loadClass(classNamePath);
        } catch (Exception e) {
            log.info("发生异常,异常信息 e:{}, getMessage:{}", e, e.getMessage());
        }

        return loadClass;
    }

    /**
     * 功能描述: 添加需要扫描的jar包
     *
     * @param jarPath
     * @return:void
     * @since: v1.0
     * @Author:wangcanfeng
     * @Date: 2019/9/12-15:21
     */
    private static void addUrl(File jarPath, URLClassLoader classLoader) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, MalformedURLException {
        // URLClassLoader 该类加载器用于从指向 JAR 文件和目录的 URL 的搜索路径加载类和资源。这里假定任何以 '/' 结束的 URL 都是指向目录的。如果不是以该字符结束，则认为该 URL 指向一个将根据需要打开的 JAR 文件。

        // 反射获取类加载器中的addURL方法，并将需要加载类的jar路径 反射获取URL.class类的addURL()方法
        Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);

        // 反射访问属性或方法时将Accessible设置为true
        if (!method.isAccessible()) {
            method.setAccessible(true);
        }
        // 把文件路径转为URL对象
        URL url = jarPath.toURI().toURL();
        // 把当前jar的路径加入到类加载器需要扫描的路径
        method.invoke(classLoader, url);

    }

    /**
     * 生成json文件
     *
     * @param clazz
     */
    public static void CreationJsonText(Class<?> clazz, String outPath) {
        // 判断是否是一个接口
        if (clazz.isInterface()) {
            log.info("className:{} 该文件是一个接口,生成json文件失败", clazz.getName());
            return;
        }
        try {
            Object instance = clazz.newInstance();
            String content = JSONObject.toJSONString(instance, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);
            FileUtils.fileWrite(outPath, clazz.getSimpleName()+".json", content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断当前类型是否是基本类型 或 包装类型 或String
     * 如果非以上类型的话,会报错, catch里会返回false;
     * @param clazz
     * @return
     */
    public static boolean isWrapClass(Class clazz) {
        try {
            if (clazz.equals(String.class) || clazz.isPrimitive()) {
                System.out.println(clazz.getName() + "当前是基本类型");
                return true;
            }
            return ((Class) clazz.getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) {
            return false;
        }

    }
}

