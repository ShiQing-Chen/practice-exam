package com.example.practiceexam.dao;

import com.example.practiceexam.model.ArticleInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 帖子信息Dao层
 * @author ShiQing_Chen  2020/3/5  17:56
 **/
public interface ArticleInfoDao extends JpaRepository<ArticleInfo, Long>, ArticleInfoDaoCustom {
}
