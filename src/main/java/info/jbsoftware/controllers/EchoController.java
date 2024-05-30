package info.jbsoftware.controllers;

import info.jbsoftware.httpserver.request.model.HttpRequest;
import info.jbsoftware.httpserver.response.HttpResponseFactory;
import info.jbsoftware.httpserver.response.model.HttpResponse;

public class EchoController {
    public HttpResponse echo(final HttpRequest request) {
        final String path = request.path();
        final String param = path.substring(path.lastIndexOf('/') + 1);
        return HttpResponseFactory.ok(param);
    }
}
