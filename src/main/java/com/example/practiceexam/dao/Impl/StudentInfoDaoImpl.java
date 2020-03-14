package com.example.practiceexam.dao.Impl;

import com.example.practiceexam.dao.StudentInfoDaoCustom;
import com.example.practiceexam.dto.StudentDto;
import com.example.practiceexam.param.SearchStudentParam;
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
 * @author ShiQing_Chen  2020/3/7  22:54
 **/
@Repository
public class StudentInfoDaoImpl implements StudentInfoDaoCustom {
    @PersistenceContext
    private EntityManager entityManager;


    @SuppressWarnings({"unchecked", "Duplicates"})
    @Override
    public List<StudentDto> getListByPage(SearchStudentParam param) {
        if (param == null) {
            return Lists.newArrayList();
        }
        StringBuilder sqlSb = new StringBuilder();
        sqlSb.append(" select s.student_id studentId, s.user_id userId, s.student_number studentNumber, ");
        sqlSb.append(" s.student_name studentName, s.class_id classId, c.class_name className, s.create_time createTime, ");
        sqlSb.append(" u.avatar avatar, u.user_type userType, u.gender gender, u.mobile mobile, c.grade grade, c.major_name majorName ");
        sqlSb.append(" from student_info s left join user_info u on s.user_id=u.user_id ");
        sqlSb.append(" left join class_info c on s.class_id = c.class_id ");
        sqlSb.append(" where 1=1 ");

        Map<String, Object> paramMap = Maps.newHashMap();
        if (param.getClassId() != null) {
            sqlSb.append(" AND s.class_id = :classId ");
            paramMap.put("classId", param.getClassId());
        }
        if (!CollectionUtils.isEmpty(param.getClassIdList())) {
            sqlSb.append(" AND s.class_id in (:classIdList) ");
            paramMap.put("classIdList", param.getClassIdList());
        }
        if (StringUtils.isNotEmpty(param.getSearch())) {
            sqlSb.append(" AND (s.student_number LIKE :search or s.student_name LIKE :search or u.mobile LIKE :search) ");
            paramMap.put("search", "%" + param.getSearch() + "%");
        }
        if (StringUtils.isNotBlank(param.getGrade())) {
            sqlSb.append(" AND c.grade = :grade ");
            paramMap.put("grade", param.getGrade());
        }
        if (StringUtils.isNotBlank(param.getMajorName())) {
            sqlSb.append(" AND c.major_name = :majorName ");
            paramMap.put("majorName", param.getMajorName());
        }
        if (StringUtils.isNotBlank(param.getClassName())) {
            sqlSb.append(" AND c.class_name = :className ");
            paramMap.put("className", param.getClassName());
        }
        if ("desc".equals(param.getOrder()) && StringUtils.isNotBlank(param.getSort())) {
            sqlSb.append(" order by ").append(param.getSort()).append(" desc");
        } else if ("asc".equals(param.getOrder()) && StringUtils.isNotBlank(param.getSort())) {
            sqlSb.append(" order by ").append(param.getSort()).append(" asc");
        }
        Session session = entityManager.unwrap(Session.class);
        NativeQuery query = session.createSQLQuery(sqlSb.toString());
        query.addScalar("studentId", StandardBasicTypes.LONG)
                .addScalar("userId", StandardBasicTypes.LONG)
                .addScalar("studentNumber", StandardBasicTypes.STRING)
                .addScalar("studentName", StandardBasicTypes.STRING)
                .addScalar("classId", StandardBasicTypes.LONG)
                .addScalar("className", StandardBasicTypes.STRING)
                .addScalar("createTime", StandardBasicTypes.TIMESTAMP)
                .addScalar("avatar", StandardBasicTypes.STRING)
                .addScalar("userType", StandardBasicTypes.INTEGER)
                .addScalar("gender", StandardBasicTypes.INTEGER)
                .addScalar("mobile", StandardBasicTypes.STRING)
                .addScalar("grade", StandardBasicTypes.STRING)
                .addScalar("majorName", StandardBasicTypes.STRING);
        if (!CollectionUtils.isEmpty(paramMap)) {
            for (Map.Entry<String, Object> m : paramMap.entrySet()) {
                query.setParameter(m.getKey(), m.getValue());
            }
        }
        query.setFirstResult(param.getOffset());
        query.setMaxResults(param.getLimit());
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(StudentDto.class));
        return query.list();
    }

    @SuppressWarnings({"unchecked", "Duplicates"})
    @Override
    public Integer getCountByPage(SearchStudentParam param) {
        if (param == null) {
            return 0;
        }
        StringBuilder sqlSb = new StringBuilder();
        sqlSb.append(" select COUNT(*) count ");
        sqlSb.append(" from student_info s left join user_info u on s.user_id=u.user_id ");
        sqlSb.append(" left join class_info c on s.class_id = c.class_name ");
        sqlSb.append(" where 1=1 ");

        Map<String, Object> paramMap = Maps.newHashMap();
        if (param.getClassId() != null) {
            sqlSb.append(" AND s.class_id = :classId ");
            paramMap.put("classId", param.getClassId());
        }
        if (!CollectionUtils.isEmpty(param.getClassIdList())) {
            sqlSb.append(" AND s.class_id in (:classIdList) ");
            paramMap.put("classIdList", param.getClassIdList());
        }
        if (StringUtils.isNotEmpty(param.getSearch())) {
            sqlSb.append(" AND (s.student_number LIKE :search or s.student_name LIKE :search or u.mobile LIKE :search) ");
            paramMap.put("search", "%" + param.getSearch() + "%");
        }
        if (StringUtils.isNotBlank(param.getGrade())) {
            sqlSb.append(" AND c.grade = :grade ");
            paramMap.put("grade", param.getGrade());
        }
        if (StringUtils.isNotBlank(param.getMajorName())) {
            sqlSb.append(" AND c.major_name = :majorName ");
            paramMap.put("majorName", param.getMajorName());
        }
        if (StringUtils.isNotBlank(param.getClassName())) {
            sqlSb.append(" AND c.class_name = :className ");
            paramMap.put("className", param.getClassName());
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
}
