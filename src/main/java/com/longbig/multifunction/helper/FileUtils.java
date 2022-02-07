package com.longbig.multifunction.helper;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
public class FileUtils {

    /**
     * 读取文件后以数组形式存放
     *
     * @param inputFile
     * @return
     * @throws Exception
     */
    public static List<String> readFileToStringList(String inputFile) {
        List<String> stringList = Lists.newArrayList();
        try {
            FileReader fileReader = new FileReader(inputFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                stringList.add(line);
                line = bufferedReader.readLine();
            }
            fileReader.close();
        } catch (IOException e) {
            log.error("读取输入文件失败：{}", inputFile);
            e.printStackTrace();
        }
        return stringList;
    }

    /**
     * 将二维数组写入到文件中
     *
     * @param strings
     * @param outputFile
     */
    public static void writeStringListToFile(String[][] strings, String outputFile) {
        try {
            File file = new File(outputFile);
            FileWriter out = new FileWriter(file);
            //将数组中的数据写入到文件中，以空格分开
            for (String[] stringList : strings) {
                for (String string : stringList) {
                    if (Objects.nonNull(string)) {
                        out.write(string + " ");
                    }
                }
                out.write("\n");
            }
            out.close();
        } catch (IOException e) {
            log.error("输出路径有问题：{}", outputFile);
            e.printStackTrace();
        }

    }

    /**
     * 读取文件到缓存
     *
     * @param inputFile
     */
    public static BufferedReader readFile(String inputFile) {
        try {
            File file = new File(inputFile);
            InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
            System.out.println(reader.getEncoding());
            BufferedReader bufferedReader = new BufferedReader(reader);
            return bufferedReader;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将缓存写入文件
     *
     * @param outputFile
     * @param bufferedReader
     */
    public static void writeFile(String outputFile, BufferedReader bufferedReader) {
        if (Objects.isNull(bufferedReader)) {
            return;
        }
        try {
            File writeName = new File(outputFile);
            writeName.createNewFile();
            FileWriter writer = new FileWriter(writeName);
            String line = "";
            while (null != line) {
                line = bufferedReader.readLine();
                BufferedWriter out = new BufferedWriter(writer);
                if (Objects.nonNull(line)) {
                    out.write("\"" + line + "\"" + ",");
                    out.flush();
                }
            }
        } catch (IOException e) {
            log.error("输出路径不存在：{}", outputFile);
            e.printStackTrace();
        }
    }

    /**
     * 读取某一目录下所有文件名
     * @param path
     * @return
     */
    public static List<String> getFiles(String path) {
        List<String> files = Lists.newArrayList();
        File file = new File(path);
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                files.add(tempList[i].toString());
            }
            if (tempList[i].isDirectory()) {
                continue;
            }
        }
        return files;
    }

    /**
     * 获取文件名后缀
     *
     * @param path 文件完整路径
     * @return 文件名后缀
     */
    public static String getExtension(String path) {
        int i = path.lastIndexOf('.');
        return i > 0 ? path.substring(i + 1) : null;
    }

    /**
     * 获取文件名
     *
     * @param path 文件完整路径
     * @return 文件名
     */
    public static String getFileName(String path) {
        path = path.trim();
        String[] temp = path.split("\\\\");
        path = temp[temp.length - 1];
        String[] arr = path.split("\\.");
        return arr[0];
    }

    /**
     * 根据原始文件路径生成一个不重复的文件路径，如果A.shp不存在，则使用原路径
     * 如果A.shp已经存在，则使用新路径A(1).shp，如果A（1）.txt已存在，则使用新路径A（2）.txt，依次类推
     *
     * @param filePath 文件完整路径
     * @return 新路径
     */
    public static File getNewFile(String filePath) {
        File file = new File(filePath);
        File parentFile = file.getParentFile();
        // 如果文件夹不存在则生成文件夹
        if (!parentFile.exists()) {
            if (!parentFile.mkdirs()) return null;
        }
        // 如果文件存在则
        if (!file.exists()) {
            return file;
        } else {
            int i = 1;
            while (true) {
                String tempPath = file.getPath();
                int index = tempPath.lastIndexOf('.');
                String name = tempPath.substring(0,index);
                String suffix = tempPath.substring(index + 1);
                String nameFilePath = name + "(" + i + ")" + "." + suffix;
                File newFile = new File(nameFilePath);
                if (!newFile.exists()) {
                    return newFile;
                }
                i++;
            }
        }
    }

    /**
     * 生成cpg文件
     *
     * @param filePath 文件完整路径
     * @param charset  文件编码
     * @return 是否生成成功
     */
    public static boolean generateCpgFile(String filePath, Charset charset) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                return false;
            }
            String tempPath = file.getPath();
            int index = tempPath.lastIndexOf('.');
            String name = tempPath.substring(0,index);
            String cpgFilePath = name + ".cpg";
            File cpgFile = new File(cpgFilePath);
            if (cpgFile.exists()) {
                return true;
            }
            boolean newFile = cpgFile.createNewFile();
            if (newFile) {
                Files.write(cpgFile.toPath(), charset.toString().getBytes(charset));
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
