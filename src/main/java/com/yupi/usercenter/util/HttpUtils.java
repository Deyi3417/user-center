package com.yupi.usercenter.util;

import com.alibaba.fastjson.JSON;
import com.yupi.usercenter.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.rendering.RenderDestination;
import org.springframework.http.HttpStatus;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

/**
 * HttpStatus
 * HttpHeaders
 * MediaType
 */
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
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10000).setConnectionRequestTimeout(10000).setSocketTimeout(10000).build();
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
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10000).setConnectionRequestTimeout(10000).setSocketTimeout(10000).build();
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

    /**
     * pdf流转图片流
     *
     * @param url      请求地址
     * @param params   参数信息
     * @param response 反应response
     */
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
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10000).setConnectionRequestTimeout(10000).setSocketTimeout(10000).build();
            httpGet.setConfig(requestConfig);
            CloseableHttpResponse res = httpClient.execute(httpGet);
            if (res.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = res.getEntity();
                InputStream is = entity.getContent();
                ServletOutputStream os = response.getOutputStream();
                PDDocument pdDocument = PDDocument.load(is);
                PDFRenderer pdfRenderer = new PDFRenderer(pdDocument);

//                BufferedImage bufferedImage = pdfRenderer.renderImage(0);
                BufferedImage bufferedImage = pdfRenderer.renderImage(0, 4f, ImageType.BINARY, RenderDestination.VIEW);
                // todo 多页pdf待转化
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
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


    /**
     * get请求
     *
     * @param url 目标url
     * @return result
     */
    public static String get(String url) {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();

            HttpGet httpGet = new HttpGet(url);
            httpGet.addHeader("Content-Type", "application/json;charset=UTF-8");
            httpGet.setHeader("Accept", "application/json");
            // 连接主机服务超时时间
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10000)
                    // 请求超时时间
                    .setConnectionRequestTimeout(10000)
                    // 数据读取超时时间
                    .setSocketTimeout(10000).build();
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

    public static String httpPost(String url, String jsonstr) {
        // post请求返回结果
        String response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost method = new HttpPost(url);
        try {
            if (null != jsonstr && !"".equals(jsonstr)) {
                //解决中文乱码问题
                StringEntity entity = new StringEntity(jsonstr, "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                method.setEntity(entity);
            }
            HttpResponse result = httpClient.execute(method);
            url = URLDecoder.decode(url, "UTF-8");
            if (result != null) {
                int statusCode = result.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    HttpEntity httpEntity = result.getEntity();
                    if (httpEntity != null) {
                        response = EntityUtils.toString(httpEntity, "UTF-8");
                        return response;
                    }
                } else {
                    return "" + statusCode;
                }
            }
        } catch (Exception e) {
            log.info("post请求提交失败:" + url, e);
        }
        return response;
    }

    /**
     * post请求
     *
     * @param url    请求地址
     * @param params 请求参数
     * @return 返回调用结果
     */
    public static String post02(String url, Map<String, Object> params) {
        try {
            log.info("post调用地址url=" + url + " 参数：" + params.toString());
            CloseableHttpClient httpClient = HttpClients.createDefault();

            //设备post授权
            String authorization = "";
            if (ToolUtil.isNotEmpty(params.get("authorization"))) {
                authorization = String.valueOf(params.get("authorization"));
            }
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Authorization", authorization);
            StringEntity se = new StringEntity(JSON.toJSONString(params), "UTF-8");
            // 设置请求头
            se.setContentEncoding("UTF-8");
            se.setContentType("application/json;charset=UTF-8");
            httpPost.setEntity(se);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10000).setConnectionRequestTimeout(10000).setSocketTimeout(10000).build();
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

    /**
     * post请求-解决账号密码登陆的问题
     *
     * @param url      请求地址
     * @param param    请求参数
     * @param username 用户名
     * @param password 密码
     * @return HttpResponse
     * @throws IOException 抛出异常
     */
    public static HttpResponse sendPost(String url, String param, String username, String password) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        String author = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
        StringEntity entity = new StringEntity(param, StandardCharsets.UTF_8);
        entity.setContentType("application/json");

        HttpPost requestPost = new HttpPost(url);
        requestPost.addHeader("Authorization", author);
        requestPost.setEntity(entity);
        return httpClient.execute(requestPost);
    }

    public static void sendPostHandler01(String url, String param, String username, String password, HttpServletResponse response) throws IOException {
        HttpResponse res = sendPost(url, param, username, password);
        if (res.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
            InputStream inputStream = res.getEntity().getContent();
            ServletOutputStream os = response.getOutputStream();
            byte[] bytes = new byte[2048];
            int n;
            while ((n = inputStream.read(bytes)) != -1) {
                os.write(bytes, 0, n);
                os.flush();
            }
            os.close();
            inputStream.close();
        }
    }

    public static void sendPostHandler02(String url, String param, String username, String password, HttpServletResponse response) throws IOException {
        HttpResponse res = sendPost(url, param, username, password);
        if (res.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
            InputStream is = res.getEntity().getContent();
            ServletOutputStream os = response.getOutputStream();

            // 如获取到的文件是zip
            ZipArchiveInputStream inputStream = new ZipArchiveInputStream(is);
            ZipArchiveEntry entry;
            while ((entry = inputStream.getNextZipEntry()) != null) {
                if (!entry.getName().endsWith("pdf")) {
                    continue;
                }
                // response.setContentType("application/pdf");
                // response.setContentType("image/png");
                // response.setHeader("Content-Disposition","inline;filename=" + URLEncoder.encode(entry.getName(), "UTF-8"));
                byte[] bytes = IOUtils.toByteArray(inputStream);
                os.write(bytes, 0, bytes.length);
                os.close();
                inputStream.close();
                is.close();
            }
        } else {
            log.info("请求返回状态码非 200");
            throw new BusinessException("没有获取到目标信息");
        }
    }


    public static void sendPostHandler03(String url, String param, String username, String password, HttpServletResponse response) throws IOException {
        HttpResponse res = sendPost(url, param, username, password);
        if (res.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
            InputStream is = res.getEntity().getContent();
            ServletOutputStream os = response.getOutputStream();

            // 如获取到的文件是zip
            ZipArchiveInputStream inputStream = new ZipArchiveInputStream(is);
            ZipArchiveEntry entry;
            while ((entry = inputStream.getNextZipEntry()) != null) {
                if (!entry.getName().endsWith("pdf")) {
                    continue;
                }
                // response.setContentType("application/pdf");
                // response.setContentType("image/png");
                // response.setHeader("Content-Disposition","inline;filename=" + URLEncoder.encode(entry.getName(), "UTF-8"));

                // pdf流转图片流
                PDDocument pdDocument = PDDocument.load(inputStream);
                PDFRenderer pdfRenderer = new PDFRenderer(pdDocument);

                BufferedImage bufferedImage = pdfRenderer.renderImage(0, 4f, ImageType.BINARY, RenderDestination.VIEW);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "png", byteArrayOutputStream);

                byte[] bytes = byteArrayOutputStream.toByteArray();
                os.write(bytes, 0, bytes.length);
                os.close();
                byteArrayOutputStream.close();
                pdDocument.close();
                inputStream.close();
                is.close();
            }
        } else {
            log.info("请求返回状态码非 200");
            throw new BusinessException("没有获取到目标信息");
        }
    }
}
