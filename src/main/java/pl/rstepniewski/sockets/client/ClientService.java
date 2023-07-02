package pl.rstepniewski.sockets.client;

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
    private Socket socket;
    PrintWriter printWriter;
    BufferedReader bufferedReader;
    private static final Logger logger = LogManager.getLogger(ClientService.class);

    public ClientService() {
        startClient();
        logger.info("Starting Battleship application");
    }

    private void startClient() {
        try{
            socket = connectToServer();
            startBufferedStreams(socket);
            logger.info("Client is ready to play");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void startBufferedStreams(Socket socket) throws IOException {
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        printWriter = new PrintWriter(socket.getOutputStream(), true);
        logger.info("bufferedReader and printWriter created");
    }

    private static Socket connectToServer() {
        Socket socket = null;
        try{
            socket = new Socket(LOCALHOST, PORT);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger.info("Client connected to the server");
        return socket;
    }

    public PrintWriter getPrintWriter() {
        return printWriter;
    }

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }
}
