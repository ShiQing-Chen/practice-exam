package com.example.practiceexam.dao.Impl;

import com.example.practiceexam.dao.TeacherInfoDaoCustom;
import com.example.practiceexam.dto.ClassTeacherDto;
import com.example.practiceexam.dto.TeacherDto;
import com.example.practiceexam.param.SearchClassTeacherParam;
import com.example.practiceexam.param.SearchTeacherParam;
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
 * @author ShiQing_Chen  2020/3/7  17:53
 **/
@Repository
public class TeacherInfoDaoImpl implements TeacherInfoDaoCustom {
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 班级设置教师
     * 获取教师列表
     * 分页查询
     * @param param
     * @return
     */
    @SuppressWarnings({"unchecked", "Duplicates"})
    @Override
    public List<ClassTeacherDto> classGetListByPage(SearchClassTeacherParam param) {
        if (param == null) {
            return Lists.newArrayList();
        }
        StringBuilder sqlSb = new StringBuilder();
        sqlSb.append(" select t.teacher_id teacherId, c.course_name courseName, t.teacher_number teacherNumber, t.teacher_name teacherName ");
        sqlSb.append(" from teacher_info t ");
        sqlSb.append(" left join course_info c on t.course_id = c.course_id WHERE 1=1 ");

        Map<String, Object> paramMap = Maps.newHashMap();
        if (StringUtils.isNotEmpty(param.getSearch())) {
            sqlSb.append(" AND (t.teacher_number LIKE :search or t.teacher_name LIKE :search) ");
            paramMap.put("search", "%" + param.getSearch() + "%");
        }
        if ("desc".equals(param.getOrder()) && StringUtils.isNotBlank(param.getSort())) {
            sqlSb.append(" order by ").append(param.getSort()).append(" desc");
        } else if ("asc".equals(param.getOrder()) && StringUtils.isNotBlank(param.getSort())) {
            sqlSb.append(" order by ").append(param.getSort()).append(" asc");
        }
        Session session = entityManager.unwrap(Session.class);
        NativeQuery query = session.createSQLQuery(sqlSb.toString());
        query.addScalar("teacherId", StandardBasicTypes.LONG)
                .addScalar("courseName", StandardBasicTypes.STRING)
                .addScalar("teacherNumber", StandardBasicTypes.STRING)
                .addScalar("teacherName", StandardBasicTypes.STRING);
        if (!CollectionUtils.isEmpty(paramMap)) {
            for (Map.Entry<String, Object> m : paramMap.entrySet()) {
                query.setParameter(m.getKey(), m.getValue());
            }
        }
        query.setFirstResult(param.getOffset());
        query.setMaxResults(param.getLimit());
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(ClassTeacherDto.class));
        return query.list();
    }

    /**
     * 班级设置教师
     * 获取教师列表
     * 分页查询
     * @param param
     * @return
     */
    @SuppressWarnings({"unchecked", "Duplicates"})
    @Override
    public Integer classGetCountByPage(SearchClassTeacherParam param) {
        if (param == null) {
            return 0;
        }
        StringBuilder sqlSb = new StringBuilder();
        sqlSb.append(" select COUNT(*) count ");
        sqlSb.append(" from teacher_info t WHERE 1=1 ");

        Map<String, Object> paramMap = Maps.newHashMap();
        if (StringUtils.isNotEmpty(param.getSearch())) {
            sqlSb.append(" AND (t.teacher_number LIKE :search or t.teacher_name LIKE :search) ");
            paramMap.put("search", "%" + param.getSearch() + "%");
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
     * 分页查询
     * @param param
     * @return
     */
    @SuppressWarnings({"unchecked", "Duplicates"})
    @Override
    public List<TeacherDto> getListByPage(SearchTeacherParam param) {
        if (param == null) {
            return Lists.newArrayList();
        }
        StringBuilder sqlSb = new StringBuilder();
        sqlSb.append(" select t.teacher_id teacherId, t.user_id userId, t.teacher_number teacherNumber, ");
        sqlSb.append(" c.course_id courseId, c.course_name courseName, ");
        sqlSb.append(" t.teacher_name teacherName, t.create_time createTime, ");
        sqlSb.append(" u.avatar avatar, u.user_type userType, u.gender gender, u.mobile mobile ");
        sqlSb.append(" from teacher_info t left join user_info u on t.user_id=u.user_id ");
        sqlSb.append(" left join course_info c on t.course_id = c.course_id ");
        sqlSb.append(" where 1=1 ");

        Map<String, Object> paramMap = Maps.newHashMap();
        if (StringUtils.isNotEmpty(param.getSearch())) {
            sqlSb.append(" AND (t.teacher_number LIKE :search or t.teacher_name LIKE :search or u.mobile LIKE :search) ");
            paramMap.put("search", "%" + param.getSearch() + "%");
        }
        if ("desc".equals(param.getOrder()) && StringUtils.isNotBlank(param.getSort())) {
            sqlSb.append(" order by ").append(param.getSort()).append(" desc");
        } else if ("asc".equals(param.getOrder()) && StringUtils.isNotBlank(param.getSort())) {
            sqlSb.append(" order by ").append(param.getSort()).append(" asc");
        }
        Session session = entityManager.unwrap(Session.class);
        NativeQuery query = session.createSQLQuery(sqlSb.toString());
        query.addScalar("teacherId", StandardBasicTypes.LONG)
                .addScalar("userId", StandardBasicTypes.LONG)
                .addScalar("courseId", StandardBasicTypes.LONG)
                .addScalar("courseName", StandardBasicTypes.STRING)
                .addScalar("teacherNumber", StandardBasicTypes.STRING)
                .addScalar("teacherName", StandardBasicTypes.STRING)
                .addScalar("createTime", StandardBasicTypes.TIMESTAMP)
                .addScalar("avatar", StandardBasicTypes.STRING)
                .addScalar("userType", StandardBasicTypes.INTEGER)
                .addScalar("gender", StandardBasicTypes.INTEGER)
                .addScalar("mobile", StandardBasicTypes.STRING);
        if (!CollectionUtils.isEmpty(paramMap)) {
            for (Map.Entry<String, Object> m : paramMap.entrySet()) {
                query.setParameter(m.getKey(), m.getValue());
            }
        }
        query.setFirstResult(param.getOffset());
        query.setMaxResults(param.getLimit());
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(TeacherDto.class));
        return query.list();
    }

    /**
     * 分页查询
     * @param param
     * @return
     */
    @SuppressWarnings({"unchecked", "Duplicates"})
    @Override
    public Integer getCountByPage(SearchTeacherParam param) {
        if (param == null) {
            return 0;
        }
        StringBuilder sqlSb = new StringBuilder();
        sqlSb.append(" select COUNT(*) count ");
        sqlSb.append(" from teacher_info t left join user_info u on t.user_id=u.user_id ");
        sqlSb.append(" left join course_info c on t.course_id = c.course_id where 1=1 ");

        Map<String, Object> paramMap = Maps.newHashMap();
        if (StringUtils.isNotEmpty(param.getSearch())) {
            sqlSb.append(" AND (t.teacher_number LIKE :search or t.teacher_name LIKE :search or u.mobile LIKE :search) ");
            paramMap.put("search", "%" + param.getSearch() + "%");
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
