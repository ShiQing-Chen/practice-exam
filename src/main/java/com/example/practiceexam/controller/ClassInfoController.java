package com.example.practiceexam.controller;

import com.example.common.cache.SharedUser;
import com.example.common.util.BindingResultUtils;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.form.AddClassForm;
import com.example.practiceexam.form.UpdateClassForm;
import com.example.practiceexam.param.SearchClassParam;
import com.example.practiceexam.service.ClassInfoService;
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
public class ClassInfoController {
    private final ClassInfoService classInfoService;

    @Autowired
    public ClassInfoController(ClassInfoService classInfoService) {
        this.classInfoService = classInfoService;
    }


    /**
     * 添加班级
     * @param sharedUser
     * @param classForm
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/class/add", method = RequestMethod.POST)
    @ResponseBody
    public MessageVo add(SharedUser sharedUser, @RequestBody @Valid AddClassForm classForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return MessageVo.fail("表单验证失败！", BindingResultUtils.getErrorString(bindingResult));
        }
        return classInfoService.add(sharedUser, classForm);
    }

    /**
     * 修改班级信息
     * @param classForm
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/class/update", method = RequestMethod.POST)
    @ResponseBody
    public MessageVo update(SharedUser sharedUser, @RequestBody @Valid UpdateClassForm classForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return MessageVo.fail("表单验证失败！", BindingResultUtils.getErrorString(bindingResult));
        }
        return classInfoService.update(sharedUser, classForm);
    }

    /**
     * 根据id删除班级
     * @param classId 班级ID
     * @return
     */
    @RequestMapping(value = "/class/delById", method = RequestMethod.GET)
    @ResponseBody
    public MessageVo delById(Long classId) {
        if (classId == null) {
            return MessageVo.fail("缺少班级ID参数！");
        }
        return classInfoService.delById(classId);
    }

    /**
     * 根据id获取班级信息
     * @param classId 班级ID
     * @return
     */
    @RequestMapping(value = "/class/getById", method = RequestMethod.GET)
    @ResponseBody
    public MessageVo getById(Long classId) {
        if (classId == null) {
            return MessageVo.fail("缺少班级ID参数！");
        }
        return classInfoService.getById(classId);
    }

    /**
     * 管理员
     * 分页查询
     * @param classParam
     * @return
     */
    @RequestMapping(value = "/admin/class/getListByPage", method = RequestMethod.POST)
    @ResponseBody
    public MessageVo adminGetListByPage(@RequestBody @Valid SearchClassParam classParam, BindingResult bindingResult) {
        if (classParam == null) {
            return MessageVo.fail("获取班级数据失败！");
        }
        if (bindingResult.hasErrors()) {
            return MessageVo.fail("表单验证失败！", BindingResultUtils.getErrorString(bindingResult));
        }
        return classInfoService.adminGetListByPage(classParam);
    }

    /**
     * 根据班级id获取当前教师信息
     * @param classId 班级ID
     * @return
     */
    @RequestMapping(value = "/class/getClassTeacherByClassId", method = RequestMethod.GET)
    @ResponseBody
    public MessageVo getClassTeacherByClassId(Long classId) {
        if (classId == null) {
            return MessageVo.fail("缺少班级ID参数！");
        }
        return classInfoService.getClassTeacherByClassId(classId);
    }

    /**
     * 根据班级id设置班级教师
     * @param classId   班级ID
     * @param teacherId 教师ID
     * @return
     */
    @RequestMapping(value = "/class/setClassTeacher", method = RequestMethod.GET)
    @ResponseBody
    public MessageVo setClassTeacher(Long classId, Long teacherId) {
        if (classId == null || teacherId == null) {
            return MessageVo.fail("缺少班级ID或教师参数！");
        }
        return classInfoService.setClassTeacher(classId, teacherId);
    }

}
