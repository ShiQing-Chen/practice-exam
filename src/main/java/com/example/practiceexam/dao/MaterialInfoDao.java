package com.example.practiceexam.dao;

import com.example.practiceexam.model.MaterialInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 资料信息Dao层
 * @author ShiQing_Chen  2020/3/5  17:54
 **/
public interface MaterialInfoDao extends JpaRepository<MaterialInfo, Long>, MaterialInfoDaoCustom {

    /**
     * 根据id获取资料
     * @param materialId 资料ID
     * @return
     */
    @Query(value = "select m.* from material_info m where m.material_id = ?1 ", nativeQuery = true)
    MaterialInfo getById(Long materialId);

    /**
     * 根据id删除资料
     * @param materialId 资料ID
     * @return
     */
    @Modifying
    @Query(value = "delete from material_info where material_id = ?1 ", nativeQuery = true)
    int delById(Long materialId);
}
