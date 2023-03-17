package com.yupi.usercenter.controller;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.yupi.usercenter.common.BaseResponse;
import com.yupi.usercenter.common.ResultUtils;
import com.yupi.usercenter.service.FileHandlerService;
import com.yupi.usercenter.util.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.util.Map;

/**
 * 各种流之间的转换
 *
 * @author : HP
 * @date : 2023/3/15
 */
@RestController
@Api(tags = "流转换控制器")
@Slf4j
@RequestMapping("/stream")
public class StreamController {

    @Resource
    private FileHandlerService fileHandlerService;

    @GetMapping("wordToPdf")
    @ApiOperation("word流转pdf线上展示")
    public BaseResponse<?> wordToPdf(HttpServletResponse response) {
        return null;
    }

    @GetMapping("wordToPdfToLocal")
    @ApiOperation("word流转pdf保存到本地--")
    public BaseResponse<?> wordToPdfToLocal(HttpServletResponse response) {
        return null;
    }

    @GetMapping("exportWord")
    @ApiOperation("根据模板导出word")
    public BaseResponse<?> exportWord(HttpServletResponse response) {
        Map<String, Object> models = fileHandlerService.generateModels();
        try {
            String fileName = "导出图片_" + FileUtil.FILE_NAME_SUFFIX_DOCX;
            String templateFile = "D:\\tmp\\usercenter\\userdata.docx";
            String outputPath = "D:\\tmp\\usercenter\\tempFile\\" + fileName;
            Configure config = Configure.builder().build();
            XWPFTemplate template = XWPFTemplate.compile(templateFile, config).render(models);
            // 将生成的Word文档写入文件
            FileOutputStream out = new FileOutputStream(outputPath);
            template.write(out);
            out.flush();
            out.close();
            template.close();
            return ResultUtils.success();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return ResultUtils.success();
    }


    @GetMapping("exportLocal")
    @ApiOperation("根据模板导出word-local")
    public BaseResponse<?> exportWordLocal(HttpServletResponse response) {
        fileHandlerService.exportWordByTemplateToLocal(response);
        return ResultUtils.success();
    }

    @GetMapping("exportOnline")
    @ApiOperation("根据模板导出word-online")
    public void exportWordOnline(HttpServletResponse response) {
        fileHandlerService.exportWordByTemplateToOnline(response);
    }


}
