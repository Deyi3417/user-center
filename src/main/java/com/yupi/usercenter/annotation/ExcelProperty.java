package com.yupi.usercenter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author HP
 * @interface 可以实现三种功能：
 * (1)声明类:Class
 * (2)声明类别:Category
 * (3)声明扩展:Extension
 *
 * @date 2022-08-02
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelProperty {

}
