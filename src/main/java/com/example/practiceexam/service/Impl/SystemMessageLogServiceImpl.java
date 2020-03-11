package com.example.practiceexam.service.Impl;

import com.example.common.cache.SharedUser;
import com.example.common.util.IdGeneratorUtils;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.dao.MessageInfoDao;
import com.example.practiceexam.dao.SystemMessageLogDao;
import com.example.practiceexam.dao.UserInfoDao;
import com.example.practiceexam.dto.SystemMessageLogDto;
import com.example.practiceexam.form.AddSystemMessageLogForm;
import com.example.practiceexam.model.MessageInfo;
import com.example.practiceexam.model.SystemMessageLog;
import com.example.practiceexam.model.UserInfo;
import com.example.practiceexam.param.SearchSystemMessageParam;
import com.example.practiceexam.service.SystemMessageLogService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author ShiQing_Chen  2020/3/11  19:48
 **/
@Service
public class SystemMessageLogServiceImpl implements SystemMessageLogService {
    @Autowired
    private SystemMessageLogDao systemMessageLogDao;
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private MessageInfoDao messageInfoDao;


    /**
     * 如果为多类型，则自动创建每种类型一条系统消息
     * 添加
     * @param form
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public MessageVo add(SharedUser sharedUser, AddSystemMessageLogForm form) {
        if (form != null && sharedUser != null) {
            // 记录消息
            List<Integer> typeList = form.getAcceptUserType();
            if (!CollectionUtils.isEmpty(typeList)) {
                List<MessageInfo> messageInfoList = Lists.newArrayList();
                for (Integer type : typeList) {
                    SystemMessageLog systemMessageLog = new SystemMessageLog();
                    Date curDate = new Date();
                    systemMessageLog.setMessageId(IdGeneratorUtils.getNewId());
                    systemMessageLog.setAcceptUserType(type);
                    systemMessageLog.setMessageContent(form.getMessageContent());
                    systemMessageLog.setCreateUserId(sharedUser.getUserId());
                    systemMessageLog.setCreateTime(curDate);
                    systemMessageLogDao.save(systemMessageLog);
                    // 根据类型查找用户发送消息
                    List<UserInfo> userInfoList = userInfoDao.getListByUserType(type);
                    if (!CollectionUtils.isEmpty(userInfoList)) {
                        userInfoList.forEach(e -> {
                            MessageInfo messageInfo = new MessageInfo();
                            messageInfo.setMessageId(IdGeneratorUtils.getNewId());
                            messageInfo.setMessageType(MessageInfo.TYPE_SYSTEM);
                            messageInfo.setAcceptUserId(e.getUserId());
                            messageInfo.setMessageContent(form.getMessageContent());
                            messageInfo.setCreateTime(curDate);
                            messageInfoList.add(messageInfo);
                        });
                    }
                }
                if (!CollectionUtils.isEmpty(messageInfoList)) {
                    messageInfoDao.saveAll(messageInfoList);
                }
            }
            return MessageVo.success();
        }
        return MessageVo.fail("发送系统消息失败！");
    }

    /**
     * 分页查询
     * @param param
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public MessageVo getListByPage(SearchSystemMessageParam param) {
        if (param != null) {
            List<SystemMessageLogDto> list = systemMessageLogDao.getListByPage(param);
            Integer count = systemMessageLogDao.getCountByPage(param);
            Map<String, Object> map = Maps.newHashMap();
            map.put("list", list);
            map.put("total", count);
            return MessageVo.success(map);
        } else {
            return MessageVo.success(Lists.newArrayList());
        }
    }
}
