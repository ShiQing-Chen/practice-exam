package com.example.practiceexam.controller;

import com.example.common.cache.SharedUser;
import com.example.common.util.BindingResultUtils;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.form.AddPaperForm;
import com.example.practiceexam.form.UpdatePaperForm;
import com.example.practiceexam.param.SearchPaperParam;
import com.example.practiceexam.service.PaperInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * @author ShiQing_Chen  2020/3/14  01:29
 **/
@Controller
public class PaperInfoController {
    private final PaperInfoService paperInfoService;

    @Autowired
    public PaperInfoController(PaperInfoService paperInfoService) {
        this.paperInfoService = paperInfoService;
    }

    /**
     * 添加
     * @param sharedUser
     * @param form
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/paper/add", method = RequestMethod.POST)
    @ResponseBody
    public MessageVo add(SharedUser sharedUser, @RequestBody @Valid AddPaperForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return MessageVo.fail(BindingResultUtils.getErrorString(bindingResult));
        }
        return paperInfoService.add(sharedUser, form);
    }

    /**
     * 修改信息
     * @param form
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/paper/update", method = RequestMethod.POST)
    @ResponseBody
    public MessageVo update(SharedUser sharedUser, @RequestBody @Valid UpdatePaperForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return MessageVo.fail(BindingResultUtils.getErrorString(bindingResult));
        }
        return paperInfoService.update(sharedUser, form);
    }

    /**
     * 根据id删除
     * @param paperId 试卷ID
     * @return
     */
    @RequestMapping(value = "/paper/delById", method = RequestMethod.GET)
    @ResponseBody
    public MessageVo delById(Long paperId) {
        if (paperId == null) {
            return MessageVo.fail("缺少试卷ID参数！");
        }
        return paperInfoService.delById(paperId);
    }

    /**
     * 根据id获取信息
     * @param paperId 试卷ID
     * @return
     */
    @RequestMapping(value = "/paper/getById", method = RequestMethod.GET)
    @ResponseBody
    public MessageVo getById(Long paperId) {
        if (paperId == null) {
            return MessageVo.fail("缺少试卷ID参数！");
        }
        return paperInfoService.getById(paperId);
    }

    /**
     * 分页查询
     * @param param
     * @return
     */
    @RequestMapping(value = "/paper/getListByPage", method = RequestMethod.POST)
    @ResponseBody
    public MessageVo getListByPage(@RequestBody @Valid SearchPaperParam param, BindingResult bindingResult) {
        if (param == null) {
            return MessageVo.fail("获取试卷数据失败！");
        }
        if (bindingResult.hasErrors()) {
            return MessageVo.fail(BindingResultUtils.getErrorString(bindingResult));
        }
        return paperInfoService.getListByPage(param);
    }

    /**
     * 发布试卷
     * @param paperId 试卷ID
     * @return
     */
    @RequestMapping(value = "/paper/publicPaper", method = RequestMethod.GET)
    @ResponseBody
    public MessageVo publicPaper(SharedUser sharedUser, Long paperId) {
        if (paperId == null) {
            return MessageVo.fail("缺少试卷ID参数！");
        }
        if (sharedUser == null) {
            return MessageVo.fail("请登录后重试");
        }
        return paperInfoService.publicPaper(sharedUser, paperId);
    }
}
