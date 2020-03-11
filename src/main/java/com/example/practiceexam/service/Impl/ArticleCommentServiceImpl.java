package com.example.practiceexam.service.Impl;

import com.example.common.cache.SharedUser;
import com.example.common.util.IdGeneratorUtils;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.dao.ArticleCommentDao;
import com.example.practiceexam.dao.UserInfoDao;
import com.example.practiceexam.form.AddCommentForm;
import com.example.practiceexam.model.ArticleComment;
import com.example.practiceexam.model.UserInfo;
import com.example.practiceexam.param.SearchCommentParam;
import com.example.practiceexam.service.ArticleCommentService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author ShiQing_Chen  2020/3/5  18:04
 **/
@Service
public class ArticleCommentServiceImpl implements ArticleCommentService {
    @Autowired
    private ArticleCommentDao articleCommentDao;
    @Autowired
    private UserInfoDao userInfoDao;

    /**
     * 发表评论
     * @param sharedUser
     * @param commentForm
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public MessageVo add(SharedUser sharedUser, AddCommentForm commentForm) {
        if (commentForm != null && sharedUser != null) {
            ArticleComment comment = new ArticleComment();
            Date curDate = new Date();
            BeanUtils.copyProperties(commentForm, comment);
            comment.setCommentId(IdGeneratorUtils.getNewId());
            comment.setCreateUserId(sharedUser.getUserId());
            comment.setCreateUserName(sharedUser.getNickName());
            comment.setCreateTime(curDate);
            UserInfo userInfo = userInfoDao.getById(sharedUser.getUserId());
            if (userInfo != null) {
                comment.setCreateUserAvatar(userInfo.getAvatar());
            }
            articleCommentDao.save(comment);
            return MessageVo.success();
        }
        return MessageVo.fail("评论失败！");
    }

    /**
     * 分页查询
     * @param param
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public MessageVo getListByPage(SearchCommentParam param) {
        if (param != null) {
            if (param.getOffset() == null) {
                param.setOffset(0);
            }
            if (param.getLimit() == null) {
                param.setLimit(10);
            }
            List<ArticleComment> list = articleCommentDao.getListByPage(param.getArticleId(), param.getOffset(), param.getLimit());
            Integer count = articleCommentDao.getCountByPage(param.getArticleId());
            Map<String, Object> map = Maps.newHashMap();
            map.put("list", list);
            map.put("total", count);
            return MessageVo.success(map);
        } else {
            return MessageVo.success(Lists.newArrayList());
        }
    }
}
