package pl.rstepniewski.sockets.controller.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by rafal on 09.06.2023
 *
 * @author : rafal
 * @date : 09.06.2023
 * @project : Battleship
 */
public class ServerService {
    private static final int PORT = 6767;
    private Socket clientSocket;
    ServerSocket serverSocket;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;
    private static final Logger LOGGER = LogManager.getLogger(ServerService.class);

    public ServerService() {
        LOGGER.info("Starting Battleship application");
        startServer();
        LOGGER.info("Starting server");
    }

    private void startServer() {
        try {
            startSocket();
            handleClient();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void startSocket() throws IOException {
        serverSocket = new ServerSocket(PORT);
        clientSocket = serverSocket.accept();
    }

    private void handleClient() {
        try {
            startBufferedStreams();
        } catch (IOException e) {
            LOGGER.error("Client connection closed unexpectedly: " + e.getMessage());
            closeConnection();
        }
    }

    private void startBufferedStreams() throws IOException {
        bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
    }

    public void closeConnection() {
        try {
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
            }
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (printWriter != null) {
                printWriter.close();
            }
        } catch (IOException e) {
            LOGGER.error("Error while closing the connection: " + e.getMessage());
        }
    }

    public PrintWriter getPrintWriter() {
        return printWriter;
    }

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

}
