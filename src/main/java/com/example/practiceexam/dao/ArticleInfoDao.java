package com.example.practiceexam.dao;

import com.example.practiceexam.model.ArticleInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 帖子信息Dao层
 * @author ShiQing_Chen  2020/3/5  17:56
 **/
public interface ArticleInfoDao extends JpaRepository<ArticleInfo, Long>, ArticleInfoDaoCustom {

    /**
     * 根据id获取
     * @param articleId ID
     * @return
     */
    @Query(value = "select a.* from article_info a where a.article_id = ?1 ", nativeQuery = true)
    ArticleInfo getById(Long articleId);

    /**
     * 根据id删除
     * @param articleId ID
     * @return
     */
    @Modifying
    @Query(value = "delete from article_info where article_id = ?1 ", nativeQuery = true)
    int delById(Long articleId);
}
