package com.yupi.usercenter.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.data.ImageData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.util.ListUtils;
import com.yupi.usercenter.config.properties.BasicProperties;
import com.yupi.usercenter.model.domain.vo.ImageDemoData;
import com.yupi.usercenter.util.DateUtil;
import com.yupi.usercenter.util.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : HP
 * @date : 2022/10/27
 */
@Slf4j
@RestController
@Api(tags = "ExcelHandlerController")
@RequestMapping("/learn")
public class ExcelHandlerController {

    @Resource
    private BasicProperties basicProperties;

    @GetMapping("/exportPicture")
    @ApiOperation("测试导出包含图片")
    public void export() throws Exception {
        String fileName = "测试导出_LOCAL_" + FileUtil.FILE_NAME_SUFFIX_XLSX;
        String fileSaveDirectory = basicProperties.getFileSavePath() + File.separator + DateUtil.getDefaultDate();
        // 生成文件夹
        String rootPath = FileUtil.getRootPath(fileSaveDirectory);
        String saveFilePath = rootPath + fileName;

        String imagePath = "D:\\tmp\\usercenter\\tempFile\\liudy23.png";
        try(InputStream inputStream = FileUtils.openInputStream(new File(imagePath))) {
            List<ImageDemoData> list =  new ArrayList<>();
            ImageDemoData imageDemoData = new ImageDemoData();
            list.add(imageDemoData);
            // 放入五种类型的图片 实际使用只要选一种即可
            imageDemoData.setByteArray(FileUtils.readFileToByteArray(new File(imagePath)));
            imageDemoData.setFile(new File(imagePath));
            imageDemoData.setString(imagePath);
            imageDemoData.setInputStream(inputStream);
            imageDemoData.setUrl(new URL(
                    "https://images.zsxq.com/FgllBCZYIFNUanzQdTp4nFENiojr?e=1682870399&token=kIxbL07-8jAj8w1n4s9zv64FuZZNEATmlU_Vm6zD:n-n4CBJULGApxzoZjAgFRwHPc5s="));
            // 这里演示
            // 需要额外放入文字
            // 而且需要放入2个图片
            // 第一个图片靠左
            // 第二个靠右 而且要额外的占用他后面的单元格
            WriteCellData<Void> writeCellData = new WriteCellData<>();
            imageDemoData.setWriteCellDataFile(writeCellData);
            // 这里可以设置为 EMPTY 则代表不需要其他数据了
            writeCellData.setType(CellDataTypeEnum.STRING);
            writeCellData.setStringValue("额外的放一些文字");

            // 可以放入多个图片
            List<ImageData> imageDataList = new ArrayList<>();
            ImageData imageData = new ImageData();
            imageDataList.add(imageData);
            writeCellData.setImageDataList(imageDataList);
            // 放入2进制图片
            imageData.setImage(FileUtils.readFileToByteArray(new File(imagePath)));
            // 图片类型
            imageData.setImageType(ImageData.ImageType.PICTURE_TYPE_PNG);
            // 上 右 下 左 需要留空
            // 这个类似于 css 的 margin
            // 这里实测 不能设置太大 超过单元格原始大小后 打开会提示修复。暂时未找到很好的解法。
            imageData.setTop(5);
            imageData.setRight(40);
            imageData.setBottom(5);
            imageData.setLeft(5);

            // 放入第二个图片
            imageData = new ImageData();
            imageDataList.add(imageData);
            writeCellData.setImageDataList(imageDataList);
            imageData.setImage(FileUtils.readFileToByteArray(new File(imagePath)));
            imageData.setImageType(ImageData.ImageType.PICTURE_TYPE_PNG);
            imageData.setTop(5);
            imageData.setRight(5);
            imageData.setBottom(5);
            imageData.setLeft(50);
            // 设置图片的位置 假设 现在目标 是 覆盖 当前单元格 和当前单元格右边的单元格
            // 起点相对于当前单元格为0 当然可以不写
            imageData.setRelativeFirstRowIndex(0);
            imageData.setRelativeFirstColumnIndex(0);
            imageData.setRelativeLastRowIndex(0);
            // 前面3个可以不写  下面这个需要写 也就是 结尾 需要相对当前单元格 往右移动一格
            // 也就是说 这个图片会覆盖当前单元格和 后面的那一格
            imageData.setRelativeLastColumnIndex(1);

            // 写入数据
            EasyExcel.write(saveFilePath, ImageDemoData.class).sheet().doWrite(list);
        }
    }

}
