package com.test.util.http;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by wl on 2021/4/7.
 */
public class HttpResult {
    private Integer code;
    private String body;

    public HttpResult(Integer code, String body) {
        this.code = code;
        this.body = body;
    }

    public JSONObject getBody4Json(){
        return JSON.parseObject(this.getBody());
    }

    public ResponseBaseResult getBody4BaseResult(){
        if(this.getBody() != null){
            return JSON.parseObject(this.getBody(), ResponseBaseResult.class);
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
