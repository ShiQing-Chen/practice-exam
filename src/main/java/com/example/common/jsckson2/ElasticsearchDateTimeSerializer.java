package com.example.common.jsckson2;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author FJK  2019-01-17 4:19 PM
 */
public class ElasticsearchDateTimeSerializer extends JsonSerializer<Date> {

    @Override
    public void serialize(Date date, JsonGenerator jgen, SerializerProvider arg2)
            throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        mapper.writeValue(jgen, sdf.format(date));
    }
}
