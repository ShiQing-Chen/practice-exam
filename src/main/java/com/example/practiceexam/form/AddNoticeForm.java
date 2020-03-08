package com.example.practiceexam.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author ShiQing_Chen
 * @since 2019/8/9 13:38
 */
public class AddNoticeForm {
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
     * 通知内容
     */
    @NotEmpty(message = "通知内容不能为空！")
    @Size(max = 1500, message = "通知内容长度不超过 1500 个字符！")
    private String noticeContent;
    /**
     * 通知状态 1草稿 2发布
     */
    @NotNull(message = "通知状态不能为空！")
    private Integer noticeStatus;

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

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public Integer getNoticeStatus() {
        return noticeStatus;
    }

    public void setNoticeStatus(Integer noticeStatus) {
        this.noticeStatus = noticeStatus;
    }
}
