package com.example.practiceexam.controller;

import com.example.common.cache.SharedUser;
import com.example.common.util.BindingResultUtils;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.form.AddSystemMessageLogForm;
import com.example.practiceexam.param.SearchSystemMessageParam;
import com.example.practiceexam.service.SystemMessageLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * @author ShiQing_Chen  2020/3/11  20:00
 **/
@Controller
public class SystemMessageLogController {

    private final SystemMessageLogService systemMessageLogService;

    @Autowired
    public SystemMessageLogController(SystemMessageLogService systemMessageLogService) {
        this.systemMessageLogService = systemMessageLogService;
    }


    /**
     * 添加
     */
    @RequestMapping(value = "/systemMessage/add", method = RequestMethod.POST)
    @ResponseBody
    public MessageVo add(SharedUser sharedUser, @RequestBody @Valid AddSystemMessageLogForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return MessageVo.fail(BindingResultUtils.getErrorString(bindingResult));
        }
        return systemMessageLogService.add(sharedUser, form);
    }

    /**
     * 分页查询
     * @param param
     * @return
     */
    @RequestMapping(value = "/systemMessage/getListByPage", method = RequestMethod.POST)
    @ResponseBody
    public MessageVo getListByPage(@RequestBody @Valid SearchSystemMessageParam param, BindingResult bindingResult) {
        if (param == null) {
            return MessageVo.fail("获取系统消息数据失败！");
        }
        if (bindingResult.hasErrors()) {
            return MessageVo.fail(BindingResultUtils.getErrorString(bindingResult));
        }
        return systemMessageLogService.getListByPage(param);
    }
}
