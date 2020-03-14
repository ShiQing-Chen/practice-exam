package com.example.practiceexam.service.Impl;

import com.example.common.cache.SharedUser;
import com.example.common.util.IdGeneratorUtils;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.dao.PaperGenerateDao;
import com.example.practiceexam.dto.GenerateQuesDto;
import com.example.practiceexam.form.AddPaperGenerateForm;
import com.example.practiceexam.model.PaperGenerate;
import com.example.practiceexam.service.PaperGenerateService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @author ShiQing_Chen  2020/3/14  12:22
 **/
@Service
public class PaperGenerateServiceImpl implements PaperGenerateService {


    @Autowired
    private PaperGenerateDao paperGenerateDao;


    /**
     * 保存组卷关系
     * @param sharedUser
     * @param generateForm
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public MessageVo add(SharedUser sharedUser, AddPaperGenerateForm generateForm) {
        if (generateForm != null && sharedUser != null) {
            List<GenerateQuesDto> quesList = generateForm.getQuesList();
            Date curDate = new Date();
            if (!CollectionUtils.isEmpty(quesList)) {
                paperGenerateDao.delByPaperId(generateForm.getPaperId());
                List<PaperGenerate> generateList = Lists.newArrayList();
                int i = 1;
                for (GenerateQuesDto quesDto : quesList) {
                    PaperGenerate generate = new PaperGenerate();
                    generate.setGenerateId(IdGeneratorUtils.getNewId());
                    generate.setPaperId(generateForm.getPaperId());
                    generate.setQuestionId(quesDto.getQuestionId());
                    generate.setOrderNumber(i);
                    generate.setCreateTime(curDate);
                    generate.setCreateUserId(sharedUser.getUserId());
                    // 根据类型设置分数
                    Integer type = quesDto.getQuestionType();
                    if (type != null) {
                        if (type.equals(1)) {
                            generate.setQuestionScore(generateForm.getChoiceScore());
                        } else if (type.equals(2)) {
                            generate.setQuestionScore(generateForm.getSubjectiveScore());
                        }
                    }
                    generateList.add(generate);
                    i++;
                }
                paperGenerateDao.saveAll(generateList);
                return MessageVo.success();
            } else {
                return MessageVo.fail("未获取到试题信息");
            }
        }
        return MessageVo.fail("组卷保存失败！");
    }
}
