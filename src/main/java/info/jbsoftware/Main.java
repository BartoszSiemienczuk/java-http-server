package info.jbsoftware;

import info.jbsoftware.httpserver.HttpServer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
    private static final int PORT = 4221;

    public static void main(String[] args) {
        final HttpServer server = new HttpServer(PORT);
        server.startListening();
    }
}
