package info.jbsoftware.httpserver.request;

import info.jbsoftware.httpserver.request.model.HttpRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RequestParser {

    public HttpRequest parse(final InputStream inputStream) throws IOException {
        final String[] requestLineParts = getLines(inputStream);
        final String method = requestLineParts[0];
        final String path = requestLineParts[1];

        // Iterate to skip headers (until an empty line is found)
//        String line;
//        while ((line = reader.readLine()) != null && !line.trim().isEmpty()) {
//            // You can handle headers here if needed
//        }

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
        return new HttpRequest(method, path, "");
    }

    private String[] getLines(final InputStream inputStream) throws IOException {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        // Read the request line
        final String requestLine = reader.readLine();
        if (requestLine == null || requestLine.isEmpty()) {
            throw new IOException("Invalid request line");
        }

        // Split the request line into method and path
        final String[] requestLineParts = requestLine.split(" ");
        if (requestLineParts.length < 2) {
            throw new IOException("Invalid request line format");
        }
        return requestLineParts;
    }
}
