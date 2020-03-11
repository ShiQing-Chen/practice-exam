package com.example.practiceexam.controller;

import com.example.practiceexam.service.QuestionInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author ShiQing_Chen  2020/3/12  02:01
 **/
@Controller
public class QuestionInfoController {
    private final QuestionInfoService questionInfoService;

    @Autowired
    public QuestionInfoController(QuestionInfoService questionInfoService) {
        this.questionInfoService = questionInfoService;
    }


}
