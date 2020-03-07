package com.example.practiceexam.controller;

import com.example.common.cache.SharedUser;
import com.example.common.util.BindingResultUtils;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.form.AddStudentForm;
import com.example.practiceexam.form.UpdateStudentForm;
import com.example.practiceexam.param.SearchStudentParam;
import com.example.practiceexam.service.StudentInfoService;
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
public class StudentInfoController {
    private final StudentInfoService studentInfoService;

    @Autowired
    public StudentInfoController(StudentInfoService studentInfoService) {
        this.studentInfoService = studentInfoService;
    }


    /**
     * 添加学生
     * @param sharedUser
     * @param studentForm
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/student/add", method = RequestMethod.POST)
    @ResponseBody
    public MessageVo add(SharedUser sharedUser, @RequestBody @Valid AddStudentForm studentForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return MessageVo.fail("表单验证失败！", BindingResultUtils.getErrorString(bindingResult));
        }
        return studentInfoService.add(sharedUser, studentForm);
    }

    /**
     * 修改学生信息
     * @param studentForm
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/student/update", method = RequestMethod.POST)
    @ResponseBody
    public MessageVo update(SharedUser sharedUser, @RequestBody @Valid UpdateStudentForm studentForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return MessageVo.fail("表单验证失败！", BindingResultUtils.getErrorString(bindingResult));
        }
        return studentInfoService.update(sharedUser, studentForm);
    }

    /**
     * 根据id删除学生
     * @param studentId 学生ID
     * @return
     */
    @RequestMapping(value = "/student/delById", method = RequestMethod.GET)
    @ResponseBody
    public MessageVo delById(Long studentId) {
        if (studentId == null) {
            return MessageVo.fail("缺少学生ID参数！");
        }
        return studentInfoService.delById(studentId);
    }

    /**
     * 根据id获取学生信息
     * @param studentId 学生ID
     * @return
     */
    @RequestMapping(value = "/student/getById", method = RequestMethod.GET)
    @ResponseBody
    public MessageVo getById(Long studentId) {
        if (studentId == null) {
            return MessageVo.fail("缺少学生ID参数！");
        }
        return studentInfoService.getById(studentId);
    }

    /**
     * 添加
     * 校验学号
     * @param studentNumber
     * @return
     */
    @RequestMapping(value = "/student/checkStudentNumber", method = RequestMethod.GET)
    @ResponseBody
    public MessageVo checkStudentNumber(String studentNumber) {
        return studentInfoService.checkStudentNumber(studentNumber);
    }

    /**
     * 修改
     * 校验学号
     * @param studentId
     * @param studentNumber
     * @return
     */
    @RequestMapping(value = "/student/checkStudentNumberById", method = RequestMethod.GET)
    @ResponseBody
    public MessageVo checkStudentNumberById(Long studentId, String studentNumber) {
        return studentInfoService.checkStudentNumberById(studentId, studentNumber);
    }

    /**
     * 分页查询
     * @param studentParam
     * @return
     */
    @RequestMapping(value = "/student/getListByPage", method = RequestMethod.POST)
    @ResponseBody
    public MessageVo getListByPage(@RequestBody @Valid SearchStudentParam studentParam, BindingResult bindingResult) {
        if (studentParam == null) {
            return MessageVo.fail("获取学生数据失败！");
        }
        if (bindingResult.hasErrors()) {
            return MessageVo.fail("表单验证失败！", BindingResultUtils.getErrorString(bindingResult));
        }
        return studentInfoService.getListByPage(studentParam);
    }

}
