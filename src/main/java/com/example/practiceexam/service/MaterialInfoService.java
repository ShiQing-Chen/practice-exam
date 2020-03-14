package com.example.practiceexam.service;

import com.example.common.cache.SharedUser;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.form.AddMaterialForm;
import com.example.practiceexam.form.UpdateMaterialForm;
import com.example.practiceexam.param.SearchMaterialParam;

/**
 * @author ShiQing_Chen  2020/3/5  18:01
 **/
public interface MaterialInfoService {
    /**
     * 添加
     * @param form
     * @return
     */
    MessageVo add(SharedUser sharedUser, AddMaterialForm form);

    /**
     * 修改信息
     * @param form
     * @return
     */
    MessageVo update(SharedUser sharedUser, UpdateMaterialForm form);

    /**
     * 根据id删除
     * @param materialId
     * @return
     */
    MessageVo delById(Long materialId);

    /**
     * 根据id获取信息
     * @param materialId
     * @return
     */
    MessageVo getById(Long materialId);

    /**
     * 根据id获取详细信息
     * @param materialId
     * @return
     */
    MessageVo getInfoById(Long materialId);

    /**
     * 发布
     * @param sharedUser
     * @param materialId
     * @return
     */
    MessageVo publicMaterial(SharedUser sharedUser, Long materialId);

    /**
     * 分页查询
     * @param param
     * @return
     */
    MessageVo getListByPage(SearchMaterialParam param);

    /**
     * 首页获取前5条
     * @return
     */
    MessageVo indexGetList();

    /**
     * 教师
     * 分页查询
     * @param param
     * @return
     */
    MessageVo teacherGetListByPage(SharedUser sharedUser, SearchMaterialParam param);
}
