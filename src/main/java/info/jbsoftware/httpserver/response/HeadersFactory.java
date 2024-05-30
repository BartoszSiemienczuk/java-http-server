package info.jbsoftware.httpserver.response;

import info.jbsoftware.httpserver.common.HttpHeader;
import info.jbsoftware.httpserver.common.HttpHeaderKey;

import java.util.List;
import java.util.Optional;

public class HeadersFactory {
    public static List<HttpHeader> text(final String body) {
        return List.of(
                new HttpHeader(HttpHeaderKey.CONTENT_TYPE.getKey(), "text/plain"),
                new HttpHeader("Content-Length", contentLength(body))
        );
    }

    private static String contentLength(final String body) {
        return Optional.ofNullable(body)
                .map(String::getBytes)
                .map(bytes -> bytes.length)
                .map(String::valueOf)
                .orElse("0");
    }
}
