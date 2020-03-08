package com.example.practiceexam.dao.Impl;

import com.example.practiceexam.dao.MaterialInfoDaoCustom;
import com.example.practiceexam.dto.MaterialDto;
import com.example.practiceexam.dto.MaterialInfoDto;
import com.example.practiceexam.param.SearchMaterialParam;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

/**
 * @author ShiQing_Chen  2020/3/8  19:37
 **/
@Repository
public class MaterialInfoDaoImpl implements MaterialInfoDaoCustom {
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 分页查询
     * @param param
     * @return
     */
    @SuppressWarnings({"unchecked", "Duplicates"})
    @Override
    public List<MaterialDto> getListByPage(SearchMaterialParam param) {
        if (param == null) {
            return Lists.newArrayList();
        }
        StringBuilder sqlSb = new StringBuilder();
        sqlSb.append(" select n.material_id materialId, n.main_title mainTitle, n.sub_title subTitle, n.material_status materialStatus, ");
        sqlSb.append(" n.create_user_id createUserId, cu.nick_name createUserName, n.create_time createTime, ");
        sqlSb.append(" n.publish_user_id publishUserId, pu.nick_name publishUserName, n.publish_time publishTime ");
        sqlSb.append(" from material_info n ");
        sqlSb.append(" left join user_info cu on n.create_user_id = cu.user_id ");
        sqlSb.append(" left join user_info pu on n.publish_user_id = pu.user_id WHERE 1=1 ");

        Map<String, Object> paramMap = Maps.newHashMap();
        if (StringUtils.isNotEmpty(param.getSearch())) {
            sqlSb.append(" AND (n.main_title LIKE :search or n.sub_title LIKE :search) ");
            paramMap.put("search", "%" + param.getSearch() + "%");
        }
        if (param.getMaterialStatus() != null) {
            sqlSb.append(" AND n.material_status = :materialStatus ");
            paramMap.put("materialStatus", param.getMaterialStatus());
        }
        if ("desc".equals(param.getOrder()) && StringUtils.isNotBlank(param.getSort())) {
            sqlSb.append(" order by ").append(param.getSort()).append(" desc");
        } else if ("asc".equals(param.getOrder()) && StringUtils.isNotBlank(param.getSort())) {
            sqlSb.append(" order by ").append(param.getSort()).append(" asc");
        }
        Session session = entityManager.unwrap(Session.class);
        NativeQuery query = session.createSQLQuery(sqlSb.toString());
        query.addScalar("materialId", StandardBasicTypes.LONG)
                .addScalar("mainTitle", StandardBasicTypes.STRING)
                .addScalar("subTitle", StandardBasicTypes.STRING)
                .addScalar("materialStatus", StandardBasicTypes.INTEGER)
                .addScalar("publishUserId", StandardBasicTypes.LONG)
                .addScalar("publishUserName", StandardBasicTypes.STRING)
                .addScalar("publishTime", StandardBasicTypes.TIMESTAMP)
                .addScalar("createUserId", StandardBasicTypes.LONG)
                .addScalar("createUserName", StandardBasicTypes.STRING)
                .addScalar("createTime", StandardBasicTypes.TIMESTAMP);
        if (!CollectionUtils.isEmpty(paramMap)) {
            for (Map.Entry<String, Object> m : paramMap.entrySet()) {
                query.setParameter(m.getKey(), m.getValue());
            }
        }
        query.setFirstResult(param.getOffset());
        query.setMaxResults(param.getLimit());
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(MaterialDto.class));
        return query.list();
    }

    /**
     * 分页查询
     * @param param
     * @return
     */
    @SuppressWarnings({"unchecked", "Duplicates"})
    @Override
    public Integer getCountByPage(SearchMaterialParam param) {
        if (param == null) {
            return 0;
        }
        StringBuilder sqlSb = new StringBuilder();
        sqlSb.append(" SELECT COUNT(*) count ");
        sqlSb.append(" from material_info n ");
        sqlSb.append(" left join user_info cu on n.create_user_id = cu.user_id ");
        sqlSb.append(" left join user_info pu on n.publish_user_id = pu.user_id WHERE 1=1 ");

        Map<String, Object> paramMap = Maps.newHashMap();
        if (StringUtils.isNotEmpty(param.getSearch())) {
            sqlSb.append(" AND (n.main_title LIKE :search or n.sub_title LIKE :search) ");
            paramMap.put("search", "%" + param.getSearch() + "%");
        }
        if (param.getMaterialStatus() != null) {
            sqlSb.append(" AND n.material_status = :materialStatus ");
            paramMap.put("materialStatus", param.getMaterialStatus());
        }
        Session session = entityManager.unwrap(Session.class);
        NativeQuery query = session.createSQLQuery(sqlSb.toString());
        query.addScalar("count", StandardBasicTypes.LONG);
        if (!CollectionUtils.isEmpty(paramMap)) {
            for (Map.Entry<String, Object> m : paramMap.entrySet()) {
                query.setParameter(m.getKey(), m.getValue());
            }
        }
        Long count = (Long) query.uniqueResult();
        if (count != null) {
            return count.intValue();
        }
        return 0;
    }

    /**
     * 根据ID获取详情
     * @param materialId
     * @return
     */
    @SuppressWarnings({"unchecked", "Duplicates"})
    @Override
    public MaterialInfoDto getInfoById(Long materialId) {
        if (materialId == null) {
            return null;
        }
        Session session = entityManager.unwrap(Session.class);
        String sqlSb = " select n.material_id materialId, n.main_title mainTitle, n.sub_title subTitle, n.material_content materialContent,  " +
                " n.material_status materialStatus, n.create_user_id createUserId, cu.nick_name createUserName, n.create_time createTime, " +
                " n.publish_user_id publishUserId, pu.nick_name publishUserName, n.publish_time publishTime " +
                " from material_info n " +
                " left join user_info cu on n.create_user_id = cu.user_id " +
                " left join user_info pu on n.publish_user_id = pu.user_id WHERE n.material_id = :materialId ";
        NativeQuery query = session.createSQLQuery(sqlSb);
        query.addScalar("materialId", StandardBasicTypes.LONG)
                .addScalar("mainTitle", StandardBasicTypes.STRING)
                .addScalar("subTitle", StandardBasicTypes.STRING)
                .addScalar("materialContent", StandardBasicTypes.STRING)
                .addScalar("materialStatus", StandardBasicTypes.INTEGER)
                .addScalar("publishUserId", StandardBasicTypes.LONG)
                .addScalar("publishUserName", StandardBasicTypes.STRING)
                .addScalar("publishTime", StandardBasicTypes.TIMESTAMP)
                .addScalar("createUserId", StandardBasicTypes.LONG)
                .addScalar("createUserName", StandardBasicTypes.STRING)
                .addScalar("createTime", StandardBasicTypes.TIMESTAMP);
        query.setParameter("materialId", materialId);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(MaterialInfoDto.class));
        return (MaterialInfoDto) query.uniqueResult();
    }
}
