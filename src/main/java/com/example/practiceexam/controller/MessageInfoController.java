package com.example.practiceexam.controller;

import com.example.common.cache.SharedUser;
import com.example.common.util.BindingResultUtils;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.param.SearchMessageParam;
import com.example.practiceexam.service.MessageInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * @author ShiQing_Chen  2020/3/5  18:07
 **/
@Controller
public class MessageInfoController {

    private final MessageInfoService messageInfoService;

    @Autowired
    public MessageInfoController(MessageInfoService messageInfoService) {
        this.messageInfoService = messageInfoService;
    }

    /**
     * 已读
     * @param messageId ID
     * @return
     */
    @RequestMapping(value = "/message/readMessage", method = RequestMethod.GET)
    @ResponseBody
    public MessageVo readMessage(Long messageId) {
        if (messageId == null) {
            return MessageVo.fail("缺少消息ID参数！");
        }
        return messageInfoService.readMessage(messageId);
    }

    /**
     * 根据id删除
     * @param messageId ID
     * @return
     */
    @RequestMapping(value = "/message/delById", method = RequestMethod.GET)
    @ResponseBody
    public MessageVo delById(Long messageId) {
        if (messageId == null) {
            return MessageVo.fail("缺少消息ID参数！");
        }
        return messageInfoService.delById(messageId);
    }

    /**
     * 分页查询
     * @param param
     * @return
     */
    @RequestMapping(value = "/message/getListByPage", method = RequestMethod.POST)
    @ResponseBody
    public MessageVo getListByPage(SharedUser sharedUser, @RequestBody @Valid SearchMessageParam param, BindingResult bindingResult) {
        if (param == null) {
            return MessageVo.fail("获取帖子数据失败！");
        }
        if (bindingResult.hasErrors()) {
            return MessageVo.fail(BindingResultUtils.getErrorString(bindingResult));
        }
        return messageInfoService.getListByPage(sharedUser, param);
    }
}
