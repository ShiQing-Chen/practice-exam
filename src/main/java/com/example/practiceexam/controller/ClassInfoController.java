package com.example.practiceexam.controller;

import com.example.common.cache.SharedUser;
import com.example.common.util.BindingResultUtils;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.form.AddClassForm;
import com.example.practiceexam.form.UpdateClassForm;
import com.example.practiceexam.param.SearchClassParam;
import com.example.practiceexam.service.ClassInfoService;
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
     * 管理员
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
            return MessageVo.fail(BindingResultUtils.getErrorString(bindingResult));
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
            return MessageVo.fail(BindingResultUtils.getErrorString(bindingResult));
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
            return MessageVo.fail(BindingResultUtils.getErrorString(bindingResult));
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


    /**
     * 获取专业名称列表、班级名称列表
     * @return
     */
    @RequestMapping(value = "/class/getListClassAndMajor", method = RequestMethod.GET)
    @ResponseBody
    public MessageVo getListClassAndMajor() {
        return classInfoService.getListClassAndMajor();
    }

    /**
     * 远程模糊查询班级信息
     * @return
     */
    @RequestMapping(value = "/class/searchListClassName", method = RequestMethod.GET)
    @ResponseBody
    public MessageVo searchListClassName(String search) {
        if (StringUtils.isBlank(search)) {
            return MessageVo.fail("缺少搜索内容参数！");
        }
        return classInfoService.searchListClassName(search);
    }

    /**
     * 学生编辑初始化学生班级信息
     * @return
     */
    @RequestMapping(value = "/class/initStudentClassById", method = RequestMethod.GET)
    @ResponseBody
    public MessageVo initStudentClassById(Long classId) {
        if (classId == null) {
            return MessageVo.fail("初始化学生班级信息失败，缺少班级ID参数！");
        }
        return classInfoService.initStudentClassById(classId);
    }

    /**
     * 获取班级列表
     * @return
     */
    @RequestMapping(value = "/class/getListClassIdName", method = RequestMethod.GET)
    @ResponseBody
    public MessageVo getListClassIdName() {
        return classInfoService.getListClassIdName();
    }

    /**
     * 管理员
     * 分页查询
     * @param classParam
     * @return
     */
    @RequestMapping(value = "/class/teacher/getListByPage", method = RequestMethod.POST)
    @ResponseBody
    public MessageVo teacherGetListByPage(SharedUser sharedUser, @RequestBody @Valid SearchClassParam classParam, BindingResult bindingResult) {
        if (classParam == null) {
            return MessageVo.fail("获取班级数据失败！");
        }
        if (bindingResult.hasErrors()) {
            return MessageVo.fail(BindingResultUtils.getErrorString(bindingResult));
        }
        return classInfoService.teacherGetListByPage(sharedUser, classParam);
    }

    /**
     * 教师
     * 添加班级
     * @param sharedUser
     * @param classForm
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/class/addByTeacher", method = RequestMethod.POST)
    @ResponseBody
    public MessageVo addByTeacher(SharedUser sharedUser, @RequestBody @Valid AddClassForm classForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return MessageVo.fail(BindingResultUtils.getErrorString(bindingResult));
        }
        return classInfoService.addByTeacher(sharedUser, classForm);
    }

    /**
     * 教师
     * 获取班级列表
     * @return
     */
    @RequestMapping(value = "/class/teacher/getListClassIdName", method = RequestMethod.GET)
    @ResponseBody
    public MessageVo teacherGetListClassIdName(SharedUser sharedUser) {
        if (sharedUser==null){
            return MessageVo.fail("请登录后重试！");
        }
        return classInfoService.teacherGetListClassIdName(sharedUser);
    }
}
