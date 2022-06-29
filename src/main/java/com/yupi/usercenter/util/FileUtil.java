package com.yupi.usercenter.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
public class FileUtil {

    /**
     * 从网络Url中下载文件
     *
     * @param urlStr   url地址
     * @param fileName 文件名
     * @param savePath 保存路径
     * @throws IOException
     */
    public static String downLoadFromUrl(String urlStr, String fileName, String savePath) {

        InputStream inputStream = null;
        FileOutputStream fos = null;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置超时间为3秒
            conn.setConnectTimeout(300 * 1000);
            //防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            //得到输入流
            inputStream = conn.getInputStream();
            //获取自己数组
            byte[] getData = readInputStream(inputStream);
            if (getData.length <= 0) {
                throw new Exception("this file is empty");
            }
            //文件保存位置
            File saveDir = new File(savePath);
            if (!saveDir.exists()) {
                saveDir.mkdir();
            }
            File file = new File(saveDir + File.separator + fileName);
            fos = new FileOutputStream(file);
            fos.write(getData);
            if (fos != null) {
                fos.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("info:" + urlStr + " download failed:" + e.getMessage());
            return savePath;
        }
        System.out.println("info:" + urlStr + " download success");
        return savePath;
    }

    public static String downLoadFromUrl(String urlStr, String savePath) {

        InputStream inputStream = null;
        FileOutputStream fos = null;
        String fileName = urlStr.substring(urlStr.lastIndexOf("\\") + 1);
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置超时间为3秒
            conn.setConnectTimeout(300 * 1000);
            //防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            //得到输入流
            inputStream = conn.getInputStream();
            //获取自己数组
            byte[] getData = readInputStream(inputStream);
            if (getData.length <= 0) {
                throw new Exception("this file is empty");
            }
            //文件保存位置
            File saveDir = new File(savePath);
            if (!saveDir.exists()) {
                saveDir.mkdir();
            }
            File file = new File(saveDir + File.separator + fileName);
            fos = new FileOutputStream(file);
            fos.write(getData);
            if (fos != null) {
                fos.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("info:" + urlStr + " download failed:" + e.getMessage());
            return savePath;
        }
        System.out.println("info:" + urlStr + " download success");
        return savePath;
    }


    /**
     * 从输入流中获取字节数组
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    public static void main(String[] args) {
        String url = "http://zhE2C45E21&jg";
        String savePath = downLoadFromUrl(url, "11.jpg", "d:");
        System.out.println(savePath);
    }

}
