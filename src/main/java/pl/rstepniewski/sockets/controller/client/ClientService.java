package pl.rstepniewski.sockets.controller.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by rafal on 09.06.2023
 *
 * @author : rafal
 * @date : 09.06.2023
 * @project : Battleship
 */
public class ClientService {
    public static final int PORT = 6767;
    public static final String LOCALHOST = "localhost";
    private static final Logger LOGGER = LogManager.getLogger(ClientService.class);
    PrintWriter printWriter;
    BufferedReader bufferedReader;
    private Socket socket;

    public ClientService() {
        startClient();
        LOGGER.info("Starting Battleship application");
    }

    private static Socket connectToServer() {
        Socket socket;
        try {
            socket = new Socket(LOCALHOST, PORT);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        LOGGER.info("Client connected to the server");
        return socket;
    }

    private void startClient() {
        try {
            socket = connectToServer();
            startBufferedStreams(socket);
            LOGGER.info("Client is ready to play");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void startBufferedStreams(Socket socket) throws IOException {
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        printWriter = new PrintWriter(socket.getOutputStream(), true);
        LOGGER.info("bufferedReader and printWriter created");
    }

    public void disconnectFromServer() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
                LOGGER.info("Disconnected from the server");
            }

            if (printWriter != null) {
                printWriter.close();
            }

            if (bufferedReader != null) {
                bufferedReader.close();
            }
        } catch (IOException e) {
            LOGGER.error("Error while disconnecting from the server: " + e.getMessage());
        }
    }

    public PrintWriter getPrintWriter() {
        return printWriter;
    }

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }
}
