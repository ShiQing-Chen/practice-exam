package com.example.practiceexam.dao;

import com.example.practiceexam.dto.UserDto;
import com.example.practiceexam.param.SearchUserParam;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户信息dao层
 * @author ShiQing_Chen  2020/3/5  17:51
 **/
@Repository
public interface UserInfoDaoCustom {

    /**
     * 管理员
     * 分页查询
     * @param userParam
     * @return
     */
    List<UserDto> adminGetListByPage(SearchUserParam userParam);

    /**
     * 管理员
     * 分页查询
     * @param userParam
     * @return
     */
    Integer adminGetCountByPage(SearchUserParam userParam);

}
