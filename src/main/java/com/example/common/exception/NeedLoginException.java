package com.example.common.exception;

/**
 * 需要登录
 * @author HanHongmin 2019-07-12
 */
public class NeedLoginException extends RuntimeException{

    public NeedLoginException(String message) {
        super(message);
    }
}
