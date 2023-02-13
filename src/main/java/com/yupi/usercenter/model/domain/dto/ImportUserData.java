package com.yupi.usercenter.model.domain.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import io.swagger.annotations.ApiModel;
import lombok.*;

/**
 * @author : HP
 * @date : 2023/2/13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ApiModel("上传用户数据")
public class ImportUserData {

    private String name;

    private String account;

    private String gender;

    private String phone;

    private Integer status;

    private String email;
}
