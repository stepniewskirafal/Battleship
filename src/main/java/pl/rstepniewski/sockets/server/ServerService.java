package pl.rstepniewski.sockets.server;

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
    private final Server server;
    private Socket clientSocket;
    ServerSocket serverSocket;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;

    public ServerService(Server server) {
        this.server = server;
        startServer();
    }

    private void startServer(){
        try{
            startSocket();
            startBufferedStreams();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void startSocket() throws IOException{
        serverSocket = new ServerSocket(PORT);
        clientSocket = serverSocket.accept();
    }

    private void startBufferedStreams() throws IOException {
        bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
    }

    public PrintWriter getPrintWriter() {
        return printWriter;
    }

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }
}
