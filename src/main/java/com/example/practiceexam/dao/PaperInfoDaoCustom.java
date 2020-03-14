package com.example.practiceexam.dao;

import com.example.practiceexam.dto.PaperInfoDto;
import com.example.practiceexam.param.SearchPaperParam;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ShiQing_Chen  2020/3/14  02:37
 **/
@Repository
public interface PaperInfoDaoCustom {
    /**
     * 分页查询
     * @param param
     * @return
     */
    List<PaperInfoDto> getListByPage(SearchPaperParam param);

    /**
     * 分页查询
     * @param param
     * @return
     */
    Integer getCountByPage(SearchPaperParam param);
}