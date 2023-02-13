package com.yupi.usercenter.enums;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * @author : HP
 * @date : 2023/2/13
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum GenderEnum {
    MAN(0,"男"),
    WOMAN(1,"女");
    private Integer code;

    private String name;

    public static Integer getCodeByName(String name) {
        if (name == null) {
            return null;
        } else {
            for (GenderEnum enumValue : GenderEnum.values()) {
                if (name.equals(enumValue.getName())) {
                    return enumValue.getCode();
                }
            }
            return null;
        }
    }

    public static String getNameByCode(Integer code) {
        if (code == null) {
            return StringUtils.EMPTY;
        } else {
            for (GenderEnum enumValue : GenderEnum.values()) {
                if (code.equals(enumValue.getCode())) {
                    return enumValue.getName();
                }
            }
            return StringUtils.EMPTY;
        }
    }
}
