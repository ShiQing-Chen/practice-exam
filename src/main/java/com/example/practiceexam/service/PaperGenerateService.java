package com.example.practiceexam.service;

import com.example.common.cache.SharedUser;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.form.AddPaperGenerateForm;

/**
 * @author ShiQing_Chen  2020/3/14  12:22
 **/
public interface PaperGenerateService {

    /**
     * 添加
     * @param sharedUser
     * @param generateForm
     * @return
     */
    MessageVo add(SharedUser sharedUser, AddPaperGenerateForm generateForm);
}
