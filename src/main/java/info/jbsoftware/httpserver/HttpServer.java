package info.jbsoftware.httpserver;

import info.jbsoftware.controllers.EchoController;
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

@Builder
@RequiredArgsConstructor
@Slf4j
public class HttpServer {
    private final int port;
    private final RequestParser parser = new RequestParser();
    private final HttpResponseByteWriter writer = new HttpResponseByteWriter();
    private final EchoController echoController = new EchoController();

    public void startListening() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            serverSocket.setReuseAddress(true);
            try (Socket clientSocket = serverSocket.accept()) {
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
                    default -> HttpResponseFactory.notFound();
                };
                clientOutput.write(writer.writeBytes(response));
                log.debug("Written response bytes");
            }
        } catch (IOException e) {
            log.error("IOException: {}", e.getMessage());
        }
    }
}
