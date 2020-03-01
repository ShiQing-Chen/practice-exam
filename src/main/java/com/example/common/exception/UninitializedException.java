package com.example.common.exception;

/**
 * 未被初始化
 */
public class UninitializedException extends RuntimeException{

    public UninitializedException(String message) {
        super(message);
    }
}
