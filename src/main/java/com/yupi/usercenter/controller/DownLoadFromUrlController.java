package com.yupi.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.spire.pdf.PdfDocument;
import com.spire.pdf.graphics.PdfImageType;
import com.yupi.usercenter.mapper.UserMapper;
import com.yupi.usercenter.model.domain.User;
import com.yupi.usercenter.util.FileUtil;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;
import java.util.List;

/**
 * @author liudy23
 * @date 2022-06-29
 */
@RestController
@RequestMapping("/download")
public class DownLoadFromUrlController {

    @Resource
    private UserMapper userMapper;

    @GetMapping("/all")
    public List<User> all() {
        return userMapper.selectList(new QueryWrapper<User>().eq("isDelete", 0));
    }

    @GetMapping("/url")
    public Object downloadFromUrl() {
        String url = "https://yarnpkg.com/latest.msi";
        String fileName = "yarn.msi";
        String savePath = "D:\\DownloadFile\\GoogleDownload";
        String result = FileUtil.downLoadFromUrl(url, fileName, savePath);
        return result;
    }

    @GetMapping("/file")
    public Object testFile() throws FileNotFoundException {
        File file = new File("D:/File_liudy23/test/test.pdf");
        System.out.println("liudy23测试：" + file);
        FileOutputStream fos = new FileOutputStream(file);
        System.out.println("liudy23测试2:" + fos);
        return "testFile liudy23";
    }

    @GetMapping("/img")
    public void imgtest(HttpServletResponse response) throws IOException {
        // 生成PDF二进制流文件
        PdfDocument pdfDocument = new PdfDocument();
        pdfDocument.loadFromFile("D:\\File_liudy23\\test\\test02.pdf");
        // 设置精度-图片清晰度
        BufferedImage bufferedImage = pdfDocument.saveAsImage(0, PdfImageType.Bitmap, 600, 400);
        // 图片流转二进制数组
        ByteArrayOutputStream os = new ByteArrayOutputStream(1024);
        ImageIO.write(bufferedImage, "png", os);
        byte[] bytes = os.toByteArray();
        System.out.println(Arrays.toString(bytes));
        ServletOutputStream outputStream = response.getOutputStream();
//        outputStream.write(bytes);
        outputStream.write(bytes, 0, bytes.length);
        ImageIO.write(bufferedImage, "png", new File("D:\\File_liudy23\\test\\ToImage70.png"));
        outputStream.close();
        pdfDocument.close();

    }

    @GetMapping("/img02")
    public void imgtest02(HttpServletResponse response) throws IOException {
        // 生成PDF二进制流文件
        InputStream is = new FileInputStream("D:\\File_liudy23\\test\\test02.pdf");
        PDDocument pdDocument = PDDocument.load(is);
        PDFRenderer pdfRenderer = new PDFRenderer(pdDocument);
//        BufferedImage bufferedImage = pdfRenderer.renderImage(0,5);
        BufferedImage bufferedImage = pdfRenderer.renderImageWithDPI(0, 600);
        // 图片流转二进制数组
        ByteArrayOutputStream os = new ByteArrayOutputStream(1024);
        ImageIO.write(bufferedImage, "png", os);
        byte[] bytes = os.toByteArray();
        ServletOutputStream outputStream = response.getOutputStream();
//        outputStream.write(bytes);
        outputStream.write(bytes, 0, bytes.length);
        outputStream.close();
        pdDocument.close();
        is.close();
    }

    @GetMapping("/pdf")
    public void pdfTest(HttpServletResponse response) throws IOException {
        // 生成PDF二进制流文件
        PdfDocument pdfDocument = new PdfDocument();
        pdfDocument.loadFromFile("D:\\File_liudy23\\test\\test02.pdf");
        BufferedImage bufferedImage = pdfDocument.saveAsImage(0);
        // PDF流文件
        response.reset();
        // response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=\"" + "fileName" + "\"");
    }
}
