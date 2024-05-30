package info.jbsoftware.httpserver.response;

import info.jbsoftware.httpserver.common.HttpHeader;
import info.jbsoftware.httpserver.common.HttpHeaderKey;
import info.jbsoftware.httpserver.common.utils.HttpBodyUtils;

import java.util.List;

public class HeadersFactory {
    public static List<HttpHeader> text(final String body) {
        return List.of(
                new HttpHeader(HttpHeaderKey.CONTENT_TYPE.getKey(), "text/plain"),
                new HttpHeader(HttpHeaderKey.CONTENT_LENGTH.getKey(), contentLength(body))
        );
    }

    public static List<HttpHeader> bytes(final byte[] body, final String contentType) {
        return List.of(
                new HttpHeader(HttpHeaderKey.CONTENT_TYPE.getKey(), contentType),
                new HttpHeader(HttpHeaderKey.CONTENT_LENGTH.getKey(), String.valueOf(body.length))
        );
    }

    public static String contentLength(final String body) {
        return String.valueOf(HttpBodyUtils.calculateContentLength(body));
    }
}
