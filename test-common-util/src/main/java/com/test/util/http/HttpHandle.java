package com.test.util.http;

import java.util.Map;



/**
 * Created by wl on 2021/4/7.
 */
public interface HttpHandle {


    /**
     * 发送一个https的GET请求
     * @param url https访问连接
     * @param textParams GET请求时的参数集
     * @return 远程响应正文
     */
    public HttpResult sendSSLGetRequest(String url, Map<String, String> textParams);


    /**
     * 发送一个https的GET请求
     * @param url https访问连接
     * @param requestHeaders 请求头
     * @param textParams GET请求时的参数集
     * @param decodeCharset 解码字符集,用于解析响应数据时,若为NULL默认UTF-8解码
     * @return 远程响应正文
     */
    public HttpResult sendSSLGetRequest(String url, Map<String, String> requestHeaders, Map<String, String> textParams, String decodeCharset);

    /**
     * 发送一个http的GET请求
     * @param url http访问连接
     * @param textParams GET请求时的参数集
     * @return 远程响应正文
     */
    public HttpResult sendGetRequest(String url, Map<String, String> textParams);

    public HttpResult sendGetRequest(String url);

    /**
     * 发送一个http的GET请求
     * @param url https访问连接
     * @param requestHeaders 请求头
     * @param textParams GET请求时的参数集
     * @param decodeCharset 解码字符集,用于解析响应数据时,若为NULL默认UTF-8解码
     * @param isSSL 是否是https请求
     * @return 远程响应正文
     */
    public HttpResult sendGetRequest(String url, Map<String, String> requestHeaders, Map<String, String> textParams, String decodeCharset, boolean isSSL, boolean isNeesURLEncode);

    /**
     * 发送一个https的POST请求
     * @param url https访问连接
     * @param bodyParams GET请求时的参数集
     * @return 远程响应正文
     */
    public HttpResult sendSSLPostRequest(String url, Map<String, String> bodyParams);

    /**
     * 发送一个https的POST请求
     * @param url https访问连接
     * @param requestHeaders 请求头
     * @param bodyParams GET请求时的参数集
     * @param decodeCharset 解码字符集,用于解析响应数据时,若为NULL默认UTF-8解码
     * @return 远程响应正文
     */
    public HttpResult sendSSLPostRequest(String url, Map<String, String> requestHeaders, Map<String, String> bodyParams, String decodeCharset);

    /**
     * 发送一个http的POST请求
     * @param url http访问连接
     * @param bodyParams GET请求时的参数集
     * @return 远程响应正文
     */
    public HttpResult sendPostRequest(String url, Map<String, String> bodyParams);

    /**
     * 发送一个http的POST请求
     * @param url http访问连接
     * @param requestHeaders 请求头
     * @param bodyParams GET请求时的参数集
     * @param decodeCharset 解码字符集,用于解析响应数据时,若为NULL默认UTF-8解码
     * @param isSSL 是否是https请求
     * @return 远程响应正文
     */
    public HttpResult sendPostRequest(String url, Map<String, String> requestHeaders, Map<String, String> bodyParams, String decodeCharset, boolean isSSL);

    /**
     * 发送一个https的POST请求
     * @param url
     * @param paramXmlStr
     * @return
     */
    public HttpResult sendSSLPostRequestWithXml(String url, String paramXmlStr);

    public HttpResult sendSSLPostRequestWithJSON(String url, Object paramObj);

    public HttpResult sendSSLPostRequestWithJSON(String url, Map<String, String> requestHeaders, Object paramObj, String decodeCharset);

    public HttpResult sendPostRequestWithJSON(String url, Object paramObj);

    /**
     *
     * @param url
     * @param paramObj
     * @param connectionTimeout		连接超时(ms)
     * @param sendDataTimeout		数据传输超时(ms)
     */
    public HttpResult sendPostRequestWithJSON(String url, Object paramObj, int connectionTimeout, int sendDataTimeout);

    public HttpResult sendPostRequestWithJSON(String url, Map<String, String> requestHeaders, Object paramObj, String decodeCharset, boolean isSSL);

    /**
     *
     * @param url
     * @param requestHeaders
     * @param paramObj
     * @param decodeCharset
     * @param isSSL
     * @param connectionTimeout		连接超时(ms)
     * @param sendDataTimeout		数据传输超时(ms)
     * @return
     */
    public HttpResult sendPostRequestWithJSON(String url, Map<String, String> requestHeaders, Object paramObj, String decodeCharset, boolean isSSL, int connectionTimeout, int sendDataTimeout);

    public HttpResult sendSSLPutRequest(String url, Map<String, String> bodyParams);

    public HttpResult sendSSLPutRequest(String url, Map<String, String> requestHeaders, Map<String, String> bodyParams, String decodeCharset);

    public HttpResult sendPutRequest(String url, Map<String, String> bodyParams);

    public HttpResult sendPutRequest(String url, Map<String, String> requestHeaders, Map<String, String> bodyParams, String decodeCharset, boolean isSSL);

    public HttpResult sendSSLPutRequestWithJSON(String url, Object paramObj);

    public HttpResult sendSSLPutRequestWithJSON(String url, Map<String, String> requestHeaders, Object paramObj, String decodeCharset);

    public HttpResult sendPutRequestWithJSON(String url, Object paramObj);

    public HttpResult sendPutRequestWithJSON(String url, Map<String, String> requestHeaders, Object paramObj, String decodeCharset, boolean isSSL);
}
