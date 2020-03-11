package com.example.practiceexam.dao;

import com.example.practiceexam.model.MessageInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 消息dao层
 * @author ShiQing_Chen  2020/3/11  17:42
 **/
public interface MessageInfoDao extends JpaRepository<MessageInfo, Long> {
    /**
     * 根据id获取
     * @param messageId ID
     * @return
     */
    @Query(value = "select n.* from message_info n where n.message_id = ?1 ", nativeQuery = true)
    MessageInfo getById(Long messageId);
    /**
     * 根据id删除
     * @param messageId ID
     * @return
     */
    @Modifying
    @Query(value = "delete from message_info where message_id = ?1 ", nativeQuery = true)
    int delById(Long messageId);

    /**
     * 分页查询
     * @return
     */
    @Query(value = "select m.* from message_info m where m.accept_user_id = ?1 and m.message_status = ?2  order by m.create_time desc limit ?3 , ?4", nativeQuery = true)
    List<MessageInfo> getListByPage(Long acceptUserId, Integer messageStatus, Integer offset, Integer limit);

    /**
     * 分页查询
     * @return
     */
    @Query(value = "select count(*) from message_info m where m.accept_user_id = ?1 and m.message_status = ?2", nativeQuery = true)
    Integer getCountByPage(Long acceptUserId, Integer messageStatus);

    /**
     * 分页查询
     * @return
     */
    @Query(value = "select m.* from message_info m where m.accept_user_id = ?1 order by m.create_time desc limit ?2 , ?3", nativeQuery = true)
    List<MessageInfo> getListByPage(Long acceptUserId, Integer offset, Integer limit);

    /**
     * 分页查询
     * @return
     */
    @Query(value = "select count(*) from message_info m where m.accept_user_id = ?1 ", nativeQuery = true)
    Integer getCountByPage(Long acceptUserId);

    /**
     * 获取未读消息数量
     * @return
     */
    @Query(value = "select count(*) from message_info m where m.accept_user_id = ?1 and m.message_status = 1", nativeQuery = true)
    Integer getUnReadNumber(Long acceptUserId);
}
