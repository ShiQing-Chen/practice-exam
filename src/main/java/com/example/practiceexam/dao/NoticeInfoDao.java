package com.example.practiceexam.dao;

import com.example.practiceexam.model.NoticeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 通知信息dao层
 * @author ShiQing_Chen  2020/3/5  17:53
 **/
public interface NoticeInfoDao extends JpaRepository<NoticeInfo, Long>, NoticeInfoDaoCustom {
    /**
     * 根据id获取通知
     * @param noticeId 班级ID
     * @return
     */
    @Query(value = "select n.* from notice_info n where n.notice_id = ?1 ", nativeQuery = true)
    NoticeInfo getById(Long noticeId);

    /**
     * 根据id删除通知
     * @param noticeId 通知ID
     * @return
     */
    @Modifying
    @Query(value = "delete from notice_info where notice_id = ?1 ", nativeQuery = true)
    int delById(Long noticeId);
}
