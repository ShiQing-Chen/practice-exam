package com.example.practiceexam.dao;

import com.example.practiceexam.dto.NoticeDto;
import com.example.practiceexam.dto.NoticeInfoDto;
import com.example.practiceexam.dto.ValueLabelDto;
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

    /**
     * 根据ID获取详情
     * @param noticeId
     * @return
     */
    NoticeInfoDto getInfoById(Long noticeId);

    /**
     * 首页获取前5条
     * @return
     */
    List<ValueLabelDto> indexGetList();
}
