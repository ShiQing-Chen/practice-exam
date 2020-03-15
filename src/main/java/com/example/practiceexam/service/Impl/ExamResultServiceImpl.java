package com.example.practiceexam.service.Impl;

import com.example.common.cache.SharedUser;
import com.example.common.util.IdGeneratorUtils;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.dao.ExamResultDao;
import com.example.practiceexam.dao.PaperGenerateDao;
import com.example.practiceexam.dao.PaperInfoDao;
import com.example.practiceexam.dao.QuestionInfoDao;
import com.example.practiceexam.dto.ExamResultDto;
import com.example.practiceexam.form.AddExamResultForm;
import com.example.practiceexam.form.AddMarkExamResultForm;
import com.example.practiceexam.model.ExamResult;
import com.example.practiceexam.model.PaperInfo;
import com.example.practiceexam.model.QuestionInfo;
import com.example.practiceexam.service.ExamResultService;
import com.example.practiceexam.vo.StudentSubmitExamResultVo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ShiQing_Chen  2020/3/15  15:23
 **/
@Service
public class ExamResultServiceImpl implements ExamResultService {
    @Autowired
    private ExamResultDao examResultDao;
    @Autowired
    private PaperGenerateDao paperGenerateDao;
    @Autowired
    private QuestionInfoDao questionInfoDao;
    @Autowired
    private PaperInfoDao paperInfoDao;

    /**
     * 添加
     * @param form
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public MessageVo add(SharedUser sharedUser, AddExamResultForm form) {
        if (sharedUser != null && form != null) {
            if (sharedUser.getStudentId() == null) {
                return MessageVo.fail("试卷提交失败，未获取到当前用户学生信息！");
            }
            List<StudentSubmitExamResultVo> resultList = form.getResultList();
            if (CollectionUtils.isEmpty(resultList)) {
                return MessageVo.fail("提交结果为空，请填写后再提交");
            }
            // 获取分数
            BigDecimal choiceScore = paperGenerateDao.getChoiceScoreByPaperId(form.getPaperId());
            List<QuestionInfo> questionInfoList = questionInfoDao.getQuesListByPaperId(form.getPaperId());
            Map<Long, List<QuestionInfo>> map = questionInfoList.stream().collect(Collectors.groupingBy(QuestionInfo::getQuestionId));
            Date curDate = new Date();
            List<ExamResult> examResultList = Lists.newArrayList();
            for (StudentSubmitExamResultVo examResultVo : resultList) {
                ExamResult examResult = new ExamResult();
                BeanUtils.copyProperties(examResultVo, examResult);
                examResult.setResultId(IdGeneratorUtils.getNewId());
                examResult.setCreateTime(curDate);
                examResult.setCreateUserId(sharedUser.getUserId());
                examResult.setStudentId(sharedUser.getStudentId());
                // 自动对选择题进行判断
                Integer quesType = examResultVo.getQuestionType();
                if (quesType != null && quesType.equals(1)) {
                    String resultAns = examResultVo.getResultAnswer();
                    if (StringUtils.isNotBlank(resultAns)) {
                        List<QuestionInfo> quesList = map.get(examResultVo.getQuestionId());
                        if (!CollectionUtils.isEmpty(quesList)) {
                            QuestionInfo ques = quesList.get(0);
                            if (ques != null && StringUtils.isNotBlank(ques.getQuestionAnswer())) {
                                if (resultAns.equals(ques.getQuestionAnswer())) {
                                    examResult.setResultScore(choiceScore);
                                }
                            }
                        }
                    }
                    examResult.setResultStatus(ExamResult.STATUS_MARK);
                    examResult.setMarkTime(curDate);
                } else {
                    examResult.setResultStatus(ExamResult.STATUS_NOT_MARK);
                }
                examResultList.add(examResult);
            }
            if (!CollectionUtils.isEmpty(examResultList)) {
                PaperInfo paperInfo = paperInfoDao.getById(form.getPaperId());
                if (paperInfo != null && paperInfo.getPaperType() != null && paperInfo.getPaperType().equals(4)) {
                    paperInfo.setEndTime(curDate);
                    paperInfoDao.save(paperInfo);
                }
                examResultDao.saveAll(examResultList);
                return MessageVo.success();
            }
        }
        return MessageVo.fail("试卷提交失败！");
    }

    /**
     * 校验是否可以考试
     * @param paperId 试卷ID
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public MessageVo checkDoExam(SharedUser sharedUser, Long paperId) {
        if (sharedUser != null && paperId != null) {
            if (sharedUser.getStudentId() == null) {
                return MessageVo.fail("当前用户未绑定学生信息，校验失败！");
            }
            int count = examResultDao.getCountByPaperIdAndStudentId(paperId, sharedUser.getStudentId());
            return MessageVo.success(count);
        }
        return MessageVo.fail("校验失败!");
    }

    /**
     * 学生
     * 根据试卷ID获取试题和结果
     * @param paperId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public MessageVo studentGetQuesListByPaperId(SharedUser sharedUser, Long paperId) {
        if (sharedUser != null && paperId != null) {
            if (sharedUser.getStudentId() == null) {
                return MessageVo.fail("当前用户未绑定学生信息，获取数据失败！");
            }
            List<ExamResultDto> dtoList = examResultDao.getQuesListByPaperIdAndStudent(paperId, sharedUser.getStudentId());
            BigDecimal totalScore = examResultDao.getStudentScoreByPaperIdAndStudentId(paperId, sharedUser.getStudentId());
            Map<String, Object> map = Maps.newHashMap();
            map.put("list", dtoList);
            map.put("totalScore", totalScore);
            return MessageVo.success(map);
        }
        return MessageVo.fail("获取数据失败!");
    }

    /**
     * 批改
     * 根据试卷ID和试题ID随机获取未批改的结果
     * @param paperId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public MessageVo getResultByPaperIdAndQuesId(Long paperId, Long questionId) {
        if (paperId != null && questionId != null) {
            List<ExamResult> resultList = examResultDao.getResultByPaperIdAndQuesId(paperId, questionId);
            if (CollectionUtils.isEmpty(resultList)) {
                return MessageVo.success();
            } else {
                return MessageVo.success(resultList.get(0));
            }
        }
        return MessageVo.fail("获取学生答题情况失败！");
    }

    /**
     * 教师批改提交
     * @param sharedUser
     * @param form
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public MessageVo markAdd(SharedUser sharedUser, AddMarkExamResultForm form) {
        if (sharedUser != null && form != null) {
            ExamResult examResult = examResultDao.getById(form.getResultId());
            if (examResult == null) {
                return MessageVo.fail("为获取到该题答题记录！");
            }
            if (examResult.getResultStatus() != null && examResult.getResultStatus().equals(2)) {
                return MessageVo.fail("该学生答题结果已被批改！");
            }
            BeanUtils.copyProperties(form, examResult);
            examResult.setMarkUserId(sharedUser.getUserId());
            examResult.setMarkTime(new Date());
            examResult.setResultStatus(2);
            examResultDao.save(examResult);
            return MessageVo.success();
        }
        return MessageVo.fail("提交失败！");
    }
}
