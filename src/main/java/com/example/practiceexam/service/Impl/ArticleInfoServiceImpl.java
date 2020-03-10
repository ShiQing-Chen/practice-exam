package com.example.practiceexam.service.Impl;

import com.example.common.cache.SharedUser;
import com.example.common.util.IdGeneratorUtils;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.dao.ArticleInfoDao;
import com.example.practiceexam.dto.ArticleDto;
import com.example.practiceexam.form.AddArticleForm;
import com.example.practiceexam.model.ArticleInfo;
import com.example.practiceexam.param.SearchArticleParam;
import com.example.practiceexam.service.ArticleInfoService;
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
public class ArticleInfoServiceImpl implements ArticleInfoService {

    @Autowired
    private ArticleInfoDao articleInfoDao;

    /**
     * 发帖
     * @param sharedUser
     * @param addArticleForm
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public MessageVo add(SharedUser sharedUser, AddArticleForm addArticleForm) {
        if (addArticleForm != null && sharedUser != null) {
            ArticleInfo articleInfo = new ArticleInfo();
            Date curDate = new Date();
            BeanUtils.copyProperties(addArticleForm, articleInfo);
            articleInfo.setArticleId(IdGeneratorUtils.getNewId());
            articleInfo.setCreateUserId(sharedUser.getUserId());
            articleInfo.setCreateTime(curDate);
            articleInfoDao.save(articleInfo);
            return MessageVo.success();
        }
        return MessageVo.fail("发帖失败！");
    }

    /**
     * 分页查询
     * @param param
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public MessageVo getListByPage(SearchArticleParam param) {
        if (param != null) {
            List<ArticleDto> list = articleInfoDao.getListByPage(param);
            Integer count = articleInfoDao.getCountByPage(param);
            Map<String, Object> map = Maps.newHashMap();
            map.put("list", list);
            map.put("total", count);
            return MessageVo.success(map);
        } else {
            return MessageVo.success(Lists.newArrayList());
        }
    }
}
