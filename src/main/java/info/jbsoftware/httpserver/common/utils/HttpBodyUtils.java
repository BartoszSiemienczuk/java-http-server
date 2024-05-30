package info.jbsoftware.httpserver.common.utils;

import java.util.Optional;

public class HttpBodyUtils {
    public static int calculateContentLength(final String body) {
        return Optional.ofNullable(body)
                .map(String::getBytes)
                .map(bytes -> bytes.length)
                .orElse(0);
    }
}
