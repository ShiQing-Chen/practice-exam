package com.example.practiceexam.dao;

import com.example.practiceexam.model.PaperClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 试卷班级关系dao
 * @author ShiQing_Chen  2020/3/14  01:27
 **/
public interface PaperClassDao extends JpaRepository<PaperClass, Long> {
    /**
     * 根据id删除
     * @param paperId 试卷ID
     * @return
     */
    @Modifying
    @Query(value = "delete from paper_class where paper_id = ?1 ", nativeQuery = true)
    int delByPaperId(Long paperId);

    /**
     * 根据试卷ID获取班级ID列表
     * @param paperId 试卷ID
     * @return
     */
    @Query(value = "select pc.class_id from paper_class pc where pc.paper_id = ?1 ", nativeQuery = true)
    List<Long> getClassIdsByPaperId(Long paperId);
}
