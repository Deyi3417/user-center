package com.yupi.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yupi.usercenter.common.BaseResponse;
import com.yupi.usercenter.common.ResultUtils;
import com.yupi.usercenter.mapstruct.basic.UserConvert2DTO;
import com.yupi.usercenter.model.domain.User;
import com.yupi.usercenter.model.domain.dto.SafetyUserDTO;
import com.yupi.usercenter.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.rendering.RenderDestination;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author : HP
 * @date : 2022/12/6
 */

@Slf4j
@RestController
@RequestMapping("/test")
@Api(tags = "测试控制器")
public class TestController {

    @Resource
    private UserService userService;

    @ApiOperation("获取脱敏后的用户信息")
    @GetMapping("/getSafetyUser")
    public BaseResponse<SafetyUserDTO> getSafeUser(String id) {
        User user = userService.getById(id);
        SafetyUserDTO resu = UserConvert2DTO.INSTANCE.toCovetSafetyUserDTO(user);
        return ResultUtils.success(resu);
    }

    @ApiOperation("获取脱敏后的用户信息List")
    @PostMapping("/listSafetyUser")
    public BaseResponse<List<SafetyUserDTO>> getListSafetyUser(@RequestBody List<Integer> ids) {
        List<User> users = userService.list(new QueryWrapper<User>().in("id", ids).eq("is_delete", 0));
        List<SafetyUserDTO> result = UserConvert2DTO.INSTANCE.toCovetSafetyUserDTOList(users);
        return ResultUtils.success(result);
    }

    @ApiOperation("本地zip解析")
    @GetMapping("/parseZip")
    public void parseZip(HttpServletResponse response) {
        try {
            ServletOutputStream os = response.getOutputStream();
            File zipFile = new File("D:\\tmp\\tempfile.zip"); // 假设这里有一个本地的 zip 文件
            FileInputStream fileInputStream = new FileInputStream(zipFile);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fileInputStream.read(buffer)) > 0) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            fileInputStream.close();
            byteArrayOutputStream.close();
            byte[] binaryData = byteArrayOutputStream.toByteArray();
            System.out.println("binaryData: " + binaryData.toString());

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(binaryData);
            ZipInputStream zipInputStream = new ZipInputStream(byteArrayInputStream);
            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                System.out.println(zipEntry.getName());
                if (!zipEntry.getName().endsWith("pdf")) {
                    continue;
                }

                PDDocument pdDocument = PDDocument.load(zipInputStream);
                PDFRenderer pdfRenderer = new PDFRenderer(pdDocument);

                // pdf-png并调整清晰度
                BufferedImage bufferedImage = pdfRenderer.renderImage(0, 4f, ImageType.BINARY, RenderDestination.VIEW);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "png", bos);

                byte[] bytes = bos.toByteArray();
                os.write(bytes, 0, bytes.length);
                os.close();
                byteArrayOutputStream.close();
                pdDocument.close();
                zipInputStream.close();
                byteArrayInputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ApiOperation("getArray")
    @GetMapping("/getArray")
    public void testGetArray(@RequestParam Integer[] ids) {
        log.info("are you ok:{}",ids);
        System.out.println(ids);
    }

    @ApiOperation("getArray02")
    @GetMapping("/getArray02")
    public void testGetArray02(@RequestParam(value = "ids[]", required = true) List<String> ids) {
        log.info("are you ok:{}",ids);
        System.out.println(ids);
    }




}
