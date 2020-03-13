package com.example.practiceexam.service.Impl;

import com.example.common.cache.SharedUser;
import com.example.common.util.IdGeneratorUtils;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.dao.PaperClassDao;
import com.example.practiceexam.dao.PaperInfoDao;
import com.example.practiceexam.dto.PaperDto;
import com.example.practiceexam.dto.PaperInfoDto;
import com.example.practiceexam.form.AddPaperForm;
import com.example.practiceexam.form.UpdatePaperForm;
import com.example.practiceexam.model.PaperClass;
import com.example.practiceexam.model.PaperInfo;
import com.example.practiceexam.param.SearchPaperParam;
import com.example.practiceexam.service.PaperInfoService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author ShiQing_Chen  2020/3/14  01:29
 **/
@Service
public class PaperInfoServiceImpl implements PaperInfoService {
    @Autowired
    private PaperClassDao paperClassDao;
    @Autowired
    private PaperInfoDao paperInfoDao;

    /**
     * 添加
     * @param form
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public MessageVo add(SharedUser sharedUser, AddPaperForm form) {
        if (form != null && sharedUser != null) {
            PaperInfo paperInfo = new PaperInfo();
            Date curDate = new Date();
            BeanUtils.copyProperties(form, paperInfo);
            paperInfo.setPaperId(IdGeneratorUtils.getNewId());
            paperInfo.setCreateUserId(sharedUser.getUserId());
            paperInfo.setCreateTime(curDate);
            paperInfo.setUpdateTime(curDate);
            paperInfoDao.save(paperInfo);
            if (!CollectionUtils.isEmpty(form.getClassList())) {
                List<PaperClass> paperClassList = Lists.newArrayList();
                form.getClassList().forEach(e -> {
                    PaperClass paperClass = new PaperClass();
                    paperClass.setId(IdGeneratorUtils.getNewId());
                    paperClass.setPaperId(paperInfo.getPaperId());
                    paperClass.setClassId(e);
                    paperClassList.add(paperClass);
                });
                paperClassDao.saveAll(paperClassList);
            }
            return MessageVo.success();
        }
        return MessageVo.fail("添加试卷失败！");
    }

    /**
     * 修改信息
     * @param form
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public MessageVo update(SharedUser sharedUser, UpdatePaperForm form) {
        if (form != null && sharedUser != null) {
            PaperInfo paperInfo = paperInfoDao.getById(form.getPaperId());
            if (paperInfo == null) {
                return MessageVo.fail("未查询到需要修改的试卷");
            }
            Date curDate = new Date();
            BeanUtils.copyProperties(form, paperInfo);
            paperInfo.setUpdateTime(curDate);
            paperInfoDao.save(paperInfo);
            paperClassDao.delByPaperId(paperInfo.getPaperId());
            if (!CollectionUtils.isEmpty(form.getClassList())) {
                List<PaperClass> paperClassList = Lists.newArrayList();
                form.getClassList().forEach(e -> {
                    PaperClass paperClass = new PaperClass();
                    paperClass.setId(IdGeneratorUtils.getNewId());
                    paperClass.setPaperId(paperInfo.getPaperId());
                    paperClass.setClassId(e);
                    paperClassList.add(paperClass);
                });
                paperClassDao.saveAll(paperClassList);
            }
            return MessageVo.success();
        }
        return MessageVo.fail("修改试卷失败！");
    }

    /**
     * 根据id查询
     * @param paperId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public MessageVo getById(Long paperId) {
        if (paperId != null) {
            PaperInfo paperInfo = paperInfoDao.getById(paperId);
            if (paperInfo != null) {
                List<Long> classIdList = paperClassDao.getClassIdsByPaperId(paperId);
                PaperDto dto = new PaperDto();
                BeanUtils.copyProperties(paperInfo, dto);
                dto.setClassList(classIdList);
                return MessageVo.success(dto);
            }
        }
        return MessageVo.fail("获取试卷失败！");
    }

    /**
     * 根据id删除
     * @param paperId
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public MessageVo delById(Long paperId) {
        if (paperId != null) {
            int result = paperInfoDao.delById(paperId);
            if (result > 0) {
                paperClassDao.delByPaperId(paperId);
                return MessageVo.success();
            }
        }
        return MessageVo.fail("删除试卷失败！");
    }

    /**
     * 分页查询
     * @param param
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public MessageVo getListByPage(SearchPaperParam param) {
        if (param != null) {
            List<PaperInfoDto> list = paperInfoDao.getListByPage(param);
            Integer count = paperInfoDao.getCountByPage(param);
            Map<String, Object> map = Maps.newHashMap();
            map.put("list", list);
            map.put("total", count);
            return MessageVo.success(map);
        } else {
            return MessageVo.success(Lists.newArrayList());
        }
    }

    /**
     * 发布试卷
     * @param sharedUser
     * @param paperId
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public MessageVo publicPaper(SharedUser sharedUser, Long paperId) {
        if (sharedUser != null && paperId != null) {
            PaperInfo paperInfo = paperInfoDao.getById(paperId);
            if (paperInfo == null) {
                return MessageVo.fail("未查询到试卷信息");
            }
            if (paperInfo.getPaperStatus() != null && paperInfo.getPaperStatus().equals(PaperInfo.STATUS_PUBLIC)) {
                return MessageVo.fail("该试卷已经发布，请勿重新发布！");
            }
            Date curDate = new Date();
            paperInfo.setPaperStatus(PaperInfo.STATUS_PUBLIC);
            paperInfo.setPublishUserId(sharedUser.getUserId());
            paperInfo.setPublishTime(curDate);
            paperInfo.setUpdateTime(curDate);
            paperInfoDao.save(paperInfo);
            return MessageVo.success("试卷发布成功!");
        }
        return MessageVo.fail("发布试卷失败！");
    }
}
