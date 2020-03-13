package com.example.practiceexam.dao;

import com.example.practiceexam.model.PaperInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 试卷Dao层
 * @author ShiQing_Chen  2020/3/14  01:28
 **/
public interface PaperInfoDao extends JpaRepository<PaperInfo, Long>, PaperInfoDaoCustom {
    /**
     * 根据id获取
     * @param paperId 试卷ID
     * @return
     */
    @Query(value = "select p.* from paper_info p where p.paper_id = ?1 ", nativeQuery = true)
    PaperInfo getById(Long paperId);

    /**
     * 根据id删除
     * @param paperId 试卷ID
     * @return
     */
    @Modifying
    @Query(value = "delete from paper_info where paper_id = ?1 ", nativeQuery = true)
    int delById(Long paperId);
}
