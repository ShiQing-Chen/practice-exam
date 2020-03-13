package com.example.practiceexam.dao.Impl;

import com.example.practiceexam.dao.ClassInfoDaoCustom;
import com.example.practiceexam.dto.ClassDto;
import com.example.practiceexam.dto.ValueLabelDto;
import com.example.practiceexam.param.SearchClassParam;
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
 * @author ShiQing_Chen  2020/3/7  15:17
 **/
@Repository
public class ClassInfoDaoImpl implements ClassInfoDaoCustom {
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 管理员
     * 分页查询
     * @param classParam
     * @return
     */
    @SuppressWarnings({"unchecked", "Duplicates"})
    @Override
    public List<ClassDto> adminGetListByPage(SearchClassParam classParam) {
        if (classParam == null) {
            return Lists.newArrayList();
        }
        StringBuilder sqlSb = new StringBuilder();
        sqlSb.append(" select c.class_id classId, c.class_name className, c.grade grade, c.major_name majorName, ");
        sqlSb.append(" c.teacher_id teacherId, t.teacher_name teacherName, c.student_num studentNum, c.create_time createTime ");
        sqlSb.append(" FROM class_info c left join teacher_info t on c.teacher_id=t.teacher_id WHERE 1=1 ");

        Map<String, Object> paramMap = Maps.newHashMap();
        if (StringUtils.isNotEmpty(classParam.getSearch())) {
            sqlSb.append(" AND (c.class_name LIKE :search or c.major_name LIKE :search or t.teacher_name LIKE :search) ");
            paramMap.put("search", "%" + classParam.getSearch() + "%");
        }
        if (classParam.getGrade() != null) {
            sqlSb.append(" AND c.grade = :grade ");
            paramMap.put("grade", classParam.getGrade());
        }
        if ("desc".equals(classParam.getOrder()) && StringUtils.isNotBlank(classParam.getSort())) {
            sqlSb.append(" order by ").append(classParam.getSort()).append(" desc");
        } else if ("asc".equals(classParam.getOrder()) && StringUtils.isNotBlank(classParam.getSort())) {
            sqlSb.append(" order by ").append(classParam.getSort()).append(" asc");
        }
        Session session = entityManager.unwrap(Session.class);
        NativeQuery query = session.createSQLQuery(sqlSb.toString());
        query.addScalar("classId", StandardBasicTypes.LONG)
                .addScalar("className", StandardBasicTypes.STRING)
                .addScalar("grade", StandardBasicTypes.STRING)
                .addScalar("majorName", StandardBasicTypes.STRING)
                .addScalar("teacherId", StandardBasicTypes.LONG)
                .addScalar("teacherName", StandardBasicTypes.STRING)
                .addScalar("studentNum", StandardBasicTypes.INTEGER)
                .addScalar("createTime", StandardBasicTypes.TIMESTAMP);
        if (!CollectionUtils.isEmpty(paramMap)) {
            for (Map.Entry<String, Object> m : paramMap.entrySet()) {
                query.setParameter(m.getKey(), m.getValue());
            }
        }
        query.setFirstResult(classParam.getOffset());
        query.setMaxResults(classParam.getLimit());
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(ClassDto.class));
        return query.list();
    }

    /**
     * 管理员
     * 分页查询
     * @param classParam
     * @return
     */
    @SuppressWarnings({"unchecked", "Duplicates"})
    @Override
    public Integer adminGetCountByPage(SearchClassParam classParam) {
        if (classParam == null) {
            return 0;
        }
        StringBuilder sqlSb = new StringBuilder();
        sqlSb.append(" SELECT COUNT(c.class_id) count ");
        sqlSb.append(" FROM class_info c left join teacher_info t on c.teacher_id=t.teacher_id WHERE 1=1 ");

        Map<String, Object> paramMap = Maps.newHashMap();
        if (StringUtils.isNotEmpty(classParam.getSearch())) {
            sqlSb.append(" AND (c.class_name LIKE :search or c.major_name LIKE :search or t.teacher_name LIKE :search) ");
            paramMap.put("search", "%" + classParam.getSearch() + "%");
        }
        if (classParam.getGrade() != null) {
            sqlSb.append(" AND c.grade = :grade ");
            paramMap.put("grade", classParam.getGrade());
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
     * 模糊查询班级
     * @return
     */
    @SuppressWarnings({"unchecked", "Duplicates"})
    @Override
    public List<ValueLabelDto> searchListClassName(String search) {
        if (StringUtils.isBlank(search)) {
            return Lists.newArrayList();
        }
        Session session = entityManager.unwrap(Session.class);
        String sqlSb = " select c.class_id value, concat(c.grade,'-',c.major_name,'-',c.class_name) label " +
                " from class_info c " +
                " where c.class_name like :search or c.major_name like :search ";
        NativeQuery query = session.createSQLQuery(sqlSb);
        query.addScalar("value", StandardBasicTypes.LONG)
                .addScalar("label", StandardBasicTypes.STRING);
        query.setParameter("search", "%" + search + "%");
        query.setFirstResult(0);
        query.setMaxResults(20);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(ValueLabelDto.class));
        return query.list();
    }

    /**
     * 学生编辑初始化学生班级信息
     * @return
     */
    @SuppressWarnings({"unchecked", "Duplicates"})
    @Override
    public List<ValueLabelDto> initStudentClassById(Long classId) {
        if (classId == null) {
            return Lists.newArrayList();
        }
        Session session = entityManager.unwrap(Session.class);
        String sqlSb = " select c.class_id value, concat(c.grade,'-',c.major_name,'-',c.class_name) label " +
                " from class_info c " +
                " where c.class_id = :classId ";
        NativeQuery query = session.createSQLQuery(sqlSb);
        query.addScalar("value", StandardBasicTypes.LONG)
                .addScalar("label", StandardBasicTypes.STRING);
        query.setParameter("classId", classId);
        query.setFirstResult(0);
        query.setMaxResults(20);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(ValueLabelDto.class));
        return query.list();
    }

    /**
     * 获取班级列表
     * @return
     */
    @SuppressWarnings({"unchecked", "Duplicates"})
    @Override
    public List<ValueLabelDto> getListClassIdName() {
        Session session = entityManager.unwrap(Session.class);
        String sqlSb = " select c.class_id value, concat(c.grade,'-',c.major_name,'-',c.class_name) label from class_info c ";
        NativeQuery query = session.createSQLQuery(sqlSb);
        query.addScalar("value", StandardBasicTypes.LONG)
                .addScalar("label", StandardBasicTypes.STRING);
        query.setFirstResult(0);
        query.setMaxResults(20);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(ValueLabelDto.class));
        return query.list();
    }
}
