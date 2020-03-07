package com.example.practiceexam.controller;

import com.example.common.util.BindingResultUtils;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.param.SearchClassTeacherParam;
import com.example.practiceexam.service.TeacherInfoService;
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
public class TeacherInfoController {
    private final TeacherInfoService teacherInfoService;

    @Autowired
    public TeacherInfoController(TeacherInfoService teacherInfoService) {
        this.teacherInfoService = teacherInfoService;
    }


    /**
     * 班级设置教师
     * 获取教师列表
     * 分页查询
     * @param param
     * @return
     */
    @RequestMapping(value = "/teacher/classGetListByPage", method = RequestMethod.POST)
    @ResponseBody
    public MessageVo classGetListByPage(@RequestBody @Valid SearchClassTeacherParam param, BindingResult bindingResult) {
        if (param == null) {
            return MessageVo.fail("获取教师数据失败！");
        }
        if (bindingResult.hasErrors()) {
            return MessageVo.fail(BindingResultUtils.getErrorString(bindingResult));
        }
        return teacherInfoService.classGetListByPage(param);
    }
}
