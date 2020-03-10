package com.example.practiceexam.dao.Impl;

import com.example.practiceexam.dao.ArticleInfoDaoCustom;
import com.example.practiceexam.dto.ArticleDto;
import com.example.practiceexam.dto.MaterialDto;
import com.example.practiceexam.param.SearchArticleParam;
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
 * @author ShiQing_Chen  2020/3/11  01:06
 **/
@Repository
public class ArticleInfoDaoImpl implements ArticleInfoDaoCustom {
    @PersistenceContext
    private EntityManager entityManager;


    /**
     * 分页查询
     * @param param
     * @return
     */
    @SuppressWarnings({"unchecked", "Duplicates"})
    @Override
    public List<ArticleDto> getListByPage(SearchArticleParam param) {
        if (param == null) {
            return Lists.newArrayList();
        }
        StringBuilder sqlSb = new StringBuilder();
        sqlSb.append(" select n.article_id articleId, n.article_title articleTitle, n.article_type articleType, ");
        sqlSb.append(" n.create_user_id createUserId, cu.nick_name createUserName, n.create_time createTime ");
        sqlSb.append(" from article_info n ");
        sqlSb.append(" left join user_info cu on n.create_user_id = cu.user_id WHERE 1=1");

        Map<String, Object> paramMap = Maps.newHashMap();
        if (StringUtils.isNotEmpty(param.getSearch())) {
            sqlSb.append(" AND n.article_title LIKE :search ");
            paramMap.put("search", "%" + param.getSearch() + "%");
        }
        if (!CollectionUtils.isEmpty(param.getArticleType())) {
            sqlSb.append(" AND n.article_type in(:articleType) ");
            paramMap.put("articleType", param.getArticleType());
        }
        if ("desc".equals(param.getOrder()) && StringUtils.isNotBlank(param.getSort())) {
            sqlSb.append(" order by ").append(param.getSort()).append(" desc");
        } else if ("asc".equals(param.getOrder()) && StringUtils.isNotBlank(param.getSort())) {
            sqlSb.append(" order by ").append(param.getSort()).append(" asc");
        }
        Session session = entityManager.unwrap(Session.class);
        NativeQuery query = session.createSQLQuery(sqlSb.toString());
        query.addScalar("articleId", StandardBasicTypes.LONG)
                .addScalar("articleTitle", StandardBasicTypes.STRING)
                .addScalar("articleType", StandardBasicTypes.INTEGER)
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
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(ArticleDto.class));
        return query.list();
    }

    /**
     * 分页查询
     * @param param
     * @return
     */
    @SuppressWarnings({"unchecked", "Duplicates"})
    @Override
    public Integer getCountByPage(SearchArticleParam param) {
        if (param == null) {
            return 0;
        }
        StringBuilder sqlSb = new StringBuilder();
        sqlSb.append(" SELECT COUNT(*) count ");
        sqlSb.append(" from article_info n ");
        sqlSb.append(" left join user_info cu on n.create_user_id = cu.user_id WHERE 1=1");

        Map<String, Object> paramMap = Maps.newHashMap();
        if (StringUtils.isNotEmpty(param.getSearch())) {
            sqlSb.append(" AND n.article_title LIKE :search ");
            paramMap.put("search", "%" + param.getSearch() + "%");
        }
        if (!CollectionUtils.isEmpty(param.getArticleType())) {
            sqlSb.append(" AND n.article_type in(:articleType) ");
            paramMap.put("articleType", param.getArticleType());
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
