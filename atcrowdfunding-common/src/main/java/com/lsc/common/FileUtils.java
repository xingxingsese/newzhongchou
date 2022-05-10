package com.lsc.common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lisc on 2021/12/19
 */
public class FileUtils {
    // 得到相对路径
    public static String getRelativePath(File baseDir, File file) {
        if (baseDir.equals(file)) {
            return "";
        }
        if (baseDir.getParentFile() == null) {
            return file.getAbsolutePath().substring(baseDir.getAbsolutePath().length());
        }
        return file.getAbsolutePath().substring(baseDir.getAbsolutePath().length() + 1);
    }

    // 查询某个目录下所有的文件
    public static List<File> searchAllFile(File dir) {
        ArrayList arrayList = new ArrayList<>();
        searchFiles(dir, arrayList);
        return arrayList;
    }

    // 递归获取某个目录下所有文件
    private static void searchFiles(File dir, ArrayList<Object> collector) {
        // isDirectory() 判断当前是否是目录
        if (dir.isDirectory()) {
            File[] subFiles = dir.listFiles();
            for (int i = 0; i < subFiles.length; i++) {
                searchFiles(subFiles[i], collector);
            }
        } else {
            collector.add(dir);
        }
    }

    // 创建文件
    public static File mkdir(String dir, String file) throws IllegalAccessException {
        if (dir == null) {
            throw new IllegalAccessException("dir must be not null");
        }
        File result = new File(dir, file);
        // getParentFile() 判断它是否有上一级目录
        if (result.getParentFile() != null) {
            // mkdir() 已该路径创建目录
            result.getParentFile().mkdir();
        }
        return result;
    }

    /**
     * 输出文件到指定目录
     * @param filePath 文件目录
     * @param fileName 文件名
     * @param content 文件内容
     */
    public static void fileWrite(String filePath,String fileName,String content) {
        File file = new File(filePath,fileName);
        BufferedWriter bufferedWriter = null;
        try {
            // 如果文件不存在创建文件
            if (!file.exists()) {
                file.createNewFile();
            }
            bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(content);

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                bufferedWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    // 查询某个目录下所有的jar文件
    public static List<File> searchAllJarFile(File dir) {
        ArrayList arrayList = new ArrayList<>();
        searchJarFiles(dir, arrayList);
        return arrayList;
    }


    // 递归获取某个目录下所有文件
    private static void searchJarFiles(File dir, ArrayList<Object> collector) {
        // isDirectory() 判断当前是否是目录
        if (dir.isDirectory()) {
            File[] subFiles = dir.listFiles();
            for (int i = 0; i < subFiles.length; i++) {
                searchJarFiles(subFiles[i], collector);
            }
        } else {
            String path = dir.getPath();
            if (path.endsWith(".jar")) {
                collector.add(dir);
            }
        }
    }



}
