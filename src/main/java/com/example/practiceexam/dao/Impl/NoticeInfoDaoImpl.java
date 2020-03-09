package com.example.practiceexam.dao.Impl;

import com.example.practiceexam.dao.NoticeInfoDaoCustom;
import com.example.practiceexam.dto.NoticeDto;
import com.example.practiceexam.dto.NoticeInfoDto;
import com.example.practiceexam.dto.ValueLabelDto;
import com.example.practiceexam.param.SearchNoticeParam;
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
 * @author ShiQing_Chen  2020/3/8  17:59
 **/
@Repository
public class NoticeInfoDaoImpl implements NoticeInfoDaoCustom {
    @PersistenceContext
    private EntityManager entityManager;


    /**
     * 分页查询
     * @param param
     * @return
     */
    @SuppressWarnings({"unchecked", "Duplicates"})
    @Override
    public List<NoticeDto> getListByPage(SearchNoticeParam param) {
        if (param == null) {
            return Lists.newArrayList();
        }
        StringBuilder sqlSb = new StringBuilder();
        sqlSb.append(" select n.notice_id noticeId, n.main_title mainTitle, n.sub_title subTitle, n.notice_status noticeStatus, ");
        sqlSb.append(" n.create_user_id createUserId, cu.nick_name createUserName, n.create_time createTime, ");
        sqlSb.append(" n.publish_user_id publishUserId, pu.nick_name publishUserName, n.publish_time publishTime ");
        sqlSb.append(" from notice_info n ");
        sqlSb.append(" left join user_info cu on n.create_user_id = cu.user_id ");
        sqlSb.append(" left join user_info pu on n.publish_user_id = pu.user_id WHERE 1=1 ");

        Map<String, Object> paramMap = Maps.newHashMap();
        if (StringUtils.isNotEmpty(param.getSearch())) {
            sqlSb.append(" AND (n.main_title LIKE :search or n.sub_title LIKE :search) ");
            paramMap.put("search", "%" + param.getSearch() + "%");
        }
        if (param.getNoticeStatus()!=null) {
            sqlSb.append(" AND n.notice_status = :noticeStatus ");
            paramMap.put("noticeStatus", param.getNoticeStatus());
        }
        if ("desc".equals(param.getOrder()) && StringUtils.isNotBlank(param.getSort())) {
            sqlSb.append(" order by ").append(param.getSort()).append(" desc");
        } else if ("asc".equals(param.getOrder()) && StringUtils.isNotBlank(param.getSort())) {
            sqlSb.append(" order by ").append(param.getSort()).append(" asc");
        }
        Session session = entityManager.unwrap(Session.class);
        NativeQuery query = session.createSQLQuery(sqlSb.toString());
        query.addScalar("noticeId", StandardBasicTypes.LONG)
                .addScalar("mainTitle", StandardBasicTypes.STRING)
                .addScalar("subTitle", StandardBasicTypes.STRING)
                .addScalar("noticeStatus", StandardBasicTypes.INTEGER)
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
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(NoticeDto.class));
        return query.list();
    }

    /**
     * 分页查询
     * @param param
     * @return
     */
    @SuppressWarnings({"unchecked", "Duplicates"})
    @Override
    public Integer getCountByPage(SearchNoticeParam param) {
        if (param == null) {
            return 0;
        }
        StringBuilder sqlSb = new StringBuilder();
        sqlSb.append(" SELECT COUNT(*) count ");
        sqlSb.append(" from notice_info n ");
        sqlSb.append(" left join user_info cu on n.create_user_id = cu.user_id ");
        sqlSb.append(" left join user_info pu on n.publish_user_id = pu.user_id WHERE 1=1 ");

        Map<String, Object> paramMap = Maps.newHashMap();
        if (StringUtils.isNotEmpty(param.getSearch())) {
            sqlSb.append(" AND (n.main_title LIKE :search or n.sub_title LIKE :search) ");
            paramMap.put("search", "%" + param.getSearch() + "%");
        }
        if (param.getNoticeStatus()!=null) {
            sqlSb.append(" AND n.notice_status = :noticeStatus ");
            paramMap.put("noticeStatus", param.getNoticeStatus());
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
     * @param noticeId
     * @return
     */
    @SuppressWarnings({"unchecked", "Duplicates"})
    @Override
    public NoticeInfoDto getInfoById(Long noticeId) {
        if (noticeId == null) {
            return null;
        }
        Session session = entityManager.unwrap(Session.class);
        String sqlSb = " select n.notice_id noticeId, n.main_title mainTitle, n.sub_title subTitle, n.notice_content noticeContent,  " +
                " n.notice_status noticeStatus, n.create_user_id createUserId, cu.nick_name createUserName, n.create_time createTime, " +
                " n.publish_user_id publishUserId, pu.nick_name publishUserName, n.publish_time publishTime " +
                " from notice_info n " +
                " left join user_info cu on n.create_user_id = cu.user_id " +
                " left join user_info pu on n.publish_user_id = pu.user_id WHERE n.notice_id = :noticeId ";
        NativeQuery query = session.createSQLQuery(sqlSb);
        query.addScalar("noticeId", StandardBasicTypes.LONG)
                .addScalar("mainTitle", StandardBasicTypes.STRING)
                .addScalar("subTitle", StandardBasicTypes.STRING)
                .addScalar("noticeContent", StandardBasicTypes.STRING)
                .addScalar("noticeStatus", StandardBasicTypes.INTEGER)
                .addScalar("publishUserId", StandardBasicTypes.LONG)
                .addScalar("publishUserName", StandardBasicTypes.STRING)
                .addScalar("publishTime", StandardBasicTypes.TIMESTAMP)
                .addScalar("createUserId", StandardBasicTypes.LONG)
                .addScalar("createUserName", StandardBasicTypes.STRING)
                .addScalar("createTime", StandardBasicTypes.TIMESTAMP);
        query.setParameter("noticeId", noticeId);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(NoticeInfoDto.class));
        return (NoticeInfoDto) query.uniqueResult();
    }

    /**
     * 首页获取前5条
     * @return
     */
    @SuppressWarnings({"unchecked", "Duplicates"})
    @Override
    public List<ValueLabelDto> indexGetList() {
        Session session = entityManager.unwrap(Session.class);
        NativeQuery query = session.createSQLQuery(
                " select n.notice_id value, n.main_title label from notice_info n where n.notice_status=2 order by n.create_time desc ");
        query.addScalar("value", StandardBasicTypes.LONG)
                .addScalar("label", StandardBasicTypes.STRING);
        query.setFirstResult(0);
        query.setMaxResults(5);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(ValueLabelDto.class));
        return query.list();
    }
}
