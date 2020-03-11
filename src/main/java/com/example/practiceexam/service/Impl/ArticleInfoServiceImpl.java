package com.example.practiceexam.service.Impl;

import com.example.common.cache.SharedUser;
import com.example.common.util.IdGeneratorUtils;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.dao.ArticleInfoDao;
import com.example.practiceexam.dao.UserInfoDao;
import com.example.practiceexam.dto.ArticleDto;
import com.example.practiceexam.dto.ArticleInfoDto;
import com.example.practiceexam.form.AddArticleForm;
import com.example.practiceexam.model.ArticleInfo;
import com.example.practiceexam.model.UserInfo;
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

    @Autowired
    private UserInfoDao userInfoDao;

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
     * 根据id删除
     * @param articleId
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public MessageVo delById(Long articleId) {
        if (articleId != null) {
            int result = articleInfoDao.delById(articleId);
            if (result > 0) {
                return MessageVo.success();
            }
        }
        return MessageVo.fail("删除帖子失败！");
    }

    /**
     * 获取帖子详细信息
     * @param articleId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public MessageVo getInfoById(Long articleId) {
        if (articleId != null) {
            ArticleInfoDto dto = new ArticleInfoDto();
            ArticleInfo articleInfo = articleInfoDao.getById(articleId);
            if (articleInfo != null) {
                BeanUtils.copyProperties(articleInfo, dto);
                if (articleInfo.getCreateUserId() != null) {
                    UserInfo userInfo = userInfoDao.getById(articleInfo.getCreateUserId());
                    if (userInfo != null) {
                        dto.setCreateUserName(userInfo.getNickName());
                        dto.setUserAvatar(userInfo.getAvatar());
                    }
                }
                return MessageVo.success(dto);
            }
        }
        return MessageVo.fail("获取帖子详细失败！");
    }

    /**
     * 分页查询
     * @param param
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public MessageVo getListByPage(SharedUser sharedUser, SearchArticleParam param) {
        if (param != null && sharedUser != null) {
            if (param.getSearchMy() != null && param.getSearchMy()) {
                param.setCreateUserId(sharedUser.getUserId());
            }
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
