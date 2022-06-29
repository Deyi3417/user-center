package com.yupi.usercenter.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Map;

@Slf4j
public class HttpUtils {

    public static void get(String url, Map<String, String> params, HttpServletResponse response) {
        try {
            log.info("get调用地址url=" + url + " 参数：" + params.toString());
            CloseableHttpClient httpClient = HttpClients.createDefault();
            for (String key : params.keySet()) {
                String value = params.get(key);
                if ("authorization".equals(key)) {
                    continue;
                }
                if (url.contains("?")) {
                    url += "&" + key + "=" + value;
                } else {
                    url += "?" + key + "=" + value;
                }
            }
            String authorization = "";
            if (StringUtils.isNotEmpty(params.get("authorization"))) {
                authorization = params.get("authorization");
            }
            HttpGet httpGet = new HttpGet(url);
            httpGet.addHeader("Content-Type", "application/json;charset=UTF-8");
            httpGet.setHeader("Accept", "application/json");
            httpGet.setHeader("Authorization", authorization);
            // 连接主机服务超时时间  请求超时时间  数据读取超时时间
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10000)
                    .setConnectionRequestTimeout(10000)
                    .setSocketTimeout(10000)
                    .build();
            httpGet.setConfig(requestConfig);
            CloseableHttpResponse res = httpClient.execute(httpGet);
            if (res.getStatusLine().getStatusCode() == 200) {
                // 直接读取内容
                HttpEntity entity = res.getEntity();
                InputStream is = entity.getContent();
                ServletOutputStream os = response.getOutputStream();
                byte[] bytes = new byte[1024];
                int n;
                while ((n = is.read(bytes)) != -1) {
                    os.write(bytes, 0, n);
                    os.flush();
                }
                log.info("get调用返回：");
                os.flush();
                os.close();
                is.close();
                httpClient.close();
                res.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String get(String url, Map<String, String> params) {
        try {
            log.info("get调用地址url=" + url + " 参数：" + params.toString());
            CloseableHttpClient httpClient = HttpClients.createDefault();
            for (String key : params.keySet()) {
                String value = params.get(key);
                if ("authorization".equals(key)) {
                    continue;
                }
                if (url.contains("?")) {
                    url += "&" + key + "=" + value;
                } else {
                    url += "?" + key + "=" + value;
                }
            }
            String authorization = "";
            if (StringUtils.isNotEmpty(params.get("authorization"))) {
                authorization = params.get("authorization");
            }
            HttpGet httpGet = new HttpGet(url);
            httpGet.addHeader("Content-Type", "application/json;charset=UTF-8");
            httpGet.setHeader("Accept", "application/json");
            httpGet.setHeader("Authorization", authorization);
            // 连接主机服务超时时间  请求超时时间  数据读取超时时间
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10000)
                    .setConnectionRequestTimeout(10000)
                    .setSocketTimeout(10000)
                    .build();
            httpGet.setConfig(requestConfig);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            String resStr = null;
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    resStr = EntityUtils.toString(entity, "UTF-8");
                }
                log.info("get调用返回：" + resStr);
                httpClient.close();
                response.close();
            }
            return resStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String post(String url, Map<String, String> params) {
        try {
            log.info("post调用地址url=" + url + " 参数：" + params.toString());
            CloseableHttpClient httpClient = HttpClients.createDefault();

            //设备post授权
            String authorization = "";
            if (StringUtils.isNotEmpty(params.get("authorization"))) {
                authorization = params.get("authorization");
            }
            HttpPost httpPost = new HttpPost(url);
            //httpPost.addHeader("Authorization", "Basic " + java.util.Base64.getUrlEncoder().encodeToString((username + ":" + password).getBytes()));
            httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Authorization", authorization);
            StringEntity se = new StringEntity(JSON.toJSONString(params), "UTF-8");
            // 设置请求头
            se.setContentEncoding("UTF-8");
            se.setContentType("application/json;charset=UTF-8");
            httpPost.setEntity(se);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10000)// 连接主机服务超时时间
                    .setConnectionRequestTimeout(10000)// 请求超时时间
                    .setSocketTimeout(10000)// 数据读取超时时间
                    .build();
            httpPost.setConfig(requestConfig);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            String resStr = null;
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    resStr = EntityUtils.toString(entity, "UTF-8");
                }
                log.info("post调用返回：" + resStr);
                httpClient.close();
                response.close();
            }
            return resStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void get03(String url, Map<String, String> params, HttpServletResponse response) {
        try {
            log.info("get调用地址url=" + url + " 参数：" + params.toString());
            CloseableHttpClient httpClient = HttpClients.createDefault();
            for (String key : params.keySet()) {
                String value = params.get(key);
                if ("authorization".equals(key)) {
                    continue;
                }
                if (url.contains("?")) {
                    url += "&" + key + "=" + value;
                } else {
                    url += "?" + key + "=" + value;
                }
            }
            String authorization = "";
            if (StringUtils.isNotEmpty(params.get("authorization"))) {
                authorization = params.get("authorization");
            }
            HttpGet httpGet = new HttpGet(url);
//            httpGet.addHeader("Content-Type", "application/json;charset=UTF-8");
//            httpGet.setHeader("Accept", "application/json");
            httpGet.setHeader("Authorization", authorization);
            // 连接主机服务超时时间--请求超时时间--数据读取超时时间
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10000)
                    .setConnectionRequestTimeout(10000)
                    .setSocketTimeout(10000)
                    .build();
            httpGet.setConfig(requestConfig);
            CloseableHttpResponse res = httpClient.execute(httpGet);
            if (res.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = res.getEntity();
                InputStream is = entity.getContent();
                ServletOutputStream os = response.getOutputStream();
                PDDocument pdDocument = PDDocument.load(is);
                PDFRenderer pdfRenderer = new PDFRenderer(pdDocument);
                BufferedImage bufferedImage = pdfRenderer.renderImage(0);
                // todo 多页pdf待转化
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage,"png", byteArrayOutputStream);
                byte[] bytes = byteArrayOutputStream.toByteArray();
                os.write(bytes, 0, bytes.length);
//                ImageIO.write(bufferedImage, "png", new File("D:\\tmp\\liudy23\\ToImage02.png"));
                os.close();
                is.close();
                httpClient.close();
                res.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        return null;
    }
}
