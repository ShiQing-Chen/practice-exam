package com.example.practiceexam.controller;

import com.example.common.cache.SharedUser;
import com.example.common.util.BindingResultUtils;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.form.AddTeacherForm;
import com.example.practiceexam.form.UpdateTeacherForm;
import com.example.practiceexam.param.SearchClassTeacherParam;
import com.example.practiceexam.param.SearchTeacherParam;
import com.example.practiceexam.service.TeacherInfoService;
import org.apache.commons.lang3.StringUtils;
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

    /**
     * 添加教师
     * @param sharedUser
     * @param teacherForm
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/teacher/add", method = RequestMethod.POST)
    @ResponseBody
    public MessageVo add(SharedUser sharedUser, @RequestBody @Valid AddTeacherForm teacherForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return MessageVo.fail(BindingResultUtils.getErrorString(bindingResult));
        }
        return teacherInfoService.add(sharedUser, teacherForm);
    }

    /**
     * 修改教师信息
     * @param teacherForm
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/teacher/update", method = RequestMethod.POST)
    @ResponseBody
    public MessageVo update(SharedUser sharedUser, @RequestBody @Valid UpdateTeacherForm teacherForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return MessageVo.fail(BindingResultUtils.getErrorString(bindingResult));
        }
        return teacherInfoService.update(sharedUser, teacherForm);
    }

    /**
     * 根据id删除教师
     * @param teacherId 教师ID
     * @return
     */
    @RequestMapping(value = "/teacher/delById", method = RequestMethod.GET)
    @ResponseBody
    public MessageVo delById(Long teacherId) {
        if (teacherId == null) {
            return MessageVo.fail("缺少教师ID参数！");
        }
        return teacherInfoService.delById(teacherId);
    }

    /**
     * 根据id获取教师信息
     * @param teacherId 教师ID
     * @return
     */
    @RequestMapping(value = "/teacher/getById", method = RequestMethod.GET)
    @ResponseBody
    public MessageVo getById(Long teacherId) {
        if (teacherId == null) {
            return MessageVo.fail("缺少教师ID参数！");
        }
        return teacherInfoService.getById(teacherId);
    }

    /**
     * 添加
     * 校验工号
     * @param teacherNumber
     * @return
     */
    @RequestMapping(value = "/teacher/checkTeacherNumber", method = RequestMethod.GET)
    @ResponseBody
    public MessageVo checkTeacherNumber(String teacherNumber) {
        if (StringUtils.isBlank(teacherNumber)) {
            return MessageVo.fail("校验失败，缺少工号参数！");
        }
        return teacherInfoService.checkTeacherNumber(teacherNumber);
    }

    /**
     * 修改
     * 校验工号
     * @param teacherId
     * @param teacherNumber
     * @return
     */
    @RequestMapping(value = "/teacher/checkTeacherNumberById", method = RequestMethod.GET)
    @ResponseBody
    public MessageVo checkTeacherNumberById(Long teacherId, String teacherNumber) {
        if (teacherId == null || StringUtils.isBlank(teacherNumber)) {
            return MessageVo.fail("校验失败，缺少教师ID或工号参数！");
        }
        return teacherInfoService.checkTeacherNumberById(teacherId, teacherNumber);
    }

    /**
     * 分页查询
     * @param teacherParam
     * @return
     */
    @RequestMapping(value = "/teacher/getListByPage", method = RequestMethod.POST)
    @ResponseBody
    public MessageVo getListByPage(@RequestBody @Valid SearchTeacherParam teacherParam, BindingResult bindingResult) {
        if (teacherParam == null) {
            return MessageVo.fail("获取教师数据失败！");
        }
        if (bindingResult.hasErrors()) {
            return MessageVo.fail(BindingResultUtils.getErrorString(bindingResult));
        }
        return teacherInfoService.getListByPage(teacherParam);
    }

}
