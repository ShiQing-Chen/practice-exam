package com.example.practiceexam.controller;

import com.example.common.cache.SharedUser;
import com.example.common.util.BindingResultUtils;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.form.AddQuesReviewForm;
import com.example.practiceexam.service.QuestionReviewLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * @author ShiQing_Chen  2020/3/12  23:28
 **/
@Controller
public class QuestionReviewLogController {
    private final QuestionReviewLogService questionReviewLogService;

    @Autowired
    public QuestionReviewLogController(QuestionReviewLogService questionReviewLogService) {
        this.questionReviewLogService = questionReviewLogService;
    }

    /**
     * 添加
     * @param sharedUser
     * @param reviewForm
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/questionReview/add", method = RequestMethod.POST)
    @ResponseBody
    public MessageVo add(SharedUser sharedUser, @RequestBody @Valid AddQuesReviewForm reviewForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return MessageVo.fail(BindingResultUtils.getErrorString(bindingResult));
        }
        return questionReviewLogService.add(sharedUser, reviewForm);
    }

}
