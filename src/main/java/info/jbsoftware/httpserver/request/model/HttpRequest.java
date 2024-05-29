package info.jbsoftware.httpserver.request.model;

public record HttpRequest(String method, String path, String body) {
}

