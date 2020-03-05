package com.example.practiceexam.config;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;

import java.util.Set;

/**
 * 用户角色编码
 * @author ShiQing_Chen  2020/3/5  19:40
 **/
public class RoleManager {
    /**
     * 角色列表
     */
    private static final ImmutableMap<Integer, Set<String>> ROLE_LIST = ImmutableMap.<Integer, Set<String>>builder()
            .put(1, Sets.newHashSet("admin"))
            .put(2, Sets.newHashSet("teacher"))
            .put(3, Sets.newHashSet("student"))
            .build();

    /**
     * 根据用户类型获取角色编码
     * @param type
     * @return
     */
    public static Set<String> getRoleByType(final Integer type) {
        return ROLE_LIST.getOrDefault(type, Sets.newHashSet());
    }
}
