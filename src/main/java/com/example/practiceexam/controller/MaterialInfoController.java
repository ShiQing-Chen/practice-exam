package com.example.practiceexam.controller;

import com.example.common.cache.SharedUser;
import com.example.common.util.BindingResultUtils;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.form.AddMaterialForm;
import com.example.practiceexam.form.UpdateMaterialForm;
import com.example.practiceexam.param.SearchMaterialParam;
import com.example.practiceexam.service.MaterialInfoService;
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
public class MaterialInfoController {
    private final MaterialInfoService materialInfoService;

    @Autowired
    public MaterialInfoController(MaterialInfoService materialInfoService) {
        this.materialInfoService = materialInfoService;
    }

    /**
     * 添加
     * @param sharedUser
     * @param materialForm
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/material/add", method = RequestMethod.POST)
    @ResponseBody
    public MessageVo add(SharedUser sharedUser, @RequestBody @Valid AddMaterialForm materialForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return MessageVo.fail(BindingResultUtils.getErrorString(bindingResult));
        }
        return materialInfoService.add(sharedUser, materialForm);
    }

    /**
     * 修改信息
     * @param materialForm
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/material/update", method = RequestMethod.POST)
    @ResponseBody
    public MessageVo update(SharedUser sharedUser, @RequestBody @Valid UpdateMaterialForm materialForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return MessageVo.fail(BindingResultUtils.getErrorString(bindingResult));
        }
        return materialInfoService.update(sharedUser, materialForm);
    }

    /**
     * 根据id删除
     * @param materialId 资料ID
     * @return
     */
    @RequestMapping(value = "/material/delById", method = RequestMethod.GET)
    @ResponseBody
    public MessageVo delById(Long materialId) {
        if (materialId == null) {
            return MessageVo.fail("缺少资料ID参数！");
        }
        return materialInfoService.delById(materialId);
    }

    /**
     * 根据id获取信息
     * @param materialId 资料ID
     * @return
     */
    @RequestMapping(value = "/material/getById", method = RequestMethod.GET)
    @ResponseBody
    public MessageVo getById(Long materialId) {
        if (materialId == null) {
            return MessageVo.fail("缺少资料ID参数！");
        }
        return materialInfoService.getById(materialId);
    }

    /**
     * 根据id获取详细信息
     * @param materialId 资料ID
     * @return
     */
    @RequestMapping(value = "/material/getInfoById", method = RequestMethod.GET)
    @ResponseBody
    public MessageVo getInfoById(Long materialId) {
        if (materialId == null) {
            return MessageVo.fail("缺少资料ID参数！");
        }
        return materialInfoService.getInfoById(materialId);
    }

    /**
     * 发布资料
     * @param materialId 资料ID
     * @return
     */
    @RequestMapping(value = "/material/publicMaterial", method = RequestMethod.GET)
    @ResponseBody
    public MessageVo publicMaterial(SharedUser sharedUser, Long materialId) {
        if (materialId == null) {
            return MessageVo.fail("缺少资料ID参数！");
        }
        if (sharedUser == null) {
            return MessageVo.fail("请登录后重试");
        }
        return materialInfoService.publicMaterial(sharedUser, materialId);
    }

    /**
     * 分页查询
     * @param param
     * @return
     */
    @RequestMapping(value = "/material/getListByPage", method = RequestMethod.POST)
    @ResponseBody
    public MessageVo getListByPage(@RequestBody @Valid SearchMaterialParam param, BindingResult bindingResult) {
        if (param == null) {
            return MessageVo.fail("获取资料数据失败！");
        }
        if (bindingResult.hasErrors()) {
            return MessageVo.fail(BindingResultUtils.getErrorString(bindingResult));
        }
        return materialInfoService.getListByPage(param);
    }


    /**
     * 首页获取前五条下载资料
     * @return
     */
    @RequestMapping(value = "/material/indexGetList", method = RequestMethod.GET)
    @ResponseBody
    public MessageVo indexGetList() {
        return materialInfoService.indexGetList();
    }

    /**
     * 教师
     * 分页查询
     * @param param
     * @return
     */
    @RequestMapping(value = "/material/teacher/getListByPage", method = RequestMethod.POST)
    @ResponseBody
    public MessageVo teacherGetListByPage(SharedUser sharedUser, @RequestBody @Valid SearchMaterialParam param, BindingResult bindingResult) {
        if (param == null) {
            return MessageVo.fail("获取资料数据失败！");
        }
        if (bindingResult.hasErrors()) {
            return MessageVo.fail(BindingResultUtils.getErrorString(bindingResult));
        }
        return materialInfoService.teacherGetListByPage(sharedUser, param);
    }
}
