package com.longbig.multifunction.helper;

import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.codec.binary.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author yuyunlong
 * @date 2020/12/28 10:26 下午
 * @description
 */
public class FileHelper {

    public static String readImg() {
        String path = "/Users/yuyunlong/";
        File dir = new File(path);
        String[] children = dir.list();
        if (children == null) {
            System.out.println("目录不存在或它不是一个目录");
        } else {
            for (int i = 0; i < children.length; i++) {
                String filename = children[i];
                System.out.println(filename);
                if (filename.contains(".jpg") || filename.contains(".png")) {
                    return path + filename;
                }
            }
        }
        return null;
    }

    public static InputStream getImgInput(String imgFile) {
        //将图片文件转化为字节数组字符串，并对其进行Base64编码处理


        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try
        {
            in = new FileInputStream(imgFile);
//            data = new byte[in.available()];
//            in.read(data);
//            in.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return in;
    }

    public static void main(String[] args) {
        readImg();
    }
}
