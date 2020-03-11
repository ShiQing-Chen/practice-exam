package com.example.practiceexam.service.Impl;

import com.example.common.cache.SharedUser;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.dao.MessageInfoDao;
import com.example.practiceexam.model.MessageInfo;
import com.example.practiceexam.param.SearchMessageParam;
import com.example.practiceexam.service.MessageInfoService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author ShiQing_Chen  2020/3/11  18:28
 **/
@Service
public class MessageInfoServiceImpl implements MessageInfoService {
    @Autowired
    private MessageInfoDao messageInfoDao;


    /**
     * 根据id删除
     * @param messageId
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public MessageVo delById(Long messageId) {
        if (messageId != null) {
            int result = messageInfoDao.delById(messageId);
            if (result > 0) {
                return MessageVo.success();
            }
        }
        return MessageVo.fail("删除消息失败！");
    }

    /**
     * 已读
     * @param messageId
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public MessageVo readMessage(Long messageId) {
        if (messageId != null) {
            MessageInfo messageInfo = messageInfoDao.getById(messageId);
            if (messageInfo != null) {
                messageInfo.setMessageStatus(MessageInfo.STATUS_READ);
                messageInfo.setUpdateTime(new Date());
                messageInfoDao.save(messageInfo);
                return MessageVo.success();
            }
        }
        return MessageVo.fail("读取消息失败！");
    }

    /**
     * 分页查询
     * @param param
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public MessageVo getListByPage(SharedUser sharedUser, SearchMessageParam param) {
        if (param != null) {
            if (param.getOffset() == null) {
                param.setOffset(0);
            }
            if (param.getLimit() == null) {
                param.setLimit(10);
            }
            if (param.getMessageStatus() != null) {
                List<MessageInfo> list = messageInfoDao.getListByPage(sharedUser.getUserId(), param.getMessageStatus(), param.getOffset(), param.getLimit());
                Integer count = messageInfoDao.getCountByPage(sharedUser.getUserId(), param.getMessageStatus());
                Map<String, Object> map = Maps.newHashMap();
                map.put("list", list);
                map.put("total", count);
                return MessageVo.success(map);
            } else {
                List<MessageInfo> list = messageInfoDao.getListByPage(sharedUser.getUserId(), param.getOffset(), param.getLimit());
                Integer count = messageInfoDao.getCountByPage(sharedUser.getUserId());
                Map<String, Object> map = Maps.newHashMap();
                map.put("list", list);
                map.put("total", count);
                return MessageVo.success(map);
            }
        } else {
            return MessageVo.success(Lists.newArrayList());
        }
    }
}
