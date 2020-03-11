package com.example.practiceexam.dao;

import com.example.practiceexam.dto.SystemMessageLogDto;
import com.example.practiceexam.param.SearchSystemMessageParam;

import java.util.List;

/**
 * @author ShiQing_Chen  2020/3/11  19:35
 **/
public interface SystemMessageLogDaoCustom {
    /**
     * 分页查询
     * @param param
     * @return
     */
    List<SystemMessageLogDto> getListByPage(SearchSystemMessageParam param);

    /**
     * 分页查询
     * @param param
     * @return
     */
    Integer getCountByPage(SearchSystemMessageParam param);
}
