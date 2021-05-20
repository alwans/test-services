package com.test.util.http;

/**
 * Created by wl on 2021/4/7.
 */
public class HttpConst {
    public class Charset {

        public static final String UTF_8 = "UTF-8";

    }

    public class Https {

        public static final String SCHEME = "https";

        public static final int DEFAULT_PORT = 443;

        public static final String PROTOCOL = "TLS";

    }

    public class Http {

        public static final String SCHEME = "http";

        public static final int DEFAULT_PORT = 80;

    }

    public class Method {

        public static final String METHOD_DELETE = "DELETE";

        public static final String METHOD_GET = "GET";

        public static final String METHOD_HEAD = "HEAD";

        public static final String METHOD_OPTIONS = "OPTIONS";

        public static final String METHOD_POST = "POST";

        public static final String METHOD_PUT = "PUT";

        public static final String METHOD_TRACE = "TRACE";

    }

    public class ContentType {

        public static final String MULTIPART = "multipart/form-data;";

        public static final String FORM = "application/x-www-form-urlencoded";

        public static final String JSON = "application/json";

        public static final String XML = "text/xml";

    }

    public class Header {

        public static final String ACCEPT = "Accept";

        public static final String ACCEPT_CHARSET = "Accept-Charset";

        public static final String ACCEPT_ENCODING = "Accept-Encoding";

        public static final String AUTHORIZATION = "Authorization";

        public static final String CACHE_CONTROL = "Cache-Control";

        public static final String CONTENT_ENCODING = "Content-Encoding";

        public static final String CONTENT_LENGTH = "Content-Length";

        public static final String CONTENT_TYPE = "Content-Type";

        public static final String DATE = "Date";

        public static final String ETAG = "ETag";

        public static final String EXPIRES = "Expires";

        public static final String IF_NONE_MATCH = "If-None-Match";

        public static final String LAST_MODIFIED = "Last-Modified";

        public static final String LOCATION = "Location";

        public static final String SERVER = "Server";

        public static final String USER_AGENT = "User-Agent";

        public static final String CHARSET = "charset";

    }
}