package com.test.util.http;

import com.test.util.http.client.HttpClientHandle;

/**
 * Created by wl on 2021/4/22.
 */
public class HttpHandleFactory {
    public static HttpHandle getDefaultHandle() {
        try {
            Class.forName("org.apache.http.client.HttpClient");
            return new HttpClientHandle();
        } catch (Exception e) {
        }
        return null;
    }
}
