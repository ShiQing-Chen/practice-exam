package com.example.practiceexam.dao;

import com.example.practiceexam.model.MaterialInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 资料信息Dao层
 * @author ShiQing_Chen  2020/3/5  17:54
 **/
public interface MaterialInfoDao extends JpaRepository<MaterialInfo, Long> {
}
