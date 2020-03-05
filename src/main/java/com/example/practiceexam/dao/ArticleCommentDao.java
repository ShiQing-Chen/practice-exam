package com.example.practiceexam.dao;

import com.example.practiceexam.model.ArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 评论Dao层
 * @author ShiQing_Chen  2020/3/5  17:57
 **/
public interface ArticleCommentDao extends JpaRepository<ArticleComment, Long> {
}
