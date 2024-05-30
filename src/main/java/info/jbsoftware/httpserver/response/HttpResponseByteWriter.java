package info.jbsoftware.httpserver.response;

import info.jbsoftware.httpserver.response.model.HttpResponse;

import java.util.Optional;

import static info.jbsoftware.httpserver.common.HttpConstants.LINE_END_CRLF;

public class HttpResponseByteWriter {

    public byte[] writeBytes(final HttpResponse response) {
        final StringBuilder builder = new StringBuilder();

        status(response, builder);
        headers(response, builder);
        body(response, builder);

        return builder.toString().getBytes();
    }

    private void status(final HttpResponse response, final StringBuilder builder) {
        builder.append(response.status().toStatusLine());
    }

    private void headers(final HttpResponse response, final StringBuilder builder) {
        for (final var header : response.headers()) {
            builder.append(header.toStringLine());
        }
        builder.append(LINE_END_CRLF);
    }

    private void body(final HttpResponse response, final StringBuilder builder) {
        Optional.ofNullable(response.body())
                .ifPresent(builder::append);
    }
}
