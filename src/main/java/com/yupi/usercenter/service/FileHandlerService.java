package com.yupi.usercenter.service;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 文件处理接口
 *
 * @author : HP
 * @date : 2023/3/16
 */
public interface FileHandlerService {

    void exportWordByTemplateToLocal(HttpServletResponse response);

    void exportWordByTemplateToOnline(HttpServletResponse response);

    /**
     * 生成 model
     *
     * @return
     */
    Map<String, Object> generateModels();
}
