package com.yupi.usercenter.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author : HP
 * @date : 2023/2/22
 */
public class PageReadExcelListener<T> implements ReadListener<T> {

    public static int BATCH_COUNT = 100;
    private List<T> cacheDataList;
    private final Consumer<List<T>> consumer;

    public PageReadExcelListener(Consumer<List<T>> consumer) {
        this.cacheDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        this.consumer = consumer;
    }

    @Override
    public void invoke(T data, AnalysisContext analysisContext) {
        this.cacheDataList.add(data);
        if (this.cacheDataList.size() >= BATCH_COUNT) {
            this.consumer.accept(this.cacheDataList);
            this.cacheDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        if (CollectionUtils.isNotEmpty(this.cacheDataList)) {
            this.consumer.accept(this.cacheDataList);
        }
    }
}
