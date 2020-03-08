package com.example.practiceexam.dao;

import com.example.practiceexam.dto.NoticeDto;
import com.example.practiceexam.param.SearchNoticeParam;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ShiQing_Chen  2020/3/7  22:53
 **/
@Repository
public interface NoticeInfoDaoCustom {
    /**
     * 分页查询
     * @param param
     * @return
     */
    List<NoticeDto> getListByPage(SearchNoticeParam param);

    /**
     * 分页查询
     * @param param
     * @return
     */
    Integer getCountByPage(SearchNoticeParam param);
}
