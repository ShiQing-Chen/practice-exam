package com.example.common.exception;

/**
 * 需要登录
 */
public class NeedLoginException extends RuntimeException{

    public NeedLoginException(String message) {
        super(message);
    }
}
