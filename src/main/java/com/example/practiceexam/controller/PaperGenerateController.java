package com.example.practiceexam.controller;

import com.example.common.cache.SharedUser;
import com.example.common.util.BindingResultUtils;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.form.AddPaperGenerateForm;
import com.example.practiceexam.form.AddQuesForm;
import com.example.practiceexam.service.PaperGenerateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * @author ShiQing_Chen  2020/3/14  12:22
 **/
@Controller
public class PaperGenerateController {
    private final PaperGenerateService paperGenerateService;

    @Autowired
    public PaperGenerateController(PaperGenerateService paperGenerateService) {
        this.paperGenerateService = paperGenerateService;
    }
    /**
     * 添加
     * @param sharedUser
     * @param generateForm
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/paperGenerate/add", method = RequestMethod.POST)
    @ResponseBody
    public MessageVo add(SharedUser sharedUser, @RequestBody @Valid AddPaperGenerateForm generateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return MessageVo.fail(BindingResultUtils.getErrorString(bindingResult));
        }
        return paperGenerateService.add(sharedUser, generateForm);
    }

}
