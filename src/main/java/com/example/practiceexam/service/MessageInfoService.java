package com.example.practiceexam.service;

import com.example.common.cache.SharedUser;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.param.SearchMessageParam;

/**
 * @author ShiQing_Chen  2020/3/11  18:28
 **/
public interface MessageInfoService {
    /**
     * 根据id删除
     * @param messageId
     * @return
     */
    MessageVo delById(Long messageId);

    /**
     * 已读
     * @param messageId
     * @return
     */
    MessageVo readMessage(Long messageId);

    /**
     * 分页查询
     * @param param
     * @return
     */
    MessageVo getListByPage(SharedUser sharedUser, SearchMessageParam param);
}
