package com.example.practiceexam.service.Impl;

import com.example.practiceexam.dao.QuestionInfoDao;
import com.example.practiceexam.model.QuestionInfo;
import com.example.practiceexam.service.QuestionInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ShiQing_Chen  2020/3/12  02:00
 **/
@Service
public class QuestionInfoServiceImpl implements QuestionInfoService {
    @Autowired
    private QuestionInfoDao questionInfoDao;
}
