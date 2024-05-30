package info.jbsoftware;

import info.jbsoftware.httpserver.HttpServer;
import info.jbsoftware.httpserver.common.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
    private static final int PORT = 4221;

    public static void main(String[] args) {
        if (args.length > 1) {
            log.debug("Setting file path to: {}", args[1]);
            FileUtils.FILE_PATH = args[1];
        }
        final HttpServer server = new HttpServer(PORT);
        server.startListening();
    }
}
