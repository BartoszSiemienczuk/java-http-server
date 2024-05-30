package info.jbsoftware.httpserver.response;

import info.jbsoftware.httpserver.response.model.HttpResponse;
import info.jbsoftware.httpserver.response.model.HttpStatus;

import java.util.List;

public class HttpResponseFactory {
    public static HttpResponse ok() {
        return new HttpResponse(HttpStatus.OK, HeadersFactory.text(null), null);
    }

    public static HttpResponse ok(final String body) {
        return new HttpResponse(HttpStatus.OK, HeadersFactory.text(body), body);
    }

    public static HttpResponse created() {
        return new HttpResponse(HttpStatus.CREATED, List.of(), null);
    }

    public static HttpResponse okBytes(final byte[] body, final String contentType) {
        return new HttpResponse(HttpStatus.OK, HeadersFactory.bytes(body, contentType), new String(body));
    }

    public static HttpResponse notFound() {
        return new HttpResponse(HttpStatus.NOT_FOUND, HeadersFactory.text(null), null);
    }

    public static HttpResponse error(final String message) {
        return new HttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, HeadersFactory.text(message), message);
    }
}
