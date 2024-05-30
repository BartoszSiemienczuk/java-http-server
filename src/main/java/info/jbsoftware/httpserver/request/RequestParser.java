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

public class RequestParser {

    public HttpRequest parse(final InputStream inputStream) throws IOException {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        final String[] requestLineParts = getRequestLineParts(reader);
        final String method = requestLineParts[0];
        final String path = requestLineParts[1];

        // Iterate to skip headers (until an empty line is found)
        String line;
        final List<HttpHeader> headers = new ArrayList<>();
        while ((line = reader.readLine()) != null && !line.trim().isEmpty()) {
            final String[] headerParts = line.split(":");
            if (headerParts.length < 2) {
                throw new IOException("Invalid header line");
            }
            headers.add(new HttpHeader(headerParts[0].trim(), headerParts[1].trim()));
        }

        // Read the body, if there's any
//        final StringBuilder bodyBuilder = new StringBuilder();
//        while ((line = reader.readLine()) != null) {
//            if (line.trim().isEmpty()) {
//                break;
//            }
//            bodyBuilder.append(line).append(System.lineSeparator());
//        }
//        final String body = bodyBuilder.toString().trim();

        // Build and return the HttpRequest object
        return new HttpRequest(method, path, headers, StringUtils.EMPTY);
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
}
