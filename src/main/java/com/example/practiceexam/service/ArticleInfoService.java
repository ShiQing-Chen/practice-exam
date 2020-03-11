package com.example.practiceexam.service;

import com.example.common.cache.SharedUser;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.form.AddArticleForm;
import com.example.practiceexam.param.SearchArticleParam;

/**
 * @author ShiQing_Chen  2020/3/5  18:01
 **/
public interface ArticleInfoService {

    /**
     * 发帖
     * @param sharedUser
     * @param addArticleForm
     * @return
     */
    MessageVo add(SharedUser sharedUser, AddArticleForm addArticleForm);

    /**
     * 根据id删除
     * @param articleId
     * @return
     */
    MessageVo delById(Long articleId);

    /**
     * 获取帖子详细信息
     * @param articleId
     * @return
     */
    MessageVo getInfoById(Long articleId);

    /**
     * 分页查询
     * @param param
     * @return
     */
    MessageVo getListByPage(SharedUser sharedUser, SearchArticleParam param);
}
