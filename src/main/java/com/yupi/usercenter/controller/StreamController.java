package com.yupi.usercenter.controller;

import com.yupi.usercenter.common.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 各种流之间的转换
 *
 * @author : HP
 * @date : 2023/3/15
 */
@RestController
@Api( tags = "流转换控制器")
@Slf4j
public class StreamController {

    @GetMapping("wordToPdf")
    @ApiOperation("word流转pdf保存到本地--线上展示")
    public BaseResponse<?> wordToPdf(HttpServletResponse response) {
        return null;
    }

    @GetMapping("wordToPdfToLocal")
    @ApiOperation("word流转pdf保存到本地--线上展示")
    public BaseResponse<?> wordToPdfToLocal(HttpServletResponse response) {
        return null;
    }

}
