package com.lsc.freemarker.demo;

import com.lsc.freemarker.utils.FileUtils;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @Description: 存根
 * @Author: lisc
 * @date: 2022/5/9
 */
@NoArgsConstructor
@Slf4j
public class JarLoaderDemo {

    /**
     * 功能描述: 扫描一个文件夹下面的所有jar，不包含子文件夹和子jar
     *
     * @param directoryPath
     * @return:java.util.Map>
     * @since: v1.0
     * @Author:wangcanfeng
     * @Date: 2019/9/12-15:21
     */
    public static Map loadAllJarFromAbsolute(String directoryPath) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException {

        File directory = new File(directoryPath);

        // 判断是否为文件夹，如果是文件,直接用单个jar解析的方法去解析
        if (!directory.isDirectory()) {
            // 添加jar扫描路径
            //   addUrl(directory);

            return loadJarFromAbsolute(directoryPath);

        }

        // 如果是文件夹，则需要循环加载当前文件夹下面的所有jar
        Map clazzMap = new HashMap<>(16);

        // 获取这个文件夹下所有文件
        File[] jars = directory.listFiles();

        if (jars != null && jars.length > 0) {

            List<String> jarPath = new LinkedList<>();

            for (File file : jars) {
                String fPath = file.getPath();
                // 只加载jar
                if (fPath.endsWith(".jar")) {
                    //  addUrl(file);
                    jarPath.add(fPath);
                }
            }

            if (jarPath.size() > 0) {

                for (String path : jarPath) {

                    clazzMap.putAll(loadJarFromAbsolute(path));
                }
            }
        }
        return clazzMap;

    }

    /**
     * 功能描述: 从绝对路径中加载jar包中的类
     * 扫描指定jar包前需要将这个jar的地址加入了系统类加载器的扫描列表中
     * 注意，这里只支持单个jar的加载，如果这个jar还引入了其他jar依赖，会加载失败
     * 所以只能用来加载对其他jar包没有依赖的简单对象类信息
     *
     * @param path jar包路径加载地址
     * @return:java.util.Map>
     * @since: v1.0
     * @Author:wangcanfeng
     * @Date: 2019/9/12-14:14
     */

    public static Map loadJarFromAbsolute(String path) throws IOException {

        // 文件中读取 jar 文件的内容
        JarFile jar = new JarFile(path);

        Enumeration entryEnumeration = jar.entries();

        Map clazzMap = new HashMap<>(16);

        while (entryEnumeration.hasMoreElements()) {

            JarEntry entry = (JarEntry) entryEnumeration.nextElement();
            // 先获取类的名称，符合条件之后再做处理，避免处理不符合条件的类
            String clazzName = entry.getName();
            System.out.println("clazzName = " + clazzName);

            if (clazzName.endsWith(".class")) {

                // 去掉文件名的后缀
                clazzName = clazzName.substring(0, clazzName.length() - 6);

                // 替换分隔符
                clazzName = clazzName.replace("/", ".");

                // 加载类,如果失败直接跳过
                try {
                    Class clazz = Class.forName(clazzName);

                    // 将类名称作为键，类Class对象作为值存入mao
                    // 因为类名存在重复的可能，所以这里的类名是带包名的
                    clazzMap.put(clazzName, clazz);

                } catch (Throwable e) {
                    // 这里可能出现有些类是依赖不全的，直接跳过，不做处理，也没法做处理
                }

            }

        }
        return clazzMap;

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
  /*  public static void addUrl(File jarPath) throws NoSuchMethodException, InvocationTargetException,IllegalAccessException, MalformedURLException {
        // URLClassLoader 该类加载器用于从指向 JAR 文件和目录的 URL 的搜索路径加载类和资源。这里假定任何以 '/' 结束的 URL 都是指向目录的。如果不是以该字符结束，则认为该 URL 指向一个将根据需要打开的 JAR 文件。

        // 获取委托的系统类加载器
        URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();

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

    }*/

    /**
     * 功能描述: 添加需要扫描的jar包
     *
     * @param jarPath
     * @return:void
     * @since: v1.0
     * @Author:wangcanfeng
     * @Date: 2019/9/12-15:21
     */
    public static void addUrl(File jarPath, URLClassLoader classLoader) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, MalformedURLException {
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
                JarLoaderDemo.addUrl(file1, classLoader);
            }
            loadClass = classLoader.loadClass(classNamePath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return loadClass;
    }
}

