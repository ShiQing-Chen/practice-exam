package com.example.practiceexam.dao;

import com.example.practiceexam.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 用户信息dao层
 * @author ShiQing_Chen  2020/3/5  17:51
 **/
public interface UserInfoDao extends JpaRepository<UserInfo, Long>, UserInfoDaoCustom {

    /**
     * 根据登录名或手机号获取用户
     * @param loginText 登录名或手机号
     * @return
     */
    @Query(value = "select u.* from user_info u where u.login_name = ?1 or u.mobile = ?1",nativeQuery = true)
    UserInfo getByLoginNameOrMobile(String loginText);

    /**
     * 根据refreshToken获取用户
     * @param refreshToken refreshToken
     * @return
     */
    @Query(value = "select u.* from user_info u where u.refresh_token = ?1 ",nativeQuery = true)
    UserInfo getByRefreshToken(String refreshToken);

    /**
     * 根据id获取用户
     * @param userId 用户ID
     * @return
     */
    @Query(value = "select u.* from user_info u where u.user_id = ?1 ", nativeQuery = true)
    UserInfo getById(Long userId);

    /**
     * 根据id删除用户
     * @param userId 用户ID
     * @return
     */
    @Modifying
    @Query(value = "delete from user_info where user_id = ?1 ", nativeQuery = true)
    int delById(Long userId);

    /**
     * 根据id禁用用户
     * @param userId
     * @return
     */
    @Modifying
    @Query(value = "update user_info u set u.user_status = 0 where u.user_id = ?1 ", nativeQuery = true)
    int disableById(Long userId);

    /**
     * 根据id启用用户
     * @param userId
     * @return
     */
    @Modifying
    @Query(value = "update user_info u set u.user_status = 1 where u.user_id = ?1 ", nativeQuery = true)
    int enableById(Long userId);

    /**
     * 校验添加信息时
     * 手机号是否已经存在
     * @param mobile
     * @return
     */
    @Query(value = "SELECT COUNT(*) FROM user_info u WHERE u.mobile = ?1", nativeQuery = true)
    int checkMobile(String mobile);

    /**
     * 校验修改信息时
     * 手机号是否已经存在
     * @param userId
     * @param mobile
     * @return
     */
    @Query(value = "SELECT COUNT(*) FROM user_info u WHERE u.user_id <> ?1 AND u.mobile = ?2", nativeQuery = true)
    int checkMobile(Long userId, String mobile);

    /**
     * 校验添加信息时
     * 登录名是否已经存在
     * @param loginName
     * @return
     */
    @Query(value = "SELECT COUNT(*) FROM user_info u WHERE u.login_name = ?1 OR u.mobile = ?1", nativeQuery = true)
    int checkLoginName(String loginName);

    /**
     * 校验修改信息时
     * 登录名是否已经存在
     * @param userId
     * @param loginName
     * @return
     */
    @Query(value = "SELECT COUNT(*) FROM user_info u WHERE u.user_id <> ?1 AND (u.login_name = ?2 OR u.mobile = ?2) ", nativeQuery = true)
    int checkLoginName(Long userId, String loginName);

    /**
     * 校验添加信息时
     * 登录名和手机号是否已经存在
     * @param loginName
     * @param mobile
     * @return
     */
    @Query(value = "SELECT COUNT(*) FROM user_info u WHERE u.login_name = ?1 OR u.mobile = ?1 OR u.mobile = ?2", nativeQuery = true)
    int checkLogNameAndMobile(String loginName, String mobile);

    /**
     * 校验修改信息时
     * 登录名和手机号是否已经存在
     * @param userId
     * @param loginName
     * @param mobile
     * @return
     */
    @Query(value = "SELECT COUNT(*) FROM user_info u WHERE u.user_id <> ?1 AND (u.login_name = ?2 OR u.mobile = ?2 OR u.mobile = ?3)", nativeQuery = true)
    int checkLogNameAndMobile(Long userId, String loginName, String mobile);

    /**
     * 获取系统内用户数量
     * @return
     */
    @Query(value = "SELECT COUNT(*) FROM user_info ", nativeQuery = true)
    int getUserCount();

    /**
     * 根据登录名或手机号查询用户
     * @return
     */
    @Query(value = "select u.* from user_info u where u.login_name = ?1 or u.mobile = ?1",nativeQuery = true)
    UserInfo getByUserNameOrMobile(String loginText);
}
