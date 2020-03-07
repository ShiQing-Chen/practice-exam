package com.example.practiceexam.dao;

import com.example.practiceexam.dto.StudentDto;
import com.example.practiceexam.param.SearchStudentParam;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ShiQing_Chen  2020/3/7  22:53
 **/
@Repository
public interface StudentInfoDaoCustom {
    /**
     * 分页查询
     * @param param
     * @return
     */
    List<StudentDto> getListByPage(SearchStudentParam param);

    /**
     * 分页查询
     * @param param
     * @return
     */
    Integer getCountByPage(SearchStudentParam param);
}
