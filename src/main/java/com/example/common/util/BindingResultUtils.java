package com.example.common.util;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ShiQing_Chen  2020/3/6  20:05
 **/
public class BindingResultUtils {

    /**
     * 获取表单错误结果
     * @param bindingResult 表单校验结果
     * @return 表单校验错误结果
     */
    public static String getErrorString(BindingResult bindingResult) {
        if (bindingResult != null) {
            Map<String, String> errors = bindingResult.getFieldErrors().stream()
                    .filter(e -> StringUtils.isNotBlank(e.getDefaultMessage()))
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
            if (!CollectionUtils.isEmpty(errors)) {
                return StringUtils.join(Lists.newArrayList(errors.values()), ",");
            }
        }
        return "";
    }
}
