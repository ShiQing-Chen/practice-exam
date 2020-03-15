package com.example.practiceexam.service;

import com.example.common.cache.SharedUser;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.form.AddPaperForm;
import com.example.practiceexam.form.UpdatePaperForm;
import com.example.practiceexam.param.SearchPaperParam;
import com.example.practiceexam.param.StudentSearchPaperParam;

/**
 * @author ShiQing_Chen  2020/3/14  01:28
 **/
public interface PaperInfoService {
    /**
     * 添加
     * @param form
     * @return
     */
    MessageVo add(SharedUser sharedUser, AddPaperForm form);

    /**
     * 修改信息
     * @param form
     * @return
     */
    MessageVo update(SharedUser sharedUser, UpdatePaperForm form);

    /**
     * 根据id查询
     * @param paperId
     * @return
     */
    MessageVo getById(Long paperId);

    /**
     * 根据id删除
     * @param paperId
     * @return
     */
    MessageVo delById(Long paperId);

    /**
     * 管理员
     * 分页查询
     * @param param
     * @return
     */
    MessageVo getListByPage(SearchPaperParam param);

    /**
     * 发布试卷
     * @param sharedUser
     * @param paperId
     * @return
     */
    MessageVo publicPaper(SharedUser sharedUser, Long paperId);

    /**
     * 教师
     * 分页查询
     * @param param
     * @return
     */
    MessageVo teacherGetListByPage(SharedUser sharedUser, SearchPaperParam param);

    /**
     * 学生
     * 分页查询
     * @param param
     * @return
     */
    MessageVo studentGetListByPage(SharedUser sharedUser, StudentSearchPaperParam param);
}
