package com.yupi.usercenter.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;
import com.yupi.usercenter.constant.DateUtil;
import com.yupi.usercenter.mapstruct.basic.ImportUserConvert;
import com.yupi.usercenter.model.domain.User;
import com.yupi.usercenter.model.domain.dto.ImportUserData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * 导入数据-easyExcel上传
 * http://www.codebaoku.com/it-java/it-java-232484.html  Java EasyExcel实现文件的导入导出
 *
 * @author : HP
 * @date : 2023/2/13
 */
@Slf4j
public class EasyExcelListener<T> implements ReadListener<ImportUserData> {

    public static int BATCH_COUNT = 100;
    public List<ImportUserData> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    // 每读一样，会调用该invoke方法一次
    @Override
    public void invoke(ImportUserData data, AnalysisContext analysisContext) {
        log.info("{}:解析到一条数据:{}", DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"), JSON.toJSONString(data));
        System.out.println("解析到一条数据：" + JSON.toJSONString(data));
        User user = ImportUserConvert.INSTANCE.toUser(data);
        log.info("{}:数据转为User:{}", DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"), JSON.toJSONString(user));
        cachedDataList.add(data);
        if (cachedDataList.size() >= BATCH_COUNT) {

        }

    }

    // 全部读完之后，会调用该方法
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("{}:全部解析完成",DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        System.out.println("全部解析完成");
    }
}
