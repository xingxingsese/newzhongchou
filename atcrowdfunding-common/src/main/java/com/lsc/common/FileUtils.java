package com.lsc.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by lisc on 2021/12/19
 */
public class FileUtils {
    private static final Logger log = LoggerFactory.getLogger(FileUtils.class);

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
     *
     * @param filePath 文件目录
     * @param fileName 文件名
     * @param content  文件内容
     */
    public static void fileWrite(String filePath, String fileName, String content) {
        File file = new File(filePath, fileName);
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
        } finally {
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

    /**
     * 返回Stream(Java 8) 流式数据处理，按行读取
     *
     * @param url 文件路径
     * @return
     */
    public static Stream readFileLinesReturnStream(String url) {

        // 读取文件内容到Stream流中，按行读取
        Stream<String> lines = null;
        try {
            lines = Files.lines(Paths.get(url));
        } catch (IOException e) {
            log.error(String.format("FileUtils:readFileReturnStream方法发生异常 e : %s", e));
        }

        // 随机顺序进行数据处理
        //  lines.forEach(ele -> { System.out.println(ele);});

        // 按文件行顺序进行处理
        //  lines.forEachOrdered(System.out::println);

        // 转换成List<String>, 要注java.lang.OutOfMemoryError: Java heap space
        // List<String> collect = lines.collect(Collectors.toList());
        return lines;
    }


    /**
     * 返回List 读取所有行
     *
     * @param url 文件路径
     * @return
     */
    public static List readFileAllLinesReturnList(String url) {

        List<String> lines = null;
        try {
            // 转换成List<String>, 要注意java.lang.OutOfMemoryError: Java heap space
            lines = Files.readAllLines(Paths.get(url), StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error(String.format("FileUtils:readFileReturnList 方法发生异常 e : %s", e));
        }

        return lines;
    }

    /**
     * 读取文件所有字节 返回bytes
     *
     * @param url
     * @return
     */
    public static byte[] readFileAllBytesReturnBytes(String url) {

        byte[] bytes = new byte[0];
        try {
            bytes = Files.readAllBytes(Paths.get(url));
        } catch (IOException e) {
            log.error(String.format("FileUtils:readFileAllBytesReturnBytes 方法发生异常 e : %s", e));
        }
        // String content = new String(bytes, StandardCharsets.UTF_8);
        return bytes;
    }

    public static String readFileAllBytesReturnString(String url) {

        byte[] bytes = new byte[0];
        try {
            bytes = Files.readAllBytes(Paths.get(url));
        } catch (IOException e) {
            log.error(String.format("FileUtils:readFileAllBytesReturnBytes 方法发生异常 e : %s", e));
        }

        return new String(bytes, StandardCharsets.UTF_8);
    }


    public static BufferedReader readFileBufferedReader(String url) {

        // 带缓冲的流读取，默认缓冲区8k
        BufferedReader br = null;
        try {
            br = Files.newBufferedReader(Paths.get(url));
        } catch (IOException e) {
            log.error(String.format("FileUtils:readFileBufferedReader 方法发生异常 e : %s", e));
        }
         /*   String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }*/

        return br;
    }


    /**
     * 输出文件到指定目录
     *
     * @param filePath 文件目录
     * @param content  文件内容
     */
    public static void fileWriteAdbMkdir(String filePath, String content) {
        File file = new File(filePath);

        // 文件夹已存在
        if (file.exists()) {
            System.out.println("File exists");
        }
        if (null != file.getParentFile()) {
            file.getParentFile().mkdirs();
        }


        BufferedWriter bufferedWriter = null;
        try {
            // 如果文件不存在创建文件
            // if (!file.exists()) {
            file.createNewFile();
            //  }
            bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(content);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
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
}
