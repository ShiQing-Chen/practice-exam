package com.example.practiceexam.controller;

import com.example.common.cache.SharedUser;
import com.example.common.util.BindingResultUtils;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.form.AddNoticeForm;
import com.example.practiceexam.form.UpdateNoticeForm;
import com.example.practiceexam.param.SearchNoticeParam;
import com.example.practiceexam.service.NoticeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * @author ShiQing_Chen  2020/3/5  18:06
 **/
@Controller
public class NoticeInfoController {
    private final NoticeInfoService noticeInfoService;

    @Autowired
    public NoticeInfoController(NoticeInfoService noticeInfoService) {
        this.noticeInfoService = noticeInfoService;
    }

    /**
     * 添加
     * @param sharedUser
     * @param noticeForm
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/notice/add", method = RequestMethod.POST)
    @ResponseBody
    public MessageVo add(SharedUser sharedUser, @RequestBody @Valid AddNoticeForm noticeForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return MessageVo.fail(BindingResultUtils.getErrorString(bindingResult));
        }
        return noticeInfoService.add(sharedUser, noticeForm);
    }

    /**
     * 修改信息
     * @param noticeForm
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/notice/update", method = RequestMethod.POST)
    @ResponseBody
    public MessageVo update(SharedUser sharedUser, @RequestBody @Valid UpdateNoticeForm noticeForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return MessageVo.fail(BindingResultUtils.getErrorString(bindingResult));
        }
        return noticeInfoService.update(sharedUser, noticeForm);
    }

    /**
     * 根据id删除
     * @param noticeId 通知ID
     * @return
     */
    @RequestMapping(value = "/notice/delById", method = RequestMethod.GET)
    @ResponseBody
    public MessageVo delById(Long noticeId) {
        if (noticeId == null) {
            return MessageVo.fail("缺少通知ID参数！");
        }
        return noticeInfoService.delById(noticeId);
    }

    /**
     * 根据id获取信息
     * @param noticeId 通知ID
     * @return
     */
    @RequestMapping(value = "/notice/getById", method = RequestMethod.GET)
    @ResponseBody
    public MessageVo getById(Long noticeId) {
        if (noticeId == null) {
            return MessageVo.fail("缺少通知ID参数！");
        }
        return noticeInfoService.getById(noticeId);
    }

    /**
     * 发布通知
     * @param noticeId 通知ID
     * @return
     */
    @RequestMapping(value = "/notice/publicNotice", method = RequestMethod.GET)
    @ResponseBody
    public MessageVo publicNotice(SharedUser sharedUser, Long noticeId) {
        if (noticeId == null) {
            return MessageVo.fail("缺少通知ID参数！");
        }
        if (sharedUser == null) {
            return MessageVo.fail("请登录后重试");
        }
        return noticeInfoService.publicNotice(sharedUser, noticeId);
    }

    /**
     * 分页查询
     * @param noticeParam
     * @return
     */
    @RequestMapping(value = "/notice/getListByPage", method = RequestMethod.POST)
    @ResponseBody
    public MessageVo getListByPage(@RequestBody @Valid SearchNoticeParam noticeParam, BindingResult bindingResult) {
        if (noticeParam == null) {
            return MessageVo.fail("获取通知数据失败！");
        }
        if (bindingResult.hasErrors()) {
            return MessageVo.fail(BindingResultUtils.getErrorString(bindingResult));
        }
        return noticeInfoService.getListByPage(noticeParam);
    }
}
