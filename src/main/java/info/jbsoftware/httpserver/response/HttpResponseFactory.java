package info.jbsoftware.httpserver.response;

import info.jbsoftware.httpserver.response.model.HttpResponse;
import info.jbsoftware.httpserver.response.model.HttpStatus;

public class HttpResponseFactory {
    public static HttpResponse ok() {
        return new HttpResponse(HttpStatus.OK, HeadersFactory.text(null), null);
    }

    public static HttpResponse ok(final String body) {
        return new HttpResponse(HttpStatus.OK, HeadersFactory.text(body), body);
    }

    public static HttpResponse notFound() {
        return new HttpResponse(HttpStatus.NOT_FOUND, HeadersFactory.text(null), null);
    }
}
