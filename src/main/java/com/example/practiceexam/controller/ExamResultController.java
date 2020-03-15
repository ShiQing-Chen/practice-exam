package com.example.practiceexam.controller;

import com.example.common.cache.SharedUser;
import com.example.common.util.BindingResultUtils;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.form.AddExamResultForm;
import com.example.practiceexam.form.AddMarkExamResultForm;
import com.example.practiceexam.service.ExamResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * @author ShiQing_Chen  2020/3/15  15:23
 **/
@Controller
public class ExamResultController {
    private final ExamResultService examResultService;

    @Autowired
    public ExamResultController(ExamResultService examResultService) {
        this.examResultService = examResultService;
    }

    /**
     * 添加
     * @param sharedUser
     * @param form
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/examResult/add", method = RequestMethod.POST)
    @ResponseBody
    public MessageVo add(SharedUser sharedUser, @RequestBody @Valid AddExamResultForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return MessageVo.fail(BindingResultUtils.getErrorString(bindingResult));
        }
        return examResultService.add(sharedUser, form);
    }

    /**
     * 校验是否可以考试
     * @param paperId 试卷ID
     * @return
     */
    @RequestMapping(value = "/examResult/checkDoExam", method = RequestMethod.GET)
    @ResponseBody
    public MessageVo checkDoExam(SharedUser sharedUser, Long paperId) {
        if (paperId == null) {
            return MessageVo.fail("缺少试卷ID参数！");
        }
        return examResultService.checkDoExam(sharedUser, paperId);
    }

    /**
     * 学生
     * 根据试卷ID获取试题和结果
     * @param paperId
     * @return
     */
    @RequestMapping(value = "/examResult/student/getQuesListByPaperId", method = RequestMethod.GET)
    @ResponseBody
    public MessageVo studentGetQuesListByPaperId(SharedUser sharedUser, Long paperId) {
        if (paperId == null) {
            return MessageVo.fail("缺少试卷ID参数！");
        }
        return examResultService.studentGetQuesListByPaperId(sharedUser, paperId);
    }

    /**
     * 批改
     * 根据试卷ID和试题ID随机获取未批改的结果
     * @param paperId
     * @return
     */
    @RequestMapping(value = "/examResult/mark/getResultByPaperIdAndQuesId", method = RequestMethod.GET)
    @ResponseBody
    public MessageVo getResultByPaperIdAndQuesId(Long paperId, Long questionId) {
        if (paperId == null) {
            return MessageVo.fail("缺少试卷ID参数！");
        }
        if (questionId == null) {
            return MessageVo.fail("缺少试题ID参数！");
        }
        return examResultService.getResultByPaperIdAndQuesId(paperId, questionId);
    }

    /**
     * 教师批改提交
     * @param sharedUser
     * @param form
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/examResult/mark/add", method = RequestMethod.POST)
    @ResponseBody
    public MessageVo markAdd(SharedUser sharedUser, @RequestBody @Valid AddMarkExamResultForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return MessageVo.fail(BindingResultUtils.getErrorString(bindingResult));
        }
        return examResultService.markAdd(sharedUser, form);
    }
}
