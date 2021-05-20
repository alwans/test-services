package com.test.util.http;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by wl on 2021/4/7.
 */
public class ParameterConverter {
    public static String convert2URL(Map<String, String> parameters) {
        return convert2URL(parameters, null, true);
    }

    public static String convert2URL(Map<String, String> parameters, boolean isNeedURLEncode) {
        return convert2URL(parameters, null, isNeedURLEncode);
    }

    public static String convert2URL(Map<String, String> parameters, String decodeCharset, boolean isNeedURLEncode) {
        if (parameters != null && parameters.size() > 0) {
            StringBuilder query = new StringBuilder();
            for (Iterator<Map.Entry<String, String>> itr = parameters.entrySet().iterator(); itr.hasNext();) {
                Map.Entry<String, String> param = itr.next();
                try {
                    String paramKey = isNeedURLEncode ? URLEncoder.encode(param.getKey(), StringUtils.isBlank(decodeCharset) ? HttpConst.Charset.UTF_8 : decodeCharset) : param.getKey();
                    String paramValue = isNeedURLEncode ? URLEncoder.encode(param.getValue(), StringUtils.isBlank(decodeCharset) ? HttpConst.Charset.UTF_8 : decodeCharset) : param.getValue();
                    query.append(paramKey)
                            .append("=")
                            .append(paramValue);
                } catch (UnsupportedEncodingException e) {
                    new RuntimeException(e.getMessage(), e);
                }
                if (itr.hasNext()) {
                    query.append("&");
                }
            }
            return query.toString();
        }
        return null;
    }

    public static List<BasicNameValuePair> convert2ValuePair(Map<String, String> parameters) {
        if (parameters != null && parameters.size() > 0){
            List<BasicNameValuePair> valuePairs = new ArrayList<BasicNameValuePair>();
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                valuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            return valuePairs;
        }
        return null;
    }
}

