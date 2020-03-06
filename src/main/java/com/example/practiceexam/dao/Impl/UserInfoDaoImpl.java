package com.example.practiceexam.dao.Impl;

import com.example.practiceexam.dao.UserInfoDaoCustom;
import com.example.practiceexam.dto.UserDto;
import com.example.practiceexam.param.SearchUserParam;
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
 * @author ShiQing_Chen  2020/3/7  00:23
 **/
@Repository
public class UserInfoDaoImpl implements UserInfoDaoCustom {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 管理员
     * 分页查询
     * @param userParam
     * @return
     */
    @SuppressWarnings({"unchecked", "Duplicates" })
    @Override
    public List<UserDto> adminGetListByPage(SearchUserParam userParam) {
        if (userParam == null) {
            return Lists.newArrayList();
        }
        StringBuilder sqlSb = new StringBuilder();
        sqlSb.append(" SELECT u.user_id userId, u.login_name loginName, u.nick_name nickName, u.avatar avatar, ");
        sqlSb.append(" u.user_type userType, u.gender gender, u.mobile mobile, u.user_status userStatus, ");
        sqlSb.append(" u.create_time createTime, u.update_time updateTime, u.last_login_time lastLoginTime  ");
        sqlSb.append(" FROM user_info u WHERE 1=1 ");

        Map<String, Object> paramMap = Maps.newHashMap();
        if (StringUtils.isNotEmpty(userParam.getSearch())) {
            sqlSb.append(" AND (u.`nick_name` LIKE :search or u.login_name LIKE :search or u.mobile LIKE :search) ");
            paramMap.put("search", "%" + userParam.getSearch() + "%");
        }
        if (userParam.getUserType() != null) {
            sqlSb.append(" AND u.user_type = :userType ");
            paramMap.put("userType", userParam.getUserType());
        }
        if (userParam.getUserStatus() != null) {
            sqlSb.append(" AND u.user_status = :userStatus ");
            paramMap.put("userStatus", userParam.getUserStatus());
        }
        if ("desc".equals(userParam.getOrder()) && StringUtils.isNotBlank(userParam.getSort())) {
            sqlSb.append(" order by ").append(userParam.getSort()).append(" desc");
        } else if ("asc".equals(userParam.getOrder()) && StringUtils.isNotBlank(userParam.getSort())) {
            sqlSb.append(" order by ").append(userParam.getSort()).append(" asc");
        }
        Session session = entityManager.unwrap(Session.class);
        NativeQuery query = session.createSQLQuery(sqlSb.toString());
        query.addScalar("userId", StandardBasicTypes.LONG)
                .addScalar("loginName", StandardBasicTypes.STRING)
                .addScalar("nickName", StandardBasicTypes.STRING)
                .addScalar("avatar", StandardBasicTypes.STRING)
                .addScalar("userType", StandardBasicTypes.INTEGER)
                .addScalar("gender", StandardBasicTypes.INTEGER)
                .addScalar("mobile", StandardBasicTypes.STRING)
                .addScalar("userStatus", StandardBasicTypes.INTEGER)
                .addScalar("createTime", StandardBasicTypes.TIMESTAMP)
                .addScalar("updateTime", StandardBasicTypes.TIMESTAMP)
                .addScalar("lastLoginTime", StandardBasicTypes.TIMESTAMP);
        if (!CollectionUtils.isEmpty(paramMap)) {
            for (Map.Entry<String, Object> m : paramMap.entrySet()) {
                query.setParameter(m.getKey(), m.getValue());
            }
        }
        query.setFirstResult(userParam.getOffset());
        query.setMaxResults(userParam.getLimit());
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(UserDto.class));
        return query.list();
    }

    /**
     * 管理员
     * 分页查询
     * @param userParam
     * @return
     */
    @SuppressWarnings({"unchecked", "Duplicates" })
    @Override
    public Integer adminGetCountByPage(SearchUserParam userParam) {
        if (userParam == null) {
            return 0;
        }
        StringBuilder sqlSb = new StringBuilder();
        sqlSb.append(" SELECT COUNT(*) count FROM user_info u WHERE 1=1 ");
        Map<String, Object> paramMap = Maps.newHashMap();
        if (StringUtils.isNotEmpty(userParam.getSearch())) {
            sqlSb.append(" AND (u.`nick_name` LIKE :search or u.login_name LIKE :search or u.mobile LIKE :search) ");
            paramMap.put("search", "%" + userParam.getSearch() + "%");
        }
        if (userParam.getUserType() != null) {
            sqlSb.append(" AND u.user_type = :userType ");
            paramMap.put("userType", userParam.getUserType());
        }
        if (userParam.getUserStatus() != null) {
            sqlSb.append(" AND u.user_status = :userStatus ");
            paramMap.put("userStatus", userParam.getUserStatus());
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
