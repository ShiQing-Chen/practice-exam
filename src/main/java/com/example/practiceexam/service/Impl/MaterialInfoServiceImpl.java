package com.example.practiceexam.service.Impl;

import com.example.common.cache.SharedUser;
import com.example.common.util.IdGeneratorUtils;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.dao.MaterialInfoDao;
import com.example.practiceexam.dto.MaterialDto;
import com.example.practiceexam.dto.MaterialInfoDto;
import com.example.practiceexam.dto.ValueLabelDto;
import com.example.practiceexam.form.AddMaterialForm;
import com.example.practiceexam.form.UpdateMaterialForm;
import com.example.practiceexam.model.MaterialInfo;
import com.example.practiceexam.param.SearchMaterialParam;
import com.example.practiceexam.service.MaterialInfoService;
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
 * @author ShiQing_Chen  2020/3/5  18:03
 **/
@Service
public class MaterialInfoServiceImpl implements MaterialInfoService {

    @Autowired
    private MaterialInfoDao materialInfoDao;

    /**
     * 添加
     * @param form
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public MessageVo add(SharedUser sharedUser, AddMaterialForm form) {
        if (form != null && sharedUser != null) {
            MaterialInfo materialInfo = new MaterialInfo();
            Date curDate = new Date();
            BeanUtils.copyProperties(form, materialInfo);
            materialInfo.setMaterialId(IdGeneratorUtils.getNewId());
            materialInfo.setCreateUserId(sharedUser.getUserId());
            materialInfo.setCreateTime(curDate);
            materialInfo.setUpdateTime(curDate);
            if (form.getMaterialStatus().equals(MaterialInfo.STATUS_PUBLIC)) {
                materialInfo.setPublishTime(curDate);
                materialInfo.setPublishUserId(sharedUser.getUserId());
            }
            materialInfoDao.save(materialInfo);
            return MessageVo.success();
        }
        return MessageVo.fail("添加资料失败！");
    }

    /**
     * 修改信息
     * @param form
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public MessageVo update(SharedUser sharedUser, UpdateMaterialForm form) {
        if (form != null && sharedUser != null) {
            MaterialInfo materialInfo = materialInfoDao.getById(form.getMaterialId());
            if (materialInfo != null) {
                Date curDate = new Date();
                BeanUtils.copyProperties(form, materialInfo);
                materialInfo.setUpdateTime(curDate);
                materialInfoDao.save(materialInfo);
                return MessageVo.success();
            }
        }
        return MessageVo.fail("修改资料失败！");
    }

    /**
     * 根据id删除
     * @param materialId
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public MessageVo delById(Long materialId) {
        if (materialId != null) {
            int result = materialInfoDao.delById(materialId);
            if (result > 0) {
                return MessageVo.success();
            }
        }
        return MessageVo.fail("删除资料失败！");
    }

    /**
     * 根据id获取信息
     * @param materialId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public MessageVo getById(Long materialId) {
        if (materialId != null) {
            MaterialInfo materialInfo = materialInfoDao.getById(materialId);
            if (materialInfo != null) {
                return MessageVo.success(materialInfo);
            }
        }
        return MessageVo.fail("获取资料失败！");
    }

    /**
     * 根据id获取详细信息
     * @param materialId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public MessageVo getInfoById(Long materialId) {
        if (materialId != null) {
            MaterialInfoDto materialInfo = materialInfoDao.getInfoById(materialId);
            if (materialInfo != null) {
                return MessageVo.success(materialInfo);
            }
        }
        return MessageVo.fail("获取资料失败！");
    }

    /**
     * 发布
     * @param sharedUser
     * @param materialId
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public MessageVo publicMaterial(SharedUser sharedUser, Long materialId) {
        if (sharedUser != null && materialId != null) {
            MaterialInfo materialInfo = materialInfoDao.getById(materialId);
            if (materialInfo == null) {
                return MessageVo.fail("未查询到资料信息");
            }
            Date curDate = new Date();
            materialInfo.setMaterialStatus(MaterialInfo.STATUS_PUBLIC);
            materialInfo.setPublishUserId(sharedUser.getUserId());
            materialInfo.setPublishTime(curDate);
            materialInfo.setUpdateTime(curDate);
            materialInfoDao.save(materialInfo);
            return MessageVo.success("资料发布成功!");
        }
        return MessageVo.fail("发布资料失败！");
    }

    /**
     * 分页查询
     * @param param
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public MessageVo getListByPage(SearchMaterialParam param) {
        if (param != null) {
            List<MaterialDto> list = materialInfoDao.getListByPage(param);
            Integer count = materialInfoDao.getCountByPage(param);
            Map<String, Object> map = Maps.newHashMap();
            map.put("list", list);
            map.put("total", count);
            return MessageVo.success(map);
        } else {
            return MessageVo.success(Lists.newArrayList());
        }
    }

    /**
     * 首页获取前五条下载资料
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public MessageVo indexGetList() {
        List<ValueLabelDto> list = materialInfoDao.indexGetList();
        if (CollectionUtils.isEmpty(list)) {
            return MessageVo.success(Lists.newArrayList());
        }
        return MessageVo.success(list);
    }

    /**
     * 教师
     * 分页查询
     * @param param
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public MessageVo teacherGetListByPage(SharedUser sharedUser, SearchMaterialParam param) {
        if (param != null && sharedUser != null) {
            param.setCreateUserId(sharedUser.getUserId());
            List<MaterialDto> list = materialInfoDao.getListByPage(param);
            Integer count = materialInfoDao.getCountByPage(param);
            Map<String, Object> map = Maps.newHashMap();
            map.put("list", list);
            map.put("total", count);
            return MessageVo.success(map);
        } else {
            return MessageVo.success(Lists.newArrayList());
        }
    }
}
