package info.jbsoftware.controllers;

import info.jbsoftware.httpserver.common.HttpHeader;
import info.jbsoftware.httpserver.common.HttpHeaderKey;
import info.jbsoftware.httpserver.request.model.HttpRequest;
import info.jbsoftware.httpserver.response.HttpResponseFactory;
import info.jbsoftware.httpserver.response.model.HttpResponse;

public class UserAgentController {
    public HttpResponse userAgentEndpoint(final HttpRequest request) {
        final String userAgent = request.headers().stream()
                .filter(header -> HttpHeaderKey.USER_AGENT.getKey().equalsIgnoreCase(header.key()))
                .findFirst()
                .map(HttpHeader::value)
                .orElse("N/A");
        return HttpResponseFactory.ok(userAgent);
    }

}
