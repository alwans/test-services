package com.services.core.http;

import com.test.util.http.ResponseBaseResult;

/**
 * web列表页面需要这个
 * @param <T>
 */
public class WebResponseBaseResult<T> extends ResponseBaseResult {

    private T page;

    public T getPage() {
        return page;
    }

    public void setPage(T page) {
        this.page = page;
    }

    public WebResponseBaseResult(){super();};
}
