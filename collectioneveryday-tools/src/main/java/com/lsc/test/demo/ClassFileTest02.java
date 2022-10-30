package com.lsc.test.demo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author lisc
 * @Description: com.lsc.freemarker.demo
 * @date 2022/10/20 19:10
 */
@Slf4j
@Data
public class ClassFileTest02 extends ClassLoader {

    private List<String> classFile = null;

    @Test
    public void test() throws ClassNotFoundException {
        String rootPath = "D:\\Code\\javaCore\\ibizecoprod\\app";

        ClassFileTest02 test02 = new ClassFileTest02();
        test02.setClassFile(getClassFilePath(rootPath));
        Class<?> loadClass = test02.loadClass("com.ipay.ibizecoprod.core.service.xero.response.GetTokenByAuthorizationCodeResponse");
        System.out.println("loadClass.getSimpleName() = " + loadClass);
    }

    /**
     * 获取class文件的本地文件路径
     * @param classPath 例如/usr/java/classes下有一个test.App类，则/usr/java/classes即这个类的根路径，而.class文件的实际位置是/usr/java/classes/test/App.class
     */
    public List<String> getClassFilePath(String classPath) {
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
                            return (pathname.isDirectory() && !pathname.getName().contains("test")) || (pathname.getName().endsWith(".class") && !pathname.getName().contains("$"));
                        }
                    });

                    for (File subFile : classFiles) {
                        if (subFile.isDirectory()) {
                            stack.push(subFile);
                        } else {
                            // 文件名称
                            String className = subFile.getAbsolutePath();
                            className = className.substring(clazzPathLen, className.length() - 6);
                            int indexOf = className.indexOf("com");
                            className = className.substring(indexOf);
                            className = className.replace(File.separatorChar, '.');
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

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        log.info("findClass -> {}", name);
        Class<?> aClass = null;
        return loadedPathClassFile(name, aClass);
    }

    /**
     * 加载本地编译后的class文件
     * @param name
     * @param aClass
     * @return
     */
    private Class<?> loadedPathClassFile(String name, Class<?> aClass) {
        List<String> classFile = this.getClassFile();
        if (classFile.isEmpty()) {
            log.info("classFile is null");
            return null;
        }

        for (String cla : classFile) {
            int lastIndexOf = cla.lastIndexOf('\\');
            int indexOf = cla.lastIndexOf('.');
            String substring = cla.substring(lastIndexOf + 1, indexOf);

            int index = name.lastIndexOf('.');
            String substring1 = name.substring(index + 1);

            if (!substring.equals(substring1)) {
                continue;
            }
            log.info("listName: {}, className: {}", substring, substring1);
            byte[] loadClazz = loadClazz(cla);
            return defineClass(name, loadClazz, 0, loadClazz.length);
        }

        return aClass;
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
