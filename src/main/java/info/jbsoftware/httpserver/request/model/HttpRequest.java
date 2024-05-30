package info.jbsoftware.httpserver.request.model;

import info.jbsoftware.httpserver.common.HttpHeader;

import java.util.List;

public record HttpRequest(String method, String path, List<HttpHeader> headers, String body) {
}

