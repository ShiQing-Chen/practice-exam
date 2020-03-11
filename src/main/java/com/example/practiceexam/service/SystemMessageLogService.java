package com.example.practiceexam.service;

import com.example.common.cache.SharedUser;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.form.AddSystemMessageLogForm;
import com.example.practiceexam.param.SearchSystemMessageParam;

/**
 * @author ShiQing_Chen  2020/3/11  19:44
 **/
public interface SystemMessageLogService {
    /**
     * 添加
     * @param form
     * @return
     */
    MessageVo add(SharedUser sharedUser, AddSystemMessageLogForm form);

    /**
     * 分页查询
     * @param param
     * @return
     */
    MessageVo getListByPage(SearchSystemMessageParam param);
}
