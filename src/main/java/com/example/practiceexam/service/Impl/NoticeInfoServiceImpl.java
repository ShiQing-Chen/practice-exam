package com.example.practiceexam.service.Impl;

import com.example.common.cache.SharedUser;
import com.example.common.util.IdGeneratorUtils;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.dao.NoticeInfoDao;
import com.example.practiceexam.dto.NoticeDto;
import com.example.practiceexam.form.AddNoticeForm;
import com.example.practiceexam.form.UpdateNoticeForm;
import com.example.practiceexam.model.NoticeInfo;
import com.example.practiceexam.param.SearchNoticeParam;
import com.example.practiceexam.service.NoticeInfoService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author ShiQing_Chen  2020/3/5  18:03
 **/
@Service
public class NoticeInfoServiceImpl implements NoticeInfoService {
    @Autowired
    private NoticeInfoDao noticeInfoDao;

    /**
     * 添加
     * @param form
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public MessageVo add(SharedUser sharedUser, AddNoticeForm form) {
        if (form != null && sharedUser != null) {
            NoticeInfo noticeInfo = new NoticeInfo();
            Date curDate = new Date();
            BeanUtils.copyProperties(form, noticeInfo);
            noticeInfo.setNoticeId(IdGeneratorUtils.getNewId());
            noticeInfo.setCreateUserId(sharedUser.getUserId());
            noticeInfo.setCreateTime(curDate);
            noticeInfo.setUpdateTime(curDate);
            noticeInfoDao.save(noticeInfo);
            return MessageVo.success();
        }
        return MessageVo.fail("添加通知失败！");
    }

    /**
     * 修改信息
     * @param form
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public MessageVo update(SharedUser sharedUser, UpdateNoticeForm form) {
        if (form != null && sharedUser != null) {
            NoticeInfo noticeInfo = noticeInfoDao.getById(form.getNoticeId());
            if (noticeInfo != null) {
                Date curDate = new Date();
                BeanUtils.copyProperties(form, noticeInfo);
                noticeInfo.setUpdateTime(curDate);
                noticeInfoDao.save(noticeInfo);
                return MessageVo.success();
            }
        }
        return MessageVo.fail("修改通知失败！");
    }

    /**
     * 根据id删除
     * @param noticeId
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public MessageVo delById(Long noticeId) {
        if (noticeId != null) {
            int result = noticeInfoDao.delById(noticeId);
            if (result > 0) {
                return MessageVo.success();
            }
        }
        return MessageVo.fail("删除通知失败！");
    }

    /**
     * 根据id获取信息
     * @param noticeId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public MessageVo getById(Long noticeId) {
        if (noticeId != null) {
            NoticeInfo noticeInfo = noticeInfoDao.getById(noticeId);
            if (noticeInfo != null) {
                return MessageVo.success(noticeInfo);
            }
        }
        return MessageVo.fail("获取通知失败！");
    }

    /**
     * 发布通知
     * @param sharedUser
     * @param noticeId
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public MessageVo publicNotice(SharedUser sharedUser, Long noticeId) {
        if (sharedUser != null && noticeId != null) {
            NoticeInfo noticeInfo = noticeInfoDao.getById(noticeId);
            if (noticeInfo == null) {
                return MessageVo.fail("未查询到通知信息");
            }
            Date curDate = new Date();
            noticeInfo.setNoticeStatus(NoticeInfo.STATUS_PUBLIC);
            noticeInfo.setPublishUserId(sharedUser.getUserId());
            noticeInfo.setPublishTime(curDate);
            noticeInfo.setUpdateTime(curDate);
            noticeInfoDao.save(noticeInfo);
            return MessageVo.success("通知发布成功!");
        }
        return MessageVo.fail("发布通知失败！");
    }

    /**
     * 分页查询
     * @param noticeParam
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public MessageVo getListByPage(SearchNoticeParam noticeParam) {
        if (noticeParam != null) {
            List<NoticeDto> noticeDtoList = noticeInfoDao.getListByPage(noticeParam);
            Integer count = noticeInfoDao.getCountByPage(noticeParam);
            Map<String, Object> map = Maps.newHashMap();
            map.put("list", noticeDtoList);
            map.put("total", count);
            return MessageVo.success(map);
        } else {
            return MessageVo.success(Lists.newArrayList());
        }
    }
}
