package info.jbsoftware.httpserver.response.model;

import info.jbsoftware.httpserver.common.HttpHeader;

import java.util.List;

public record HttpResponse(HttpStatus status, List<HttpHeader> headers, String body) {
}
