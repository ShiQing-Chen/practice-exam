package com.example.practiceexam.config;

import com.example.common.cache.SharedUser;

import java.util.concurrent.ConcurrentHashMap;

public class OnlineUserManager {
    private static final ConcurrentHashMap<Long, SharedUser> USER_CLIENTS = new ConcurrentHashMap<Long, SharedUser>();

    public static boolean addUserClient(Long userId, SharedUser sharedUser) {
        if (userId == null || sharedUser != null) {
            return false;
        }
        USER_CLIENTS.put(userId, sharedUser);
        return true;
    }

    public static boolean removeUserClient(Long userId, SharedUser sharedUser) {
        if (userId == null || sharedUser != null) {
            return false;
        }
        USER_CLIENTS.remove(userId, sharedUser);
        return true;
    }

    public static SharedUser getUserClient(Long userId) {
        if (userId == null) {
            return null;
        }
        return USER_CLIENTS.get(userId);
    }
}
