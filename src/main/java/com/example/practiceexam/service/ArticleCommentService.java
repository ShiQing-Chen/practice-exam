package com.example.practiceexam.service;

import com.example.common.cache.SharedUser;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.form.AddCommentForm;
import com.example.practiceexam.param.SearchCommentParam;

/**
 * @author ShiQing_Chen  2020/3/5  18:01
 **/
public interface ArticleCommentService {
    /**
     * 发表评论
     * @param sharedUser
     * @param commentForm
     * @return
     */
    MessageVo add(SharedUser sharedUser, AddCommentForm commentForm);

    /**
     * 分页查询
     * @param param
     * @return
     */
    MessageVo getListByPage(SearchCommentParam param);
}
