package com.example.practiceexam.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author ShiQing_Chen
 * @since 2019/8/9 13:38
 */
public class AddMaterialForm {
    /**
     * 主标题
     */
    @NotEmpty(message = "主标题不能为空！")
    @Size(min = 2, max = 200, message = "主标题长度为 2 ~ 200 个字符！")
    private String mainTitle;
    /**
     * 副标题
     */
    @Size(max = 200, message = "副标题长度不超过 200 个字符！")
    private String subTitle;
    /**
     * 资料内容
     */
    @NotEmpty(message = "资料内容不能为空！")
    @Size(max = 1500, message = "资料内容长度不超过 1500 个字符！")
    private String materialContent;
    /**
     * 资料状态 1草稿 2发布
     */
    @NotNull(message = "资料状态不能为空！")
    private Integer materialStatus;

    public String getMainTitle() {
        return mainTitle;
    }

    public void setMainTitle(String mainTitle) {
        this.mainTitle = mainTitle;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getMaterialContent() {
        return materialContent;
    }

    public void setMaterialContent(String materialContent) {
        this.materialContent = materialContent;
    }

    public Integer getMaterialStatus() {
        return materialStatus;
    }

    public void setMaterialStatus(Integer materialStatus) {
        this.materialStatus = materialStatus;
    }
}
