package com.example.practiceexam.controller;

import com.example.common.cache.SharedUser;
import com.example.common.util.BindingResultUtils;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.form.*;
import com.example.practiceexam.service.CourseInfoService;
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
public class CourseInfoController {
    private final CourseInfoService courseInfoService;

    @Autowired
    public CourseInfoController(CourseInfoService courseInfoService) {
        this.courseInfoService = courseInfoService;
    }

    /**
     * 添加
     * @param sharedUser
     * @param courseForm
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/course/add", method = RequestMethod.POST)
    @ResponseBody
    public MessageVo add(SharedUser sharedUser, @RequestBody @Valid AddCourseForm courseForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return MessageVo.fail(BindingResultUtils.getErrorString(bindingResult));
        }
        return courseInfoService.add(sharedUser, courseForm);
    }

    /**
     * 修改信息
     * @param courseForm
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/course/update", method = RequestMethod.POST)
    @ResponseBody
    public MessageVo update(SharedUser sharedUser, @RequestBody @Valid UpdateCourseForm courseForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return MessageVo.fail(BindingResultUtils.getErrorString(bindingResult));
        }
        return courseInfoService.update(sharedUser, courseForm);
    }

    /**
     * 根据id删除
     * @param courseId 课程ID
     * @return
     */
    @RequestMapping(value = "/course/delById", method = RequestMethod.GET)
    @ResponseBody
    public MessageVo delById(Long courseId) {
        if (courseId == null) {
            return MessageVo.fail("缺少课程ID参数！");
        }
        return courseInfoService.delById(courseId);
    }

    /**
     * 根据id获取信息
     * @param courseId 课程ID
     * @return
     */
    @RequestMapping(value = "/course/getById", method = RequestMethod.GET)
    @ResponseBody
    public MessageVo getById(Long courseId) {
        if (courseId == null) {
            return MessageVo.fail("缺少课程ID参数！");
        }
        return courseInfoService.getById(courseId);
    }

    /**
     * 查询所有
     * 有参数则模糊查询
     * @param search
     * @return
     */
    @RequestMapping(value = "/course/getList", method = RequestMethod.GET)
    @ResponseBody
    public MessageVo getList(String search) {
        return courseInfoService.getList(search);
    }
}
