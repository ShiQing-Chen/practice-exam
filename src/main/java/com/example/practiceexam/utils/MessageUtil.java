package com.example.practiceexam.utils;

import com.example.common.util.DateUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * @author ShiQing_Chen  2020/3/11  17:51
 **/
public class MessageUtil {

    /**
     * 加工评论消息通知
     * @param userName     评论人昵称
     * @param articleTitle 帖子主题
     * @param date         时间
     * @return
     */
    public static String getCommentMessage(String userName, String articleTitle, Date date) {
        if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(articleTitle) && date != null) {
            return "【" + DateUtils.getDateString(date) + "】" + userName + "  对您发布的《" + articleTitle + "》进行了评论";
        }
        return null;
    }
}
