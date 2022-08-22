package com.yupi.usercenter.model.domain;

import java.io.Serializable;
import java.util.TreeMap;

/**
 * 微信消息模板
 *
 * @author : HP
 * @date : 2022/8/22
 */
public class WxTemplateMessage implements Serializable {

    private static final long serialVersionUID = 522516428719928671L;

    /**
     * 接收者openId
     */
    private String touser;

    /**
     * 模板id
     */
    private String template_id;

    /**
     * 模板跳转链接
     */
    private String url;

    /**
     * 消息头颜色
     */
    private String topcolor;

    /**
     * 消息data
     */
    private TreeMap<String, TreeMap<String, String>> data;

    public static TreeMap<String, String> item(String value, String color) {
        TreeMap<String, String> params = new TreeMap<>();
        params.put("value", value);
        params.put("color", color);
        return params;
    }

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTopcolor() {
        return topcolor;
    }

    public void setTopcolor(String topcolor) {
        this.topcolor = topcolor;
    }

    public TreeMap<String, TreeMap<String, String>> getData() {
        return data;
    }

    public void setData(TreeMap<String, TreeMap<String, String>> data) {
        this.data = data;
    }
}
