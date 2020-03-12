package com.example.practiceexam.service.Impl;

import com.example.common.cache.SharedUser;
import com.example.common.util.IdGeneratorUtils;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.dao.QuestionInfoDao;
import com.example.practiceexam.dao.QuestionReviewLogDao;
import com.example.practiceexam.form.AddQuesReviewForm;
import com.example.practiceexam.model.QuestionInfo;
import com.example.practiceexam.model.QuestionReviewLog;
import com.example.practiceexam.service.QuestionReviewLogService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author ShiQing_Chen  2020/3/12  23:11
 **/
@Service
public class QuestionReviewLogServiceImpl implements QuestionReviewLogService {
    @Autowired
    private QuestionInfoDao questionInfoDao;
    @Autowired
    private QuestionReviewLogDao questionReviewLogDao;


    /**
     * 添加
     * @param reviewForm
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public MessageVo add(SharedUser sharedUser, AddQuesReviewForm reviewForm) {
        if (sharedUser != null && reviewForm != null) {
            // 获取试题
            QuestionInfo questionInfo = questionInfoDao.getById(reviewForm.getQuestionId());
            if (questionInfo == null) {
                return MessageVo.fail("审核失败，未查询到审核的试题");
            }
            // 判断试题状态，是否为待审核
            Integer quesStatus = questionInfo.getQuestionStatus();
            if (quesStatus != null) {
                if (quesStatus.equals(QuestionInfo.STATUS_PASS)) {
                    return MessageVo.fail("审核失败，该试题已被审核通过！");
                } else if (quesStatus.equals(QuestionInfo.STATUS_NOT_PASS) || quesStatus.equals(QuestionInfo.STATUS_DRAFT)) {
                    return MessageVo.fail("审核失败，该试题未提交审核！");
                }
            } else {
                return MessageVo.fail("该试题数据异常，不存在试题状态！");
            }

            Date curDate = new Date();

            QuestionReviewLog reviewLog = new QuestionReviewLog();
            BeanUtils.copyProperties(reviewForm, reviewLog);
            reviewLog.setReviewId(IdGeneratorUtils.getNewId());
            reviewLog.setQuestionId(questionInfo.getQuestionId());
            reviewLog.setCreateUserId(sharedUser.getUserId());
            reviewLog.setCreateTime(curDate);
            questionReviewLogDao.save(reviewLog);
            // 更新试题状态
            if (reviewForm.getReviewResult().equals(QuestionReviewLog.STATUS_PASS)) {
                questionInfo.setQuestionStatus(QuestionInfo.STATUS_PASS);
            } else if (reviewForm.getReviewResult().equals(QuestionReviewLog.STATUS_NOT_PASS)) {
                questionInfo.setQuestionStatus(QuestionInfo.STATUS_NOT_PASS);
            }
            questionInfo.setReviewUserId(sharedUser.getUserId());
            questionInfo.setReviewTime(curDate);
            questionInfo.setUpdateTime(curDate);
            questionInfoDao.save(questionInfo);
            return MessageVo.success();
        }
        return MessageVo.fail("审核失败，刷新后重试！");
    }
}
