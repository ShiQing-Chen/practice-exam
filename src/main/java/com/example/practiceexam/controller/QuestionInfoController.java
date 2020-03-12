package com.example.practiceexam.controller;

import com.example.common.cache.SharedUser;
import com.example.common.util.BindingResultUtils;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.form.AddQuesForm;
import com.example.practiceexam.form.UpdateQuesForm;
import com.example.practiceexam.param.SearchQuesParam;
import com.example.practiceexam.service.QuestionInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * @author ShiQing_Chen  2020/3/12  02:01
 **/
@Controller
public class QuestionInfoController {
    private final QuestionInfoService questionInfoService;

    @Autowired
    public QuestionInfoController(QuestionInfoService questionInfoService) {
        this.questionInfoService = questionInfoService;
    }

    /**
     * 添加
     * @param sharedUser
     * @param quesForm
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/question/add", method = RequestMethod.POST)
    @ResponseBody
    public MessageVo add(SharedUser sharedUser, @RequestBody @Valid AddQuesForm quesForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return MessageVo.fail(BindingResultUtils.getErrorString(bindingResult));
        }
        return questionInfoService.add(sharedUser, quesForm);
    }

    /**
     * 修改信息
     * @param quesForm
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/question/update", method = RequestMethod.POST)
    @ResponseBody
    public MessageVo update(SharedUser sharedUser, @RequestBody @Valid UpdateQuesForm quesForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return MessageVo.fail(BindingResultUtils.getErrorString(bindingResult));
        }
        return questionInfoService.update(sharedUser, quesForm);
    }

    /**
     * 根据id删除
     * @param questionId 试题ID
     * @return
     */
    @RequestMapping(value = "/question/delById", method = RequestMethod.GET)
    @ResponseBody
    public MessageVo delById(Long questionId) {
        if (questionId == null) {
            return MessageVo.fail("缺少试题ID参数！");
        }
        return questionInfoService.delById(questionId);
    }

    /**
     * 根据id获取信息
     * @param questionId 试题ID
     * @return
     */
    @RequestMapping(value = "/question/getById", method = RequestMethod.GET)
    @ResponseBody
    public MessageVo getById(Long questionId) {
        if (questionId == null) {
            return MessageVo.fail("缺少试题ID参数！");
        }
        return questionInfoService.getById(questionId);
    }

    /**
     * 分页查询
     * @param param
     * @return
     */
    @RequestMapping(value = "/question/getListByPage", method = RequestMethod.POST)
    @ResponseBody
    public MessageVo getListByPage(@RequestBody @Valid SearchQuesParam param, BindingResult bindingResult) {
        if (param == null) {
            return MessageVo.fail("获取试题数据失败！");
        }
        if (bindingResult.hasErrors()) {
            return MessageVo.fail(BindingResultUtils.getErrorString(bindingResult));
        }
        return questionInfoService.getListByPage(param);
    }

}
