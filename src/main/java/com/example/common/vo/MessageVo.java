package com.example.common.vo;

/**
 * 接口请求返回的结果信息
 */
public class MessageVo {
    private boolean success;// 处理是否成功

    private String message = "成功";// 附加消息, 如处理结果失败时的原因等

    private Object data;// 可以附带返回一些结果数据

    public MessageVo() {
        //default
    }

    public MessageVo(boolean success) {
        this(success, null);
    }

    public MessageVo(boolean success, String message) {
        this(success, message, null);
    }

    public MessageVo(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public MessageVo(boolean success, Object data) {
        this.success = success;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static MessageVo fail() {
        return new MessageVo(false);
    }

    public static MessageVo fail(String message) {
        return new MessageVo(false, message);
    }

    public static MessageVo fail(String message, Object data) {
        return new MessageVo(false, message, data);
    }

    public static MessageVo success() {
        return new MessageVo(true);
    }

    public static MessageVo success(String message) {
        return new MessageVo(true, message);
    }

    public static MessageVo success(String message, Object data) {
        return new MessageVo(true, message, data);
    }

    public static MessageVo success(Object data) {
        return new MessageVo(true, "成功", data);
    }
}
