package com.example.practiceexam.dao;

import com.example.practiceexam.model.ArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 评论Dao层
 * @author ShiQing_Chen  2020/3/5  17:57
 **/
public interface ArticleCommentDao extends JpaRepository<ArticleComment, Long> {

    /**
     * 分页查询
     * @return
     */
    @Query(value = "select a.* from article_comment a where a.article_id = ?1 order by a.create_time desc limit ?2 , ?3", nativeQuery = true)
    List<ArticleComment> getListByPage(Long articleId, Integer offset, Integer limit);

    /**
     * 分页查询
     * @return
     */
    @Query(value = "select count(*) from article_comment a where a.article_id = ?1 ", nativeQuery = true)
    Integer getCountByPage(Long articleId);
}
