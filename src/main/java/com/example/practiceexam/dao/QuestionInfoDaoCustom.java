package com.example.practiceexam.dao;

import com.example.practiceexam.dto.QuesDto;
import com.example.practiceexam.param.SearchQuesParam;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ShiQing_Chen  2020/3/12  10:58
 **/
@Repository
public interface QuestionInfoDaoCustom {
    /**
     * 分页查询
     * @param param
     * @return
     */
    List<QuesDto> getListByPage(SearchQuesParam param);

    /**
     * 分页查询
     * @param param
     * @return
     */
    Integer getCountByPage(SearchQuesParam param);
}
