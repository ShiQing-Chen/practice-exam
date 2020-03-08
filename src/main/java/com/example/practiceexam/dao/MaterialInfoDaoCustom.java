package com.example.practiceexam.dao;

import com.example.practiceexam.dto.MaterialDto;
import com.example.practiceexam.dto.MaterialInfoDto;
import com.example.practiceexam.dto.ValueLabelDto;
import com.example.practiceexam.param.SearchMaterialParam;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ShiQing_Chen  2020/3/8  19:37
 **/
@Repository
public interface MaterialInfoDaoCustom {
    /**
     * 分页查询
     * @param param
     * @return
     */
    List<MaterialDto> getListByPage(SearchMaterialParam param);

    /**
     * 分页查询
     * @param param
     * @return
     */
    Integer getCountByPage(SearchMaterialParam param);

    /**
     * 根据ID获取详情
     * @param materialId
     * @return
     */
    MaterialInfoDto getInfoById(Long materialId);

    /**
     * 首页获取前5条
     * @return
     */
    List<ValueLabelDto> indexGetList();
}
