package com.test.util.http;

import java.util.HashMap;

/**
 * Created by wl on 2021/4/20.
 */
public class ResponseBaseResult<T> implements java.io.Serializable {

    public static ResponseBaseResult failedResponseBaseResult = new ResponseBaseResult(StausCode.FAILED,null);
    public static ResponseBaseResult successResponseBaseResult = new ResponseBaseResult(StausCode.SUCCESS,null);

    private int code;
    private T data;
    private String message;
    public ResponseBaseResult(){};

    public ResponseBaseResult(StausCode stausCode, T data){
        this(stausCode, data, null);
    }

    public ResponseBaseResult(StausCode stausCode, T data, String message){
        this.code = stausCode.getCode();
        this.data = data;
        this.message = message;

    }

    public static ResponseBaseResult successResponseBaseResult(){
        return new ResponseBaseResult(StausCode.SUCCESS,null);
    }

    public static ResponseBaseResult failedResponseBaseResult(){
        return new ResponseBaseResult(StausCode.FAILED, null);
    }



    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public ResponseBaseResult setData(T data) {
        this.data = data;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ResponseBaseResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public enum StausCode {
        SUCCESS(1, "请求成功"),
        FAILED(-1, "请求处理失败"),
        ERRORPARAMTER(-10000, "参数错误"),
        UNKONWERROR(9999, "未知错误");

        private int code;
        private String msg;
        StausCode(int code, String msg){
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }


}
