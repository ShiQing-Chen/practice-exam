package com.example.practiceexam.controller;

import com.example.common.cache.SharedUser;
import com.example.common.util.BindingResultUtils;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.form.AddArticleForm;
import com.example.practiceexam.param.SearchArticleParam;
import com.example.practiceexam.service.ArticleInfoService;
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
public class ArticleInfoController {
    private final ArticleInfoService articleInfoService;

    @Autowired
    public ArticleInfoController(ArticleInfoService articleInfoService) {
        this.articleInfoService = articleInfoService;
    }

    /**
     * 添加
     * @param sharedUser
     * @param addArticleForm
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/article/add", method = RequestMethod.POST)
    @ResponseBody
    public MessageVo add(SharedUser sharedUser, @RequestBody @Valid AddArticleForm addArticleForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return MessageVo.fail(BindingResultUtils.getErrorString(bindingResult));
        }
        return articleInfoService.add(sharedUser, addArticleForm);
    }

    /**
     * 分页查询
     * @param param
     * @return
     */
    @RequestMapping(value = "/article/getListByPage", method = RequestMethod.POST)
    @ResponseBody
    public MessageVo getListByPage(@RequestBody @Valid SearchArticleParam param, BindingResult bindingResult) {
        if (param == null) {
            return MessageVo.fail("获取资料数据失败！");
        }
        if (bindingResult.hasErrors()) {
            return MessageVo.fail(BindingResultUtils.getErrorString(bindingResult));
        }
        return articleInfoService.getListByPage(param);
    }
}
