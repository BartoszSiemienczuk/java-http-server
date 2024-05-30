package info.jbsoftware.httpserver.request;

import info.jbsoftware.httpserver.common.HttpHeader;
import info.jbsoftware.httpserver.request.model.HttpRequest;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static info.jbsoftware.httpserver.common.HttpHeaderKey.CONTENT_LENGTH;

public class RequestParser {

    public HttpRequest parse(final InputStream inputStream) throws IOException {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        final String[] requestLineParts = getRequestLineParts(reader);
        final String method = requestLineParts[0];
        final String path = requestLineParts[1];

        // Iterate to skip headers (until an empty line is found)
        final List<HttpHeader> headers = headers(reader);

        // Read the body, if there's any
        final int contentLength = findContentLength(headers);
        final String body = body(reader, contentLength);

        // Build and return the HttpRequest object
        return new HttpRequest(method, path, headers, body);
    }

    private String[] getRequestLineParts(final BufferedReader reader) throws IOException {
        // Read the request line
        final String requestLine = reader.readLine();
        if (requestLine == null || requestLine.isEmpty()) {
            throw new IOException("Invalid request line");
        }

        // Split the request line into method and path
        final String[] requestLineParts = requestLine.split(StringUtils.SPACE);
        if (requestLineParts.length < 2) {
            throw new IOException("Invalid request line");
        }
        return requestLineParts;
    }

    private List<HttpHeader> headers(final BufferedReader reader) throws IOException {
        String line;
        final List<HttpHeader> headers = new ArrayList<>();
        while ((line = reader.readLine()) != null && !line.trim().isEmpty()) {
            final String[] headerParts = line.split(":");
            if (headerParts.length < 2) {
                throw new IOException("Invalid header line");
            }
            headers.add(new HttpHeader(headerParts[0].trim(), headerParts[1].trim()));
        }
        return headers;
    }

    private int findContentLength(final List<HttpHeader> headers) {
        return headers.stream()
                .filter(header -> CONTENT_LENGTH.getKey().equalsIgnoreCase(header.key()))
                .map(header -> Integer.parseInt(header.value()))
                .findFirst()
                .orElse(-1);

    }

    private String body(final BufferedReader reader, final int contentLength) throws IOException {
        if (contentLength <= 0) {
            return ""; // No body
        }

        char[] charBuffer = new char[contentLength];
        int bytesRead = reader.read(charBuffer, 0, contentLength);
        return new String(charBuffer, 0, bytesRead);
    }
}
