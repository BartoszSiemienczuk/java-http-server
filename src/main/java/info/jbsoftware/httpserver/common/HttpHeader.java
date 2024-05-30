package info.jbsoftware.httpserver.common;

public record HttpHeader(String key, String value) {
    public String toString() {
        return key + ": " + value;
    }

    public String toStringLine() {
        return key + ": " + value + HttpConstants.LINE_END_CRLF;
    }
}
