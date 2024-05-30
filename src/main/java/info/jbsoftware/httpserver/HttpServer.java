package info.jbsoftware.httpserver;

import info.jbsoftware.controllers.EchoController;
import info.jbsoftware.controllers.FilesController;
import info.jbsoftware.controllers.UserAgentController;
import info.jbsoftware.httpserver.request.RequestParser;
import info.jbsoftware.httpserver.request.model.HttpRequest;
import info.jbsoftware.httpserver.response.HttpResponseByteWriter;
import info.jbsoftware.httpserver.response.HttpResponseFactory;
import info.jbsoftware.httpserver.response.model.HttpResponse;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Builder
@RequiredArgsConstructor
@Slf4j
public class HttpServer {
    private final int port;
    private final RequestParser parser = new RequestParser();
    private final HttpResponseByteWriter writer = new HttpResponseByteWriter();
    private final EchoController echoController = new EchoController();
    private final UserAgentController userAgentController = new UserAgentController();
    private final FilesController filesController = new FilesController();
    private final ExecutorService executorService = Executors.newFixedThreadPool(32);

    public void startListening() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            serverSocket.setReuseAddress(true);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                executorService.submit(handler(clientSocket));
            }
        } catch (IOException e) {
            log.error("IOException: {}", e.getMessage());
        }
    }

    private Runnable handler(final Socket clientSocket) {
        return () -> {
            try {
                handleRequest(clientSocket);
            } catch (IOException e) {
                log.error("Error", e);
                throw new RuntimeException(e);
            } finally {
                try {
                    if (!clientSocket.isClosed()) {
                        clientSocket.close();
                    }
                } catch (IOException e) {
                    log.error("Error closing socket.", e);
                }
            }
        };
    }

    private void handleRequest(final Socket clientSocket) throws IOException {
        log.debug("accepted new connection from {}", clientSocket.getInetAddress().getHostAddress());
        final InputStream clientInput = clientSocket.getInputStream();
        final OutputStream clientOutput = clientSocket.getOutputStream();
        final HttpRequest request = parser.parse(clientInput);
        log.debug("parsed request");
        final String path = request.path();
        final String[] split = path.split("/");
        final String resource = split.length > 1 ? split[1] : "";
        final HttpResponse response = switch (resource) {
            case "" -> HttpResponseFactory.ok();
            case "echo" -> echoController.echo(request);
            case "user-agent" -> userAgentController.userAgentEndpoint(request);
            case "files" -> filesController.filesEndpoint(request);
            default -> HttpResponseFactory.notFound();
        };
        clientOutput.write(writer.writeBytes(response));
        log.debug("Written response bytes");
    }
}
