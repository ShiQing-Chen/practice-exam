package com.example.common.exception;

/**
 * 未被初始化
 * @author HanHongmin 2019-07-12
 */
public class UninitializedException extends RuntimeException{

    public UninitializedException(String message) {
        super(message);
    }
}
