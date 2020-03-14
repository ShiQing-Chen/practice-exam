package com.example.practiceexam.dao;

import com.example.practiceexam.dto.GenerateQuesDto;
import com.example.practiceexam.dto.QuesDto;
import com.example.practiceexam.param.GenerateSearchQuesParam;
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

    /**
     * 组卷关系获取试题
     * 分页查询
     * @param param
     * @return
     */
    List<GenerateQuesDto> generateGetListByPage(GenerateSearchQuesParam param);

    /**
     * 组卷关系获取试题
     * 分页查询
     * @param param
     * @return
     */
    Integer generateGetCountByPage(GenerateSearchQuesParam param);
}
