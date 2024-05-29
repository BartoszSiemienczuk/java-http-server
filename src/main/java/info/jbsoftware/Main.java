package info.jbsoftware;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class Main {
    private static final int PORT = 4221;

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
            log.debug("accepted new connection");
            final String okStatusLine = "HTTP/1.1 200 OK\r\n\r\n";
            clientSocket.getOutputStream().write(okStatusLine.getBytes());
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }
}
