package com.example.practiceexam.dao;

import com.example.practiceexam.dto.ArticleDto;
import com.example.practiceexam.param.SearchArticleParam;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ShiQing_Chen  2020/3/11  01:05
 **/
@Repository
public interface ArticleInfoDaoCustom {

    /**
     * 分页查询
     * @param param
     * @return
     */
    List<ArticleDto> getListByPage(SearchArticleParam param);

    /**
     * 分页查询
     * @param param
     * @return
     */
    Integer getCountByPage(SearchArticleParam param);
}
