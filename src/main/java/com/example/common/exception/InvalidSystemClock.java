package com.example.common.exception;

/**
 * 系统时钟错误，用于系统时钟回退引起的ID 生成策略混乱
 */
public class InvalidSystemClock extends RuntimeException{

    public InvalidSystemClock(String message) {
        super(message);
    }
}
