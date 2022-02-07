package com.longbig.multifunction.helper;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @author yuyunlong
 * @date 2020/12/28 9:20 下午
 * @description
 */
public class PdfHelper {

    public static void createPdf() throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream("test.pdf");

        Document document = new Document();
        PdfWriter.getInstance(document, fileOutputStream);

        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Chunk chunk = new Chunk("hello world", font);

        document.add(chunk);


        //图片1
        Image image1 = Image.getInstance(FileHelper.readImg());
        //设置图片位置的x轴和y周
        image1.setAbsolutePosition(100f, 550f);
        //设置图片的宽度和高度
        image1.scaleAbsolute(200, 200);
        //将图片1添加到pdf文件中
        document.add(image1);

        document.close();
    }

    public static void main(String[] args) throws Exception {
        createPdf();
    }
}
