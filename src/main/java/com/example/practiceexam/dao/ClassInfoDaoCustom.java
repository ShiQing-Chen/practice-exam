package com.example.practiceexam.dao;

import com.example.practiceexam.dto.ClassDto;
import com.example.practiceexam.param.SearchClassParam;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 班级信息Dao层
 * @author ShiQing_Chen  2020/3/5  17:55
 **/
@Repository
public interface ClassInfoDaoCustom {
    /**
     * 管理员
     * 分页查询
     * @param classParam
     * @return
     */
    List<ClassDto> adminGetListByPage(SearchClassParam classParam);

    /**
     * 管理员
     * 分页查询
     * @param classParam
     * @return
     */
    Integer adminGetCountByPage(SearchClassParam classParam);
}
