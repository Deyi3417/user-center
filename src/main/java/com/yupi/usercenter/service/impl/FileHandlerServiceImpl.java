package com.yupi.usercenter.service.impl;

import cn.hutool.core.io.resource.ResourceUtil;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.yupi.usercenter.config.properties.BasicProperties;
import com.yupi.usercenter.service.FileHandlerService;
import com.yupi.usercenter.util.DateUtil;
import com.yupi.usercenter.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : HP
 * @date : 2023/3/16
 */
@Service
@Slf4j
public class FileHandlerServiceImpl implements FileHandlerService {

    @Resource
    private BasicProperties basicProperties;

    /**
     * 根据模板导出word-导出到本地
     *
     * @param response
     */
    @Override
    public void exportWordByTemplateToLocal(HttpServletResponse response) {
        String fileName = "测试导出_LOCAL_" + FileUtil.FILE_NAME_SUFFIX_DOCX;
        String fileSaveDirectory = basicProperties.getFileSavePath() + File.separator + DateUtil.getDefaultDate();
        // 生成文件夹
        String rootPath = FileUtil.getRootPath(fileSaveDirectory);
        String saveFilePath = rootPath + fileName;
        try {
            Configure config = Configure.builder().build();
            InputStream stream = ResourceUtil.getStream("static/template/doc/userdata.docx");
            XWPFTemplate template = XWPFTemplate.compile(stream, config).render(this.generateModels());
            FileOutputStream out = new FileOutputStream(saveFilePath);
            template.write(out);
            out.flush();
            out.close();
            template.close();
        } catch (Exception e) {
            log.error("导出模板异常");
            e.printStackTrace();
        } finally {
            log.info("文件保存的路径：" + saveFilePath);
        }
    }

    @Override
    public void exportWordByTemplateToOnline(HttpServletResponse response) {
        String fileName = "测试导出_ONLINE_" + FileUtil.FILE_NAME_SUFFIX_DOCX;
        String fileSaveDirectory = basicProperties.getFileSavePath() + File.separator + DateUtil.getDefaultDate();
        // 生成文件夹
        String rootPath = FileUtil.getRootPath(fileSaveDirectory);
        String saveFilePath = rootPath + fileName;
        try {
            Configure config = Configure.builder().build();
            InputStream stream = ResourceUtil.getStream("static/template/doc/userdata.docx");
            XWPFTemplate template = XWPFTemplate.compile(stream, config).render(this.generateModels());

            // 导出为响应（Response）
            response.reset();
            response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            ServletOutputStream out = response.getOutputStream();
            template.write(out);
            out.flush();
            out.close();
            template.close();
        } catch (Exception e) {
            log.error("导出模板异常");
            e.printStackTrace();
        } finally {
        }
    }

    @Override
    public Map<String, Object> generateModels() {
        Map<String, Object> models = new HashMap<>();
        models.put("title", "刘德意的学习小天地");
        models.put("userName", "九夏光年");
        models.put("gender", "男");
        models.put("age", "18");
        models.put("address", "湖南省衡阳县岘山乡紫云村麻塘组");
        return models;
    }

}
