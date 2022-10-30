package com.lsc.freemarker.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author lisc
 * @Description: 自定义类加载器
 * 主要用于加载当前项目下不存在的jar包
 * @date 2022/9/30 14:34
 */


public class CustomClassLoader extends ClassLoader {

    public Logger log = LoggerFactory.getLogger(this.getClass());

    // maven仓库根路径
    public static final String ROOT_PATH = "D:\\maven\\Repoistory\\";

    private static final String SUFFIX = ".class";


    @Override
    public Class<?> findClass(String name) {
        log.info("findclass方法开始执行");

        // 检查该类的class文件是否已被加载，如果已加载则返回class文件(字节码文件)对象，如果没有加载返回null
        Class<?> loadedClass = findLoadedClass(name);
        // 如果已加载直接返回该类的class文件(字节码文件)对象
        if (loadedClass != null) {
            log.info("findLoadedClass : 找到class");
            return loadedClass;
        }
        String rootPath = "D:\\Code\\javaCore\\ibizecoprod\\app";
        if (name.contains("iexpbizprod")) {
            rootPath = "D:\\Code\\javaCore\\iexpbizprod\\app";
        }
        List<String> classFilePath = getClassFilePath(rootPath);

        // 如果没查到的话 加载项目下class文件
        loadedClass = loadedPathClassFile(name, classFilePath);
        if (loadedClass != null) {
            log.info("loadedPathClassFile : 找到class");
            return loadedClass;
        }
        // 如果没查到的话,就调用自定义的方法查找jar包
        loadedClass = JarLoaderUtils.getClassObject(ROOT_PATH, name);

        log.info("findclass方法结束执行");
        return loadedClass;
    }


    /**
     * 获取class文件的本地文件路径
     *
     * @param classPath 例如/usr/java/classes下有一个test.App类，则/usr/java/classes即这个类的根路径，而.class文件的实际位置是/usr/java/classes/test/App.class
     */
    public List<String> getClassFilePath(String classPath) {
        log.info("获取本地class文件: {}",classPath);
        // 设置class文件所在根路径
        File clazzPath = new File(classPath);

        ArrayList<String> pathList = new ArrayList<>();
        try {
            // 是否存在, 是否是目录
            if (clazzPath.exists() && clazzPath.isDirectory()) {
                // 获取路径长度
                int clazzPathLen = clazzPath.getAbsolutePath().length() + 1;

                Stack<File> stack = new Stack<>();
                stack.push(clazzPath);

                // 遍历类路径
                while (stack.isEmpty() == false) {
                    // 移除堆栈顶部的对象，并作为此函数的值返回该对象。
                    File path = stack.pop();

                    File[] classFiles = path.listFiles(new FileFilter() {
                        public boolean accept(File pathname) {
                            // 留下 是文件夹 并且文件夹名不包含test 或者 是.class文件且不包含$
                            return (pathname.isDirectory() && !pathname.getName().contains("test")) || (pathname.getName().endsWith(".class") ); // && !pathname.getName().contains("$")
                        }
                    });

                    for (File subFile : classFiles) {
                        if (subFile.isDirectory()) {
                            stack.push(subFile);
                        } else {
                            pathList.add(subFile.getPath());
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.info("获取class文件本地地址异常", e);
        }
        return pathList;
    }

    /**
     * 加载本地编译后的class文件
     *
     * @param name
     * @return
     */
    private Class<?> loadedPathClassFile(String name, List<String> classFiles) {
        if (classFiles.isEmpty()) {
            log.info("classFile is null");
            return null;
        }

        for (String classFile : classFiles) {
/*
            int lastIndexOf = classFile.lastIndexOf('\\');
            int indexOf = classFile.lastIndexOf('.');
            String listClassFile = classFile.substring(lastIndexOf + 1, indexOf);

            int index = name.lastIndexOf('.');
            String findClassPath = name.substring(index + 1);

            if (!listClassFile.equals(findClassPath)) {
                continue;
            }
          //  log.info("listName: {}, findClassPath: {}", listClassFile, findClassPath);
          */


            // 把// 替换成 .
            String replaceAll = classFile.replaceAll("\\\\", ".");
            int com = replaceAll.indexOf("com");
            int lastIndexOf = replaceAll.lastIndexOf(".");
            String substring = replaceAll.substring(com,lastIndexOf);
            if (!substring.equals(name)) {
                continue;
            }
          //   log.info("replaceAll: {}, findName: {}", replaceAll, name);

            byte[] loadClazz = loadClazz(classFile);
            return defineClass(name, loadClazz, 0, loadClazz.length);
        }
        return null;
    }

    public byte[] loadClazz(String classPath) {
        try {
            log.info("loadClazz方法执行: classPath: {}", classPath);
            FileInputStream in = new FileInputStream(new File(classPath));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int b;
            while ((b = in.read()) != -1) {
                baos.write(b);
            }
            in.close();
            return baos.toByteArray();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            log.info("loadClazz方法执行结束");
        }
        return null;
    }

}