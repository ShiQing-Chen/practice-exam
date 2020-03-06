package com.example.common.jsckson2;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.util.Date;

/**
 * jackson2的工厂类
 * @author HanHongmin
 * @since 0.0.1
 */
public class ObjectMapperFactory {

    private ObjectMapperFactory() {
        //empty
    }

    public static ObjectMapper getSimpleMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
        SimpleModule sm = new SimpleModule();
        sm.addSerializer(Date.class,new DateTimeSerializer());
        mapper.registerModule(sm);
        return mapper;
    }

    /**
     * Elasticsearch 专用
     * @return ObjectMapper
     */
    public static ObjectMapper getElasticsearchMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
        SimpleModule sm = new SimpleModule();
        sm.addSerializer(Date.class,new ElasticsearchDateTimeSerializer());
        mapper.registerModule(sm);
        return mapper;
    }
}
