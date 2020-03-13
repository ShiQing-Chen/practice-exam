package com.example.practiceexam.service.Impl;

import com.example.common.cache.SharedUser;
import com.example.common.util.IdGeneratorUtils;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.dao.QuestionInfoDao;
import com.example.practiceexam.dto.QuesDto;
import com.example.practiceexam.form.AddQuesForm;
import com.example.practiceexam.form.UpdateQuesForm;
import com.example.practiceexam.model.QuestionInfo;
import com.example.practiceexam.model.UserInfo;
import com.example.practiceexam.param.SearchQuesParam;
import com.example.practiceexam.service.QuestionInfoService;
import com.example.practiceexam.utils.DateCodeUtil;
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
 * @author ShiQing_Chen  2020/3/12  02:00
 **/
@Service
public class QuestionInfoServiceImpl implements QuestionInfoService {

    @Autowired
    private QuestionInfoDao questionInfoDao;

    /**
     * 添加
     * @param quesForm
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public MessageVo add(SharedUser sharedUser, AddQuesForm quesForm) {
        if (quesForm != null && sharedUser != null) {
            // 如果当前用户为教师，则根据教师的课程添加
            if (sharedUser.getUserType() == null) {
                return MessageVo.fail("试题添加失败，当前用户无类型！");
            }
            QuestionInfo questionInfo = new QuestionInfo();
            Date curDate = new Date();
            BeanUtils.copyProperties(quesForm, questionInfo);
            questionInfo.setQuestionId(IdGeneratorUtils.getNewId());
            // 生成编号
            questionInfo.setQuestionCode(DateCodeUtil.getLocalTrmSeqNum());
            // 课程
            if (sharedUser.getUserType().equals(UserInfo.TYPE_TEACHER)) {
                questionInfo.setCourseId(sharedUser.getCourseId());
            }
            questionInfo.setCreateUserId(sharedUser.getUserId());
            questionInfo.setCreateTime(curDate);
            questionInfoDao.save(questionInfo);
            return MessageVo.success();
        }
        return MessageVo.fail("添加试题失败！");
    }

    /**
     * 修改信息
     * @param quesForm
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public MessageVo update(SharedUser sharedUser, UpdateQuesForm quesForm) {
        if (quesForm != null && sharedUser != null) {
            QuestionInfo questionInfo = questionInfoDao.getById(quesForm.getQuestionId());
            if (questionInfo != null) {
                Date curDate = new Date();
                BeanUtils.copyProperties(quesForm, questionInfo);
                questionInfo.setUpdateTime(curDate);
                questionInfoDao.save(questionInfo);
                return MessageVo.success();
            }
        }
        return MessageVo.fail("修改试题信息失败！");
    }

    /**
     * 根据id获取信息
     * @param questionId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public MessageVo getById(Long questionId) {
        if (questionId != null) {
            QuestionInfo questionInfo = questionInfoDao.getById(questionId);
            if (questionInfo != null) {
                return MessageVo.success(questionInfo);
            }
        }
        return MessageVo.fail("获取试题失败！");
    }

    /**
     * 随机获取到某课程下
     * 待审核的试题
     * @param courseId 课程ID
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public MessageVo getReadyReviewByCourseId(Long courseId) {
        if (courseId != null) {
            QuestionInfo questionInfo = questionInfoDao.getReadyReviewByCourseId(courseId);
            if (questionInfo != null) {
                return MessageVo.success(questionInfo);
            } else {
                return MessageVo.fail("题库的试题已经审核完，暂无待审试题！");
            }
        }
        return MessageVo.fail("获取试题失败！");
    }

    /**
     * 根据id提交审核试题
     * @param questionId
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public MessageVo submit(Long questionId) {
        if (questionId != null) {
            QuestionInfo questionInfo = questionInfoDao.getById(questionId);
            if (questionInfo != null) {
                // 判断试题状态，是否为草稿或审核不通过
                Integer quesStatus = questionInfo.getQuestionStatus();
                if (quesStatus != null) {
                    if (quesStatus.equals(QuestionInfo.STATUS_PASS)) {
                        return MessageVo.fail("提交审核失败，该试题已被审核通过！");
                    } else if (quesStatus.equals(QuestionInfo.STATUS_READY_REVIEW)) {
                        return MessageVo.fail("提交审核失败，该试题已提交审核！");
                    }
                } else {
                    return MessageVo.fail("该试题数据异常，不存在试题状态！");
                }
                questionInfo.setQuestionStatus(QuestionInfo.STATUS_READY_REVIEW);
                questionInfo.setUpdateTime(new Date());
                questionInfoDao.save(questionInfo);
                return MessageVo.success();
            }
        }
        return MessageVo.fail("提交审核试题失败！");
    }

    /**
     * 根据id删除
     * @param questionId
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public MessageVo delById(Long questionId) {
        if (questionId != null) {
            int result = questionInfoDao.delById(questionId);
            if (result > 0) {
                return MessageVo.success();
            }
        }
        return MessageVo.fail("删除试题失败！");
    }

    /**
     * 分页查询
     * @param param
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public MessageVo getListByPage(SearchQuesParam param) {
        if (param != null) {
            List<QuesDto> list = questionInfoDao.getListByPage(param);
            Integer count = questionInfoDao.getCountByPage(param);
            Map<String, Object> map = Maps.newHashMap();
            map.put("list", list);
            map.put("total", count);
            return MessageVo.success(map);
        } else {
            return MessageVo.success(Lists.newArrayList());
        }
    }
}