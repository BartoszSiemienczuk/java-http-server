package info.jbsoftware;

import info.jbsoftware.httpserver.request.RequestParser;
import info.jbsoftware.httpserver.request.model.HttpRequest;
import info.jbsoftware.httpserver.response.HttpStatus;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class Main {
    private static final int PORT = 4221;
    private static final RequestParser parser = new RequestParser();

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket clientSocket = null;

        try {
            log.debug("Starting server on {}", PORT);
            serverSocket = new ServerSocket(PORT);
            // Since the tester restarts your program quite often, setting SO_REUSEADDR
            // ensures that we don't run into 'Address already in use' errors
            serverSocket.setReuseAddress(true);
            clientSocket = serverSocket.accept(); // Wait for connection from client.
            log.debug("accepted new connection from {}", clientSocket.getInetAddress().getHostAddress());
            final InputStream inputStream = clientSocket.getInputStream();
            final HttpRequest request = parser.parse(inputStream);
            HttpStatus status = request.path().equals("/")
                    ? HttpStatus.OK
                    : HttpStatus.NOT_FOUND;
            clientSocket.getOutputStream().write(status.toStatusLine().getBytes());
            clientSocket.getOutputStream().write("\r\n".getBytes());
            clientSocket.close();
        } catch (IOException e) {
            log.error("IOException: {}", e.getMessage());
        }
    }
}
