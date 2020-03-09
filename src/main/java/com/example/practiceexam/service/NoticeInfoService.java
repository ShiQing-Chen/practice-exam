package com.example.practiceexam.service;

import com.example.common.cache.SharedUser;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.form.AddNoticeForm;
import com.example.practiceexam.form.UpdateNoticeForm;
import com.example.practiceexam.param.SearchNoticeParam;

/**
 * @author ShiQing_Chen  2020/3/5  18:00
 **/
public interface NoticeInfoService {
    /**
     * 添加
     * @param form
     * @return
     */
    MessageVo add(SharedUser sharedUser, AddNoticeForm form);

    /**
     * 修改信息
     * @param form
     * @return
     */
    MessageVo update(SharedUser sharedUser, UpdateNoticeForm form);

    /**
     * 根据id删除
     * @param noticeId
     * @return
     */
    MessageVo delById(Long noticeId);

    /**
     * 根据id获取信息
     * @param noticeId
     * @return
     */
    MessageVo getById(Long noticeId);

    /**
     * 根据id获取详细信息
     * @param noticeId
     * @return
     */
    MessageVo getInfoById(Long noticeId);

    /**
     * 发布通知
     * @param sharedUser
     * @param noticeId
     * @return
     */
    MessageVo publicNotice(SharedUser sharedUser, Long noticeId);

    /**
     * 分页查询
     * @param noticeParam
     * @return
     */
    MessageVo getListByPage(SearchNoticeParam noticeParam);

    /**
     * 首页获取前5条
     * @return
     */
    MessageVo indexGetList();
}
