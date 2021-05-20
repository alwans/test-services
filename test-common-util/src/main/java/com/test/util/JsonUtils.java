package com.test.util;

/**
 * Created by wl on 2021/4/25.
 */
import com.alibaba.fastjson.JSON;

public class JsonUtils {

    public static Object objectConverion(Object baseObect, Class clazz){
        String s1 = JSON.toJSONString(baseObect);
        return JSON.parseObject(s1, clazz);
    }
}
